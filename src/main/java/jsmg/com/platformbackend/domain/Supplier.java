package jsmg.com.platformbackend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jsmg.com.platformbackend.dto.SupplierDTO;
import jsmg.com.platformbackend.dto.SupplierModelCsvDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long suppId;
    private String name;
    private String code;
    private String notificationEmail;
    private String alertEmail;

    @JsonIgnore
    @OneToMany(mappedBy = "supplierId")
    private List<Product> products;

    @JsonIgnore
    @OneToMany(mappedBy = "supplierId")
    private List<AuthorizedChannel> authorizedChannels;

    @PrePersist
    private void prePersist() {
        if (Objects.isNull(code)) {
            code = UUID.randomUUID().toString();
        }
    }

    public Supplier (SupplierDTO supplierDTO){
        BeanUtils.copyProperties(supplierDTO, this);
    }
    public Supplier(SupplierModelCsvDTO supplierModelCsvDTO) {
        BeanUtils.copyProperties(supplierModelCsvDTO, this);
    }
}
