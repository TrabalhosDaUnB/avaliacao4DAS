import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Scanner;

/*
 * Esta classe e responsavel pelo elemento 'CLIENTE' do javaRMI.
 * Poder ser instanciada varias e simultaneas vezes em linha de comando.
 * 
 */

public class Client {

	static Scanner scan = new Scanner(System.in);
	static boolean userIsOn = false;
	static UsersAndMessages stub = null;
	static String userNickname = null;
	static ArrayList<String> userMessages = null;
	
	private Client() {}
	
	
	public static void main(String[] args) throws RemoteException {
	
		getServerStub(args);
		userIsOn = true;
		userInteractionLoop();
		System.out.println("Cliente encerrado!");
	
	}
	
	static void getServerStub(String[] args) {
		
		try {
			
			
			/*
			 * Neste passo ha a coleta do REGISTRO do sistema RMI
			 */
			Registry registry = LocateRegistry.getRegistry(8080);
			
			
			/*
			 * Busca-se o STUB par a comunicacao deste cliente ao server 'fga'
			 */
			stub = (UsersAndMessages) registry.lookup("fga");
			
		} catch(Exception e) {
			
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
			
		}	
	}
	
	
	// TELAS E METODOS DE REGISTRO E LOGIN DE NOVO USUARIO (OBJETOS REMOTOS)
	static void userInteractionLoop() throws RemoteException {
		
		while(userIsOn) {
			
			verifyUserRegistration();
			
		}
		
	}
	
	static void verifyUserRegistration() throws RemoteException {
		
		int opt;
		System.out.println("");
		System.out.println("====================================================");
		System.out.println("======MENU==DE==LOGIN==OU==REGISTRO=================");
		System.out.println("====================================================");
		System.out.println("");
		System.out.println("Seja bem vindo(a)!");
		System.out.println("Escolha uma das opcoes a seguir: ");
		System.out.println("Voce ja esta registrado?");
		System.out.println("1 - Estou registrado");
		System.out.println("2 - Registrar-se");
		System.out.println("Outro - Sair");
		opt = scan.nextInt();
		
		if(opt == 1) {
			
			userLogin();
		
		} else if(opt == 2 ) {
			
			newUser();
			
		} else {
			userIsOn = false;
		}
		
		
	}
	
	static void newUser() throws RemoteException {
		System.out.println("");
		System.out.println("====================================================");
		System.out.println("======REGISTRO==DE==NOVO==USUARIO===================");
		System.out.println("====================================================");
		System.out.println("");
		System.out.println("Forneca seu nickname: ");
		System.out.println("Obs: nao sao suportados espacos!");
		userNickname = scan.next();
		userNickname.toString();
		if(stub.registryNewUser(userNickname)) {
			System.out.println("Você foi registrado com sucesso!");
			userMenu();
		} else {
			System.out.println("Você nao pode ser registrado! Tente um outro nickname!");
			verifyUserRegistration();
		}
	}
	
	static void userLogin() throws RemoteException{
		System.out.println("");
		System.out.println("====================================================");
		System.out.println("======LOGIN==DE==USUARIO==CADASTRADO================");
		System.out.println("====================================================");
		System.out.println("");
		System.out.println("Forneca seu nickname: ");
		userNickname = scan.next();
		userNickname.toString();
		if(stub.findUser(userNickname)) {
			System.out.println("Você foi logado com sucesso!");
			userMenu();
		} else {
			System.out.println("Seu nickname nao foi encontrado!");
			verifyUserRegistration();
		}
	}

	
	
	//METODOS REFERENTES AO ENVIO DE MENSAGENS TANTO PÚBLICAS QUANTO PRIVADAS
	static void userMenu() throws RemoteException {
		System.out.println("");
		System.out.println("====================================================");
		System.out.println("======MENU==DE==USUARIO=============================");
		System.out.println("====================================================");
		System.out.println("");
		System.out.println("Escolha uma das opcoes a seguir: ");
		System.out.println("1 - Ver caixa de mensagens recebidas");
		System.out.println("2 - Enviar mensagem a um usuario");
		System.out.println("3 - Enviar mensagem para todos os usuarios");
		System.out.println("Outro - Sair");
		
		int opt = scan.nextInt();
		
		switch(opt) {
		case 1:
			printUserMessages();
			userMenu();
			break;
		case 2:
			sendPrivateMessage();
			break;
		case 3:
			sendPublicMessage();
			userMenu();
			break;
		default:
			userIsOn = false;
		}
		
	}
	
	static void printUserMessages() throws RemoteException {
		System.out.println("");
		System.out.println("====================================================");
		System.out.println("======SUA==CAIXA==DE==MENSAGENS=====================");
		System.out.println("====================================================");
		System.out.println("");
		userMessages = stub.getUserMessages(userNickname);
		
		if(userMessages.size() != 0) {
			for(String message : userMessages) {
				System.out.println(message);
			}
		} else {
			System.out.println("Sua caixa de mensagens esta vazia!");
		}
	
		System.out.println("");
	}
	
	static void sendPublicMessage() throws RemoteException {
		System.out.println("");
		System.out.println("====================================================");
		System.out.println("======ENVIO==DE==MENSAGEMS==PUBLICAS================");
		System.out.println("====================================================");
		System.out.println("");
		System.out.println("Digite a mensagem que voce deseja enviar a todos os usuarios cadastrados: ");
		
		String message = scan.nextLine();
		message = (String)scan.nextLine();
		message = "[MENSAGEM PUBLICA DE " + userNickname + "] " + message;
		stub.sendMessageToAll(message);
	}

	static void sendPrivateMessage() throws RemoteException{
		System.out.println("");
		System.out.println("====================================================");
		System.out.println("======ENVIO==DE==MENSAGEMS==PRIVADAS================");
		System.out.println("====================================================");
		System.out.println("");
		System.out.println("Digite para qual usuario voce deseja enviar a mensagem:");
		String nick = scan.next();
		System.out.println("Digite a mensagem a ser enviada a " + nick);
		String message = scan.nextLine();
		message = scan.nextLine();
		message = "[MENSAGEM PRIVADA DE " +userNickname+"] " + message; 
		System.out.println("Tentando enviar mensagem a " + nick);
		if(stub.sendMessageToOnlyUser(nick, message)) {
			System.out.println("Mensagem enviada com sucesso a " + nick);
		} else {
			System.out.println("Nao foi possivel enviar a mensagem!");
		}
		userMenu();
		
		
	}
	
}
