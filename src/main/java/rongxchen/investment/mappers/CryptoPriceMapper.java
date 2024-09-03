package rongxchen.investment.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import rongxchen.investment.models.po.CryptoPrice;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CryptoPriceMapper extends BaseMapper<CryptoPrice> {

    void insertOrUpdateBatch(List<CryptoPrice> itemList);

    @Select("select datetime from crypto_prices " +
            "where ticker = #{ticker} and `interval` = #{interval} " +
            "order by datetime desc limit 1")
    LocalDateTime findLatestDatetime(String ticker, String interval);

}
