import java.net.*;
import java.io.*;

public class GameClientThread extends Thread {
	GameGroup parent;
	Socket theSock;
	BufferedReader br;
	PrintWriter pw;
	//Grab grab;

	GameClientThread(Socket s, GameGroup p) {
		theSock = s;
		parent = p;
	}

	public void run() {
		// Get the streams to talk to the Client
		try {
			br = new BufferedReader(new InputStreamReader(theSock.getInputStream()));
			pw = new PrintWriter(theSock.getOutputStream());
		} catch (Exception e) {
		}

		// Wait for messages from the client
		while (theSock != null) {
			String input = null;
			try {
				input = br.readLine().trim();
				if (input != null) {
					// System.out.println("From client thread - got:"+input);
					// Send the message to the GameGroup to be read
					parent.processMessage(input);					
				}
			} catch (Exception e) {
				theSock = null;
			}
			try {
				sleep(100);
			} catch (Exception e) {
			}
			;
		}
	}

	public boolean message(String str) {
		// Send a message to our client
		boolean flag = false;
		System.out.println("Sending message:" + str);

		while (!flag)
			try {
				pw.println(str);
				pw.flush();
				flag = true;
			} catch (Exception e) {
				flag = false;
			}
		return true;
	}

	public void finalize() {
		try {
			pw.close();
			br.close();
			theSock.close();
		} catch (Exception e) {
		}
		;
		theSock = null;
	}
}
