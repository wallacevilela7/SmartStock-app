package tech.wvs.smartstockapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import tech.wvs.smartstockapp.client.dto.PurchaseRequest;
import tech.wvs.smartstockapp.client.dto.PurchaseResponse;

@FeignClient(name = "PurchaseSectorClient", url = "${app.auth.url}")
public interface PurchaseSectorClient {

    @PostMapping(path = "/api/purchases")
    ResponseEntity<PurchaseResponse> purchase(@RequestHeader("Authorization") String token,
                                              @RequestBody PurchaseRequest request);
}
