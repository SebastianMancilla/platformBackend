package jsmg.com.platformbackend.dto;

import com.opencsv.bean.CsvBindByName;
import lombok.*;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class SupplierModelCsvDTO {
    @CsvBindByName
    private String name;

    @CsvBindByName
    private  String notificationEmail;

    @CsvBindByName
    private String alertEmail;
}
