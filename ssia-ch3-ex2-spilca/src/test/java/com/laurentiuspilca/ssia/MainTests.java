package com.laurentiuspilca.ssia;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test calling /hello endpoint authenticated returns ok.")
    void helloAuthenticated() throws Exception {
        mvc.perform(get("/hello")
                .with(user("john")))
                .andExpect(status().isOk());
    }

    @Disabled("Doesn't work with SB2.3.0 but work in SB3.0.1")
    @Test
    @WithUserDetails("john")
    @DisplayName("Test calling /hello endpoint with UserDetailsService user exists.")
    void helloUserServiceUserExists() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk());
    }

}
