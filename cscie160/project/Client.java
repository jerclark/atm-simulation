package cscie160.project;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NoSuchObjectException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.UnknownHostException;
import java.rmi.server.UnicastRemoteObject;

/** 
 * Client class that exercises the system. Subclasses UnicastRemoteObject because it registers itself to handle notifications
 * generated in the remote ATM.
 * 12/10/2011
 * @author  Jeremy Clark
 * @version 1.0
 */
public class Client extends UnicastRemoteObject implements ATMListener {

	
	private static final long serialVersionUID = 1L;


	//STATIC METHODS	
	/** 
	 * main() method does the following:
	 * 1) Looks up the ATMFactory from the registry, gets an ATM
	 * 2) Instantiates a client object and adds that object as a listener to the ATM
	 * 3) Performs the tests defined as static on the Client class
	 * 4) Unexports the client object from the registry. 
	 */
	public static void main(String[] args) {
		ATM atm = null;
		Client client = null;
		try {
			ATMFactory factory = (ATMFactory)Naming.lookup("//localhost/atmfactory");
			client = new Client();
			atm = factory.getATM();
			atm.addListener(client);
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (NotBoundException nbe) {
			nbe.printStackTrace();
		} catch (UnknownHostException uhe) {
			uhe.printStackTrace();
		} catch (RemoteException re) {
			re.printStackTrace();
		}

		Client.testATM(atm);
		
		
		//Remove the client from the registry
		try {
			atm.removeListener(client);
			UnicastRemoteObject.unexportObject(client, true);
		} catch (NoSuchObjectException e) {
			System.out.println("No client was found to unexport from the registry");
		} catch (RemoteException e) {
			System.out.println("There was an issue removing the client from listening to the ATM");
		}
		

	}
	
	
	public static void testATM(ATM atm) {
		if (atm!=null) {
			printBalances(atm);
			performTestOne(atm);
			performTestTwo(atm);
			performTestThree(atm);
			performTestFour(atm);
			performTestFive(atm);
			performTestSix(atm);
			performTestSeven(atm);
			performTestEight(atm);
			performTestNine(atm);
			printBalances(atm);
		}
	}        
	
	public static void printBalances(ATM atm) {        
		try {
			System.out.println("Balance(0000001): "+atm.getBalance(getAccountInfo(0000001, 1234)));
			System.out.println("Balance(0000002): "+atm.getBalance(getAccountInfo(0000002, 2345)));
			System.out.println("Balance(0000003): "+atm.getBalance(getAccountInfo(0000003, 3456)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void performTestOne(ATM atm) {       
		try {
			atm.getBalance(getAccountInfo(0000001, 5555));
		} catch (Exception e) {
			System.out.println("Failed as expected: "+e);
		}
	}
	public static void performTestTwo(ATM atm) {       
		try {
			atm.withdraw(getAccountInfo(0000002, 2345), (float)500);
		} catch (Exception e) {
			System.out.println("Failed as expected: "+e);
		}
	}
	public static void performTestThree(ATM atm) {        
		try {
			atm.withdraw(getAccountInfo(0000001, 1234), (float)50);
		} catch (Exception e) {
			System.out.println("Failed as expected: "+e);
		}
	}
	public static void performTestFour(ATM atm) {         
		try {
			atm.deposit(getAccountInfo(0000001, 1234), (float)500);
		} catch (Exception e) {
			System.out.println("Unexpected error: "+e);
		}
	}
	public static void performTestFive(ATM atm) {         
		try {
			atm.deposit(getAccountInfo(0000002, 2345), (float)100);
		} catch (Exception e) {
			System.out.println("Unexpected error: "+e);
		}
	}
	public static void performTestSix(ATM atm) {       
		try {
			atm.withdraw(getAccountInfo(0000001, 1234), (float)100);
		} catch (Exception e) {
			System.out.println("Unexpected error: "+e);
		}
	}
	public static void performTestSeven(ATM atm) {        
		try {
			atm.withdraw(getAccountInfo(0000003, 3456), (float)300);
		} catch (Exception e) {
			System.out.println("Unexpected error: "+e);
		}
	}
	public static void performTestEight(ATM atm) {        
		try {
			atm.withdraw(getAccountInfo(0000001, 1234), (float)200);
		} catch (Exception e) {
			System.out.println("Failed as expected: "+e);
		}
	}
	public static void performTestNine(ATM atm) {        
		try {
			atm.transfer(getAccountInfo(0000001, 1234),getAccountInfo(0000002, 2345), (float)100);
		} catch (Exception e) {
			System.out.println("Unexpected error: "+e);
		}
	}

	
	/**
	 * Convenience method to create an AccountInfo object
	 */
	private static AccountInfo getAccountInfo(int accountId, int pinNumber){
		return new AccountInfo(accountId, pinNumber);
	}

	
	
	
	//INSTANCE METHODS
	
	/**
	 * Default Constructor
	 */
	public Client() throws RemoteException{}
	
	
	/**
	 * Method to handle notifications from the ATMs to which we are listening. 
	 */
	public void handleTransactionNotification(TransactionNotification n) throws RemoteException{
		System.out.println(n);
	}


}