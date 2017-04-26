package main;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import database.DatabaseConnection;
import game.GameServer;
import lobby.LobbyServer;
import login.LoginServer;

public class Main {
	public static final boolean IS_RELEASE = false;
	public static final boolean FORCE_LATENCY = false;
	public static final int FORCE_LATENCY_TIME = 150;
	
	public static void main(String[] args) throws IOException, SQLException {
//		byte[] bytes = {0x24,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x7f,(byte)0x77,(byte)0xff,(byte)0xff,(byte)0x1f,(byte)0x97,(byte)0x39,(byte)0x3a,(byte)0xea,(byte)0x72,(byte)0xf3,(byte)0x17,(byte)0x68,(byte)0xd8,(byte)0x89,(byte)0xe8,(byte)0xfb,(byte)0x2c,(byte)0x2d,(byte)0x2e,(byte)0x37,(byte)0x28,(byte)0x29,(byte)0x2a,(byte)0x2b,(byte)0x24,(byte)0x25,(byte)0x26,(byte)0x27,(byte)0x20,(byte)0x21,(byte)0x22};
//		Cryptography.decryptMessage(1, bytes);
//		HexTools.printHexArray(bytes, false);
//		
//		byte[] bytes2 = {0x24,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0xd3,(byte)0x44,(byte)0x3d,(byte)0x3e,(byte)0x1f,(byte)0x97,(byte)0x39,(byte)0x3a,(byte)0xea,(byte)0x72,(byte)0xf3,(byte)0x17,(byte)0x68,(byte)0xd8,(byte)0x89,(byte)0xe8,(byte)0xfb,(byte)0x2c,(byte)0x2d,(byte)0x2e,(byte)0x37,(byte)0x28,(byte)0x29,(byte)0x2a,(byte)0x2b,(byte)0x24,(byte)0x25,(byte)0x26,(byte)0x27,(byte)0x20,(byte)0x21,(byte)0x22};
//		Cryptography.decryptMessage(2, bytes2);
//		HexTools.printHexArray(bytes2, false);
//		System.out.println(HexTools.getIntegerInByteArray(bytes2, 12));
//		System.out.println(Cryptography.getDigest(bytes2));
		
		// Reset connections
		resetDatabase();
		
		LoginServer loginServer = new LoginServer(21000, 100);
		loginServer.startServer();
		
		//192.99.73.148
		//10.0.0.50
		String ip = "10.0.0.50";
		
		if (IS_RELEASE) {
			ip = "192.99.73.148";
		}
		
		LobbyServer lobbyServer1 = new LobbyServer(ip, 21001, 100);
		GameServer gameServer1 = new GameServer(lobbyServer1, 21001);
		lobbyServer1.setGameServer(gameServer1);
		lobbyServer1.startServer();
		gameServer1.startServer();
		
		LobbyServer lobbyServer2 = new LobbyServer(ip, 21002, 100);
		GameServer gameServer2 = new GameServer(lobbyServer2, 21002);
		lobbyServer2.setGameServer(gameServer2);
		lobbyServer2.startServer();
		gameServer2.startServer();
		
		LobbyServer lobbyServer3 = new LobbyServer(ip, 21003, 100);
		GameServer gameServer3 = new GameServer(lobbyServer3, 21003);
		lobbyServer3.setGameServer(gameServer1);
		lobbyServer3.startServer();
		gameServer3.startServer();
	}
	
	public static void resetDatabase() throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("UPDATE users SET server_hostname = NULL, server_port = NULL, is_connected = false;");
		ps.executeUpdate();
		ps.close();
		
		ps = con.prepareStatement("UPDATE servers SET population = 0;");
		ps.executeUpdate();
		ps.close();
		
		con.close();
	}
}
