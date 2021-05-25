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
    private final UsersRepository usersRepository;
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

//        user.setRoles(Arrays.asList("ROLE_USER"));#TODO


        return usersRepository.save(users);
    }

    private boolean usernameExists(String username) {
        return usersRepository.findByUsername(username) != null;
    }

    // updatuje username, password, birthdate istniejacego usera w tabeli Users
    public void updateUserData(UsersDto userDto) {
        // userDto posiada dane ISTNIEJACEGO user w bazie danych
        // chcemy zmienić w bazie danych: zmienić/"nadpisać" username, password, data urodzenia ale nie ID
        Users user = usersRepository.findByUsername(userDto.getUsername());
        user.setUsername(userDto.getUsername());
        user.setPassword(enc.encode(userDto.getPassword()));
        user.setBirthdate(userDto.getBirthdate());

//        user.setRoles(Arrays.asList("ROLE_USER"));#TODO


        //return usersRepository.update(user); <= funkcja 'update' moze??
    }


}