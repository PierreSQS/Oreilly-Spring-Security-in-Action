package com.laurentiuspilca.ssia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainTests {

    @Value("${authorization.key}")
    String key;

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Endpoint /hello without providing the Authorization header")
    void testHelloNoAuthorizationHeader() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Endpoint /hello providing a wrong Authorization header")
    void testHelloUsingInvalidAuthorizationHeader() throws Exception {
        mvc.perform(get("/hello")
                     .header("Authorization", "12345"))
                .andExpect(status().isUnauthorized());
    }
    @Test
    @DisplayName("Endpoint /hello providing a valid Authorization header")
    void testHelloUsingValidAuthorizationHeader() throws Exception {
        mvc.perform(get("/hello")
                .header("Authorization", key))
                .andExpect(status().isOk());
    }
}
