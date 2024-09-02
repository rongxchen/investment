package rongxchen.investment.enums;

import lombok.Getter;

@Getter
public enum Interval {

    ONE_MINUTE("1m"),
    ONE_HOUR("1h"),
    ONE_DAY("1d"),
    ONE_WEEK("1w"),
    ONE_MONTH("1M");

    Interval(String interval) {
        this.interval = interval;
    }
    private final String interval;

}
