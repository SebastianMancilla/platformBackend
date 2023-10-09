package jsmg.com.platformbackend.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jsmg.com.platformbackend.dto.AuthorizedChannelDTO;
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
public class AuthorizedChannel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long channelId;
    private String code;
    private String name;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplierId")
    private Supplier supplierId;

    @PrePersist
    private void prePersist() {
        if (Objects.isNull(code)) {
            code = UUID.randomUUID().toString();
        }
    }
    public  AuthorizedChannel(AuthorizedChannelDTO authorizedChannelDTO){
        BeanUtils.copyProperties(authorizedChannelDTO, this);
    }
}
