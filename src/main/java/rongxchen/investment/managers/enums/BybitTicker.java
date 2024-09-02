package rongxchen.investment.managers.enums;

import lombok.Getter;

@Getter
public enum BybitTicker {
    BTC_USDT("BTCUSDT"),
    BTC_USDC("BTCUSDC"),
    ETH_USDT("ETHUSDT"),
    ETH_USDC("ETHUSDC"),
    MNT_USDT("MNTUSDT"),
    TON_USDT("TONUSDT"),
    SOL_USDT("SOLUSDT"),
    WIF_USDT("WIFUSDT"),
    AVAX_USDT("AVAXUSDT"),
    PEPE_USDT("PEPEUSDT"),
    XRP_USDT("XRPUSDT"),
    NEAR_USDT("NEARUSDT"),
    ADA_USDT("ADAUSDT"),
    DOGS_USDT("DOGSUSDT"),
    USDC_USDT("USDCUSDT"),
    DOGE_USDT("DOGEUSDT");

    BybitTicker(String ticker) {
        this.ticker = ticker;
    }
    private final String ticker;
}
