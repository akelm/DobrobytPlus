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

    @Scheduled(cron = "0 0 0 1 * ?")
    public void moveToHistory() {
        log.info("Moving CurrentTransactions and Dispositions to History...");

        //przeniesienie danych z CurrentTransactions do History
        var transactions = currentTransactionsRepository.findAll();
        for (CurrentTransactions ct : transactions) {
            historyRepository.save(new History(ct.getValue(), ct.getTime(), ct.getDescription(), ct.getAccount(), ct.getUser()));
        }

        Date date = Date.valueOf(new Date(System.currentTimeMillis()).toLocalDate().plusMonths(-1));

        //przeniesienie danych z AutoDispositions do History
        var autodispositions = autoDispositionsRepository.findAll();
        for (AutoDispositions d : autodispositions) {
            historyRepository.save(new History(d.getValue(), date, d.getDescription(), d.getAccount(), d.getUser()));
        }

        //przeniesienie danych z Dispositions do History
        var dispositions = dispositionsRepository.findAll();
        for (Dispositions d : dispositions) {
            historyRepository.save(new History(d.getValue(), date, d.getDescription(), d.getAccount(), d.getUser()));
        }


        currentTransactionsRepository.deleteAll();

        log.info("Moving CurrentTransactions and Dispositions to History - SUCCESS");
    }
}
