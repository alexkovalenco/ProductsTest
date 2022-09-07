package com.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.product.Application;
import com.product.dto.ProductDTO;
import com.product.repository.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@TestPropertySource(
    locations = "classpath:application-integrationtest.properties")
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private static String URL = "/api/shop/product";

    @Test
    public void create() throws Exception {
        ProductDTO productDTO =  new ProductDTO("Eva", "desc");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(productDTO);

        mockMvc.perform(
                post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    public void findByFilter() throws Exception {
        String nameFilter = "?nameFilter=^E.*$";
        mockMvc.perform(get(URL + nameFilter)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        ProductDTO productDTO = new ProductDTO("testEva", "desc");
        createProduct(productDTO);

        mockMvc.perform(get(URL + nameFilter)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        productDTO = new ProductDTO("zzzzzz", "desc");
        createProduct(productDTO);
        nameFilter = "?nameFilter=^.*[eva].*$";

        mockMvc.perform(get(URL + nameFilter)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public void createProduct(ProductDTO productDTO) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(productDTO);

        mockMvc.perform(
                post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andReturn();
    }
}