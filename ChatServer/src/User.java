
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Set;


public class User {
	private Room chatRoom;
	private String userName;
	private BufferedReader reader;
	private PrintWriter writer;

	public User(Socket client) throws IOException {
		this.userName = null;
		reader = new BufferedReader(new InputStreamReader(
				client.getInputStream()));
		writer = new PrintWriter(client.getOutputStream(), true);
		this.chatRoom = null;
	}

	public void name(String userName) {
		this.userName = userName;
	}

	public void joinRoom(Room chatRoom) {
		this.chatRoom = chatRoom;
	}
	
	public void leaveRoom(){
		if(chatRoom != null)
			chatRoom.removeUser(this);
		this.chatRoom = null;
	}

	public String name() {
		return userName;
	}

	public Room room(){
		return chatRoom;
	}
	
	public BufferedReader reader() {
		return reader;
	}

	public PrintWriter writer() {
		return writer;
	}
	
	public void writeMessage(String message){
		message = Encrypter.encrypt(message);
		writer.println("<= "+message);
	}
	
	public void privateMessage(String line){
		if(chatRoom != null){
			String[] arguments = line.split(":");
			String toName = arguments[0];
			Set<User> usersInRoom = chatRoom.users();
			boolean found = false;
			for (User each : usersInRoom) {
				if (each.userName.startsWith(toName)) {
					each.writeMessage(name()+": "+arguments[1]);
					found = true;
					break;
				}
			}
			if(!found){
				writeMessage(toName+" is not present in "+chatRoom.name()+" room");
			}
		}
	}
}
