package cscie160.project;

import java.rmi.*;

/** 
 * ATMServer class that binds an ATMFactory to the registry
 * 12/10/2011
 * @author  Jeremy Clark
 * @version 1.0
 */
public class ATMServer
{

   public static void main(String args[])
   {
	try {
	    ATMFactoryImpl atmFactory = new ATMFactoryImpl();
	    Naming.rebind("//localhost/atmfactory", atmFactory);
	    System.out.println("ATMFactory bound in registry");
	} catch (Exception e) {
	    System.out.println("ATMFactory err: " + e.getMessage());
	    e.printStackTrace();
	}
   }

}
