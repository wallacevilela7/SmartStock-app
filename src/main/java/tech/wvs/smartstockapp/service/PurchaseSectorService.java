package tech.wvs.smartstockapp.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import tech.wvs.smartstockapp.client.PurchaseSectorClient;
import tech.wvs.smartstockapp.client.dto.PurchaseRequest;
import tech.wvs.smartstockapp.domain.CsvStockItem;

@Service
public class PurchaseSectorService {

    private static final Logger logger = LoggerFactory.getLogger(PurchaseSectorService.class);

    private final AuthService authService;
    private final PurchaseSectorClient purchaseSectorClient;

    public PurchaseSectorService(AuthService authService, PurchaseSectorClient purchaseSectorClient) {
        this.authService = authService;
        this.purchaseSectorClient = purchaseSectorClient;
    }

    public boolean sendPurchaseRequest(CsvStockItem item,
                                       Integer purchaseQuantity) {
        // 1. Autenticar na api para recuperar o token
        var token = authService.getToken();

        // 2. Enviar a solicitação de compra
        var requestBody = new PurchaseRequest(
                item.getItemId(),
                item.getItemName(),
                item.getSupplierName(),
                item.getSupplierEmail(),
                purchaseQuantity);

        var response = purchaseSectorClient.purchase(token, requestBody);

        // 3. validar se a resposta chegou com sucesso
        if (response.getStatusCode().value() != HttpStatus.ACCEPTED.value()) {
            logger.error("Error while sending purchase request. " +
                            "Status code: {}," +
                            "Body: {}",
                    response.getStatusCode(),
                    response.getBody());

            return false;
        }
        return true;
    }

}
