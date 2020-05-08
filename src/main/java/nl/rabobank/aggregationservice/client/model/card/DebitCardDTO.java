package nl.rabobank.aggregationservice.client.model.card;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DebitCardDTO extends AbstractCardDTO {

    private LimitDTO atmLimit;
    private LimitDTO posLimit;
    private boolean contactless;
}
