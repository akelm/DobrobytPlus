package com.example.dobrobytplus.service;

import com.example.dobrobytplus.exceptions.UserAlreadyExistException;
import com.example.dobrobytplus.dto.UserDto;
import com.example.dobrobytplus.entities.User;
import com.example.dobrobytplus.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class UserService implements IUserService {
    //@Autowired
    private final UserRepository repository;
    private final PasswordEncoder enc;


    @Override
    public User registerNewUserAccount(UserDto userDto) throws UserAlreadyExistException {
        if (usernameExists(userDto.getUsername())) {
            throw new UserAlreadyExistException("There is an account with that getName getName: "
                    + userDto.getUsername());
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(enc.encode(userDto.getPassword()));
        //user.setBirthday(userDto.getBirthdate());
//        user.setRoles(Arrays.asList("ROLE_USER"));#TODO

         // bedziemy tez zbierac plec i date urodzenia


        return repository.save(user);
    }

    private boolean usernameExists(String username) {
        return repository.findByUsername(username) != null;
    }
}