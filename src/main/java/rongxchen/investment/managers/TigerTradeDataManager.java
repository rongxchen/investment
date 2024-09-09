package rongxchen.investment.managers;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import rongxchen.investment.enums.Interval;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Log
@RequiredArgsConstructor
public class TigerTradeDataManager {

    private Integer retryCount = 0;

    private final Map<String, String> TIGER_INTERVAL_MAPPING = new HashMap<>();

    private final ObjectMapper objectMapper;

    {
        TIGER_INTERVAL_MAPPING.put(Interval.ONE_DAY.getInterval(), "day");
        TIGER_INTERVAL_MAPPING.put(Interval.ONE_WEEK.getInterval(), "week");
        TIGER_INTERVAL_MAPPING.put(Interval.ONE_MONTH.getInterval(), "month");
    }

    private boolean obtainToken() {
        String url = Constants.TIGER_TRADE_FOR_TOKEN_URL;
        try {
            String body = Jsoup.connect(url)
                    .get().html();
            Pattern tokenPattern = Pattern.compile("\"access_token\":\"(.*?)\"");
            Matcher matcher = tokenPattern.matcher(body);
            if (matcher.find()) {
                Constants.TIGER_TRADE_KLINE_TOKEN = matcher.group(1);
                log.info("Obtained TigerTrade token: " + Constants.TIGER_TRADE_KLINE_TOKEN);
                return true;
            }
        } catch (IOException e) {
            log.warning(e.getMessage());
        }
        return false;
    }

    public void syncEquityPrice(String ticker, String market, String interval) {
        if (!TIGER_INTERVAL_MAPPING.containsKey(interval)) {
            log.warning("invalid interval mapping for TigerTrade mapping: " + interval);
            return;
        }
        String tigerTradeInterval = TIGER_INTERVAL_MAPPING.get(interval);
        long timestamp = System.currentTimeMillis();
        market = market.toLowerCase();
        String url = "hk".equals(market) ? String.format(Constants.TIGER_TRADE_KLINE_URL_HK, tigerTradeInterval, ticker, timestamp)
                : ("us".equals(market) ? String.format(Constants.TIGER_TRADE_KLINE_URL_US, tigerTradeInterval, ticker, timestamp)
                : "");
        try {
            String body = Jsoup.connect(url)
                    .userAgent(Constants.HTTP_USER_AGENT)
                    .ignoreContentType(true)
                    .header("Authorization", "Bearer " + Constants.TIGER_TRADE_KLINE_TOKEN)
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
            if (this.retryCount == 0 && this.obtainToken()) {
                this.syncEquityPrice(ticker, market, interval);
                this.retryCount++;
            }
            log.warning("failed to connect to TigerTrade API");
            log.warning("detailed exception message: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        TigerTradeDataManager manager = new TigerTradeDataManager(new ObjectMapper());
        manager.syncEquityPrice("NVDA", "us", "1d");
    }

}
