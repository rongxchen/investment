package rongxchen.investment.controllers;

import jakarta.validation.constraints.Max;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rongxchen.investment.models.Response;
import rongxchen.investment.models.vo.market_data.PriceDataVO;
import rongxchen.investment.services.CryptoPriceService;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class PriceController {

    private final CryptoPriceService cryptoPriceService;

    @GetMapping("/crypto")
    public Response<PriceDataVO> getCryptoPrice(@RequestParam(defaultValue = "") String ticker,
                                                @RequestParam(defaultValue = "") String interval,
                                                @RequestParam(defaultValue = "100") @Max(500) int size) {
        PriceDataVO cryptoPrice = cryptoPriceService.getCryptoPrice(ticker, interval, size);
        return Response.success(cryptoPrice);
    }

}
