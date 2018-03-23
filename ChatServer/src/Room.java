//check if room already exists
//check constructors
import java.util.HashSet;
import java.util.Set;

abstract class Room {
	protected String roomName;
	protected User owner;
	protected Set<User> users;
	protected String passcode;

	public Room(String name) {
		roomName = name;
		this.owner = null;
		users = new HashSet<User>();
		passcode = "";
	}

	public Room(String name, User owner) {
		roomName = name;
		users = new HashSet<User>();
		this.owner = owner;
		users.add(owner);
		passcode = "";
	}

	public Set<User> users() {
		return users;
	}

	public String name() {
		return roomName;
	}

	public int userCount() {
		return users.size();
	}

	public boolean addUser(User newUser, String code) {
		if (validateUser(code)) {
			users.add(newUser);
			return true;
		}
		return false;
	}

	public boolean removeUser(User oldUser) {
		users.remove(oldUser);
		return true;
	}

	public abstract boolean validateUser(String passcode);

	public abstract boolean isFederation();
}
