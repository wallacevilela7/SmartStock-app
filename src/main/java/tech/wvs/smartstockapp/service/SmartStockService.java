package tech.wvs.smartstockapp.service;

import org.springframework.stereotype.Service;
import tech.wvs.smartstockapp.domain.CsvStockItem;

import java.io.IOException;
import java.util.List;

@Service
public class SmartStockService {

    private final ReportService reportService;

    public SmartStockService(ReportService reportService) {
        this.reportService = reportService;
    }

    public void start(String reportPath) {
        // 1. ler o arquivo csv

        try {
            List<CsvStockItem> stockItems = reportService.readStockReport(reportPath);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }


        // 2. para cada item do csv, fazer a requisição para a api de compras do smartstock



        // 3. salvar cada item comprado no mongodb
    }
}
