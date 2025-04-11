package tech.wvs.smartstockapp.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record AuthResponse(@JsonProperty("access_token") String accessToken,
                           @JsonProperty("expires_in") Integer expiresIn) {
}
