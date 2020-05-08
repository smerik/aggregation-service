package nl.rabobank.aggregationservice.mapper;

import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyDTO;
import nl.rabobank.aggregationservice.helper.PowerOfAttorneyDTOTestHelper;
import nl.rabobank.aggregationservice.model.Direction;
import nl.rabobank.aggregationservice.model.PowerOfAttorney;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PowerOfAttorneyMapperTest {

    private static final PowerOfAttorneyMapper MAPPER = new PowerOfAttorneyMapperImpl();

    @Test
    void mapToPowerOfAttorney() {
        // Given
        final PowerOfAttorneyDTO powerOfAttorney = PowerOfAttorneyDTOTestHelper.createDefaultPowerOfAttorney();
        // When
        final PowerOfAttorney result = MAPPER.mapToPowerOfAttorney(powerOfAttorney);
        // Then
        assertSame("Super duper company", result.getGrantor());
        assertSame("Fellowship of the ring", result.getGrantee());
        assertNull(result.getAccount(), "Account mapping should be ignored");
        assertNull(result.getCards(), "Cards mapping should be ignored");
        assertEquals(Direction.GIVEN, result.getDirection());
    }
}