package cscie160.project;

import java.rmi.RemoteException;

public interface ATM extends java.rmi.Remote {
	
	/**
	* ATM Transactions enumerator. Used by security to defined authorized transactions.
	*/
	public enum Transaction{
		DEPOSIT, WITHDRAW, BALANCE, TRANSFER
	}
	
    public void deposit(AccountInfo accountInfo, float amount) throws RemoteException, UnauthorizedTransactionException, AuthenticationFailedException;
    public void withdraw(AccountInfo accountInfo, float amount) throws RemoteException, UnauthorizedTransactionException, AuthenticationFailedException, AccountInsufficientFundsException, ATMInsufficientCashException;
    public void transfer(AccountInfo fromAccountInfo, AccountInfo toAccountInfo, float amount) throws RemoteException, UnauthorizedTransactionException, AuthenticationFailedException, AccountInsufficientFundsException, ATMInsufficientCashException;
    public Float getBalance(AccountInfo accountInfo) throws RemoteException, UnauthorizedTransactionException, AuthenticationFailedException;
    public void addListener(ATMListener listener) throws RemoteException;
    public void removeListener(ATMListener listener) throws RemoteException;
    
}
