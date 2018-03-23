
import java.io.BufferedReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class ChatServer {

	private static Map<String, User> users = new HashMap<String, User>();
	private static Map<String, Room> activeRooms = new HashMap<String, Room>();

	private static void createDefaultRooms() {
		Room chat = new PublicRoom("chat");
		Room hottub = new PublicRoom("hottub");
		Room fed = new Federation("fed", "weeby");
		activeRooms.put(chat.name(), chat);
		activeRooms.put(hottub.name(), hottub);
		activeRooms.put(fed.name(), fed);
	}

	public static void main(String[] args) throws IOException {
		int port = 9559;
		ServerSocket serverSocket = new ServerSocket(port);
		serverSocket.setSoTimeout(200000);
		createDefaultRooms();
		try {
			while (true) {
				new Thread(new ClientHandler(serverSocket.accept())).start();
			}
		} finally {
			serverSocket.close();
		}
	}

	private static class ClientHandler implements Runnable {
		private volatile boolean running = true;
		private User currentUser;

		public ClientHandler(Socket client) throws IOException {
			this.currentUser = new User(client);
		}
		
		public void terminate() {
			running = false;
		}


		private void assignLogin() throws IOException {
			BufferedReader reader = currentUser.reader();
			currentUser.writeMessage("Welcome to the P & S chat server");
			currentUser.writeMessage("Login Name?");
			while (running) {
				String line = reader.readLine();
				line.replaceAll("=>", "");
				if (line.equals("/quit")) {
					currentUser.writeMessage("Bye");
					executeQuit();
				}
				synchronized (line) {
					if (!users.containsKey(line)) {
						currentUser.name(line);
						users.put(currentUser.name(), currentUser);
						break;
					} else {
						currentUser.writeMessage("Sorry, name taken.");
						currentUser.writeMessage("Login Name?");
					}
				}
			}
			currentUser.writeMessage("Welcome " + currentUser.name() + "!");
		}

		@Override
		public void run() {
			try {
				assignLogin();
				
				BufferedReader reader = currentUser.reader();
				while (running) {
					String line = reader.readLine();
					if (line.toLowerCase().startsWith("/quit")) {
						executeQuit();
						break;
					} else if (line.toLowerCase().startsWith("/rooms")) {
						executeRooms();
					} else if (line.toLowerCase().startsWith("/join")) {
						executeJoin(line);
					} else if (line.toLowerCase().startsWith("/leave")) {
						executeLeave();
					} else if (line.toLowerCase().startsWith("/users")) {// private message
						displayRoomDetails(currentUser.room());
					} else if (line.toLowerCase().startsWith("/create")) {// private message
						executeCreate(line);
					} else if (line.toLowerCase().contains(":")) {// private message
						currentUser.privateMessage(line);
					} else {// broadcast message
						String broadcastMessage = currentUser.name()+": "+line;
						new Thread(new RoomBroadcaster(currentUser, currentUser.room(), broadcastMessage)).start();
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void executeQuit() {
			if(currentUser.room() != null)
				executeLeave();
			users.remove(currentUser.name());
			currentUser.writeMessage("Bye");
			terminate();
		}

		private void executeLeave() {
			Room chatRoom = currentUser.room();
			currentUser.writeMessage("* user has left" + currentUser.room().name()+ ": " + currentUser.name()+"(** this is you)");
			currentUser.leaveRoom();
			String broadcastMessage = "* user has left" + chatRoom.name()
			+ ": " + currentUser.name();
			new Thread(new RoomBroadcaster(currentUser, chatRoom, broadcastMessage)).start();
		}

		private void executeRooms() {
			currentUser.writeMessage("Active rooms are:");
			Iterator<Entry<String, Room>> it = activeRooms.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry pair = (Map.Entry) it.next();
				Room chatRoom = (Room) pair.getValue();
				int userCount = chatRoom.userCount();
				currentUser.writeMessage("* " + chatRoom.name() + " (" + userCount + ")");
			}
			currentUser.writeMessage("end of list.");
		}

		private void executeJoin(String line) {
			String[] arguments = line.split("-p");
			String roomName = arguments[0].split(" ", 2)[1];
			roomName = roomName.trim();

			Room chatRoom = activeRooms.get(roomName);
			if(chatRoom == null){
				currentUser.writeMessage(roomName+ " does not exists. Please join a room from a list below:");
				executeRooms();
				return;
			}
			
			// retrieve passcode
			String passwordArgument = null;
			if (arguments.length > 1)
				passwordArgument = arguments[1].trim();
			String passcode = getPasscode(chatRoom, passwordArgument);
			if ((passcode != null)
					&& (!chatRoom.addUser(currentUser, passcode))) {
				currentUser.writeMessage("Wrong passcode.");
				return;
			}

			currentUser.joinRoom(chatRoom);
			currentUser.writeMessage("entering room: " + chatRoom.name());
			displayRoomDetails(chatRoom);
			
			String broadcastMessage = "* new user joined " + chatRoom.name()
					+ ": " + currentUser.name();
			new Thread(new RoomBroadcaster(currentUser, chatRoom, broadcastMessage)).start();
		}

		private String getPasscode(Room chatRoom, String password) {
			String passcode = "";
			if (!chatRoom.isFederation())
				return passcode;
			if (password != null)
				passcode = password;
			else
				currentUser.writeMessage("Enter passcode using -p argument");
			return passcode;
		}
		
		private void displayRoomDetails(Room chatRoom){
			if (chatRoom == null){
				currentUser.writeMessage("You have not joined any room.");
				return;
			}
			for (User each : chatRoom.users()) {
				if (each.name().equals(currentUser.name()))
					currentUser.writeMessage("* " + each.name()
							+ " (** this is you)");
				else
					currentUser.writeMessage("* " + each.name());
			}
			currentUser.writeMessage("end of list.");
		}
	}
	private static void executeCreate(String line) {
		String roomName = line.split(" ", 2)[1];
		Room newRoom = new PublicRoom(roomName);
		activeRooms.put(newRoom.name(), newRoom);
	}
}
