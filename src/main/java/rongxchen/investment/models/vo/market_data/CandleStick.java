package rongxchen.investment.models.vo.market_data;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CandleStick {

    @JsonProperty("o")
    private Integer open;

    @JsonProperty("h")
    private Integer high;

    @JsonProperty("l")
    private Integer low;

    @JsonProperty("c")
    private Integer close;

    @JsonProperty("v")
    private Long volume;

    @JsonProperty("t")
    private Long timestamp;

}
