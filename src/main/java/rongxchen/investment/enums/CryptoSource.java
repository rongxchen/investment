package rongxchen.investment.enums;

import lombok.Getter;

@Getter
public enum CryptoSource {

    BYBIT("Bybit");

    CryptoSource(String source) {
        this.source = source;
    }
    private final String source;

}
