package rongxchen.investment.enums;

import lombok.Getter;

@Getter
public enum Market {

    US("US"),

    HK("HK");

    Market(String market) {
        this.market = market;
    }

    private final String market;

}
