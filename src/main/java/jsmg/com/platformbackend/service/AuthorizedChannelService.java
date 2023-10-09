package jsmg.com.platformbackend.service;

import jsmg.com.platformbackend.domain.AuthorizedChannel;
import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.dto.AuthorizedChannelDTO;
import jsmg.com.platformbackend.exception.BadRequestExc;
import jsmg.com.platformbackend.exception.NoContentExc;
import jsmg.com.platformbackend.repository.AuthorizedChannelRepository;
import jsmg.com.platformbackend.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class AuthorizedChannelService {
    private final AuthorizedChannelRepository authorizedChannelRepository;
    private  final SupplierRepository supplierRepository;

    public List<AuthorizedChannel> findAll(){
        List<AuthorizedChannel> channels = authorizedChannelRepository.findAll();
        if(channels.isEmpty()){
            throw new NoContentExc("There are no channels");
        }

        return channels;
    }

    public AuthorizedChannelDTO findByCode(String code){
        if(!isUUIDValid(code)){
            throw new BadRequestExc("This not is a valid UUID");
        }
        Optional<AuthorizedChannel> channel =  authorizedChannelRepository.findByCode(code);
        if(channel.isEmpty()){
            throw new BadRequestExc("Authorized Channel not found");
        }
        return AuthorizedChannelDTO.builder()
                .channelId(channel.get().getChannelId())
                .code(channel.get().getCode())
                .name(channel.get().getName())
                .supplierCode(channel.get().getSupplierId().getCode())
                .supplier(channel.get().getSupplierId())
                .build();
    }

    public AuthorizedChannel save(AuthorizedChannelDTO authorizedChannelDTO){
        if (authorizedChannelDTO.getName().isEmpty()){
            throw new BadRequestExc("The 'name' is required");
        }
        if (authorizedChannelDTO.getSupplierCode().isEmpty()){
            throw new BadRequestExc("The supplierId is required");
        }
        Optional<Supplier> supplier = supplierRepository.findByCode(authorizedChannelDTO.getSupplierCode());
        if (supplier.isEmpty()){
            throw new BadRequestExc("The supplier doesn't exists");
        }
        AuthorizedChannel authorizedChannel = new AuthorizedChannel(authorizedChannelDTO);
        authorizedChannel.setSupplierId(supplier.get());
        return authorizedChannelRepository.save(authorizedChannel);
    }

    public AuthorizedChannel update(AuthorizedChannelDTO dto, String code){
        AuthorizedChannelDTO channelDTO = this.findByCode(code);
        if (dto.getName() != null) {
            channelDTO.setName(dto.getName());
        }
        return authorizedChannelRepository.save(new AuthorizedChannel(channelDTO));
    }
    public void deleteByCode(String code){
        this.findByCode(code);
        authorizedChannelRepository.deleteByCode(code);
    }

    private static boolean isUUIDValid(String code) {
        try {
            UUID uuid = UUID.fromString(code);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
