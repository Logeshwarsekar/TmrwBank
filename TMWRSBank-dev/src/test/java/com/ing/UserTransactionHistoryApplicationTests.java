package com.ing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ing.dao.TransactionDao;
import com.ing.dao.UserDao;
import com.ing.entity.Account;
import com.ing.entity.Transaction;
import com.ing.request.SaveAccountRequest;
import com.ing.response.SaveAccountResponse;
import com.ing.service.FundTransferServiceImpl;
import com.ing.service.UserService;
import com.ing.service.UserServiceImp;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest
//@ContextConfiguration(classes = UserTransactionHistoryApplication.class)
public class UserTransactionHistoryApplicationTests {
	
	@InjectMocks
	UserServiceImp service;
	
	@Mock
	UserDao repo;
	
	@Mock
	TransactionDao transRepo;
	@InjectMocks
	FundTransferServiceImpl fundService;
	
	
	@Autowired
    private MockMvc mockMvc;

	@MockBean
	private UserService ser;
	
	
//    @Test
 //  public void shouldReturnDefaultMessage() throws Exception {
 //       this.mockMvc.perform(post("/addaccount"))
   //                 .andDo(print())
     //               .andExpect(status().isOk())
       //             .andExpect(content()
         //       	.string(containsString("success")));
			// }

//https://howtodoinjava.com/spring-boot2/spring-boot-mockmvc-example/
//https://medium.com/@gustavo.ponce.ch/spring-boot-restful-junit-mockito-hamcrest-eclemma-5add7f725d4e
	
	@Test
	public void addAccountTest() throws Exception 
	{
		mockMvc.perform( MockMvcRequestBuilders
	      .post("/addaccount")
	      .content(asJsonString(new Account(1,"logesh","sekar","logesh","passwd","credit",1000.0,"logesh@xyz.com")))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.status").exists());
	}
	 
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	
	@Test
	public void updateEmployeeAPI() throws Exception 
	{
	  mockMvc.perform( MockMvcRequestBuilders
	      .put("/updateaccountstatus")
	      .content(asJsonString(new SaveAccountResponse()))
	      .contentType(MediaType.APPLICATION_JSON)
	      .accept(MediaType.APPLICATION_JSON))
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("Success"));
	     // .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("lastName2"))
	      //.andExpect(MockMvcResultMatchers.jsonPath("$.email").value("email2@mail.com"));
	}
	
	
	@Test
	public void getAllAccount() throws Exception 
	{
		mockMvc.perform( MockMvcRequestBuilders
	      .get("/fetchaccount")
	      .accept(MediaType.APPLICATION_JSON))
	      .andDo(print())
	      .andExpect(status().isOk())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.SaveAccountResponse").exists())
	      .andExpect(MockMvcResultMatchers.jsonPath("$.SaveAccountResponse[*].id").isNotEmpty());
	}
	 
	
	//private WebMvc
	@Test
	public void testSaveAccounts() {
		SaveAccountRequest request = new SaveAccountRequest();
		Account acc = new Account();
		acc.setId(1);
		Mockito.when(repo.save(acc)).thenReturn(acc);
		 SaveAccountResponse response = service.saveAccount(request);
		//assertEquals(response.getStatus(), "Sucess");
		// asser
		 assertNotNull(response);
		
	}
	
	@Test
	public void whenGettingTheUser() {
		Account account =new Account(1,"karthik","raja",1234565,"Raja","DPJk12","passwd","savings",10000.0,"nill","logesh","kk@gmail");
		
		Mockito.when(repo.findAccountById(1)).thenReturn(account);
		Account acc= service.getAccountById(account.getId());
		
		assertThat(account).isEqualTo(acc);
	}
	
	@Test
	public void whenGettingTransactions() {
		List<Transaction> transaction =new ArrayList<Transaction>();
		transaction.add(new Transaction(1,"logesh",2,1000.0,"debit","hdfc",1));
		Mockito.when(transRepo.findTransactionsById(1)).thenReturn(transaction);
		
		List<Transaction> list =fundService.getTransactions(1);
		
		assertThat(transaction.size()).isEqualTo(list.size());
		
	}
}
