import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientWriter implements Runnable {
	private Socket client;

	public ClientWriter(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		String response = "";
		while (true) {
			try {
				// exit
				if (client.isClosed())
					break;

				BufferedReader in = new BufferedReader(new InputStreamReader(
						client.getInputStream()));
				while (in.ready()) {
					response = in.readLine();
					System.out.print(response);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
