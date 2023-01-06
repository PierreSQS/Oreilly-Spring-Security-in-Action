package com.laurentiuspilca.ssia;

import com.laurentiuspilca.ssia.config.TestConfigNoPwdEnc;
import com.laurentiuspilca.ssia.controllers.HelloController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HelloController.class)
@Import(value = TestConfigNoPwdEnc.class)
class MainPierrotNoPwdEncTests {

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
    @DisplayName("Test calling /hello endpoint authenticated returns ok.")
    @WithUserDetails("john")
    void helloAuthenticated() throws Exception {
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andDo(print());

    }

    @Test
    void displaySomeBeans(ApplicationContext appCtx) {
        Arrays.stream(appCtx.getBeanDefinitionNames())
//                .filter(name -> name.contains("encoder"))
                .forEach(System.out::println);

    }
}
