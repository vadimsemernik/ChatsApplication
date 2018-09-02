package test.net.server_side;

public class ServerLauncher {
	
	static int port = 2018;
	
	public static void main(String [] args){
		Server server = new Server(port);
		System.out.println("Server is launched");
	}

}
