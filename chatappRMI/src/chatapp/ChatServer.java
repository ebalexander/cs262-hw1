package chatapp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

/**
 *  An interface for chat application server that can create
 *  and list and delete accounts, create and list groups,
 *  and send messages to be delivered on command.
*/ 
public interface ChatServer extends Remote {

	Boolean createAccount(String loginID)
			throws RemoteException;
	
	void deleteAccount(String userID)
			throws RemoteException;

    void login(ChatClient client) 
    		throws RemoteException;

    void logout(ChatClient client) 
    		throws RemoteException;	
	
	Set<String> getAccounts()
			throws RemoteException;
	
	String createGroup(Set<String> members, String groupID)
			throws RemoteException;
	
 	Set<String> getGroups()
			throws RemoteException;
	
	void sendMessage(Message msg)
			throws RemoteException;
	
	Set<Message> deliverMessages(String toUser)
			throws RemoteException;
	
	String hostname()
			throws RemoteException;
}