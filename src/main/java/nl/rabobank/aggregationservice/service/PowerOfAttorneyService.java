package nl.rabobank.aggregationservice.service;

import lombok.extern.slf4j.Slf4j;
import nl.rabobank.aggregationservice.client.PowerOfAttorneyServiceClient;
import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyDTO;
import nl.rabobank.aggregationservice.client.model.card.CardReferenceDTO;
import nl.rabobank.aggregationservice.mapper.PowerOfAttorneyMapper;
import nl.rabobank.aggregationservice.mapper.account.AccountMapper;
import nl.rabobank.aggregationservice.mapper.card.CardMapper;
import nl.rabobank.aggregationservice.model.PowerOfAttorney;
import nl.rabobank.aggregationservice.model.account.Account;
import nl.rabobank.aggregationservice.model.card.AbstractCard;
import nl.rabobank.aggregationservice.util.AccountUtil;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PowerOfAttorneyService {

    private final PowerOfAttorneyServiceClient client;
    private final PowerOfAttorneyMapper powerOfAttorneyMapper;
    private final AccountMapper accountMapper;
    private final CardMapper cardMapper;

    public PowerOfAttorneyService(final PowerOfAttorneyServiceClient client,
                                  final PowerOfAttorneyMapper powerOfAttorneyMapper,
                                  final AccountMapper accountMapper,
                                  final CardMapper cardMapper) {
        this.client = client;
        this.powerOfAttorneyMapper = powerOfAttorneyMapper;
        this.accountMapper = accountMapper;
        this.cardMapper = cardMapper;
    }

    public List<PowerOfAttorney> getPowerOfAttorneys() {
        return Arrays.stream(client.getPowerOfAttorneys())
                .map(powerOfAttorneyReference -> getPowerOfAttorney(powerOfAttorneyReference.getId()))
                .collect(Collectors.toList());
    }

    public PowerOfAttorney getPowerOfAttorney(final String id) {
        final PowerOfAttorneyDTO clientPOA = client.getPowerOfAttorney(id);

        final PowerOfAttorney result = powerOfAttorneyMapper.mapToPowerOfAttorney(clientPOA);
        result.setAccount(mapAccount(clientPOA));
        result.setCards(mapCards(clientPOA));

        return result;
    }

    private Account mapAccount(final PowerOfAttorneyDTO clientPOA) {
        final String accountNumber = AccountUtil.extractAccountNumber(clientPOA.getAccount());
        final Account account = accountMapper.mapToAccount(client.getAccount(accountNumber));
        account.setIban(clientPOA.getAccount());

        if (!account.isAuthorized(clientPOA.getGrantor())) {
            LOG.debug("Grantor '{}' is not allowed to see details for {}", clientPOA.getGrantee(), account);
            return null;
        }
        if (!account.isActive()) {
            LOG.debug("Do not show inactive {}", account);
            return null;
        }
        return account;
    }

    private Set<AbstractCard> mapCards(final PowerOfAttorneyDTO clientPOA) {
        return clientPOA.getCards().stream()
                .map(this::mapCard)
                .filter(card -> isCardAllowedToBeShown(clientPOA, card))
                .collect(Collectors.toSet());
    }

    private AbstractCard mapCard(final CardReferenceDTO cardReferenceDTO) {
        switch (cardReferenceDTO.getType()) {
            case CREDIT_CARD:
                return cardMapper.mapToCreditCard(client.getCreditCard(cardReferenceDTO.getId()));
            case DEBIT_CARD:
                return cardMapper.mapToDebitCard(client.getDebitCard(cardReferenceDTO.getId()));
            default:
                throw new UnsupportedOperationException(
                        "Card mapper not implemented for type '" + cardReferenceDTO.getType() + "'.");
        }
    }

    private boolean isCardAllowedToBeShown(final PowerOfAttorneyDTO clientPOA, final AbstractCard card) {
        if (clientPOA.getAuthorizations().stream()
                .noneMatch(authorization -> authorization.name().equals(card.getCardType().name()))) {
            LOG.debug("Not authorized for {}", card);
            return false;
        }
        if (!card.isActive()) {
            LOG.debug("Inactive {}", card);
            return false;
        }
        return true;
    }
}
