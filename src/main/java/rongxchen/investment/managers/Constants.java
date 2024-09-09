package rongxchen.investment.managers;

public class Constants {

    public static String HTTP_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36";

    public static Integer MYBATIS_BATCH_SAVE_SIZE = 100;

    /**
     * bybit related constants
     */
    public static String BYBIT_KLINE_URL = "https://api2.bybit.com/spot/api/quote/v2/klines?symbol=%s&interval=%s&limit=%d&to=%d";

    /**
     * tiger trade related constants
     */
    public static String TIGER_TRADE_FOR_TOKEN_URL = "https://www.laohu8.com/quotes";

    public static String TIGER_TRADE_KLINE_URL_US = "https://hq.laohu8.com/stock_info/candle_stick/%s/%s?_s=%d&lang=zh_CN&lang_content=cn&region=HKG&appVer=4.26.3&appName=laohu8&vendor=web&platform=web&edition=full&delay=true&manualRefresh=true";

    public static String TIGER_TRADE_KLINE_URL_HK = "https://hq.laohu8.com/hkstock/stock_info/candle_stick/%s/%s?_s=%d&lang=zh_CN&lang_content=cn&region=HKG&appVer=4.26.3&appName=laohu8&vendor=web&platform=web&edition=full&delay=true&manualRefresh=true";

    public static String TIGER_TRADE_KLINE_TOKEN = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjVVQzB5NGhnUXUiLCJ0eXAiOiJKV1QifQ.eyJleHAiOjE3Mjg0MDgyMDUsImlzcyI6IkNITiIsIm5vbmNlIjoiM3RVOWNOcHdoMUtmb1Bsd0hmWXhpdURMblYxYWZ5ejY4ZTNxRzAxNHVLdFR6Rk9nOHEifQ.Ti2_TFfGx4d6sTYveEBpDdbAMaANVppnHN1Cam9yLuEs8caD38h2M6wUlnJ6d-xwTD31A7OGPYebsmyBZwRLDA";

}
