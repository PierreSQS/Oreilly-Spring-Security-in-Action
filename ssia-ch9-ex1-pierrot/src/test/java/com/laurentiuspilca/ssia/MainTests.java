package com.laurentiuspilca.ssia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainTests {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Endpoint /hello without providing the Request-Id header")
    void testHelloNoRequestIdHeader() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Endpoint /hello providing the Request-Id header")
    void testHelloValidRequestIdHeader() throws Exception {
        mvc.perform(get("/hello").header("Request-Id", "12345"))
                .andExpect(status().isOk());
    }

}
