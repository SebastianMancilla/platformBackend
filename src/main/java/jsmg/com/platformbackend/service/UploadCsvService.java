package jsmg.com.platformbackend.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import jsmg.com.platformbackend.components.EmailServiceImp;
import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.dto.SupplierDTO;
import jsmg.com.platformbackend.dto.SupplierModelCsvDTO;
import jsmg.com.platformbackend.exception.BadRequestExc;
import jsmg.com.platformbackend.exception.InternalServerExc;
import jsmg.com.platformbackend.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;

@Service
@AllArgsConstructor
public class UploadCsvService {
    private SupplierRepository supplierRepository;
    private SupplierService supplierService;
    private EmailServiceImp emailService;


    public List<Supplier> uploadSuppliersCsvFile(MultipartFile file){
        if(file.isEmpty()){
            throw new BadRequestExc("Need some data in the file ");
        }
        try(Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            CsvToBean<SupplierModelCsvDTO> csvToBean = new CsvToBeanBuilder<SupplierModelCsvDTO>(reader)
                    .withType(SupplierModelCsvDTO.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();
            List<SupplierModelCsvDTO> suppliers = csvToBean.parse();
            List<Supplier> supplierList = new ArrayList<>();

            suppliers.forEach(supplierModelCsvDTO -> {
                Supplier supplierRep = supplierRepository.findByNotificationEmail(supplierModelCsvDTO.getNotificationEmail());

                if (!Objects.isNull(supplierRep)){
                    SupplierDTO supplierDTO = supplierService.findByCode(supplierRep.getCode());
                   supplierList.add(supplierService.update(supplierDTO, supplierDTO.getCode()));
                    return;
                 }
                supplierList.add(supplierService.save(new SupplierDTO(supplierModelCsvDTO)));
            });
            emailService.sendMail("quiensea@jsmg.com", "Subida de Distribuidores por CSV", "Proceso finalizado");
            return supplierList;


        } catch (Exception ex) {
            throw new InternalServerExc("Sorry, view the logs");
        }

    }

}
