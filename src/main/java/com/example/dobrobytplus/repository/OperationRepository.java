package com.example.dobrobytplus.repository;

import com.example.dobrobytplus.entities.Operation;
import com.example.dobrobytplus.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;


public interface OperationRepository extends JpaRepository<Operation, Long> {

    List<Operation> findByUserUsername(String username);

    List<Operation> findByUser(User user);

    List<Operation> findByUserAndTimeBetween(User user, Date time, Date time2);

    List<Operation> findOperationsByUserAndTimeBetweenAndAmountGreaterThan(User user, Date time, Date time2, Double amount);

    List<Operation> findOperationsByUserAndTimeBetweenAndAmountLessThan(User user, Date time, Date time2, Double amount);

    @Query("SELECT sum(o.amount) from Operation o WHERE o.amount > 0 and o.user = :user and o.time BETWEEN :time_start AND :time_end")
    Double sumEarningsForUser(@Param("user") User user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);

    @Query("SELECT sum(o.amount) from Operation o WHERE o.amount < 0 and o.user = :user and o.time BETWEEN :time_start AND :time_end")
    Double sumExpensesForUser(@Param("user") User user, @Param("time_start") Date time_start, @Param("time_end") Date time_end);

}