package com.banking.bankingservice.application.controller;

import com.banking.bankingservice.application.service.BankService;
import com.banking.bankingservice.infrastructure.dto.BankDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@Tag(name = "Bank API")
@RequestMapping("/api/banks")
public class BankController {

    private final BankService bankService;
    private final RestTemplate restTemplate;

    @Autowired
    public BankController(BankService bankService, RestTemplate restTemplate) {
        this.bankService = bankService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/getAllBanks")
    public List<BankDTO> getAllBanks() {
        return bankService.findAll();
    }

    @Operation(
            summary = "Get a bank by id",
            description = "Get a bank by its unique identifier")
    @GetMapping("/findBankById/{id}")
    public ResponseEntity<BankDTO> findBankById(@PathVariable UUID id) {
        return bankService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Get a bank by name",
            description = "Get a bank by its unique name")
    @GetMapping("/findBankByName/{name}")
    public ResponseEntity<BankDTO> findBankByName(@PathVariable String name) {
        return bankService.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Create a new bank",
            description = "Create a new bank with the provided information")
    @PostMapping("/createBank")
    public ResponseEntity<BankDTO> createBank(@RequestBody BankDTO bankDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bankService.create(bankDTO));
    }

    @Operation(
            summary = "Update a bank",
            description = "Update a bank with the provided information")
    @PutMapping("/updateBank/{id}")
    public ResponseEntity<BankDTO> updateBank(@PathVariable UUID id, @RequestBody BankDTO bankDTO) {
        return ResponseEntity.ok(bankService.update(id, bankDTO));
    }

    @Operation(
            summary = "Delete a bank",
            description = "Delete a bank by its unique identifier")
    @DeleteMapping("/deleteBank/{id}")
    public ResponseEntity<Void> deleteBank(@PathVariable UUID id) {
        bankService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Consume own service",
            description = "Consume the getAllBanks endpoint internally")
    @GetMapping("/consumeInternal")
    public List<BankDTO> consumeInternal() {
        String url = "http://localhost:8080/api/banks/getAllBanks";
        ResponseEntity<List<BankDTO>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BankDTO>>() {}
        );
        return response.getBody();
    }
}
