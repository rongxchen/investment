package rongxchen.investment.models.vo.market_data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CandleStickVO {

    private Double open;

    private Double high;

    private Double low;

    private Double close;

    private Double volume;

    private Long timestamp;

    private LocalDateTime datetime;

}
