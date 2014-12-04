package cscie160.project;

import java.io.Serializable;
import java.rmi.RemoteException;


/** 
 * Account class that represents a simple bank account
 * Subclasses UnicastRemoteObject because Accounts are passed between the ATMImplementation and Bank remote objects.
 * 12/8/2011
 * @author  Jeremy Clark
 * @version 1.2
 */
public class AccountImpl extends java.rmi.server.UnicastRemoteObject implements Serializable, Account {
	
	private static final long serialVersionUID = 1L;
	private final int _number;
	private float _balance;
	
	
	/**
	 * Default constructor. Takes a float value as an initial balance.
	 * @param balance
	 */
	public AccountImpl(float balance, int number) throws RemoteException{
		_balance = balance;
		_number = number;
	}
	
	
	/**
	 *Deposit funds into the account
	 */
	public void deposit(float amount) throws RemoteException{
		_balance = _balance + amount;
	}

	
	/**
	 *Withdraw funds from the account. Checks to see if there's enough money in the account, and if not, throws the AccountInsufficientFundsException.
	 */
	public void withdraw(float amount) throws RemoteException, AccountInsufficientFundsException{
		if (_balance >= amount){
			_balance = _balance - amount;
		}else{
			throw(new AccountInsufficientFundsException("Insufficient funds available (Balance: " + _balance + ") for withdrwal of " + amount + " in Account: " + _number));
		}
	}
	
	
	/**
	 *Get the balance of the account
	 */
	public Float getBalance() throws RemoteException{
		return (Float)_balance;
	}
	
	
	/**
	 *Get the account number
	 */
	public int getNumber() throws RemoteException{
		return _number;
	
	}	
	

}
