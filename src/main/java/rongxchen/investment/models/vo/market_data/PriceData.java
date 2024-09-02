package rongxchen.investment.models.vo.market_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PriceData {

    private String ticker;

    private String productType;

    private String productSubType;

    private String market;

    @JsonProperty("items")
    private List<CandleStick> priceList;

}
