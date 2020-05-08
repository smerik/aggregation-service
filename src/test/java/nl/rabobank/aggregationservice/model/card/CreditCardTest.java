package nl.rabobank.aggregationservice.model.card;

import nl.rabobank.aggregationservice.client.model.card.CardType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditCardTest {

    @Test
    void getCardType() {
        final CreditCard result = new CreditCard();

        assertEquals(CardType.CREDIT_CARD, result.getCardType());
    }
}