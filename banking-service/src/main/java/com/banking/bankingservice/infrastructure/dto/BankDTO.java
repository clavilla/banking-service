package com.banking.bankingservice.infrastructure.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankDTO {
    private UUID id;
    private String name;
    private String address;
    private String phone;
    private String email;

}
