public class RoomBroadcaster implements Runnable {
	private User sender;
	private Room chatRoom;
	private String message;

	public RoomBroadcaster(User sender, Room chatRoom, String message) {
		this.sender = sender;
		this.chatRoom = chatRoom;
		this.message = message;
	}

	@Override
	public void run() {
		try {
			if (chatRoom == null || chatRoom.userCount() == 0)
				return;
			if (message.startsWith("* new user joined")) {
				for (User current : chatRoom.users()) {
					if (!current.name().equals(sender.name()))
						current.writeMessage(message);
				}
			} else {
				for (User current : chatRoom.users())
					current.writeMessage(message);
			}
		} catch (Exception e) {
		}
	}
}
