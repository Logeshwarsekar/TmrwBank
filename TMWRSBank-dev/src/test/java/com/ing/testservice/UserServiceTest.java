package com.ing.testservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ing.SwaggerConfig;
import com.ing.UserTransactionHistoryApplication;
import com.ing.controller.UserController;
import com.ing.dao.TransactionDao;
import com.ing.dao.UserDao;
import com.ing.entity.Account;
import com.ing.entity.Transaction;
import com.ing.request.SaveAccountRequest;
import com.ing.response.SaveAccountResponse;
import com.ing.service.UserServiceImp;
import com.ing.utils.UtilConstants;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = UserTransactionHistoryApplication.class)
@ExcludeCategory({TransactionDao.class,UserDao.class,SwaggerConfig.class,
	              UserTransactionHistoryApplication.class,SaveAccountRequest.class,
	              SaveAccountResponse.class,UtilConstants.class})
public class UserServiceTest {
	
	@Mock
	UserDao repo;
	
	@InjectMocks
	UserServiceImp service;
	
	@Test
	public void getFetchAccount() {
		List<Account> accountList = new ArrayList<Account>();
		Account accOne = new Account(1,"logesh","sekar","logesh","passwd","debited",10000.0,"logesh@xyz.com");
		accountList.add(accOne);
		Mockito.when(repo.findAll()).thenReturn(accountList);
		List<Account> list = service.findAll();
		assertThat(list).isEqualTo(accountList);
	}
	
	@Test
	public void getAccount() {
		Account account = new Account();
		account.setId(1);
		account.setFirstName("logesh");
		account.setLastName("sekar");
		account.setUserName("logesh");
		account.setPassword("passwd");
		account.setAccountType("debited");
		account.setBalance(10000.0);
		account.setMobile((long) 1234567890);
		account.setPan("ABCD");
		account.setStatus("Created");
		account.setUpdatedBy("Admin1");
		account.setEmailId("logesh@xyz.com");
		assertTrue(account.getId()==1);
		assertTrue(account.getFirstName()=="logesh");
		assertTrue(account.getLastName()=="sekar");
		assertTrue(account.getUserName()=="logesh");
		assertTrue(account.getPassword()=="passwd");
		assertTrue(account.getAccountType()=="debited");
		assertTrue(account.getBalance()==10000.0);
		assertTrue(account.getMobile()==1234567890);
		assertTrue(account.getPan()=="ABCD");
		assertTrue(account.getStatus()=="Created");
		assertTrue(account.getUpdatedBy()=="Admin1");
		assertTrue(account.getEmailId()=="logesh@xyz.com");
	}
	
	@Test
	public void getTransaction() {
		Transaction trans = new Transaction();
		trans.setAccountNo(1);
		trans.setAmount(1000);
		trans.setBankName("abc");
		trans.setBenName("karthik");
		trans.setDate(new Date());
		trans.setId(1);
		trans.setTransactionType("Debit");
		trans.setUserId(1);
		Date date = new Date();
		assertTrue(trans.getAccountNo()==1);
		assertTrue(trans.getAmount()==1000);
		assertTrue(trans.getBankName()=="abc");
		assertTrue(trans.getBenName()=="karthik");
		assertThat(trans.getDate()).isAfterOrEqualsTo(date);
		assertTrue(trans.getId()==1);
		assertTrue(trans.getTransactionType()=="Debit");
		assertTrue(trans.getUserId()==1);
	}

}
