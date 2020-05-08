package nl.rabobank.aggregationservice.mapper.card;

import nl.rabobank.aggregationservice.client.model.card.CreditCardDTO;
import nl.rabobank.aggregationservice.client.model.card.DebitCardDTO;
import nl.rabobank.aggregationservice.model.card.CreditCard;
import nl.rabobank.aggregationservice.model.card.DebitCard;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardMapper {

    @Mapping(target = "limit", ignore = true)
    @Mapping(source = "monthlyLimit", target = "limit.limit")
    @Mapping(constant = "PER_MONTH", target = "limit.periodUnit")
    CreditCard mapToCreditCard(CreditCardDTO card);

    DebitCard mapToDebitCard(DebitCardDTO card);
}
