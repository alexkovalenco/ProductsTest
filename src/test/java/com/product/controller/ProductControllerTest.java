package com.product.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductController controller;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Ignore
    @Test
    public void findByFilter() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();



        mockMvc.perform(get("/api/shop/product?nameFilter=%5EE.*%24")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}