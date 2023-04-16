package com.laurentiuspilca.ssia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PierrotTests {
    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Test calling /hello endpoint without authentication returns OK.")
    void helloUnauthenticated() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk()) // if test runs after 12:00
                .andDo(print());
    }

    @Test
    @DisplayName("Test calling /hello endpoint authenticated as MockUser returns ok.")
    @WithMockUser(username = "MockUser")
    void helloAuthenticatedWithMockUser() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello!"))
                .andDo(print());
    }

    @Test
    @DisplayName("Test calling /ciao endpoint authenticated with user john returns ok.")
    void helloAuthenticatedWithRealUser() throws Exception {
        mvc.perform(get("/hello").with(httpBasic("john","john")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello!"))
                .andDo(print());
    }
    @Test
    @DisplayName("Test calling /ciao endpoint authenticated with user-Method returns ok.")
    void helloAuthenticatedWithlUser() throws Exception {
        mvc.perform(get("/hello").with(user("TripleX").password("XXX"))) // user must not exist!!
                .andExpect(status().isOk())
                .andExpect(content().string("Hello!"))
                .andDo(print());
    }
}
