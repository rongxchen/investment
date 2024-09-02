package rongxchen.investment.managers;

import cn.hutool.json.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;
import rongxchen.investment.enums.CryptoSource;
import rongxchen.investment.mappers.CryptoPriceMapper;
import rongxchen.investment.models.po.CryptoPrice;
import rongxchen.investment.util.DateUtil;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Log
@RequiredArgsConstructor
public class BybitDataManager {

    private final ObjectMapper objectMapper;

    private final CryptoPriceMapper cryptoPriceMapper;

    public void syncCryptoPrice(String ticker, String interval, int size) {
        String url = String.format(Constants.BYBIT_KLINE_URL, ticker, interval, size, System.currentTimeMillis());
        try {
            String body = Jsoup.connect(url)
                    .userAgent(Constants.HTTP_USER_AGENT)
                    .ignoreContentType(true)
                    .execute().body();
            JSONObject entries = objectMapper.readValue(body, JSONObject.class);
            boolean isResultExist = entries.containsKey("result");
            if (!isResultExist) {
                log.warning("Bybit returns no result for ticker: " + ticker);
                return;
            }
            // get result, convert and insert into db
            List<JSONObject> result = entries.getBeanList("result", JSONObject.class);
            List<CryptoPrice> cryptoPriceList = new ArrayList<>();
            for (JSONObject object : result) {
                CryptoPrice price = new CryptoPrice();
                price.setTicker(ticker);
                price.setMarket(null);
                price.setDatetime(DateUtil.fromMillis(object.getLong("t")));
                price.setOpen(object.getDouble("o"));
                price.setHigh(object.getDouble("h"));
                price.setLow(object.getDouble("l"));
                price.setClose(object.getDouble("c"));
                price.setVolume(object.getDouble("v"));
                price.setTradingCurrency(null);
                price.setInterval(interval);
                price.setSource(CryptoSource.BYBIT.getSource());
                price.setUpdateTime(LocalDate.now());
                cryptoPriceList.add(price);
                if (cryptoPriceList.size() == Constants.MYBATIS_BATCH_SAVE_SIZE) {
                    cryptoPriceMapper.insertOrUpdateBatch(cryptoPriceList);
                    cryptoPriceList.clear();
                }
            }
            if (!cryptoPriceList.isEmpty()) {
                cryptoPriceMapper.insertOrUpdateBatch(cryptoPriceList);
                cryptoPriceList.clear();
            }
        } catch (IOException e) {
            log.warning("error occurred during fetching data from Bybit");
            log.warning("detailed exception message: " + e.getMessage());
        }
    }

}
