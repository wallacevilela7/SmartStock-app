package tech.wvs.smartstockapp.service;

import org.springframework.stereotype.Service;
import tech.wvs.smartstockapp.domain.CsvStockItem;
import tech.wvs.smartstockapp.entity.PurchaseRequestEntity;
import tech.wvs.smartstockapp.repository.PurchaseRequestRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SmartStockService {

    private final PurchaseSectorService purchaseSectorService;
    private final ReportService reportService;
    private final PurchaseRequestRepository purchaseRequestRepository;
    private final Double REORDER_SECURITY_PERCENTAGE = 0.20;

    public SmartStockService(PurchaseSectorService purchaseSectorService, ReportService reportService, PurchaseRequestRepository purchaseRequestRepository) {
        this.purchaseSectorService = purchaseSectorService;
        this.reportService = reportService;
        this.purchaseRequestRepository = purchaseRequestRepository;
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
                    var purchasedWithSuccess = purchaseSectorService.sendPurchaseRequest(item, reorderQuantity);


                    // 3. salvar cada 'item' comprado no mongodb
                    persist(item, reorderQuantity, purchasedWithSuccess);
                }
            });

        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void persist(CsvStockItem item,
                         Integer reorderQuantity,
                         boolean purchasedWithSuccess) {

        var entity = new PurchaseRequestEntity();
        entity.setItemId(item.getItemId());
        entity.setItemName(item.getItemName());
        entity.setQuantityInStock(item.getQuantity());
        entity.setReorderThreshold(item.getReorderThreshold());
        entity.setSupplierName(item.getSupplierName());
        entity.setSupplierEmail(item.getSupplierEmail());
        entity.setLastStockUpdateTime(LocalDateTime.parse(item.getLastStockUpdateTime()));

        entity.setPurchaseQuantity(reorderQuantity);
        entity.setPurchasedWithSuccess(purchasedWithSuccess);
        entity.setPurchaseDateTime(LocalDateTime.now());

        purchaseRequestRepository.save(entity);
    }

    private Integer calculateReorderQuantity(CsvStockItem item) {
        return item.getReorderThreshold() +
                ((int) Math.ceil(item.getReorderThreshold() * REORDER_SECURITY_PERCENTAGE));
    }
}
