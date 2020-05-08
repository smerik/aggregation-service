package nl.rabobank.aggregationservice.model.card;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.rabobank.aggregationservice.client.model.card.CardType;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditCard extends AbstractCard {

    private TransactionLimit limit;

    @Override
    public CardType getCardType() {
        return CardType.CREDIT_CARD;
    }
}
