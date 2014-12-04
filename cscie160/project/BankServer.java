package cscie160.project;

import java.rmi.*;


/** 
 * BankServer class that binds Bank and Security objects to the registry
 * 12/10/2011
 * @author  Jeremy Clark
 * @version 1.0
 */
public class BankServer
{

   public static void main(String args[])
   {
	try {
		
		//Load bank into registry
	    BankImpl bank = new BankImpl();
	    Naming.rebind("//localhost/bank", bank);
	    System.out.println("Bank bound in registry");
	    
	    //Load Security into registry
	    SecurityImpl security = new SecurityImpl();
	    Naming.rebind("//localhost/security", security);
	    System.out.println("Security bound in registry");
	    
	} catch (Exception e) {
	    System.out.println("BankServer err: " + e.getMessage());
	    e.printStackTrace();
	}
   }

}
