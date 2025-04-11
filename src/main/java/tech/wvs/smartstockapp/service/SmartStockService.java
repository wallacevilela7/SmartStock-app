package tech.wvs.smartstockapp.service;

import org.springframework.stereotype.Service;
import tech.wvs.smartstockapp.domain.CsvStockItem;

import java.io.IOException;
import java.util.List;

@Service
public class SmartStockService {

    private final PurchaseSectorService purchaseSectorService;
    private final ReportService reportService;
    private final Double REORDER_SECURITY_PERCENTAGE = 0.20;

    public SmartStockService(PurchaseSectorService purchaseSectorService, ReportService reportService) {
        this.purchaseSectorService = purchaseSectorService;
        this.reportService = reportService;
    }

    public void start(String reportPath) {
        // 1. ler o arquivo csv

        try {
            List<CsvStockItem> stockItems = reportService.readStockReport(reportPath);

            // 2. para cada 'item' do csv, fazer a requisição para a api de compras do smartstock
            stockItems.forEach(item -> {
                if (item.getQuantity() < item.getReorderThreshold()) {

                    // 2.1 calcular a quantidade a ser recomprada
                    var reorderQuantity = calculateReorderQuantity(item);

                    // 2.2 fazer a requisição para a api de compras do smartstock
                    purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);

                }
            });

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }


        // 3. salvar cada 'item' comprado no mongodb
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshold() +
                ((int) Math.ceil(item.getReorderThreshold() * REORDER_SECURITY_PERCENTAGE));
    }
}
