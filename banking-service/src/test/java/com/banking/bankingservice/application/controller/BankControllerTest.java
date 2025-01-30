package com.banking.bankingservice.application.controller;

import com.banking.bankingservice.application.service.BankService;
import com.banking.bankingservice.infrastructure.dto.BankDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BankControllerTest {

    @Mock
    private BankService bankService;

    @Mock
    private RestTemplate restTemplate;

    private BankController bankController;

    private BankDTO bankDTO;
    private UUID bankId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bankController = new BankController(bankService, restTemplate);
        bankId = UUID.randomUUID();
        bankDTO = new BankDTO();
        bankDTO.setId(bankId);
        bankDTO.setName("Test Bank");
    }

    @Test
    void getAllBanks() {
        when(bankService.findAll()).thenReturn(List.of(bankDTO));

        List<BankDTO> result = bankController.getAllBanks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bankDTO, result.get(0));
    }

    @Test
    void findBankById() {
        when(bankService.findById(bankId)).thenReturn(Optional.of(bankDTO));

        ResponseEntity<BankDTO> result = bankController.findBankById(bankId);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(bankDTO, result.getBody());
    }

    @Test
    void findBankByIdNotFound() {
        when(bankService.findById(bankId)).thenReturn(Optional.empty());

        ResponseEntity<BankDTO> result = bankController.findBankById(bankId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void findBankByName() {
        when(bankService.findByName("Test Bank")).thenReturn(Optional.of(bankDTO));

        ResponseEntity<BankDTO> result = bankController.findBankByName("Test Bank");

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(bankDTO, result.getBody());
    }

    @Test
    void findBankByNameNotFound() {
        when(bankService.findByName("Test Bank")).thenReturn(Optional.empty());

        ResponseEntity<BankDTO> result = bankController.findBankByName("Test Bank");

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void createBank() {
        when(bankService.create(any(BankDTO.class))).thenReturn(bankDTO);

        ResponseEntity<BankDTO> result = bankController.createBank(bankDTO);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(bankDTO, result.getBody());
    }

    @Test
    void updateBank() {
        when(bankService.update(any(UUID.class), any(BankDTO.class))).thenReturn(bankDTO);

        ResponseEntity<BankDTO> result = bankController.updateBank(bankId, bankDTO);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(bankDTO, result.getBody());
    }

    @Test
    void deleteBank() {
        doNothing().when(bankService).deleteById(bankId);

        ResponseEntity<Void> result = bankController.deleteBank(bankId);

        assertEquals(HttpStatus.NO_CONTENT, result.getStatusCode());
        verify(bankService, times(1)).deleteById(bankId);
    }

    @Test
    void consumeInternal() {
        String url = "http://localhost:8080/api/banks/getAllBanks";
        when(restTemplate.exchange(eq(url), eq(HttpMethod.GET), isNull(), any(ParameterizedTypeReference.class)))
                .thenReturn(new ResponseEntity<>(List.of(bankDTO), HttpStatus.OK));

        List<BankDTO> result = bankController.consumeInternal();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bankDTO, result.get(0));
    }
}