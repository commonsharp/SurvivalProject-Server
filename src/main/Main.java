package main;

import java.io.IOException;

import game.GameServer;
import lobby.LobbyServer;
import login.LoginServer;

public class Main {
	public static String myIP;
	
	public static void main(String[] args) throws IOException {
		LoginServer loginServer = new LoginServer(21000, 100);
		loginServer.startServer();
		
		LobbyServer lobbyServer = new LobbyServer(21001, 100);
		GameServer gameServer = new GameServer(lobbyServer, 21001);
		lobbyServer.setGameServer(gameServer);
		lobbyServer.startServer();
		gameServer.startServer();
	}
}
