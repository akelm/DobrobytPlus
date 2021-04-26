package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.UserDto;
import com.example.dobrobytplus.entities.User;

/**
 * Ten interfejs jest tu tylko dlatego,
 * ze bedziemy robili uzytkownikow kilku typow.
 * Dla jednego typu bylby niepotrzebny.
 */
public interface IUserService {
    User registerNewUserAccount(UserDto userDto);
}