/*
 * The server for the Grab game.
 * This version only supports one instance of two connections.
 * Then it shuts down after that one game, so you need to
 * restart the server before each game.
 * (Based on NetOthello code from Black Art of Java Game
 *  Programming)
 *
 * written by mike slattery - apr 2000
 */

import java.net.*;

class GameServer {
	static ServerSocket servSock = null;

	public static void main(String args[]) {
		int port;
		GameGroup gg = null;

		// Read the command line argument
		try {
			port = Integer.valueOf(args[0]).intValue();
		} catch (Exception e) {
			port = 2001;
		}

		// Set up a ServerSocket to listen for connections
		try {
			servSock = new ServerSocket(port);
		} catch (Exception e) {
			System.out.println("Could not initialize. Exiting.");
			System.exit(1);
		}
		System.out.println("Server successfully initialized.  Waiting for connection on port " + port);

		while (servSock != null) {
			Socket tempSock;
			try {
				tempSock = servSock.accept();
				System.out.println("Received New Connection.");
				// Add the new connection to a GameGroup
				if (gg != null) {
					if (!gg.full()) {
						gg.addClient(tempSock);
						if (gg.full()) {
							gg.start();
							break;
						}
					}
				} else
					gg = new GameGroup(tempSock);

			} catch (Exception e) {
				System.out.println("New Connection Failure.  Exiting.\n" + e);
				System.exit(1);
			}

			try {
				Thread.sleep(100);
			} catch (Exception e) {
			}
			;
		}
	}

	public void finalize() {
		try {
			servSock.close();
		} catch (Exception e) {
		}
		;
		servSock = null;
	}
}
