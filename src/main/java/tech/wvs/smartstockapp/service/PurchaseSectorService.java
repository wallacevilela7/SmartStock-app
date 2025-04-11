package tech.wvs.smartstockapp.service;

import org.springframework.stereotype.Service;
import tech.wvs.smartstockapp.domain.CsvStockItem;

@Service
public class PurchaseSectorService {

    private final AuthService authService;

    public PurchaseSectorService(AuthService authService) {
        this.authService = authService;
    }

    public boolean sendPurchaseRequest(CsvStockItem item,
                                       Integer purchaseQuantity) {


        // 1. Autenticar na api para recuperar o token
        var token = authService.getToken();


        // 2. Enviar a solicitação de compra


        // 3. validar se a resposta chegou com sucesso

        return true;
    }

}
