package com.innovez.controller.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * Transfer integration test
 * 
 * @author zakyalvan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
	@ContextConfiguration(locations={"classpath:/META-INF/spring/*-context.xml"}),
	@ContextConfiguration(locations={"classpath:/META-INF/spring/webapp/webapp-context.xml"})
})
@WebAppConfiguration(value="/src/main/webapp")
public class TransferTest {
	@Test
	public void test() {
		
	}
	@Test
	@DirtiesContext
	public void testAgain() {
		
	}
}
