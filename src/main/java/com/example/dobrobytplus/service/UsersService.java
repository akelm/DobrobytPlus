package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.UsersDto;
import com.example.dobrobytplus.entities.Users;
import com.example.dobrobytplus.exceptions.UserAlreadyExistException;
import com.example.dobrobytplus.repository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UsersService {
    private final UsersRepository repository;
    private final PasswordEncoder enc;

    public Users registerNewUserAccount(UsersDto userDto) throws UserAlreadyExistException {
        if (usernameExists(userDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that getName getName: "
                    + userDto.getUsername());
        }

        Users users = new Users();
        users.setUsername(userDto.getUsername());
        users.setPassword(enc.encode(userDto.getPassword()));
        users.setBirthdate(userDto.getBirthdate());
//        user.setEmail(userDto.getEmail());#TODO
//        user.setRoles(Arrays.asList("ROLE_USER"));#TODO

        // bedziemy tez zbierac plec i date urodzenia


        return repository.save(users);
    }

    private boolean usernameExists(String username) {
        return repository.findByUsername(username) != null;
    }
}