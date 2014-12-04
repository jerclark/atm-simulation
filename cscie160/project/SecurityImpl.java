package cscie160.project;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/** 
 * SecurityImpl that implements authentication and authorization methods for account access
 * 12/10/2011
 * @author  Jeremy Clark
 * @version 1.0
 */
public class SecurityImpl extends UnicastRemoteObject implements Security {
	
	
	private static final long serialVersionUID = 1L;
	private List<AccountInfo> _credentials;
	private Map<Integer, ArrayList<ATM.Transaction>> _provisions;
	
	
	/** 
	 * No-arg constructor. Creates a list of credentials (AccountInfo objects) that are used to authenticate requests by matching an account ID with a pin.
	 * Creates a list of authorization provisions that map Account ID's with transaction types to validate when accounts allow what types of transactions.
	 * The authorized transaction map looks like this:
	 * Account Number	Deposit			Withdraw		Balance
	 * 0000001			Permitted		Permitted		Permitted
	 * 0000002			Permitted		Not Permitted	Permitted
	 * 0000003			Not Permitted	Permitted		Permitted
	 */
	public SecurityImpl() throws RemoteException {
		
		//Create the authentication values
		_credentials = new ArrayList<AccountInfo>(3);
		_credentials.add(new AccountInfo(0000001,1234));
		_credentials.add(new AccountInfo(0000002,2345));
		_credentials.add(new AccountInfo(0000003,3456));
		
		//Create the authorization values
		_provisions = new HashMap<Integer,ArrayList<ATM.Transaction>>();
		
		//Account 0000001
		_provisions.put((Integer)0000001, new ArrayList<ATM.Transaction>());
		_provisions.get((Integer)0000001).add(ATM.Transaction.DEPOSIT);
		_provisions.get((Integer)0000001).add(ATM.Transaction.WITHDRAW);
		_provisions.get((Integer)0000001).add(ATM.Transaction.BALANCE);
		
		//Account 0000002
		_provisions.put((Integer)0000002, new ArrayList<ATM.Transaction>());
		_provisions.get((Integer)0000002).add(ATM.Transaction.DEPOSIT);
		_provisions.get((Integer)0000002).add(ATM.Transaction.BALANCE);
		
		//Account 0000003
		_provisions.put((Integer)0000003, new ArrayList<ATM.Transaction>());
		_provisions.get((Integer)0000003).add(ATM.Transaction.WITHDRAW);
		_provisions.get((Integer)0000003).add(ATM.Transaction.BALANCE);		
		
	}
	
	/** 
	 * Checks to see if the credentials list contains an object equal to the passed in AccountInfo. Since equals and hashCode() are overridden on 
	 * AccountInfo, this comparison is checking the object state, not the object reference. If the AccountInfo doesn't exist, an AuthenticationFailedException is thrown.
	 */
	public void authenticate(AccountInfo accountInfo) throws RemoteException, AuthenticationFailedException {
		if (!_credentials.contains(accountInfo))
			throw new AuthenticationFailedException("Authentication Failed.");
	}
	
	
	/** 
	 * Checks to see if the passed in transactionType is allowed for the account represented by AccountInfo. If not, it throws UnauthorizedTransactionException.
	 */
	public void authorizeTransaction(AccountInfo accountInfo,
			ATM.Transaction transactionType) throws RemoteException, UnauthorizedTransactionException {
		Integer accountId = (Integer)accountInfo.getAccountId();
		ArrayList<ATM.Transaction> authorizedTransactions = _provisions.get(accountId);
		if (!authorizedTransactions.contains(transactionType))
			throw new UnauthorizedTransactionException("Not authroized to perform transaction " + transactionType.toString() + " on account " + accountInfo.getAccountId());

	}

}
