package com.example.dobrobytplus.service;

import com.example.dobrobytplus.model.User;
import com.example.dobrobytplus.repository.OperationRepository;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
public class OperationSummaryService {
    private final OperationRepository operationRepository;

    public OperationSummaryService(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    public Double getExpensesSumForMonth(User user, Integer year, Integer month){
        Date dateStart = new Calendar.Builder()
                .setDate(year, month, 1)
                .setTimeOfDay(0, 0, 0)
                .build().getTime();

        // TODO
        Date dateEnd = new Calendar.Builder()
                .setDate(year, month, 28)
                .setTimeOfDay(0, 0, 0)
                .build().getTime();

        return operationRepository.sumExpensesForUser(user,dateStart,dateEnd);
    }

    public Double getEarningsSumForMonth(User user, Integer year, Integer month){
        Date dateStart = new Calendar.Builder()
                .setDate(year, month, 1)
                .setTimeOfDay(0, 0, 0)
                .build().getTime();

        // TODO
        Date dateEnd = new Calendar.Builder()
                .setDate(year, month, 28)
                .setTimeOfDay(0, 0, 0)
                .build().getTime();

        return operationRepository.sumEarningsForUser(user,dateStart,dateEnd);
    }

    public Double getEstimateForMonth(User user, Integer year, Integer month)
    {
        // TODO
        Date dateStart = new Calendar.Builder()
                .setDate(year, Calendar.JANUARY, 1)
                .setTimeOfDay(0, 0, 0)
                .build().getTime();

        // TODO
        Date dateEnd = new Calendar.Builder()
                .setDate(year, month-1, 28)
                .setTimeOfDay(0, 0, 0)
                .build().getTime();

        var ops = operationRepository.findByUserAndTimeBetween(user,dateStart,dateEnd);

        // creating regression object, passing true to have intercept term
        SimpleRegression simpleRegression = new SimpleRegression(true);

        // passing data to the model
        // model will be fitted automatically by the class
        for(var op : ops) {
            simpleRegression.addData(op.getTime().getMonth(), op.getAmount());
        }

//        // querying for model parameters
//        System.out.println("slope = " + simpleRegression.getSlope());
//        System.out.println("intercept = " + simpleRegression.getIntercept());
//
//        // trying to run model for unknown data
//        System.out.println("prediction for 1.5 = " + simpleRegression.predict(month));



        Double estimate = simpleRegression.predict(month);
        return estimate;
    }
}
