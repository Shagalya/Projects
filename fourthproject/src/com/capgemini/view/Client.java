package com.capgemini.view;
import java.util.Scanner;

import com.capgemini.beans.Account;
import com.capgemini.exceptions.InsufficientBalanceException;
import com.capgemini.exceptions.InsufficientOpeningAmountException;
import com.capgemini.exceptions.InvalidAccountNumberException;
import com.capgemini.service.ICICIBank;

public class Client {
	public static void main(String[] args) {
		Account account = new Account();
		int accountNumber;
		int amount;
		ICICIBank bank = new ICICIBank();
		Scanner sc = new Scanner(System.in);
		while(true) {
		System.out.println("MENU");
		System.out.println("1. Create Account");
		System.out.println("2. Deposit Amount");
		System.out.println("3. Withdraw Amount");
		System.out.println("4. Validate Account");
		System.out.println("5. Fund Transfer");
		System.out.println("6. Exit");
		System.out.println(" Enter the choice");
		int choice= sc.nextInt();
		
		switch(choice) {
		case 1: try {
				System.out.println("Enter Account number");
				accountNumber = sc.nextInt();
				System.out.println("Enter Amount ");
				amount = sc.nextInt();
				account.setAccountNumber(accountNumber);
				account.setAmount(amount);
				System.out.println(bank.createAccount(account));
				}
				catch(InsufficientOpeningAmountException ioae) {
					System.out.println(ioae.getMessage()); 
				}
				break;
				
		case 2:	try{
					System.out.println("Enter the account number ");
					accountNumber = sc.nextInt();
					System.out.println("Enter the amount");
					amount = sc.nextInt();
					System.out.println("Balance = "+bank.amountDeposit(accountNumber , amount));
				}
				catch(InvalidAccountNumberException iane) {
					System.out.println(iane.getMessage());
					
				}
					break;
		
		case 3: try {
				System.out.println("Enter the account number  ");
				accountNumber = sc.nextInt();
				System.out.println("Enter the amount");
				amount = sc.nextInt();
				int balance = bank.amountWithdraw(accountNumber , amount) ;
				System.out.println("Remaining Balance=" +balance);
				}			
				catch(InsufficientBalanceException ibe) {
					System.out.println(ibe.getMessage());
				}
				catch(InvalidAccountNumberException iane) {
					System.out.println(iane.getMessage());
				}
						
				break;
			
		case 4: try {
			    System.out.println("Enter account number to be searched");
			    accountNumber = sc.nextInt();
			    System.out.println(bank.searchAccount(accountNumber));
				}
				catch(InvalidAccountNumberException iane) {
					System.out.println(iane.getMessage());
				}
				break;
				
		case 5 : try{
				 System.out.println("Enter your account number");
				 int sender = sc.nextInt();
				 System.out.println("Enter account number for transfer");
				 int receiver = sc.nextInt();
				 System.out.println("Enter amount to be transfered");
				 amount = sc.nextInt();
				 int result[] = bank.fundTransfer(receiver , sender , amount); 
				 System.out.println("balance ="+ result[0] +"  "+result[1]);
				 }
				catch(InvalidAccountNumberException iane) {
					System.out.println(iane.getMessage());
				}
				catch(InsufficientBalanceException ibe) {
					System.out.println(ibe.getMessage());
				}
				 break;
				 
		case 6 : System.exit(0);;

		}
		}
		
	}


}
