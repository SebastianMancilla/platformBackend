package jsmg.com.platformbackend.service;

import jsmg.com.platformbackend.domain.Product;
import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.dto.ProductDTO;
import jsmg.com.platformbackend.exception.BadRequestExc;
import jsmg.com.platformbackend.exception.NoContentExc;
import jsmg.com.platformbackend.repository.ProductRepository;
import jsmg.com.platformbackend.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public List<Product> findAll(){
        List<Product>  products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new NoContentExc("There are no products");
        }

        return products;
    }

    public ProductDTO findByCode(String code){
        if (!isUUIDValid(code)) {
            throw new BadRequestExc("This not is a UUID");
        }
        Optional<Product> product = productRepository.findByCode(code);
        if(product.isEmpty()){
            throw new BadRequestExc("Product not found");
        }

        return  ProductDTO.builder()
                .id(product.get().getId())
                .name(product.get().getName())
                .code(product.get().getCode())
                .amount(product.get().getAmount())
                .description(product.get().getDescription())
                .supplierCode(product.get().getSupplierId().getCode())
                .supplier(product.get().getSupplierId())
                .build();
    }

    public Product save(ProductDTO productDTO){
        if (productDTO.getName().isEmpty()){
            throw new BadRequestExc("The 'name' is required");
        }
        if (productDTO.getSupplierCode().isEmpty()){
            throw new BadRequestExc("The 'suppId' is required");
        }
        Optional<Supplier> supplier = supplierRepository.findByCode(productDTO.getSupplierCode());
        if (supplier.isEmpty()){
            throw new BadRequestExc("The supplier doesn't exists");
        }
        Product product = new Product(productDTO);


        product.setSupplierId(supplier.get());
        return productRepository.save(product);
    }

    public Product update(ProductDTO dto, String code){
        ProductDTO productDTO = this.findByCode(code);
        if(dto.getName() !=null){
            productDTO.setName(dto.getName());
        }
        if(dto.getAmount() != null){
            productDTO.setAmount(dto.getAmount());
        }
        if(dto.getDescription() != null){
            productDTO.setDescription(dto.getDescription());
        }

        return productRepository.save(new Product(productDTO));
    }

    public  void deleteByCode(String code){
        this.findByCode(code);
        productRepository.deleteByCode(code);
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
