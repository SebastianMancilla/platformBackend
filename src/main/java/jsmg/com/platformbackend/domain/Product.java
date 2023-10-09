package jsmg.com.platformbackend.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jsmg.com.platformbackend.dto.ProductDTO;
import lombok.*;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long id;
    private String code;
    private  String name;
    private String description;
    private Double amount;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "supplierId")
    private Supplier supplierId;

    @PrePersist
    private void prePersist() {
        if (Objects.isNull(code)) {
            code = UUID.randomUUID().toString();
        }
    }

    public  Product(ProductDTO productDTO){
        BeanUtils.copyProperties(productDTO, this);
    }
}
