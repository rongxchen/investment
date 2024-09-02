package rongxchen.investment.managers;

public class Constants {

    public static String HTTP_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/127.0.0.0 Safari/537.36";

    public static Integer MYBATIS_BATCH_SAVE_SIZE = 100;

    /**
     * bybit related constants
     */
    public static String BYBIT_KLINE_URL = "https://api2.bybit.com/spot/api/quote/v2/klines?symbol=%s&interval=%s&limit=%d&to=%d";

}
