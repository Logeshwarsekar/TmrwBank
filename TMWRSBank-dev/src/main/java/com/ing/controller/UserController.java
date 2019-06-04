package com.ing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ing.entity.Account;
import com.ing.entity.Transaction;
import com.ing.request.SaveAccountRequest;
import com.ing.response.SaveAccountResponse;
import com.ing.response.SaveFundTransferResponse;
import com.ing.response.UpdateAccountRequest;
import com.ing.service.FundTransferService;
import com.ing.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class UserController {
	
	@Autowired
	UserService service;
	
	@Autowired
	FundTransferService fundService;
	
	@PostMapping(value = "/addaccount")
	public SaveAccountResponse saveAccount(@RequestBody SaveAccountRequest request) {
		SaveAccountResponse response = new SaveAccountResponse();
		String msg = "Success";
		try {
			service.saveAccount(request);
		} catch(Exception e) {
			msg = "Failure";
		}
		response.setStatus(msg);
		return response;
	}
	
	@PutMapping(value = "/updateaccountstatus")
	public SaveAccountResponse updateAccount(@RequestBody UpdateAccountRequest request) {
		SaveAccountResponse response = new SaveAccountResponse();
		String msg = "Success";
		try {
			service.updateAccount(request);
		} catch(Exception e) {
			e.printStackTrace();
			msg = "Failure";
		}
		response.setStatus(msg);
		return response;
	}
	
	@GetMapping(value="/fetchaccount")
	 public List<Account> fetchAccount(){
	  List<Account> list = service.findAll();
	  
	  return list;
	 }
	
	@PostMapping("/fundtranfer")
	public SaveFundTransferResponse fundTransfer(@RequestBody Transaction transaction) {
		String trans = fundService.saveTransaction(transaction);
		SaveFundTransferResponse response = new SaveFundTransferResponse();
		if(trans.equalsIgnoreCase("Insufficient Balance") ){
			 response.setStatus("failed to save");
			 return response;
		}
		else {
			response.setStatus("success");
			return response;
		}
	}
	

	

}
