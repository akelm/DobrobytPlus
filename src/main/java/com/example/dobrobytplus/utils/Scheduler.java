package com.example.dobrobytplus.utils;

import com.example.dobrobytplus.entities.AutoDispositions;
import com.example.dobrobytplus.entities.CurrentTransactions;
import com.example.dobrobytplus.entities.Dispositions;
import com.example.dobrobytplus.entities.History;
import com.example.dobrobytplus.repository.AutoDispositionsRepository;
import com.example.dobrobytplus.repository.CurrentTransactionsRepository;
import com.example.dobrobytplus.repository.DispositionsRepository;
import com.example.dobrobytplus.repository.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.config.ScheduledTask;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

/**
 *  Scheduler.
 */
@Component
public class Scheduler {
    private static final Logger log = LoggerFactory.getLogger(ScheduledTask.class);

    @Autowired
    private CurrentTransactionsRepository currentTransactionsRepository;

    @Autowired
    private DispositionsRepository dispositionsRepository;

    @Autowired
    private AutoDispositionsRepository autoDispositionsRepository;

    @Autowired
    private HistoryRepository historyRepository;

    /**
     * Move to history.
     */
    @Scheduled(cron = "0 0 0 1 * ?")
    public void moveToHistory() {
        log.info("Moving CurrentTransactions and Dispositions to History...");

        historyRepository.moveToHistoryCurrentTransactions();
        currentTransactionsRepository.deleteAll();

        Date date = Date.valueOf(new Date(System.currentTimeMillis()).toLocalDate().plusMonths(-1));

        historyRepository.moveToHistoryDispositions(date);
        historyRepository.moveToHistoryAutoDispositions(date);


        log.info("Moving CurrentTransactions and Dispositions to History - SUCCESS");
    }
}
