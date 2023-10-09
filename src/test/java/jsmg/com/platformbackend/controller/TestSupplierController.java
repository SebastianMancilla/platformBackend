package jsmg.com.platformbackend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.repository.SupplierRepository;
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

import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TestSupplierController {
    private  final String URL = "/api/supplier";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SupplierRepository supplierRepository;

    @Test
    void getSuppliers()throws Exception{
        Supplier supplier = new Supplier();

        Mockito.when(supplierRepository.findAll()).thenReturn(List.of(supplier));
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void withoutSuppliers()throws Exception{
        Mockito.when(supplierRepository.findAll()).thenReturn(List.of());
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void getSupplierByCode()throws  Exception{
        Supplier supplier = new Supplier();
        supplier.setSuppId(1L);
        supplier.setName("Jose Luis");
        supplier.setCode(UUID.randomUUID().toString());
        supplier.setNotificationEmail("prueba@gmail.com");
        supplier.setAlertEmail("prueba@gmail.com");

        Mockito.when(supplierRepository.findByCode(Mockito.any())).thenReturn(Optional.of(supplier));
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void withoutSupplierByCode()throws  Exception{

        Mockito.when(supplierRepository.findByCode(Mockito.any())).thenReturn(Optional.empty());
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(400, response.getStatus());

    }

    @Test
    void createSupplier()throws  Exception{
        ObjectMapper mapper = new ObjectMapper();
        Supplier supplier = new Supplier();
        supplier.setSuppId(1L);
        supplier.setName("Jose Luis");
        supplier.setCode(UUID.randomUUID().toString());
        supplier.setNotificationEmail("prueba@gmail.com");
        supplier.setAlertEmail("prueba@gmail.com");

        Mockito.when(supplierRepository.save(Mockito.any())).thenReturn(supplier);
        RequestBuilder builder = MockMvcRequestBuilders
                .post(URL + "/")
                .content(mapper.writeValueAsString(supplier))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(201, response.getStatus());

    }

    @Test
    void updateSupplier() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Supplier supplier = new Supplier();
        supplier.setSuppId(1L);
        supplier.setName("Jose Luis");
        supplier.setCode(UUID.randomUUID().toString());
        supplier.setNotificationEmail("prueba@gmail.com");
        supplier.setAlertEmail("prueba@gmail.com");

        Mockito.when(supplierRepository.findByCode(Mockito.any())).thenReturn(Optional.of(supplier));
        RequestBuilder builder = MockMvcRequestBuilders
                .patch(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(supplier))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void noUpdatedSupplier() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();

        Mockito.when(supplierRepository.findByCode(Mockito.any())).thenReturn(Optional.empty());
        RequestBuilder builder = MockMvcRequestBuilders
                .patch(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(new Supplier()))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

    @Test
    void deleteSupplier() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Supplier supplier = new Supplier();
        supplier.setSuppId(1L);
        supplier.setName("Jose Luis");
        supplier.setCode(UUID.randomUUID().toString());
        supplier.setNotificationEmail("prueba@gmail.com");
        supplier.setAlertEmail("prueba@gmail.com");

        Mockito.when(supplierRepository.findByCode(Mockito.any())).thenReturn(Optional.of(supplier));
        RequestBuilder builder = MockMvcRequestBuilders
                .delete(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(supplier))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void noDeleteSupplier() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Supplier supplier = new Supplier();

        Mockito.doNothing().when(supplierRepository).deleteByCode(Mockito.anyString());
        RequestBuilder builder = MockMvcRequestBuilders
                .delete(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(supplier))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(400, response.getStatus());
    }

}
