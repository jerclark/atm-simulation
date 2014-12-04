package cscie160.project;

@SuppressWarnings("serial")
public class AccountInsufficientFundsException extends Exception {
	
	public AccountInsufficientFundsException(String message){
		super(message);
	}
	
	public AccountInsufficientFundsException(){
		super();
	}
}
