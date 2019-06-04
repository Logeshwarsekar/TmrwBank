package com.ing.service;

import java.util.List;

import com.ing.entity.Transaction;

public interface FundTransferService {

	String saveTransaction(Transaction transaction);

	List<Transaction> getTransactions(int id);

}
