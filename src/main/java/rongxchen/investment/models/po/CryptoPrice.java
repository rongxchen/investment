package rongxchen.investment.models.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("crypto_prices")
@Data
public class CryptoPrice {

    @TableId(type = IdType.AUTO)
    private Long id;

    private String ticker;

    private String market;

    private LocalDateTime datetime;

    private Double open;

    private Double high;

    private Double low;

    private Double close;

    private Double volume;

    @TableField("trading_currency")
    private String tradingCurrency;

    @TableField("`interval`")
    private String interval;

    private String source;

    @TableField("update_time")
    private LocalDate updateTime;

}
