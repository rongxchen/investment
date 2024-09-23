package rongxchen.investment.util;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class TradingDateUtil {

    private TradingDateUtil() {}

    public static LocalDate getLastTradingDate(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return switch (dayOfWeek) {
            case MONDAY -> date.minusDays(3);
            case SUNDAY -> date.minusDays(2);
            default -> date.minusDays(1);
        };
    }

}
