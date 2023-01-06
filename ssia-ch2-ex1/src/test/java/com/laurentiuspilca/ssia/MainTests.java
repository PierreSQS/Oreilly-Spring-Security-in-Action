package com.laurentiuspilca.ssia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainTests {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Test calling /hello endpoint without authentication returns unauthorized.")
    void helloUnauthenticated() throws Exception {
        mvc.perform(get("/hello"))
              .andExpect(status().isUnauthorized())
              .andDo(print());
    }

    @Test
    @DisplayName("Test calling /hello endpoint authenticated with a mock user returns ok.")
    @WithMockUser
    void helloAuthenticated() throws Exception {
        mvc.perform(get("/hello"))
              .andExpect(content().string("Hello!"))
              .andExpect(status().isOk())
              .andDo(print());
    }

    @Test
    @DisplayName("Test calling /hello endpoint authenticated with a real user returns ok.")
    void helloAuthenticatedWithUser() throws Exception {
        mvc.perform(get("/hello")
                    .with(user("mary")))
                .andExpect(content().string("Hello!"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
