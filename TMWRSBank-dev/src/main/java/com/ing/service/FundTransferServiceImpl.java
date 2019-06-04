package com.ing.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ing.dao.TransactionDao;
import com.ing.dao.UserDao;
import com.ing.entity.Account;
import com.ing.entity.Transaction;

@Service
@Transactional
public class FundTransferServiceImpl implements FundTransferService{

	@Autowired
	UserDao userRepo;
	@Autowired
	TransactionDao repository;
	@Override
	public String saveTransaction(Transaction transaction) {
		
		Account account =userRepo.findAccountById(transaction.getUserId());
		 if(account.getBalance()<transaction.getAmount()) {
			 return "Insufficient Balance";
		 }
		 else {
			account.setBalance(account.getBalance()-transaction.getAmount());
			userRepo.save(account); 
			repository.save(transaction);
		  return "Saved Successfully";
		 }
		
		
	}
	
	public List<Transaction> getTransactions(int id) {
	
		return repository.findTransactionsById(id);
	}

}
