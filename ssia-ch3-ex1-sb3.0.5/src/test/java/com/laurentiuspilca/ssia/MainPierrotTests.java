package com.laurentiuspilca.ssia;

import com.laurentiuspilca.ssia.controllers.HelloController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
class MainPierrotTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test calling /hello endpoint without authentication returns unauthorized.")
    void helloUnauthenticated() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("Test calling /hello endpoint authenticated with user() returns ok.")
    void helloAuthenticated() throws Exception {
        mockMvc.perform(get("/hello").with(user("bill")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello"))
                .andDo(print());

    }
}
