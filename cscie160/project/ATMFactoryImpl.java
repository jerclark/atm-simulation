package cscie160.project;

import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


/** 
 * ATMFactoryImpl class that returns an ATM object.
 * 12/8/2011
 * @author  Jeremy Clark
 * @version 1.2
 */

public class ATMFactoryImpl extends UnicastRemoteObject implements ATMFactory{
	
	private static final long serialVersionUID = 1L;

	public ATMFactoryImpl() throws RemoteException {};
	
	/** 
	 * Constructs a new ATMImplementation object which looks up the Security and Bank objects from the registry. As such, we need to throw MalformedURLException and NotBoundExceptions.
	 */
	public ATM getATM() throws RemoteException, MalformedURLException, NotBoundException{
		return new ATMImplementation();
	}
	
}
