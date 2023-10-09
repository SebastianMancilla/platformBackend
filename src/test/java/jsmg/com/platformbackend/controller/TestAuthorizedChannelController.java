package jsmg.com.platformbackend.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import jsmg.com.platformbackend.domain.AuthorizedChannel;
import jsmg.com.platformbackend.domain.Product;
import jsmg.com.platformbackend.domain.Supplier;
import jsmg.com.platformbackend.repository.AuthorizedChannelRepository;
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
public class TestAuthorizedChannelController {
    private final String URL = "/api/authorized-channel";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorizedChannelRepository authorizedChannelRepository;


    @Test
    void getChannels()throws Exception{
        AuthorizedChannel authorizedChannel = new AuthorizedChannel();

        Mockito.when(authorizedChannelRepository.findAll()).thenReturn(List.of(authorizedChannel));
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void withoutChannels()throws Exception{
        Mockito.when(authorizedChannelRepository.findAll()).thenReturn(List.of());
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }


    @Test
    void getChannelByCode()throws  Exception{
        AuthorizedChannel channel = new AuthorizedChannel();
        channel.setChannelId(1L);
        channel.setCode(UUID.randomUUID().toString());
        channel.setName("Una Tiendita");
        channel.setSupplierId(new Supplier());


        Mockito.when(authorizedChannelRepository.findByCode(Mockito.any())).thenReturn(Optional.of(channel));
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(200, response.getStatus());

    }

    @Test
    void withoutChannelByCode()throws  Exception{

        Mockito.when(authorizedChannelRepository.findByCode(Mockito.any())).thenReturn(Optional.empty());
        RequestBuilder builder = MockMvcRequestBuilders
                .get(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(400, response.getStatus());

    }


    @Test
    void updateChannel() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        AuthorizedChannel channel = new AuthorizedChannel();
        channel.setChannelId(1L);
        channel.setCode(UUID.randomUUID().toString());
        channel.setName("Una Tiendita");
        channel.setSupplierId(new Supplier());


        Mockito.when(authorizedChannelRepository.findByCode(Mockito.any())).thenReturn(Optional.of(channel));
        RequestBuilder builder = MockMvcRequestBuilders
                .patch(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(channel))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(200, response.getStatus());
    }

    @Test
    void noUpdatedChannel() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();

        Mockito.when(authorizedChannelRepository.findByCode(Mockito.any())).thenReturn(Optional.empty());
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
    void deleteChannel() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        AuthorizedChannel channel = new AuthorizedChannel();
        channel.setChannelId(1L);
        channel.setCode(UUID.randomUUID().toString());
        channel.setName("Una Tiendita");
        channel.setSupplierId(new Supplier());


        Mockito.when(authorizedChannelRepository.findByCode(Mockito.any())).thenReturn(Optional.of(channel));
        RequestBuilder builder = MockMvcRequestBuilders
                .delete(URL + "/6003231c-f56b-4759-bf1f-b9d478e09449")
                .content(mapper.writeValueAsString(channel))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult mvcResult = mockMvc.perform(builder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();

        Assertions.assertEquals(204, response.getStatus());
    }

    @Test
    void noDeleteChannel() throws Exception
    {
        ObjectMapper mapper = new ObjectMapper();
        Product product = new Product();

        Mockito.doNothing().when(authorizedChannelRepository).deleteByCode(Mockito.anyString());
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
