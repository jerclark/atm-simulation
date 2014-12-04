package cscie160.project;

@SuppressWarnings("serial")
public class ATMInsufficientCashException extends Exception {
	
	public ATMInsufficientCashException(String message){
		super(message);
	}
	
	public ATMInsufficientCashException(){
		super();
	}

}
