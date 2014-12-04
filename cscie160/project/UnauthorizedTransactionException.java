package cscie160.project;

@SuppressWarnings("serial")
public class UnauthorizedTransactionException extends Exception {
	
	public UnauthorizedTransactionException(String message){
		super(message);
	}
	
	public UnauthorizedTransactionException(){
		super();
	}
	
}
