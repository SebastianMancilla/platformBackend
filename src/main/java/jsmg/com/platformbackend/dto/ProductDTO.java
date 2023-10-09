package jsmg.com.platformbackend.dto;

import jsmg.com.platformbackend.domain.Supplier;
import lombok.*;
import lombok.extern.jackson.Jacksonized;


@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String code;
    private String name;
    private String description;
    private Double amount;
    private String supplierCode;
    private Supplier supplier;

}
