package com.capgemini.service;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.Statement;
import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientOpeningAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.utility.JDBCConnection;


public class ICICIBank implements Bank {
	ResultSet rs;
	Connection connectionCall = JDBCConnection.connectdb();
	
	public String createAccount(Account account) throws InsufficientOpeningAmountException{
		
		try {
			Statement st = connectionCall.createStatement();
			if(account.getAmount()<500) {
				throw new InsufficientOpeningAmountException("Insufficient amount to create a account");
			}
			st.executeUpdate("insert into accounttable values("+account.getAccountNumber()+","+account.getAmount()+" )");	
			return "Account Created";
			
		}
		catch(InsufficientOpeningAmountException ioae) {
			return ioae.getMessage();
		}
		catch(Exception e) {
			return "Account Not Created Since the Account Number already present";
		}
		
	}
	
	public String searchAccount(int accountNumber) throws InvalidAccountNumberException  {
			try{
				Statement st = connectionCall.createStatement();
                rs = st.executeQuery("select accountNumber from accounttable where accountNumber = '"+accountNumber+"'");
				rs.last();
				if(rs.getRow() == 0) {
					throw new InvalidAccountNumberException("Invalid account number");
				}
				else {
				return "Account found";	
				}
			}
			catch(InvalidAccountNumberException iane) {
				return iane.getMessage();
			}
			catch(Exception e) {
				return e.getMessage();
			}
	}
	
	public int amountWithdraw(int accountNumber , int amount) throws InsufficientBalanceException , InvalidAccountNumberException{
		try{
			searchAccount(amount);
			Statement st = connectionCall.createStatement();
			rs = st.executeQuery("SELECT * from accounttable WHERE accountNumber = '"+accountNumber+"'");
			rs.next();
			if(rs.getInt("amount") > amount) {
				int amountUpdate = rs.getInt("amount") - amount;
				String s ="UPDATE accounttable SET amount = '"+amountUpdate+"' WHERE accountNumber = '"+accountNumber+"'";
				st.executeUpdate(s);
				rs = st.executeQuery("SELECT amount from accounttable WHERE accountNumber = '"+accountNumber+"'");			
				rs.next();
				return rs.getInt("amount");
			}
			else {
			throw new InsufficientBalanceException("Insufficient balance");
			}
		}
		catch(Exception e) {
			throw new InvalidAccountNumberException("Invalid Account Number");
		}
	}
	
	public int amountDeposit(int accountNumber , int amount) throws InvalidAccountNumberException{
		try {
		searchAccount(accountNumber);
		Statement st = connectionCall.createStatement();
		rs = st.executeQuery("SELECT amount from accounttable WHERE accountNumber = '"+accountNumber+"'");
		rs.next();
		int amountUpdate = rs.getInt("amount") + amount;
		st.executeUpdate("UPDATE accounttable SET amount = '"+amountUpdate+"' WHERE accountNumber = '"+accountNumber+"'");
		rs = st.executeQuery("SELECT amount from accounttable WHERE accountNumber = '"+accountNumber+"'");
		rs.next();
		return rs.getInt("amount");
		}
		catch(InvalidAccountNumberException iane) {
			throw new InvalidAccountNumberException("Invalid Account Number");
		}
		catch(Exception e) {
			throw new InvalidAccountNumberException("Invalid Account Number");
		}	
	}
	
	public int[] fundTransfer(int receiverAccountNumber , int senderAccountNumber , int amount) throws InvalidAccountNumberException,InsufficientBalanceException {
		try{
			Statement st = connectionCall.createStatement();
			
			searchAccount(senderAccountNumber);
			
			ResultSet sender = st.executeQuery("Select amount from accounttable WHERE accountNumber = '"+senderAccountNumber+"'");
			sender.next();
			if(sender.getInt("amount") < amount) {
				throw new InsufficientBalanceException("Insufficient Balance");
			}
			
			searchAccount(receiverAccountNumber);
			
			ResultSet receiver = st.executeQuery("SELECT amount from accounttable WHERE accountNumber = '"+receiverAccountNumber+"'");
			receiver.next();
			int amountUpdate = receiver.getInt("amount") + amount;
			st.executeUpdate("UPDATE accounttable SET amount = '"+amountUpdate+"' WHERE accountNumber = '"+senderAccountNumber+"'");
			
			sender = st.executeQuery("SELECT amount from accounttable WHERE accountNumber = '"+senderAccountNumber+"'");
			sender.next();
			int amountUpdate1 = sender.getInt("amount") - amount;
			st.executeUpdate("UPDATE accounttable SET amount = '"+amountUpdate1+"' WHERE accountNumber = '"+senderAccountNumber+"'");
			
			int[] total = {amountUpdate,amountUpdate1};
			return total;
			}
		catch(InvalidAccountNumberException iane) {
			throw new InvalidAccountNumberException("Invalid Account Number");
		}
		
		catch(Exception e) {
			throw new InsufficientBalanceException("Insufficient balance");
		}
		
	}
	
}