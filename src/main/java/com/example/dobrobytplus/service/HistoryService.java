package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.HistoryDto;
import com.example.dobrobytplus.entities.Accounts;
import com.example.dobrobytplus.entities.History;
import com.example.dobrobytplus.exceptions.AccountNotFoundException;
import com.example.dobrobytplus.repository.AccountsRepository;
import com.example.dobrobytplus.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.sql.Date;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final AccountsRepository accountsRepository;

    public List<HistoryDto> getHistoryForMonth(Long accountId,String yearMonth) {
//        int month = date.toLocalDate().getMonthValue();
//        int year = date.toLocalDate().getYear();
//
//        String dateStart = year + "-" + month + "-01";
//        String dateEnd = year + "-" + month + "-31";
//
//        List<History> historyForMonth = historyRepository.findByTimeBetween(Date.valueOf(dateStart), Date.valueOf(dateEnd));
//        List<HistoryDto> historyForMonthDto = new ArrayList<>();
//        for (History history : historyForMonth) {
//            historyForMonthDto.add(new HistoryDto(history));
//        }
        int year = Integer.parseInt(yearMonth.split("-")[0]);
        int month = Integer.parseInt(yearMonth.split("-")[1]);
        Accounts account = accountsRepository.findByIdAccounts(accountId);
        if (account == null) {
            throw new AccountNotFoundException();
        }

        List<History>  hist = historyRepository.findTransactionsbyAccountAndMonth(account,year,month);
        if (hist.size()<1) {
            return new ArrayList<HistoryDto>();
        }

        return hist.stream().map(HistoryDto::new).collect(Collectors.toList());
    }

    public List<String> getHistoryMonthsForAccount(Long idAccount) {
        Accounts account = accountsRepository.findByIdAccounts(idAccount);
        List<String> historyList = historyRepository.dates(account);
        // usuwam obecny miesiac
        Date today = new Date(System.currentTimeMillis());
        int month = today.getMonth();
        int year = today.getYear();
        String todayStr = Integer.toString(year) + "-" + Integer.toString(month);
        historyList.remove(todayStr);
        return historyList;

//        Set<Date> monthsSet = new HashSet<>();


//        for (History history : historyList) {
//            Date time = history.getTime();
//            String date = time.toLocalDate().getYear() + "-" + time.toLocalDate().getMonthValue() + "-01";
//            monthsSet.add(Date.valueOf(date));
//        }
//
//        return new ArrayList<>(monthsSet);
    }
}
