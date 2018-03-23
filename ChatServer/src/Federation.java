public class Federation extends Room {

	public Federation(String name, String passcode) {
		super(name);
		this.passcode = passcode;
	}

	@Override
	public boolean validateUser(String passcode) {
		return this.passcode.equals(passcode);
	}

	@Override
	public boolean isFederation() {
		return true;
	}

}
