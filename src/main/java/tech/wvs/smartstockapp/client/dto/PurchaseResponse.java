package tech.wvs.smartstockapp.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PurchaseResponse(@JsonProperty("message") String message) {
}
