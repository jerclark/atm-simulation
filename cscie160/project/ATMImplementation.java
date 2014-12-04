package cscie160.project;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;



/** 
 * ATMImplementation class that represents an ATM engine
 * 12/10/2011
 * @author  Jeremy Clark
 * @version 1.2
 */

public class ATMImplementation extends java.rmi.server.UnicastRemoteObject implements ATM {
	
	private static final long serialVersionUID = 1L;
		
	/** 
	 * Cash left in the ATM - using integer as only whole dollar values can be dispensed.
	 */
	private float _cash;
	
	
	/** 
	 * Clients listening for transaction notifications.
	 */
	private List<ATMListener> _listeners;
	
	
	/** 
	 * A reference to the security authority
	 */
	private Security _security;
	
	
	/** 
	 * A reference to the bank
	 */
	private Bank _bank;

		
	/**
	 * Default no-arg constructor. Looks up the remote Bank and remove Security authority from the rmiregistry, initializes an empty list of ATMListeners,
	 * and initializes the cash available to $500.
	 * @throws RemoteException 
	 * @throws NotBoundException 
	 * @throws MalformedURLException 
	 */
	public ATMImplementation() throws RemoteException, MalformedURLException, NotBoundException{
		_security = (Security)Naming.lookup("//localhost/security");
		_bank = (Bank)Naming.lookup("//localhost/bank");
		_listeners = new ArrayList<ATMListener>();
		_cash = 500;
	}
	
	
	/**
	 * Performs a deposit on the account
	 */
	public void deposit(AccountInfo accountInfo, float amount) throws RemoteException, UnauthorizedTransactionException, AuthenticationFailedException{
		
		//Create the notification
		HashMap<ATM.Transaction, Integer> accountMap = new HashMap<ATM.Transaction, Integer>();
    	accountMap.put(ATM.Transaction.DEPOSIT, accountInfo.getAccountId());
		TransactionNotification n = new TransactionNotification(accountMap, ATM.Transaction.DEPOSIT, amount);
		notifyListeners(n);
		
		//Prepare and validate the transaction
		Account _account = validateTransaction(accountInfo, ATM.Transaction.DEPOSIT);
		
		//Perform the transaction
		_account.deposit(amount);
	}
	
	
	/**
	 * Performs a withdrawl on the account
	 */
    public void withdraw(AccountInfo accountInfo, float amount) throws RemoteException, UnauthorizedTransactionException, AuthenticationFailedException, AccountInsufficientFundsException, ATMInsufficientCashException{
    	
    	//Create the notification
    	HashMap<ATM.Transaction, Integer> accountMap = new HashMap<ATM.Transaction, Integer>();
    	accountMap.put(ATM.Transaction.WITHDRAW, accountInfo.getAccountId());
    	TransactionNotification n = new TransactionNotification(accountMap, ATM.Transaction.WITHDRAW, amount);
    	notifyListeners(n);
    	
    	//Prepare and validate the transaction
    	Account _account = validateTransaction(accountInfo, ATM.Transaction.WITHDRAW);
		
    	//Perform the transaction
    	if (_cash < amount)
			throw new ATMInsufficientCashException("I'm sorry, but I, your humble ATM, don't have that much cash right now!");
    	_account.withdraw(amount);
    	_cash = _cash - amount;
    	
    }
    
    /**
	 * Performs a transfer from one account to another
	 */
    public void transfer(AccountInfo fromAccountInfo, AccountInfo toAccountInfo, float amount) throws RemoteException, UnauthorizedTransactionException, AuthenticationFailedException, AccountInsufficientFundsException, ATMInsufficientCashException{
    	
    	//Create the notification
    	HashMap<ATM.Transaction, Integer> accountMap = new HashMap<ATM.Transaction, Integer>();
    	accountMap.put(ATM.Transaction.WITHDRAW, fromAccountInfo.getAccountId());
    	accountMap.put(ATM.Transaction.DEPOSIT, toAccountInfo.getAccountId());
    	
    	TransactionNotification transferNotification = new TransactionNotification(accountMap, ATM.Transaction.TRANSFER, amount);    	
    	notifyListeners(transferNotification);
    	
    	Account _fromAccount = validateTransaction(fromAccountInfo, ATM.Transaction.WITHDRAW);
    	Account _toAccount = validateTransaction(toAccountInfo, ATM.Transaction.DEPOSIT);
    	
    	//If we get here, then both accounts are authorized to do what's necessary to perform the transfer
    	_fromAccount.withdraw(amount);
    	_toAccount.deposit(amount);

    }

    
    
    /**
	 * Gets the balance of the account
	 * @return Float
	 */
    public Float getBalance(AccountInfo accountInfo) throws RemoteException, UnauthorizedTransactionException, AuthenticationFailedException{
    	
    	HashMap<ATM.Transaction, Integer> accountMap = new HashMap<ATM.Transaction, Integer>();
    	accountMap.put(ATM.Transaction.BALANCE, accountInfo.getAccountId());
    	TransactionNotification n = new TransactionNotification(accountMap, ATM.Transaction.BALANCE, 0);
    	notifyListeners(n);
    	
    	Account _account = validateTransaction(accountInfo, ATM.Transaction.BALANCE); //_accounts.get((Integer)accountNum);
    	return (Float)_account.getBalance();
    }
    
    
    /**
     * Registers an ATMListener to handle events
     */
    public void addListener(ATMListener listener) throws RemoteException{
    	_listeners.add(listener);
    }
    
    
    /**
     * Removes an ATMListener
     */
    public void removeListener(ATMListener listener) throws RemoteException{
    	_listeners.remove(listener);
    }

    
    
    /**
	 * Validates that the transaction can be processed. Calls into the authenticate() and authorizeTransaction() methods on the remote Security object.
	 * @return Account the validated account for the transaction
	 */
    private Account validateTransaction(AccountInfo accountInfo, ATM.Transaction t) throws RemoteException, UnauthorizedTransactionException, AuthenticationFailedException{
    	
    	//Notify all registered listeners of transaction request
    	//if (n != null) notifyListeners(n);
    	
    	//use security service to authenticate account info
    	_security.authenticate(accountInfo);
    	
    	//use security service to authorize operation on account
    	_security.authorizeTransaction(accountInfo, t);
    	
    	//If we get here, we're authenticated and authorized. Use bank to obtain account reference(s)
    	return (Account)_bank.getAccount(accountInfo);
    	
    }
    
    /**
	 * Notifies all registered listeners with the passed in TransactionNotification.
	 */
    private void notifyListeners(TransactionNotification n) throws RemoteException{

    	//Notify all registered listeners of transaction request
    	for (ATMListener l: _listeners){
    		l.handleTransactionNotification(n);
    	}

    }



}
