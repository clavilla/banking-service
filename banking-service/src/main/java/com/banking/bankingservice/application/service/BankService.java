package com.banking.bankingservice.application.service;

import com.banking.bankingservice.domain.repository.BankEntityRepository;
import com.banking.bankingservice.infrastructure.dto.BankDTO;
import com.banking.bankingservice.infrastructure.mapper.BankMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BankService {
    private final BankEntityRepository repository;
    private final BankMapper mapper;

    public BankService(BankEntityRepository repository, BankMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<BankDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(mapper::bankToBankDTO)
                .collect(Collectors.toList());
    }

    public Optional<BankDTO> findById(UUID id) {
        return repository.findById(id).map(mapper::bankToBankDTO);
    }

    public Optional<BankDTO> findByName(String name) {
        return repository.findByName(name).map(mapper::bankToBankDTO);
    }

    public BankDTO create(BankDTO bankDTO) {
        repository.findByName(bankDTO.getName()).ifPresent(existing -> {
            throw new IllegalArgumentException("Duplicate entity with name: " + bankDTO.getName());
        });
        var entity = mapper.bankDTOToBank(bankDTO);
        return mapper.bankToBankDTO(repository.save(entity));
    }

    public BankDTO update(UUID id, BankDTO bankDTO) {
        var entity = repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Entity not found"));
        entity.setName(bankDTO.getName());
        entity.setAddress(bankDTO.getAddress());
        entity.setPhone(bankDTO.getPhone());
        entity.setEmail(bankDTO.getEmail());
        return mapper.bankToBankDTO(repository.save(entity));
    }

    public void deleteById(UUID id) {
        repository.deleteById(id);
    }
}
