package com.example.dobrobytplus.service;

import com.example.dobrobytplus.dto.HistoryDto;
import com.example.dobrobytplus.entities.History;
import com.example.dobrobytplus.repository.HistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.Date;

@AllArgsConstructor
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public List<HistoryDto> getHistoryForMonth(Date date) {
        int month = date.toLocalDate().getMonthValue();
        int year = date.toLocalDate().getYear();

        String dateStart = year + "-" + month + "-01";
        String dateEnd = year + "-" + month + "-31";

        List<History> historyForMonth = historyRepository.findByTimeBetween(Date.valueOf(dateStart), Date.valueOf(dateEnd));
        List<HistoryDto> historyForMonthDto = new ArrayList<>();
        for (History history : historyForMonth) {
            historyForMonthDto.add(new HistoryDto(history));
        }
        return historyForMonthDto;
    }

    public List<Date> getHistoryMonthsForAccount(Long idAccount) {
        List<History> historyList = historyRepository.findByAccount_IdAccounts(idAccount);
        Set<Date> monthsSet = new HashSet<>();

        for (History history : historyList) {
            Date time = history.getTime();
            String date = time.toLocalDate().getYear() + "-" + time.toLocalDate().getMonthValue() + "-01";
            monthsSet.add(Date.valueOf(date));
        }

        return new ArrayList<>(monthsSet);
    }
}
