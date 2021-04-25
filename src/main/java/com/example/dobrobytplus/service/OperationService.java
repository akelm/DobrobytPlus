package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.OperationDto;
import com.example.dobrobytplus.repository.OperationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OperationService {
    private final OperationRepository operationRepository;

    public List<OperationDto> getUserOperations(String username) {
        return operationRepository.findByUserUsername(username).stream().map(OperationDto::new).collect(Collectors.toList());
    }


}
