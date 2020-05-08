package nl.rabobank.aggregationservice.model.card;

import lombok.Data;

@Data
public class TransactionLimit {

    private int limit;
    private PeriodUnit periodUnit;
}
