package rongxchen.investment.controllers;

import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rongxchen.investment.enums.Market;
import rongxchen.investment.models.Response;
import rongxchen.investment.models.vo.market_data.PriceDataVO;
import rongxchen.investment.services.CryptoPriceService;
import rongxchen.investment.services.EquityPriceService;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final CryptoPriceService cryptoPriceService;

    private final EquityPriceService equityPriceService;

    @GetMapping("/crypto")
    public Response<PriceDataVO> getCryptoPrice(@RequestParam(defaultValue = "") String ticker,
                                                @RequestParam(defaultValue = "") String interval,
                                                @RequestParam(defaultValue = "100") @Max(500) int size) {
        PriceDataVO cryptoPrice = cryptoPriceService.getCryptoPrice(ticker, interval, size);
        return Response.success(cryptoPrice);
    }

    @GetMapping("/equity")
    public Response<PriceDataVO> getEquityPrice(@RequestParam(defaultValue = "") String ticker,
                                                @RequestParam Market market,
                                                @RequestParam(defaultValue = "") String interval,
                                                @RequestParam(defaultValue = "100") @Max(500) int size) {
        PriceDataVO equityPrice = equityPriceService.getEquityPriceList(ticker, market, interval, size);
        return Response.success(equityPrice);
    }

}
