package jsmg.com.platformbackend.dto;

import jsmg.com.platformbackend.domain.AuthorizedChannel;
import jsmg.com.platformbackend.domain.Product;
import jsmg.com.platformbackend.domain.Supplier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class SupplierDTO {
    private Long suppId;
    private String name;
    private String code;
    private String notificationEmail;
    private String alertEmail;
    private List<Product> products;
    private List<AuthorizedChannel> channels;

    public SupplierDTO(SupplierModelCsvDTO supplier){
        BeanUtils.copyProperties(supplier,this);
    }
}
