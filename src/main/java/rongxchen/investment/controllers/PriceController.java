package rongxchen.investment.controllers;

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
    public Response<PriceDataVO> getCryptoPrice(@RequestParam String ticker,
                                                @RequestParam String interval,
                                                @RequestParam int size) {
        PriceDataVO cryptoPrice = cryptoPriceService.getCryptoPrice(ticker, interval, size);
        return Response.success(cryptoPrice);
    }

}
