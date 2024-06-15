package com.inv.op.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;

@SpringBootTest
class BackendApplicationTests {

	@Test
	void contextLoads() {
		try {
            BackendApplication.main(new String[]{});
        } catch (Exception e) {
            Assert.fail(e.getMessage());
        }
	}


}
