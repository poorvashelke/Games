public class PublicRoom extends Room {

	public PublicRoom(String name) {
		super(name);
	}

	@Override
	public boolean validateUser(String passcode) {
		return true;
	}

	@Override
	public boolean isFederation() {
		return false;
	}
}