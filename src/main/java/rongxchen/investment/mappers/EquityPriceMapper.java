package rongxchen.investment.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import rongxchen.investment.models.po.EquityPrice;

import java.time.LocalDateTime;
import java.util.List;

public interface EquityPriceMapper extends BaseMapper<EquityPrice> {

    void insertOrUpdateBatch(List<EquityPrice> itemList);

    @Select("select datetime from equity_prices " +
            "where ticker = #{ticker} and market = #{market} and `interval` = #{interval} " +
            "order by datetime desc limit 1")
    LocalDateTime findLatestDatetime(String ticker, String market, String interval);

}
