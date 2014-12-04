package cscie160.project;

@SuppressWarnings("serial")
public class AuthenticationFailedException extends Exception {
	
	public AuthenticationFailedException(String message){
		super(message);
	}
	
	public AuthenticationFailedException(){
		super();
	}
	
}
