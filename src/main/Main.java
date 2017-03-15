package main;

import java.io.IOException;

import lobby.LobbyServer;
import login.LoginServer;

public class Main {
	public static void main(String[] args) throws IOException {
		LoginServer loginServer = new LoginServer(21000);
		loginServer.startServer();
		
		LobbyServer lobbyServer = new LobbyServer(21001);
		lobbyServer.startServer();
	}
}
