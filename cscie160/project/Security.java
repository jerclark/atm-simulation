package cscie160.project;

import java.rmi.RemoteException;


public interface Security extends java.rmi.Remote {
	public void authenticate(AccountInfo accountInfo) throws RemoteException, AuthenticationFailedException;
	public void authorizeTransaction(AccountInfo accountInfo, ATM.Transaction transactionType) throws RemoteException, UnauthorizedTransactionException;
}
