package tech.wvs.smartstockapp.service;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;
import tech.wvs.smartstockapp.domain.CsvStockItem;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ReportService {

    /*
    metodo responsavel por ler o arquivo de report e retornar a lista de items para recompra
    */

    public List<CsvStockItem> readStockReport(String reportPath ) throws IOException {


        try (Reader reader = Files.newBufferedReader(Paths.get(reportPath))) {

            var builder = new CsvToBeanBuilder<CsvStockItem>(reader)
                    .withType(CsvStockItem.class)
                    .build();

            return builder.parse();
        }
    }
}
