package chatapp;

import java.util.concurrent.TimeUnit;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.HashSet;

public class TestClient {
<<<<<<< HEAD
	private static String hostname = "10.252.192.83";
	private static int port = 1358;
=======
	private static String hostname = "Erics-MacBook-Pro-2.local";
	private static int port = 3550;
>>>>>>> RMI
	public static void main(String[] args) throws RemoteException {
		if (args.length > 0) {
			hostname = args[0];
		}
		
		ChatClient eric = new ChatClientImpl(hostname, port, "Eric");
		ChatClient erik = new ChatClientImpl(hostname, port, "Erik");	
		eric.createAccount();
		erik.createAccount();
		eric.login();
		eric.listAccounts("");
		eric.listAccounts(".*c");
		eric.createGroup(new HashSet<String>(Arrays.asList("Eric","Erik")), "MyGroup");
		eric.listGroups("");
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
<<<<<<< HEAD
		eric.sendMessage("MyGroup", "Hi Eric and Erik");
=======
		eric.sendMessage("MyGroup", "Hi Eric and Alex");
>>>>>>> RMI
		try {
			TimeUnit.SECONDS.sleep(30);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		erik.readMessages();
		eric.logout();
		eric.deleteMyAccount();
		erik.deleteMyAccount();
	}	
}