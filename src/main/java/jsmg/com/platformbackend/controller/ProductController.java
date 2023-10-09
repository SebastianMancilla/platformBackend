package jsmg.com.platformbackend.controller;

import jsmg.com.platformbackend.domain.Product;
import jsmg.com.platformbackend.dto.ProductDTO;
import jsmg.com.platformbackend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/product")
@CrossOrigin( origins = "http://localhost:4200")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(path = "/")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productDTO){
        return  new ResponseEntity<>(productService.save(productDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/")
    public  ResponseEntity<List<Product>> getProducts(){
        return  new ResponseEntity<>(productService.findAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{code}")
    public  ResponseEntity<ProductDTO> getProductByCode(@PathVariable String code){
        return  new ResponseEntity<>(productService.findByCode(code), HttpStatus.OK);
    }

    @PatchMapping(path = "/{code}")
    public  ResponseEntity<Product> updateProduct(@PathVariable String code, @RequestBody ProductDTO productDTO){
        return  new ResponseEntity<>(productService.update(productDTO,code), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{code}")
    public  ResponseEntity<Product> deleteProduct(@PathVariable String code){
        productService.deleteByCode(code);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
