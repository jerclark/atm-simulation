package cscie160.project;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class TransactionNotification implements Serializable {
	
	private static final long serialVersionUID = -2983929036969370302L;
	private Map<ATM.Transaction, Integer> _accountMap;
	private ATM.Transaction _transaction;
	private float _transactionAmount;
	
	
	public TransactionNotification(Map<ATM.Transaction, Integer> accountMap, ATM.Transaction transaction, float transactionAmount){
		_accountMap = new HashMap<ATM.Transaction, Integer>(accountMap);
		_transaction = transaction;
		_transactionAmount = transactionAmount;
	}
		
	
	protected ATM.Transaction getTransaction(){
		return _transaction;
	}
	
	
	public String toString(){
		String s = "Unknown Transaction";
		
		//Deposit or Withdraw
		if (_transaction.equals(ATM.Transaction.DEPOSIT) || _transaction.equals(ATM.Transaction.WITHDRAW)){
			s = _transaction.toString() + " $" +  _transactionAmount + " on account: " + _accountMap.get(_transaction);
		
		//Transfer
		}else if (_transaction.equals(ATM.Transaction.TRANSFER)){
			Integer fromAccountId = _accountMap.get(ATM.Transaction.WITHDRAW);
			Integer toAccountId = _accountMap.get(ATM.Transaction.DEPOSIT);
			s = _transaction.toString() + " $" +  _transactionAmount + " FROM account: " + fromAccountId + " TO account: " + toAccountId;
		
		//Balance inquiry
		}else if (_transaction.equals(ATM.Transaction.BALANCE)){
			s = "Performing balance inquiry on account: " + _accountMap.get(_transaction);
		}
		
		return s;
	
	}

}
