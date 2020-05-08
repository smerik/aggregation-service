package nl.rabobank.aggregationservice.mapper.card;

import nl.rabobank.aggregationservice.client.model.card.CreditCardDTO;
import nl.rabobank.aggregationservice.client.model.card.DebitCardDTO;
import nl.rabobank.aggregationservice.client.model.card.Status;
import nl.rabobank.aggregationservice.helper.PowerOfAttorneyDTOTestHelper;
import nl.rabobank.aggregationservice.model.card.CreditCard;
import nl.rabobank.aggregationservice.model.card.DebitCard;
import nl.rabobank.aggregationservice.model.card.PeriodUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardMapperTest {

    private static final CardMapper MAPPER = new CardMapperImpl();

    @Test
    void mapToCreditCard() {
        // Given
        final CreditCardDTO creditCardDTO = PowerOfAttorneyDTOTestHelper.createDefaultCreditCard();
        // When
        final CreditCard result = MAPPER.mapToCreditCard(creditCardDTO);
        // Then
        assertEquals(Status.ACTIVE, result.getStatus());
        assertEquals(5075, result.getCardNumber());
        assertEquals(1, result.getSequenceNumber());
        assertSame("Boromir", result.getCardHolder());

        assertEquals(3000, result.getLimit().getLimit());
        assertEquals(PeriodUnit.PER_MONTH, result.getLimit().getPeriodUnit());
    }

    @Test
    void mapToDebitCard() {
        // Given
        final DebitCardDTO debitCardDTO = PowerOfAttorneyDTOTestHelper.createDefaultDebitCard();
        // When
        final DebitCard result = MAPPER.mapToDebitCard(debitCardDTO);
        // Then
        assertEquals(Status.ACTIVE, result.getStatus());
        assertEquals(1234, result.getCardNumber());
        assertEquals(5, result.getSequenceNumber());
        assertSame("Frodo Basggins", result.getCardHolder());

        assertEquals(3000, result.getAtmLimit().getLimit());
        assertEquals(PeriodUnit.PER_WEEK, result.getAtmLimit().getPeriodUnit());
        assertEquals(50, result.getPosLimit().getLimit());
        assertEquals(PeriodUnit.PER_MONTH, result.getPosLimit().getPeriodUnit());
        assertTrue(result.isContactless());
    }
}