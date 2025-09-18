package com.example.ssiach15ex1;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SsiaCh15Ex1ApplicationTests {

	@Test
	void contextLoads(ApplicationContext appCtx) {
        assertThat(appCtx).isNotNull();
	}

}
