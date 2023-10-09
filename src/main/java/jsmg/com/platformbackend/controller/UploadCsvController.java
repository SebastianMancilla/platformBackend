package jsmg.com.platformbackend.controller;

import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.service.UploadCsvService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/supplier/upload-csv")
@CrossOrigin( origins = "http://localhost:4200")
@AllArgsConstructor
public class UploadCsvController {
    private UploadCsvService uploadCsvService;

    @PostMapping(path = "/")
    public ResponseEntity<List<Supplier>> uploadCsvFile(@RequestParam("file") MultipartFile file){
        return new ResponseEntity<>(uploadCsvService.uploadSuppliersCsvFile(file), HttpStatus.CREATED);
    }
}
