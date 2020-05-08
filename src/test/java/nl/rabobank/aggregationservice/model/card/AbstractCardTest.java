package nl.rabobank.aggregationservice.model.card;

import nl.rabobank.aggregationservice.client.model.card.Status;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AbstractCardTest {

    private static Stream<Arguments> provideSourceForIsActive() {
        return Stream.of(
                Arguments.of(Status.ACTIVE, true),
                Arguments.of(Status.BLOCKED, false)
        );
    }

    @ParameterizedTest
    @MethodSource("provideSourceForIsActive")
    void isActive(final Status status,
                  final boolean isActive) {

        final AbstractCard result = Mockito.mock(AbstractCard.class, Mockito.CALLS_REAL_METHODS);
        result.setStatus(status);

        assertEquals(isActive, result.isActive());
    }
}