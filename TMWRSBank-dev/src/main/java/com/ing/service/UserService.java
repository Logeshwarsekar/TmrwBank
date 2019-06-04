package com.ing.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import com.ing.entity.Account;
import com.ing.request.SaveAccountRequest;
import com.ing.response.SaveAccountResponse;
import com.ing.response.UpdateAccountRequest;

public interface UserService {
	public SaveAccountResponse saveAccount(SaveAccountRequest request);
	public SaveAccountResponse updateAccount(@RequestBody UpdateAccountRequest request);
	public List<Account> findAll();
	Account getAccountById(int id);

}
