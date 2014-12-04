package cscie160.project;

import java.rmi.RemoteException;

public class ATMException extends RemoteException 
{
	
	private static final long serialVersionUID = 1L;

	public ATMException(String msg) 
	{
		super(msg);
    }
}
