package rongxchen.investment.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import rongxchen.investment.models.po.CryptoPrice;

import java.util.List;

@Repository
public interface CryptoPriceMapper extends BaseMapper<CryptoPrice> {

    void insertOrUpdateBatch(List<CryptoPrice> itemList);

}
