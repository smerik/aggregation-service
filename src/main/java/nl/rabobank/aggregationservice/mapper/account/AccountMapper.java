package nl.rabobank.aggregationservice.mapper.account;

import nl.rabobank.aggregationservice.client.model.account.AccountDTO;
import nl.rabobank.aggregationservice.model.account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "iban", ignore = true)
    Account mapToAccount(AccountDTO account);
}
