package tech.wvs.smartstockapp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tech.wvs.smartstockapp.client.dto.AuthRequest;
import tech.wvs.smartstockapp.client.dto.AuthResponse;

@FeignClient(name = "AuthClient", url = "${app.auth.url}")
public interface AuthClient {

    @PostMapping(path = "/api/token")
    ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request);
}
