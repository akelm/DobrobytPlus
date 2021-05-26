package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UsersRepository extends JpaRepository<Users, Long> {

    @Query(value = "SELECT hibernate_sequence.next_val FROM hibernate_sequence", nativeQuery = true)
    Long getNextSeriesId();

    Users findByUsername(String username);


}