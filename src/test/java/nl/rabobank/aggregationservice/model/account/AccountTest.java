package nl.rabobank.aggregationservice.model.account;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountTest {

    private static Stream<Arguments> provideSourceForIsActive() {
        return Stream.of(
                Arguments.of(null, true),
                Arguments.of(LocalDate.now().minusDays(1), false),
                Arguments.of(LocalDate.now(), false),
                Arguments.of(LocalDate.now().plusDays(1), true)
        );
    }

    private static Stream<Arguments> provideSourceForIsAuthorized() {
        return Stream.of(
                Arguments.of("Anita", "Anita", true),
                Arguments.of("Anita", "Bob", false),
                Arguments.of("Anita", null, false),
                Arguments.of(null, null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSourceForIsActive")
    void isActive(final LocalDate endedAt, final boolean isActive) {
        final Account result = new Account();
        result.setEnded(endedAt);

        assertEquals(isActive, result.isActive());
    }

    @ParameterizedTest
    @MethodSource("provideSourceForIsAuthorized")
    void isAuthorized(final String accountOwner,
                      final String owner,
                      final boolean isAuthorized) {

        final Account result = new Account();
        result.setOwner(accountOwner);

        assertEquals(isAuthorized, result.isAuthorized(owner));
    }
}