package jsmg.com.platformbackend.controller;

import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.dto.SupplierDTO;
import jsmg.com.platformbackend.service.SupplierService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping(path = "/api/supplier")
@CrossOrigin( origins = "http://localhost:4200")
@AllArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;

    @PostMapping("/")
    public ResponseEntity<Supplier> createSupplier(@RequestBody SupplierDTO supplierDTO){
        return new ResponseEntity<>(supplierService.save(supplierDTO), HttpStatus.CREATED);
    }
    @GetMapping(path = "/")
    public ResponseEntity<List<Supplier>> getSuppliers(){
        return new ResponseEntity<>(supplierService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{code}")
    public ResponseEntity<SupplierDTO> getSupplierByCode(@PathVariable String code){
        return new ResponseEntity<>(supplierService.findByCode(code), HttpStatus.OK);
    }

    @PatchMapping(path = "/{code}")
    public  ResponseEntity<Supplier> updateSupplier(@PathVariable String code, @RequestBody SupplierDTO supplierDTO){
        return  new ResponseEntity<>(supplierService.update(supplierDTO, code), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{code}")
    public  ResponseEntity<Supplier> deleteSupplier(@PathVariable String code){
        supplierService.deleteByCode(code);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
