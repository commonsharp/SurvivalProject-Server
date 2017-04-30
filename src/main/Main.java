package main;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import database.Database;
import game.GameServer;
import lobby.LobbyServer;
import login.LoginServer;
import net.objects.Server;

public class Main {
	public static final boolean FORCE_LATENCY = false;
	public static final int FORCE_LATENCY_TIME = 150;
	
	@SuppressWarnings("unchecked")
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
		
		// Setup Hibernate session factory
		Database.setupDatabase();
		
		// Reset connections
		resetDatabase();
		
		LoginServer loginServer = new LoginServer(21000, 100);
		loginServer.startServer();
		
		Session session = Database.getSession();
		session.beginTransaction();
		List<Server> servers = session.createQuery("FROM Server").list();
		
		LobbyServer lobbyServer;
		GameServer gameServer;
		
		for (Server server : servers) {
			lobbyServer = new LobbyServer(server);
			gameServer = new GameServer(lobbyServer, server.getPort());
			lobbyServer.setGameServer(gameServer);
			lobbyServer.startServer();
			gameServer.startServer();
		}
		
		session.getTransaction().commit();
		session.close();
	}
	
	public static void resetDatabase() throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		session.createQuery("update User set connected = false, server_id = null").executeUpdate();
		session.createQuery("update Server set population = 0").executeUpdate();
		session.getTransaction().commit();
		session.close();
	}
}
