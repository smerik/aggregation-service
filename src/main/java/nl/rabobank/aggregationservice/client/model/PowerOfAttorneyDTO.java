package nl.rabobank.aggregationservice.client.model;

import lombok.Data;
import nl.rabobank.aggregationservice.client.model.card.CardReferenceDTO;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

@Data
public class PowerOfAttorneyDTO {

    private String id;
    private String grantor;
    private String grantee;
    private String account;
    private Direction direction;
    private EnumSet<Authorization> authorizations = EnumSet.noneOf(Authorization.class);
    private Set<CardReferenceDTO> cards = Collections.emptySet();
}
