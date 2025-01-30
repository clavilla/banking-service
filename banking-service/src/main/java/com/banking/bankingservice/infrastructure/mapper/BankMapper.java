package com.banking.bankingservice.infrastructure.mapper;

import com.banking.bankingservice.domain.model.Bank;
import com.banking.bankingservice.infrastructure.dto.BankDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankMapper {

    BankDTO bankToBankDTO(Bank bank);

    Bank bankDTOToBank(BankDTO bankDTO);
}
