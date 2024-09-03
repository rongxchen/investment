package rongxchen.investment.enums;

import lombok.Getter;

@Getter
public enum EquitySource {

    TIGER_TRADE("TigerTrade");

    EquitySource(String source) {
        this.source = source;
    }
    private final String source;

}
