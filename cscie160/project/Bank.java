package cscie160.project;

import java.rmi.RemoteException;

public interface Bank extends java.rmi.Remote{
	public Account getAccount(AccountInfo accountInfo) throws RemoteException;
}
