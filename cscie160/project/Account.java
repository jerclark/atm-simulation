package cscie160.project;

import java.rmi.RemoteException;

public interface Account extends java.rmi.Remote {

	/**
	 *Deposit funds into the account
	 */
	public void deposit(float amount) throws RemoteException;
	
	
	/**
	 *Withdraw funds from the account
	 */
	public void withdraw(float amount) throws RemoteException, AccountInsufficientFundsException;
	
	
	/**
	 *Get the balance of the account
	 */
	public Float getBalance() throws RemoteException;
	
	
	/**
	 *Get the account number
	 */
	public int getNumber() throws RemoteException;
	
}
