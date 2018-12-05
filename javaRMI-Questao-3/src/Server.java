import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/*
 * Esta classe e responsavel pelo elemento SERVIDOR do sistema javaRMI.
 * E instanciada uma unica vez e se comunica com todos os clients que
 * a buscarem no REGISTRO.
 * 
 */

@SuppressWarnings("unused")
public class Server implements UsersAndMessages{

	//ATRIBUTOS DO SERVIDOR
	public ArrayList<User> users = new ArrayList<User>();

	
	//CONSTRUTOR
	public Server() {}
	
	
	//METODO MAIN DO SERVIDOR
	public static void main (String args[]) {
		
		try {
			
			
			Server obj = new Server();
			
			/*
			 * Exportanto o objeto remoto fornecido, visando receber invocacoes de metodos remotos de
			 * entrada, e retorna o stub para o objeto remoto transmiti-lo aos clientes.
			 * 
			 */
			UsersAndMessages stub = (UsersAndMessages) UnicastRemoteObject.exportObject(obj, 0);
			
			/*
			 * Neste programa pode-se registrar usuarios em tempo de execucao
			 * entretanto alguns usuarios ja estao cadastrados por default
			 * 
			 */
			obj.addDefaultUsers();
			
			//Buscando o registro do javaRMI
			Registry registry = LocateRegistry.getRegistry(8080);
			
			//Registrando e nomeando este servidor no registro
			registry.bind("fga", stub);
			
			System.err.println("Servidor pronto!");
			
		} catch(Exception e) {
			
			System.err.println("Server excepion: " + e.toString());
			e.printStackTrace();
			
		}
		
	}
	
	
	
	//METODOS DE REGISTRO E LOGIN DE USUARIOS
	public boolean registryNewUser(String n) {
		
		boolean validate = true;
		
		for(User user : users) {
			if(user.getUserNickname().equals(n)) {
				validate = false;
			} 
		}
		
		if(validate) {
			users.add(new User(n));
			System.out.println("Usuario cadastrado com sucesso!");
			return true;
		} else {
			System.out.println("O usuario nao pode ser cadastrado!");
			return false;
		}

	}
	
	public boolean findUser(String n) {
		
		for(User user: users) {
			if(user.getUserNickname().equals(n)) {
				System.out.println("Usuario encontrado!");
				return true;
			}
		}
		
		System.out.println("Usuario nao encontrado no registro do servidor!");
		return false;
	}
	
	public ArrayList<String> getUserMessages(String n) {
		
		for(User user : users) {
			
			if(user.getUserNickname().equals(n)) {
				System.out.println("Usuario encontrado!");
				return user.getUserMessages();
			}
			
		}
		
		System.out.println("Usuario nao encontrado!");
		return null;
		
	}
	
	
	//METODOS REFERENTES AO ENVIO DE MENSAGENS PUBLICAS E PRIVADAS
	public void sendMessageToAll(String message) {
		
		for(User user : users) {
			user.addMessage(message);
		}
		
	}
	
	public boolean sendMessageToOnlyUser(String nick, String message) {
		
		if(!findUser(nick)) {
			
			System.out.println("Destinatario nao encontrado!");
			return false;
		
		} else {
			
			for(User user : users) {
				
				if(user.getUserNickname().equals(nick)) {
					user.addMessage(message);
					System.out.println("Mensagem enviada ao destinatario!");
					return true;
				} 
			}
			
			System.out.println("Nao foi possivel mandar a mensagem ao destinatario");
			return false;
		}
	}
	
	
	/*
	 * O metodo a baixo adiciona objetos remotos visando
	 * tornar mais facil a primeira utilizacao do programa.
	 */
	private void addDefaultUsers() {
		registryNewUser("Lucas");
		registryNewUser("Kleber");
		registryNewUser("Marcus");
		registryNewUser("DrZed");
	}
}

