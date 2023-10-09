package jsmg.com.platformbackend.service;

import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.dto.SupplierDTO;
import jsmg.com.platformbackend.exception.BadRequestExc;
import jsmg.com.platformbackend.exception.NoContentExc;
import jsmg.com.platformbackend.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<Supplier> findAll(){
        List<Supplier> suppliers = supplierRepository.findAll();
        if(suppliers.isEmpty()){
            throw new NoContentExc("Not suppliers here");
        }
        return suppliers;
    }

    public SupplierDTO findByCode(String code){
        if(!isUUIDValid(code)){
            throw  new BadRequestExc("This not is a UUID valid");
        }
        Optional<Supplier> supplier = supplierRepository.findByCode(code);
        if(supplier.isEmpty()){
            throw  new BadRequestExc("The code not found");
        }


        return  SupplierDTO.builder()
                .suppId(supplier.get().getSuppId())
                .name(supplier.get().getName())
                .code(supplier.get().getCode())
                .notificationEmail(supplier.get().getNotificationEmail())
                .alertEmail(supplier.get().getAlertEmail())
                .products(supplier.get().getProducts())
                .channels(supplier.get().getAuthorizedChannels())
                .build();
    }

    public Supplier save(SupplierDTO supplierDTO){
        if(supplierDTO.getName().isEmpty()){
            throw  new BadRequestExc("The 'name' is required");
        }

        return  supplierRepository.save(new Supplier(supplierDTO));
    }
    public  Supplier update(SupplierDTO dto, String code){
        SupplierDTO supplierDTO = this.findByCode(code);
        if (dto.getName() !=null) {
            supplierDTO.setName(dto.getName());
        }
        if (dto.getNotificationEmail() != null) {
            supplierDTO.setNotificationEmail(dto.getNotificationEmail());
        }
        if(dto.getAlertEmail() != null){
            supplierDTO.setAlertEmail(dto.getAlertEmail());
        }

        return  supplierRepository.save(new Supplier(supplierDTO));
    }

    public void deleteByCode(String code){
        this.findByCode(code);
        supplierRepository.deleteByCode(code);
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
