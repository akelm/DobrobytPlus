package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


/**
 * The interface Users repository.
 */
public interface UsersRepository extends JpaRepository<Users, Long> {

    /**
     * Gets next series id.
     *
     * @return the next series id
     */
    @Query(value = "SELECT hibernate_sequence.next_val FROM hibernate_sequence", nativeQuery = true)
    Long getNextSeriesId();

    /**
     * Find by username .
     *
     * @param username the username
     * @return the users
     */
    Users findByUsername(String username);

}