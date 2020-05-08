package nl.rabobank.aggregationservice.model.card;

import nl.rabobank.aggregationservice.client.model.card.CardType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DebitCardTest {

    @Test
    void getCardType() {
        final DebitCard result = new DebitCard();

        assertEquals(CardType.DEBIT_CARD, result.getCardType());
    }
}