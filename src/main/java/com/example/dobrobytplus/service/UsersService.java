package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.UsersDto;
import com.example.dobrobytplus.entities.Users;
import com.example.dobrobytplus.exceptions.UserAlreadyExistException;
import com.example.dobrobytplus.repository.UsersRepository;
import com.example.dobrobytplus.security.MyUsersPrincipal;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

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
        users.setPasswd(enc.encode(userDto.getPassword()));
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
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String oldUsername = ((MyUsersPrincipal) principal).getUsername();
        Users user = usersRepository.findByUsername(oldUsername);
        Users newUser = usersRepository.findByUsername(userDto.getUsername());

        if ( newUser != null && !Objects.equals(newUser.getId_users(), user.getId_users())) {
            throw new UserAlreadyExistException();
        }


        user.setUsername(userDto.getUsername());
        user.setPasswd(enc.encode(userDto.getPassword()));
        user.setBirthdate(userDto.getBirthdate());

        usersRepository.save(user);
    }


}