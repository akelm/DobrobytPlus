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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
    public void moveToHistory() throws ParseException {
        log.info("Moving CurrentTransactions and Dispositions to History...");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //przeniesienie danych z CurrentTransactions do History
        var transactions = currentTransactionsRepository.findAll();
        for (CurrentTransactions ct : transactions) {
            historyRepository.save(new History(ct.getValue(), ct.getTime(), ct.getDescription(), ct.getAccount(), ct.getUser()));
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        String date = year + "-" + month + "-" + dayOfMonth;

        //przeniesienie danych z AutoDispositions do History
        var autodispositions = autoDispositionsRepository.findAll();
        for (AutoDispositions d : autodispositions) {
            historyRepository.save(new History(d.getValue(), df.parse(date), d.getDescription(), d.getAccount(), d.getUser()));
        }

        //przeniesienie danych z Dispositions do History
        var dispositions = dispositionsRepository.findAll();
        for (Dispositions d : dispositions) {
            historyRepository.save(new History(d.getValue(), df.parse(date), d.getDescription(), d.getAccount(), d.getUser()));
        }


        currentTransactionsRepository.deleteAll();

        log.info("Moving CurrentTransactions and Dispositions to History - SUCCESS");
    }
}
