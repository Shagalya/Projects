package com.capgemini.service;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientOpeningAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;

public interface Bank {
	public String createAccount(Account account) throws InsufficientOpeningAmountException;
	public String searchAccount(int accountNumber) throws InvalidAccountNumberException;
	public int amountWithdraw(int accountNumber,int amount) throws InsufficientBalanceException, InvalidAccountNumberException;
	public int[] fundTransfer(int receiverAccountNumber , int senderAccountNumber , int amount) throws InvalidAccountNumberException,InsufficientBalanceException;
}
