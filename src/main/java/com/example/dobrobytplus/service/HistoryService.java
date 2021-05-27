package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.HistoryDto;
import com.example.dobrobytplus.dto.MonthsDto;
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

/**
 * The type History service.
 */
@AllArgsConstructor
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final AccountsRepository accountsRepository;

    /**
     * Gets history for month.
     *
     * @param accountId the account id
     * @param yearMonth the year month
     * @return the history for month
     */
    public List<HistoryDto> getHistoryForMonth(Long accountId,String yearMonth) {
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


    /**
     * Gets history months for account.
     *
     * @param idAccount the id account
     * @return the history months for account
     */
    public List<MonthsDto> getHistoryMonthsForAccount(Long idAccount) {
        Accounts account = accountsRepository.findByIdAccounts(idAccount);
        List<String> historyList = historyRepository.monthsFromHistory(account);
        // usuwam obecny miesiac
        Date today = new Date(System.currentTimeMillis());
        int month = today.getMonth();
        int year = today.getYear();
        String todayStr = Integer.toString(year) + "-" + Integer.toString(month);
        historyList.remove(todayStr);

        historyList = historyList.stream()
                .map(x -> (x.contains("-")) ? x : "0000-00")
                .map(x -> x.split("-"))
                .map(
                        x -> Arrays.stream(x).map( y -> (y.length()<2)? "0"+y : y  ).collect(Collectors.toList())
                )
                .map(x -> String.join("-",x))
        .collect(Collectors.toList());

        java.util.Collections.sort(historyList);

        List<Double> plnList = historyList.stream().map(x -> x.split("-"))
                .map(
                        x -> Arrays.stream(x).map(Integer::parseInt).collect(Collectors.toList())
                )
                        .map(x -> (x.size() !=2)? List.of(0, 0)  :x   )
                        .map(x-> historyRepository.sumTransactionsbyAccountAndMonth(account, x.get(0), x.get(1)))
                        .map(x -> (x == null) ? 0D : x).collect(Collectors.toList());

        List<Double> sasinList = plnList.stream().map(x -> x/70D).collect(Collectors.toList());

        int listLen = Math.min(plnList.size(), historyList.size());
        List<MonthsDto> monthsDtos = new ArrayList<>();
        for (int i=0; i<listLen;i++) {
            MonthsDto m = new MonthsDto(plnList.get(i), sasinList.get(i), historyList.get(i));
            monthsDtos.add(m);
        }

        return monthsDtos;
    }

    /**
     * Sum for month double.
     *
     * @param month     the month
     * @param accountId the account id
     * @return the double
     */
    public Double sumForMonth(String month, Long accountId) {
        String[] data = month.split("-");
        Accounts account = accountsRepository.findByIdAccounts(accountId);
        Double saldo =0D;
        if (data.length==2) {
            int m = Integer.parseInt( data[1]);
            int y = Integer.parseInt( data[0]);
            saldo = historyRepository.sumTransactionsbyAccountAndMonth(account,y,m);
            saldo = (saldo == null) ? 0D : saldo;
        }
        return saldo;
    }

    /**
     * Pln to mikro sasin double.
     *
     * @param pln the pln
     * @return the double
     */
    public Double plnToMikroSasin(Double pln) {
        return pln/70D;

    }

}
