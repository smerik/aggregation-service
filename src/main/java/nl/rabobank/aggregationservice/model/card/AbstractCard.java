package nl.rabobank.aggregationservice.model.card;

import lombok.Data;
import nl.rabobank.aggregationservice.client.model.card.CardType;
import nl.rabobank.aggregationservice.client.model.card.Status;

@Data
public abstract class AbstractCard {

    private Status status;
    private Integer cardNumber;
    private Integer sequenceNumber;
    private String cardHolder;

    public abstract CardType getCardType();

    public boolean isActive() {
        return Status.ACTIVE.equals(status);
    }
}
