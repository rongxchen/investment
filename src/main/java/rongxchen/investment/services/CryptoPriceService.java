package rongxchen.investment.services;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rongxchen.investment.mappers.CryptoPriceMapper;
import rongxchen.investment.models.po.CryptoPrice;
import rongxchen.investment.models.vo.market_data.CandleStickVO;
import rongxchen.investment.models.vo.market_data.PriceDataVO;
import rongxchen.investment.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoPriceService {

    private final CryptoPriceMapper cryptoPriceMapper;

    public PriceDataVO getCryptoPrice(String ticker, String interval, int size) {
        LambdaQueryWrapper<CryptoPrice> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CryptoPrice::getTicker, ticker);
        wrapper.eq(CryptoPrice::getInterval, interval);
        wrapper.orderByAsc(CryptoPrice::getDatetime);
        wrapper.last("limit " + size);
        // query from db
        PriceDataVO vo = new PriceDataVO();
        List<CryptoPrice> priceList = cryptoPriceMapper.selectList(wrapper);
        if (CollUtil.isEmpty(priceList)) {
            vo.setPriceList(new ArrayList<>());
            return vo;
        }
        // convert to price data
        vo.setTicker(priceList.get(0).getTicker());
        List<CandleStickVO> candleStickList = new ArrayList<>();
        for (CryptoPrice price : priceList) {
            CandleStickVO candleStick = new CandleStickVO();
            candleStick.setOpen(price.getOpen());
            candleStick.setHigh(price.getHigh());
            candleStick.setLow(price.getLow());
            candleStick.setClose(price.getClose());
            candleStick.setVolume(price.getVolume());
            candleStick.setDatetime(price.getDatetime());
            candleStick.setTimestamp(DateUtil.toMillis(price.getDatetime()));
            candleStickList.add(candleStick);
        }
        vo.setPriceList(candleStickList);
        return vo;
    }

}
