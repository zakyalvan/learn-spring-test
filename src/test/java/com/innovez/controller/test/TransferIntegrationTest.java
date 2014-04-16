package com.innovez.controller.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestContextManager;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import com.innovez.entity.Account;
import com.innovez.entity.Account.Status;
import com.innovez.entity.Money;
import com.innovez.entity.Person;
import com.innovez.repo.AccountRepository;
import com.innovez.repo.PersonRepository;

/**
 * Transfer integration test.
 * Thanks spring test context framework, {@link TestContext} and {@link TestContextManager}.
 * 
 * @author zakyalvan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
	@ContextConfiguration(locations={"classpath:/META-INF/spring/*-context.xml"}),
	@ContextConfiguration(locations={"classpath:/META-INF/spring/webapp/webapp-context.xml"})
})
@WebAppConfiguration(value="/src/main/webapp")
@TransactionConfiguration(defaultRollback=true)
public class TransferIntegrationTest {
	private Logger logger = Logger.getLogger(TransferIntegrationTest.class);
	
	private static final String FIRST_ACCOUNT_NUMBER = new Long(new Date().getTime()).toString();
	private static final String SECOND_ACCOUNT_NUMBER = new Long(new Date().getTime() + 123).toString();
	private static final String THIRD_ACCOUNT_NUMBER = new Long(new Date().getTime() + 456).toString();
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private AccountRepository accountRepository;
	
	private MockMvc mockMvc;
	
	@PostConstruct
	@Transactional(propagation=Propagation.REQUIRED)
	public void populateData() {
		logger.debug("Bootstrap test account data, of course with their owner.");
		Person zaky = new Person();
		zaky.setEmail("zaky@example.com");
		zaky.setName("Muhammad Zaky Alvan");
		zaky.setPassword("rahasia");
		
		Person radhy = new Person();
		radhy.setEmail("radhy@example.com");
		radhy.setName("Ahmad Radhy");
		radhy.setPassword("rahasia");
		
		zaky = personRepository.save(zaky);
		radhy = personRepository.save(radhy);
		
		Account zakyAccount = new Account();
		zakyAccount.setOwner(zaky);
		zakyAccount.setNumber(FIRST_ACCOUNT_NUMBER);
		zakyAccount.setStatus(Status.ACTIVE);
		zakyAccount.setBalance(new Money("IDR", 3000000d));
		
		Account radhyAccount = new Account();
		radhyAccount.setOwner(radhy);
		radhyAccount.setNumber(SECOND_ACCOUNT_NUMBER);
		radhyAccount.setStatus(Status.ACTIVE);
		radhyAccount.setBalance(new Money("IDR", 100000d));
		
		Account radhySuspendedAccount = new Account();
		radhySuspendedAccount.setOwner(radhy);
		radhySuspendedAccount.setNumber(THIRD_ACCOUNT_NUMBER);
		radhySuspendedAccount.setStatus(Status.SUSPENDED);
		radhySuspendedAccount.setBalance(new Money("IDR", 2000d));
		
		accountRepository.save(zakyAccount);
		accountRepository.save(radhyAccount);
		accountRepository.save(radhySuspendedAccount);
	}
	
	@Before
	public void setup() {
		logger.debug("!===========================! Setup integration test, first setup MockMvc object.");
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testTransferMoneyOnHappyDay() throws Exception {
		logger.debug("Happy day test!");
		mockMvc.perform(
			post("/transfers")
				.accept(MediaType.ALL)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("fromAccount", FIRST_ACCOUNT_NUMBER)
				.param("toAccount", SECOND_ACCOUNT_NUMBER)
				.param("currency", "IDR")
				.param("amount", "30000")
			)
			.andExpect(status().isOk())
			//.andExpect(content().contentType(MediaType.ALL_VALUE))
			.andDo(print());
	}
	
	@Test
	public void testTransferMoneyToUnregisteredAccount() throws Exception {
		logger.debug("Transfer money to unregistered account");
		mockMvc.perform(
			post("/transfers")
				.accept(MediaType.ALL)
				.contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("fromAccount", FIRST_ACCOUNT_NUMBER)
				.param("toAccount", new Long(new Date().getTime() + 1024).toString())
				.param("currency", "IDR")
				.param("amount", "30000")
			)
			.andExpect(status().isOk())
			//.andExpect(content().contentType(MediaType.ALL_VALUE))
			.andDo(print());
	}
}
