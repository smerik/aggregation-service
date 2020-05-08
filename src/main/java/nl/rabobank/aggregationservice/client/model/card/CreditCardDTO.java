package nl.rabobank.aggregationservice.client.model.card;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class CreditCardDTO extends AbstractCardDTO {

    private Long monthlyLimit;
}
