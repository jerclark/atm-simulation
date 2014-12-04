package cscie160.project;

import java.rmi.RemoteException;


public interface ATMListener extends java.rmi.Remote {
	public void handleTransactionNotification(TransactionNotification n) throws RemoteException;
}
