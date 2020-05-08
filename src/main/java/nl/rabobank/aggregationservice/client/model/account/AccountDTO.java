package nl.rabobank.aggregationservice.client.model.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountDTO {

    private static final String DATE_PATTERN = "dd-MM-yyyy";

    private String owner;
    private Long balance;

    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDate created;

    @JsonFormat(pattern = DATE_PATTERN)
    private LocalDate ended;
}
