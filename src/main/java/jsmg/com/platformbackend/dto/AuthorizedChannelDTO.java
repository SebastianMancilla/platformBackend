package jsmg.com.platformbackend.dto;

import jsmg.com.platformbackend.domain.Supplier;
import lombok.*;
import lombok.extern.jackson.Jacksonized;

@Data
@Builder
@Jacksonized
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizedChannelDTO {
    private Long channelId;
    private String code;
    private String name;
    private String supplierCode;
    private Supplier supplier;
}
