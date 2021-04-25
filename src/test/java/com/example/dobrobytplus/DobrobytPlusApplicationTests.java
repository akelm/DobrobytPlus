package com.example.dobrobytplus;

import com.example.dobrobytplus.dto.UserDto;
import com.example.dobrobytplus.model.Operation;
import com.example.dobrobytplus.model.User;
import com.example.dobrobytplus.repository.OperationRepository;
import com.example.dobrobytplus.repository.UserRepository;
import com.example.dobrobytplus.service.OperationSummaryService;
import com.example.dobrobytplus.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@SpringBootTest
class DobrobytPlusApplicationTests {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OperationRepository operationRepository;

    @Autowired
    OperationSummaryService operationSummaryService;

    public static DateFormat df = new SimpleDateFormat("dd-MM-yyyy");


    public static boolean testsInit = false;

    @Before
    public void prepareDB() throws ParseException {
        if (testsInit) return;

        testsInit = true;

        User aaa = userService.registerNewUserAccount(new UserDto("aaa", "aaa"));
        User bbb = userService.registerNewUserAccount(new UserDto("bbb", "bbb"));

        operationRepository.save(new Operation(100D, df.parse("01-01-2021"), aaa));
        operationRepository.save(new Operation(-100D, df.parse("02-01-2021"), aaa));
        operationRepository.save(new Operation(100D, df.parse("03-01-2021"), aaa));
        operationRepository.save(new Operation(-100D, df.parse("04-01-2021"), aaa));

        operationRepository.save(new Operation(100D, df.parse("01-01-2021"), bbb));
        operationRepository.save(new Operation(-100D, df.parse("02-01-2021"), bbb));
        operationRepository.save(new Operation(100D, df.parse("03-01-2021"), bbb));
        operationRepository.save(new Operation(-100D, df.parse("04-01-2021"), bbb));
    }

    @Test
    void contextLoads() {
    }

    @Test
    void opRepo() throws ParseException {
        var aaaOps = operationRepository.findByUserUsername("aaa");
        var bbbOps = operationRepository.findByUserUsername("bbb");

        assert aaaOps.size() == 4;
        assert bbbOps.size() == 4;

        var userA = userRepository.findByUsername("aaa");

        Double earnings = operationRepository.sumEarningsForUser(userA, df.parse("01-01-2021"), df.parse("04-01-2021"));
        assert earnings == 200D;

        var aaaOpsEarnings = operationRepository.findOperationsByUserAndTimeBetweenAndAmountGreaterThan(userA, df.parse("01-01-2021"), df.parse("04-01-2021"), 0D);
        var aaaOpsExpenses = operationRepository.findOperationsByUserAndTimeBetweenAndAmountGreaterThan(userA, df.parse("01-01-2021"), df.parse("04-01-2021"), 0D);

        assert aaaOpsEarnings.size() == 2;
        assert aaaOpsExpenses.size() == 2;
    }

    @Test
    void opService() {
        var userA = userRepository.findByUsername("aaa");

        Double earningsSumForMonth = operationSummaryService.getEarningsSumForMonth(userA, 2021, Calendar.JANUARY);
        Double expensesSumForMonth = operationSummaryService.getExpensesSumForMonth(userA, 2021, Calendar.JANUARY);

        assert earningsSumForMonth == 200D;
        assert expensesSumForMonth == -200D;

        // wszystko jest NaN, TODO
        Double estimate = operationSummaryService.getEstimateForMonth(userA, 2021, Calendar.MAY);
        assert estimate.equals(Double.NaN);

        Double estimate2 = operationSummaryService.getEstimateForMonth(userA, 2021, Calendar.FEBRUARY);
        Double estimate3 = operationSummaryService.getEstimateForMonth(userA, 2021, Calendar.MARCH);
        Double estimate4 = operationSummaryService.getEstimateForMonth(userA, 2021, Calendar.APRIL);

    }


}
