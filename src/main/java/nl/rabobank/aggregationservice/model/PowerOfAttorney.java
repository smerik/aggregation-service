package nl.rabobank.aggregationservice.model;

import lombok.Data;
import nl.rabobank.aggregationservice.model.account.Account;
import nl.rabobank.aggregationservice.model.card.AbstractCard;

import java.util.EnumSet;
import java.util.Set;

@Data
public class PowerOfAttorney {

    /**
     * The person giving the property.
     */
    private String grantor;

    /**
     * the person receiving the property.
     */
    private String grantee;

    private Account account;
    private Direction direction;
    private EnumSet<Authorization> authorizations;
    private Set<AbstractCard> cards;
}
