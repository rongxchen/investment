package rongxchen.investment.services;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import rongxchen.investment.enums.Market;
import rongxchen.investment.exceptions.DataException;
import rongxchen.investment.managers.TigerTradeDataManager;
import rongxchen.investment.mappers.EquityPriceMapper;
import rongxchen.investment.models.po.EquityPrice;
import rongxchen.investment.models.vo.market_data.CandleStickVO;
import rongxchen.investment.models.vo.market_data.PriceDataVO;
import rongxchen.investment.util.DateUtil;
import rongxchen.investment.util.TradingDateUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EquityPriceService {

    private Integer retryCount = 0;

    private final EquityPriceMapper equityPriceMapper;

    private final TigerTradeDataManager tigerTradeDataManager;

    public PriceDataVO getEquityPriceList(String ticker, Market market, String interval, int size) {
        LambdaQueryWrapper<EquityPrice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EquityPrice::getTicker, ticker);
        queryWrapper.eq(EquityPrice::getInterval, interval);
        queryWrapper.orderByDesc(EquityPrice::getDatetime);
        queryWrapper.last("limit " + size);
        // find equity price list
        List<EquityPrice> equityPriceList = equityPriceMapper.selectList(queryWrapper);
        LocalDateTime latestDatetime = equityPriceMapper.findLatestDatetime(ticker, market.getMarket(), interval);
        if (CollUtil.isEmpty(equityPriceList) || !TradingDateUtil.getLastTradingDate(LocalDate.now()).equals(latestDatetime.toLocalDate())) {
            if (this.retryCount != 0) {
                throw new DataException("no price data found for " + ticker);
            }
            this.retryCount++;
            tigerTradeDataManager.syncEquityPrice(ticker, market, interval);
            return this.getEquityPriceList(ticker, market, interval, size);
        } else {
            if (this.retryCount == 1) {
                this.retryCount--;
            }
        }
        PriceDataVO vo = new PriceDataVO();
        vo.setTicker(ticker);
        vo.setMarket(equityPriceList.get(0).getMarket());
        List<CandleStickVO> candleStickList = new ArrayList<>();
        for (EquityPrice price : equityPriceList) {
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
