package cscie160.project;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public interface ATMFactory extends java.rmi.Remote {
	public ATM getATM() throws RemoteException, MalformedURLException, NotBoundException;
}
