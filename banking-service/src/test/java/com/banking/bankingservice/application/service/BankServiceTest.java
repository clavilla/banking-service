package com.banking.bankingservice.application.service;

import com.banking.bankingservice.domain.repository.BankEntityRepository;
import com.banking.bankingservice.infrastructure.dto.BankDTO;
import com.banking.bankingservice.infrastructure.mapper.BankMapper;
import com.banking.bankingservice.domain.model.Bank;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BankServiceTest {

    @Mock
    private BankEntityRepository repository;

    @Mock
    private BankMapper mapper;

    @InjectMocks
    private BankService bankService;

    private BankDTO bankDTO;
    private Bank bankEntity;
    private UUID bankId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        bankId = UUID.randomUUID();
        bankDTO = new BankDTO();
        bankDTO.setId(bankId);
        bankDTO.setName("Test Bank");

        bankEntity = new Bank();
        bankEntity.setId(bankId);
        bankEntity.setName("Test Bank");
    }

    @Test
    void findAll() {
        when(repository.findAll()).thenReturn(List.of(bankEntity));
        when(mapper.bankToBankDTO(any(Bank.class))).thenReturn(bankDTO);

        List<BankDTO> result = bankService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(bankDTO, result.get(0));
    }

    @Test
    void findById() {
        when(repository.findById(bankId)).thenReturn(Optional.of(bankEntity));
        when(mapper.bankToBankDTO(any(Bank.class))).thenReturn(bankDTO);

        Optional<BankDTO> result = bankService.findById(bankId);

        assertTrue(result.isPresent());
        assertEquals(bankDTO, result.get());
    }

    @Test
    void findByIdNotFound() {
        when(repository.findById(bankId)).thenReturn(Optional.empty());

        Optional<BankDTO> result = bankService.findById(bankId);

        assertFalse(result.isPresent());
    }

    @Test
    void findByName() {
        when(repository.findByName("Test Bank")).thenReturn(Optional.of(bankEntity));
        when(mapper.bankToBankDTO(any(Bank.class))).thenReturn(bankDTO);

        Optional<BankDTO> result = bankService.findByName("Test Bank");

        assertTrue(result.isPresent());
        assertEquals(bankDTO, result.get());
    }

    @Test
    void findByNameNotFound() {
        when(repository.findByName("Test Bank")).thenReturn(Optional.empty());

        Optional<BankDTO> result = bankService.findByName("Test Bank");

        assertFalse(result.isPresent());
    }

    @Test
    void create() {
        when(repository.save(any(Bank.class))).thenReturn(bankEntity);
        when(mapper.bankDTOToBank(any(BankDTO.class))).thenReturn(bankEntity);
        when(mapper.bankToBankDTO(any(Bank.class))).thenReturn(bankDTO);

        BankDTO result = bankService.create(bankDTO);

        assertNotNull(result);
        assertEquals(bankDTO, result);
    }

    @Test
    void createDuplicate() {
        when(repository.findByName("Test Bank")).thenReturn(Optional.of(bankEntity));

        assertThrows(IllegalArgumentException.class, () -> bankService.create(bankDTO));
    }

    @Test
    void update() {
        when(repository.findById(bankId)).thenReturn(Optional.of(bankEntity));
        when(repository.save(any(Bank.class))).thenReturn(bankEntity);
        when(mapper.bankToBankDTO(any(Bank.class))).thenReturn(bankDTO);

        BankDTO result = bankService.update(bankId, bankDTO);

        assertNotNull(result);
        assertEquals(bankDTO, result);
    }

    @Test
    void updateNotFound() {
        when(repository.findById(bankId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> bankService.update(bankId, bankDTO));
    }

    @Test
    void deleteById() {
        doNothing().when(repository).deleteById(bankId);

        bankService.deleteById(bankId);

        verify(repository, times(1)).deleteById(bankId);
    }
}