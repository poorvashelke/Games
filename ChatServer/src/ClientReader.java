
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientReader implements Runnable {
	private Socket client;

	public ClientReader(Socket client) {
		this.client = client;
	}

	@Override
	public void run() {
		Scanner input = new Scanner(System.in);
		String line = "";
		while(true) {
			try {
				//exit
				if(client.isClosed())
					break;
				
				PrintWriter out = new PrintWriter(client.getOutputStream(), true);
				System.out.print("=>");
				line = input.nextLine();
				out.println(line);
				out.flush();
				
				Thread.sleep(10);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
