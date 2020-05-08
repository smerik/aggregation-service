package nl.rabobank.aggregationservice.service;

import nl.rabobank.aggregationservice.client.PowerOfAttorneyServiceClient;
import nl.rabobank.aggregationservice.client.model.Authorization;
import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyDTO;
import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyReferenceDTO;
import nl.rabobank.aggregationservice.client.model.account.AccountDTO;
import nl.rabobank.aggregationservice.client.model.card.CardType;
import nl.rabobank.aggregationservice.client.model.card.CreditCardDTO;
import nl.rabobank.aggregationservice.client.model.card.DebitCardDTO;
import nl.rabobank.aggregationservice.helper.PowerOfAttorneyDTOTestHelper;
import nl.rabobank.aggregationservice.mapper.PowerOfAttorneyMapper;
import nl.rabobank.aggregationservice.mapper.account.AccountMapper;
import nl.rabobank.aggregationservice.mapper.card.CardMapper;
import nl.rabobank.aggregationservice.model.PowerOfAttorney;
import nl.rabobank.aggregationservice.model.account.Account;
import nl.rabobank.aggregationservice.model.card.CreditCard;
import nl.rabobank.aggregationservice.model.card.DebitCard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.EnumSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PowerOfAttorneyServiceTest {

    @InjectMocks
    private PowerOfAttorneyService poaService;

    @Mock
    private PowerOfAttorneyServiceClient poaClient;
    @Mock
    private PowerOfAttorneyMapper powerOfAttorneyMapper;
    @Mock
    private AccountMapper accountMapper;
    @Mock
    private CardMapper cardMapper;

    @Mock
    private Account account;
    @Mock
    private CreditCard creditCard;
    @Mock
    private DebitCard debitCard;

    @BeforeEach
    void setup() {
        when(poaClient.getPowerOfAttorney(anyString())).thenReturn(PowerOfAttorneyDTOTestHelper.createDefaultPowerOfAttorney());
        when(poaClient.getAccount(anyString())).thenReturn(PowerOfAttorneyDTOTestHelper.createDefaultAccount());
        when(poaClient.getCreditCard(anyString())).thenReturn(PowerOfAttorneyDTOTestHelper.createDefaultCreditCard());
        when(poaClient.getDebitCard(anyString())).thenReturn(PowerOfAttorneyDTOTestHelper.createDefaultDebitCard());

        when(powerOfAttorneyMapper.mapToPowerOfAttorney(any(PowerOfAttorneyDTO.class))).thenReturn(new PowerOfAttorney());
        when(accountMapper.mapToAccount(any(AccountDTO.class))).thenReturn(account);
        when(cardMapper.mapToCreditCard(any(CreditCardDTO.class))).thenReturn(creditCard);
        when(cardMapper.mapToDebitCard(any(DebitCardDTO.class))).thenReturn(debitCard);

        lenient().when(account.isActive()).thenReturn(true);
        when(account.isAuthorized(anyString())).thenReturn(true);
        lenient().when(creditCard.isActive()).thenReturn(true);
        when(creditCard.getCardType()).thenReturn(CardType.CREDIT_CARD);
        when(debitCard.isActive()).thenReturn(true);
        when(debitCard.getCardType()).thenReturn(CardType.DEBIT_CARD);
    }

    @Test
    void getPowerOfAttorneys() {
        // Given
        final PowerOfAttorneyReferenceDTO[] poaReferenceDTOs = {
                PowerOfAttorneyDTOTestHelper.createPowerOfAttorneyReference("0001"),
                PowerOfAttorneyDTOTestHelper.createPowerOfAttorneyReference("0002")
        };
        when(poaClient.getPowerOfAttorneys()).thenReturn(poaReferenceDTOs);
        // When
        final List<PowerOfAttorney> result = poaService.getPowerOfAttorneys();
        // Then
        verify(poaClient, times(2)).getPowerOfAttorney(anyString());
        assertEquals(2, result.size());
    }

    @Test
    void getPowerOfAttorneyWithEverythingVisible() {
        // When
        final PowerOfAttorney result = poaService.getPowerOfAttorney("0001");
        // Then
        verify(poaClient).getPowerOfAttorney("0001");
        verify(poaClient).getAccount("123456789");
        verify(poaClient).getCreditCard("4444");
        verify(poaClient).getDebitCard("1111");

        assertNotNull(result.getAccount());
        verify(account).setIban("NL23RABO123456789");

        assertNotNull(result.getCards());
        assertEquals(2, result.getCards().size());
    }

    @Test
    void getPowerOfAttorneyWithUnauthorizedAccount() {
        // Given
        when(account.isAuthorized(anyString())).thenReturn(false);
        // When
        final PowerOfAttorney result = poaService.getPowerOfAttorney("0001");
        // Then
        verify(poaClient).getPowerOfAttorney("0001");
        verify(poaClient).getAccount("123456789");
        verify(poaClient).getCreditCard("4444");
        verify(poaClient).getDebitCard("1111");

        assertNull(result.getAccount());
    }

    @Test
    void getPowerOfAttorneyWithInactiveAccount() {
        // Given
        when(account.isActive()).thenReturn(false);
        // When
        final PowerOfAttorney result = poaService.getPowerOfAttorney("0001");
        // Then
        verify(poaClient).getPowerOfAttorney("0001");
        verify(poaClient).getAccount("123456789");
        verify(poaClient).getCreditCard("4444");
        verify(poaClient).getDebitCard("1111");

        assertNull(result.getAccount());
    }

    @Test
    void getPowerOfAttorneyWithUnauthorizedCard() {
        // Given
        final PowerOfAttorneyDTO powerOfAttorneyDTO = PowerOfAttorneyDTOTestHelper.createDefaultPowerOfAttorney();
        powerOfAttorneyDTO.setAuthorizations(EnumSet.of(Authorization.DEBIT_CARD));
        when(poaClient.getPowerOfAttorney(anyString())).thenReturn(powerOfAttorneyDTO);
        // When
        final PowerOfAttorney result = poaService.getPowerOfAttorney("0001");
        // Then
        verify(poaClient).getPowerOfAttorney("0001");
        verify(poaClient).getAccount("123456789");
        verify(poaClient).getCreditCard("4444");
        verify(poaClient).getDebitCard("1111");

        assertNotNull(result.getCards());
        assertEquals(1, result.getCards().size());
        assertTrue(result.getCards().iterator().next() instanceof DebitCard);
    }

    @Test
    void getPowerOfAttorneyWithInactiveCard() {
        // Given
        when(creditCard.isActive()).thenReturn(false);
        // When
        final PowerOfAttorney result = poaService.getPowerOfAttorney("0001");
        // Then
        verify(poaClient).getPowerOfAttorney("0001");
        verify(poaClient).getAccount("123456789");
        verify(poaClient).getCreditCard("4444");
        verify(poaClient).getDebitCard("1111");

        assertNotNull(result.getCards());
        assertEquals(1, result.getCards().size());
        assertTrue(result.getCards().iterator().next() instanceof DebitCard);
    }
}