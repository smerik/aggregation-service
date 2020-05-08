package nl.rabobank.aggregationservice.helper;

import nl.rabobank.aggregationservice.client.model.Authorization;
import nl.rabobank.aggregationservice.client.model.Direction;
import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyDTO;
import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyReferenceDTO;
import nl.rabobank.aggregationservice.client.model.account.AccountDTO;
import nl.rabobank.aggregationservice.client.model.card.*;

import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Set;

public final class PowerOfAttorneyDTOTestHelper {

    private PowerOfAttorneyDTOTestHelper() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static PowerOfAttorneyReferenceDTO createPowerOfAttorneyReference(final String id) {
        final PowerOfAttorneyReferenceDTO result = new PowerOfAttorneyReferenceDTO();
        result.setId(id);
        return result;
    }

    public static PowerOfAttorneyDTO createDefaultPowerOfAttorney() {
        final PowerOfAttorneyDTO result = new PowerOfAttorneyDTO();
        result.setId("0001");
        result.setGrantor("Super duper company");
        result.setGrantee("Fellowship of the ring");
        result.setAccount("NL23RABO123456789");
        result.setDirection(Direction.GIVEN);
        result.setAuthorizations(EnumSet.allOf(Authorization.class));
        result.setCards(Set.of(
                createCardReference("1111", CardType.DEBIT_CARD),
                createCardReference("4444", CardType.CREDIT_CARD)
        ));
        return result;
    }

    public static CardReferenceDTO createCardReference(final String id, final CardType type) {
        final CardReferenceDTO result = new CardReferenceDTO();
        result.setId(id);
        result.setType(type);
        return result;
    }

    public static AccountDTO createDefaultAccount() {
        final AccountDTO result = new AccountDTO();
        result.setOwner("Super duper company");
        result.setBalance(750L);
        result.setCreated(LocalDate.of(2017, 10, 12));
        return result;
    }



    public static CreditCardDTO createDefaultCreditCard() {
        final CreditCardDTO result = new CreditCardDTO();
        result.setId("3333");
        result.setStatus(Status.ACTIVE);
        result.setCardNumber(5075);
        result.setSequenceNumber(1);
        result.setCardHolder("Boromir");
        result.setMonthlyLimit(3000L);
        return result;
    }

    public static DebitCardDTO createDefaultDebitCard() {
        final DebitCardDTO result = new DebitCardDTO();
        result.setId("1111");
        result.setStatus(Status.ACTIVE);
        result.setCardNumber(1234);
        result.setSequenceNumber(5);
        result.setCardHolder("Frodo Basggins");
        result.setAtmLimit(createLimit(3000, PeriodUnit.PER_WEEK));
        result.setPosLimit(createLimit(50, PeriodUnit.PER_MONTH));
        result.setContactless(true);
        return result;
    }

    public static LimitDTO createLimit(final int limit, final PeriodUnit periodUnit) {
        final LimitDTO result = new LimitDTO();
        result.setLimit(limit);
        result.setPeriodUnit(periodUnit);
        return result;
    }
}
