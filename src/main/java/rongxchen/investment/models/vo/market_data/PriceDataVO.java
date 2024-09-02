package rongxchen.investment.models.vo.market_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PriceDataVO {

    private String ticker;

    private String market;

    @JsonProperty("items")
    private List<CandleStickVO> priceList;

}
