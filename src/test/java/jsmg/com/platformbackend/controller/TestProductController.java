package jsmg.com.platformbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsmg.com.platformbackend.domain.Product;
import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.dto.ProductDTO;
import jsmg.com.platformbackend.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class TestProductController {
    private  final String URL = "/api/product";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void getProducts()throws Exception{
        Product product = new Product();

        Mockito.when(productRepository.findAll()).thenReturn(List.of(product));
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void withoutProducts()throws Exception{
        Mockito.when(productRepository.findAll()).thenReturn(List.of());
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }


    @Test
    void getProductByCode()throws  Exception{
        Product product = new Product();
        product.setId(1L);
        product.setName("Chicharron");
        product.setCode(UUID.randomUUID().toString());
        product.setDescription("Una descripcion");
        product.setAmount(20.39);
        product.setSupplierId(new Supplier());


        Mockito.when(productRepository.findByCode(Mockito.any())).thenReturn(Optional.of(product));
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void withoutProductByCode()throws  Exception{

        Mockito.when(productRepository.findByCode(Mockito.any())).thenReturn(Optional.empty());
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    void updateProduct() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Product product = new Product();
        product.setId(1L);
        product.setName("Chicharron");
        product.setCode(UUID.randomUUID().toString());
        product.setDescription("Una descripcion");
        product.setAmount(20.39);
        product.setSupplierId(new Supplier());

        Mockito.when(productRepository.findByCode(Mockito.any())).thenReturn(Optional.of(product));
        RequestBuilder builder = MockMvcRequestBuilders
                .patch(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void noUpdatedProduct() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();

        Mockito.when(productRepository.findByCode(Mockito.any())).thenReturn(Optional.empty());
        RequestBuilder builder = MockMvcRequestBuilders
                .patch(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(new Product()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deleteProduct() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Product product = new Product();
        product.setId(1L);
        product.setName("Chicharron");
        product.setCode(UUID.randomUUID().toString());
        product.setDescription("Una descripcion");
        product.setAmount(20.39);
        product.setSupplierId(new Supplier());

        Mockito.when(productRepository.findByCode(Mockito.any())).thenReturn(Optional.of(product));
        RequestBuilder builder = MockMvcRequestBuilders
                .delete(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void noDeleteProduct() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
         Product product = new Product();

        Mockito.doNothing().when(productRepository).deleteByCode(Mockito.anyString());
        RequestBuilder builder = MockMvcRequestBuilders
                .delete(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(product))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }
}
