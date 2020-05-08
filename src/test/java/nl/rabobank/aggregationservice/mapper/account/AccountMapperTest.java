package nl.rabobank.aggregationservice.mapper.account;

import nl.rabobank.aggregationservice.client.model.account.AccountDTO;
import nl.rabobank.aggregationservice.helper.PowerOfAttorneyDTOTestHelper;
import nl.rabobank.aggregationservice.model.account.Account;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountMapperTest {

    private static final AccountMapper MAPPER = new AccountMapperImpl();

    @Test
    void mapToAccount() {
        // Given
        final AccountDTO accountDTO = PowerOfAttorneyDTOTestHelper.createDefaultAccount();
        // When
        final Account result = MAPPER.mapToAccount(accountDTO);
        // Then
        assertSame("Super duper company", result.getOwner());
        assertEquals(750L, result.getBalance());
        assertEquals(LocalDate.of(2017, 10, 12), result.getCreated());
    }
}