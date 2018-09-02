package test.net;

import test.net.client_side.Client;
import test.net.server_side.Server;

public class Launcher {
	
	static int port = 2018;

	public static void main(String[] args) {
		Server server = new Server(port);
		Client client = new Client(port);

	}

}
