package com.laurentiuspilca.ssia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MainTests {

    @Autowired
    MockMvc mvc;

    @Test
    @DisplayName("Endpoint /video without authentication")
    void testCallingVideoWithoutAuthentication() throws Exception {
        mvc.perform(get("/video/ca/fr"))
                .andExpect(status().isUnauthorized())
                .andDo(print());
    }

    @Test
    @DisplayName("Endpoint /video with authentication as ca/fr without premium")
    @WithUserDetails("john")
    void testCallingVideoWithAuthenticationWithoutPremium() throws Exception {
        mvc.perform(get("/video/ca/fr"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("Endpoint /video with authentication as ro/ro without premium")
    @WithUserDetails("john")
    void testCallingVideoWithAuthenticationWithoutPremiumForRO() throws Exception {
        mvc.perform(get("/video/ro/ro"))
                .andExpect(status().isForbidden())
                .andDo(print());
    }

    @Test
    @DisplayName("Endpoint /video with authentication as ro/ro with premium")
    @WithUserDetails("jane")
    void testCallingVideoWithAuthenticationWithPremiumforRO() throws Exception {
        mvc.perform(get("/video/ro/ro"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
