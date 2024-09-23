package rongxchen.investment.managers;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import rongxchen.investment.enums.EquitySource;
import rongxchen.investment.enums.Interval;
import rongxchen.investment.enums.Market;
import rongxchen.investment.mappers.EquityPriceMapper;
import rongxchen.investment.models.po.EquityPrice;
import rongxchen.investment.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private final EquityPriceMapper equityPriceMapper;

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

    public void syncEquityPrice(String ticker, Market market, String interval) {
        if (!TIGER_INTERVAL_MAPPING.containsKey(interval)) {
            log.warning("invalid interval mapping for TigerTrade mapping: " + interval);
            return;
        }
        String tigerTradeInterval = TIGER_INTERVAL_MAPPING.get(interval);
        long timestamp = System.currentTimeMillis();
        String marketString = market.getMarket().toLowerCase();
        String url = "hk".equals(marketString) ? String.format(Constants.TIGER_TRADE_KLINE_URL_HK, tigerTradeInterval, ticker, timestamp)
                : ("us".equals(marketString) ? String.format(Constants.TIGER_TRADE_KLINE_URL_US, tigerTradeInterval, ticker, timestamp)
                : "");
        try {
            String body = Jsoup.connect(url)
                    .userAgent(Constants.HTTP_USER_AGENT)
                    .ignoreContentType(true)
                    .header("Authorization", "Bearer " + Constants.TIGER_TRADE_KLINE_TOKEN)
                    .execute().body();
            JSONObject entries = objectMapper.readValue(body, JSONObject.class);
            boolean isDetailPresent = entries.containsKey("detail");
            boolean isItemsPresent = entries.containsKey("items");
            if (!isItemsPresent || !isDetailPresent) {
                log.warning("TigerTrade returns no items for ticker: " + ticker);
                return;
            }
            JSONObject detail = entries.getBean("detail", JSONObject.class);
            List<JSONObject> items = entries.getBeanList("items", JSONObject.class);
            List<EquityPrice> equityPriceList = new ArrayList<>();
            for (JSONObject item : items) {
                LocalDateTime dateTime = DateUtil.fromMillis(item.getLong("time"));
                EquityPrice equityPrice = new EquityPrice();
                equityPrice.setCompanyName(detail.getStr("nameCN"));
                equityPrice.setTicker(entries.getStr("symbol"));
                equityPrice.setMarket(detail.getStr("market"));
                equityPrice.setDatetime(dateTime);
                equityPrice.setOpen(item.getDouble("open"));
                equityPrice.setHigh(item.getDouble("high"));
                equityPrice.setLow(item.getDouble("low"));
                equityPrice.setClose(item.getDouble("close"));
                equityPrice.setVolume(item.getDouble("volume"));
                equityPrice.setInterval(interval);
                equityPrice.setSource(EquitySource.TIGER_TRADE.getSource());
                equityPrice.setUpdateTime(LocalDate.now());
                equityPriceList.add(equityPrice);
                if (equityPriceList.size() == Constants.MYBATIS_BATCH_SAVE_SIZE) {
                    equityPriceMapper.insertOrUpdateBatch(equityPriceList);
                    equityPriceList.clear();
                }
            }
            if (!equityPriceList.isEmpty()) {
                equityPriceMapper.insertOrUpdateBatch(equityPriceList);
                equityPriceList.clear();
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

}
