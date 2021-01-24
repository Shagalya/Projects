package com.capgemini.exceptions;

public class InsufficientBalanceException extends Exception {
	public InsufficientBalanceException(String errMsg){
		super(errMsg);
	}
}
