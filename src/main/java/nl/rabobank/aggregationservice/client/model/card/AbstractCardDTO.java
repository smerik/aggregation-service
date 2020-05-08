package nl.rabobank.aggregationservice.client.model.card;

import lombok.Data;

@Data
public abstract class AbstractCardDTO {

    private String id;
    private Status status;
    private Integer cardNumber;
    private Integer sequenceNumber;
    private String cardHolder;
}
