import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/*
 * Interface que deve ser implementada pela classe Server
 * para que a comunicação entre os elementos Cliente e Servidor 
 * funcionem corretamente no sistema javaRMI.
 * 
 */

public interface UsersAndMessages extends Remote {

	//Funcoes de interacao entre os elementos Cliente e Servidor
	boolean registryNewUser(String name) throws RemoteException;
	boolean findUser(String name) throws RemoteException;
	ArrayList<String> getUserMessages(String name) throws RemoteException;
	void sendMessageToAll(String message) throws RemoteException;
	boolean sendMessageToOnlyUser(String nickname, String message) throws RemoteException;
}
