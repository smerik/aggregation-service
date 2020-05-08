package nl.rabobank.aggregationservice.mapper;

import nl.rabobank.aggregationservice.client.model.PowerOfAttorneyDTO;
import nl.rabobank.aggregationservice.model.PowerOfAttorney;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PowerOfAttorneyMapper {

    @Mapping(target = "account", ignore = true)
    @Mapping(target = "cards", ignore = true)
    PowerOfAttorney mapToPowerOfAttorney(PowerOfAttorneyDTO powerOfAttorney);
}
