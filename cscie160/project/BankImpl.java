package cscie160.project;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;


/** 
 * Bank class
 * 12/10/2011
 * @author  Jeremy Clark
 * @version 1.0
 */

public class BankImpl extends java.rmi.server.UnicastRemoteObject implements Bank{
	

	private static final long serialVersionUID = 1L;
	private Map<Integer,AccountImpl> _accounts;
	
	/**
	 * Default no-arg constructor. Creates 3 accounts and populates the _accounts member collection.
	 */
	public BankImpl() throws RemoteException{
		_accounts = new HashMap<Integer, AccountImpl>();
		_accounts.put((Integer)0000001, new AccountImpl((float)0, 0000001));
		_accounts.put((Integer)0000002, new AccountImpl((float)100, 0000002));
		_accounts.put((Integer)0000003, new AccountImpl((float)500, 0000003));
	}
	
	
	/**
	 * Gets an account from the account collection.
	 * @return Account the account whose ID matches the id of the passed in AccountInfo object
	 */
	public Account getAccount(AccountInfo accountInfo) throws RemoteException{
		return _accounts.get((Integer)accountInfo.getAccountId());
	}

}
