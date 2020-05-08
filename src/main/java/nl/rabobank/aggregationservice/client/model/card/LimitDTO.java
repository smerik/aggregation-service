package nl.rabobank.aggregationservice.client.model.card;

import lombok.Data;

@Data
public class LimitDTO {

    private int limit;
    private PeriodUnit periodUnit;
}
