import java.util.ArrayList;

/*
 * Classe responsavel por definir um usuario
 * dentro do sistema de troca de mensagens
 * 
 */

public class User {

	//Atributos de usuario
	private String nickname;
	private ArrayList<String> posts = new ArrayList<String>();
	
	
	public User(String n) {
		this.nickname = n;
	}
	
	public String getUserNickname() {
		return this.nickname;
	}
	
	public ArrayList<String> getUserMessages(){
		return this.posts;
	}
	
	public void addMessage(String message) {
		
		posts.add(message);
		
	}
	
}
