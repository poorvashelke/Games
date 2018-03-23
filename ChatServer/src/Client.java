import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);

		try {
			Socket client = new Socket(serverName, port);// use inetaddr as 1st
			BufferedReader in = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			String response = "";

			new Thread(new ClientReader(client)).start();

			while (true) {
				response = in.readLine();
				response = Encrypter.decrypt(response);
				System.out.println("\r"+response);
				if (response.toUpperCase().contains("Bye")) {
					break;
				}
			}
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
