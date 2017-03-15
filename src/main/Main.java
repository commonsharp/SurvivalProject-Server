package main;

import java.io.IOException;

import net.LoginServer;

public class Main {
	public static void main(String[] args) throws IOException {
		LoginServer server = new LoginServer(21000);
		server.startServer();
	}
}
