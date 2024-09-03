package rongxchen.investment.managers;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import rongxchen.investment.enums.Interval;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log
@RequiredArgsConstructor
public class TigerTradeDataManager {

    private final Map<String, String> TIGER_INTERVAL_MAPPING = new HashMap<>();

    private final ObjectMapper objectMapper;

    {
        TIGER_INTERVAL_MAPPING.put(Interval.ONE_DAY.getInterval(), "day");
        TIGER_INTERVAL_MAPPING.put(Interval.ONE_WEEK.getInterval(), "week");
        TIGER_INTERVAL_MAPPING.put(Interval.ONE_MONTH.getInterval(), "month");
    }

    public void syncEquityPrice(String ticker, String market, String interval) {
        if (!TIGER_INTERVAL_MAPPING.containsKey(interval)) {
            log.warning("invalid interval mapping for TigerTrade mapping: " + interval);
            return;
        }
        interval = TIGER_INTERVAL_MAPPING.get(interval);
        long timestamp = System.currentTimeMillis();
        market = market.toLowerCase();
        String url = "hk".equals(market) ? String.format(Constants.TIGER_TRADE_KLINE_URL_HK, interval, ticker, timestamp)
                : ("us".equals(market) ? String.format(Constants.TIGER_TRADE_KLINE_URL_US, interval, ticker, timestamp)
                : "");
        try {
            String body = Jsoup.connect(url)
                    .userAgent(Constants.HTTP_USER_AGENT)
                    .ignoreContentType(true)
                    .header("Authorization", "Bearer " + "eyJhbGciOiJFUzI1NiIsImtpZCI6IjVVQzB5NGhnUXUiLCJ0eXAiOiJKV1QifQ.eyJleHAiOjE3Mjc1NDQyMDUsImlzcyI6IkNITiIsIm5vbmNlIjoiV1RjRnZPOHE0UTdjb3FSTXI1OUFvZ0o0V0R5U3F2ZWZLMzJZNGFwQnJEVkR3bUdvalEifQ.C26m6z_dRInnfoIDf5o9utgqHoFxVWYvTQpF-BgJJEHFZoUc-BGiTi5wnY4o7URu_7vXy3unz3IVFsVxPvkoFA")
                    .execute().body();
            JSONObject entries = objectMapper.readValue(body, JSONObject.class);
            boolean isItemsPresent = entries.containsKey("items");
            if (!isItemsPresent) {
                log.warning("TigerTrade returns no items for ticker: " + ticker);
                return;
            }
            List<JSONObject> items = entries.getBeanList("items", JSONObject.class);
            for (JSONObject item : items) {
                System.out.println(item);
            }
        } catch (IOException e) {
            log.warning("failed to connect to TigerTrade API");
            log.warning("detailed exception message: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TigerTradeDataManager manager = new TigerTradeDataManager(new ObjectMapper());
        manager.syncEquityPrice("NVDA", "us", "1d");
    }

}
