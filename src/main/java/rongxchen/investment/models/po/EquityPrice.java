package rongxchen.investment.models.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@TableName("equity_prices")
@Data
public class EquityPrice {

    @TableId(type = IdType.AUTO)
    private Long id;

    @TableField("company_name")
    private String companyName;

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
