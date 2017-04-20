package main;

import java.io.IOException;

import game.GameServer;
import lobby.LobbyServer;
import login.LoginServer;

public class Main {
	public static void main(String[] args) throws IOException {
		LoginServer loginServer = new LoginServer(21000, 100);
		loginServer.startServer();
		
		LobbyServer lobbyServer1 = new LobbyServer("10.0.0.50", 21001, 100);
		GameServer gameServer1 = new GameServer(lobbyServer1, 21001);
		lobbyServer1.setGameServer(gameServer1);
		lobbyServer1.startServer();
		gameServer1.startServer();
		
		LobbyServer lobbyServer2 = new LobbyServer("10.0.0.50", 21002, 100);
		GameServer gameServer2 = new GameServer(lobbyServer2, 21002);
		lobbyServer2.setGameServer(gameServer2);
		lobbyServer2.startServer();
		gameServer2.startServer();
		
		LobbyServer lobbyServer3 = new LobbyServer("10.0.0.50", 21003, 100);
		GameServer gameServer3 = new GameServer(lobbyServer3, 21003);
		lobbyServer3.setGameServer(gameServer1);
		lobbyServer3.startServer();
		gameServer3.startServer();
	}
}
