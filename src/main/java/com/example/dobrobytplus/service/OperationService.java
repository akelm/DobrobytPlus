package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.OperationDto;
import com.example.dobrobytplus.dto.UserDto;
import com.example.dobrobytplus.repository.OperationRepository;
import com.example.dobrobytplus.security.MyUserPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class OperationService {
    private final OperationRepository operationRepository;

    public List<OperationDto> getUserOperations(String username) {
        return operationRepository.findByUserUsername(username)
                .stream()
                .map(OperationDto::new)
                .collect(Collectors.toList());
    }

    public List<OperationDto> getAuthenticatedUserOperations() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((MyUserPrincipal) principal).getUsername();
        return this.getUserOperations(username);
    }



}
