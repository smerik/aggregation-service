package nl.rabobank.aggregationservice.model.card;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nl.rabobank.aggregationservice.client.model.card.CardType;

@Data
@EqualsAndHashCode(callSuper = true)
public class DebitCard extends AbstractCard {

    private TransactionLimit atmLimit;
    private TransactionLimit posLimit;
    private boolean contactless;

    @Override
    public CardType getCardType() {
        return CardType.DEBIT_CARD;
    }
}
