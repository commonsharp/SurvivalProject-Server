package net;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;

import net.handlers.GenericHandler;

public abstract class GenericTCPServer implements Runnable {
	protected ServerSocket server;
	protected int port;
	protected boolean isRunning = true;
	protected String name;
	
	protected Thread serverThread;
	
	protected ArrayList<UserTCPSession> usersSessions;
	
	public abstract GenericHandler processPacket(UserTCPSession tcpServer, int messageID, byte[] messageBytes);
	public abstract void onUserDisconnect(UserTCPSession userTCPSession) throws SQLException;
	
	public GenericTCPServer(String name, int port, int initialCapacity) {
		this.name = name;
		this.port = port;
		
		usersSessions = new ArrayList<UserTCPSession>(initialCapacity);
	}
	
	public void startServer() throws IOException {
		serverThread = new Thread(this);
		serverThread.start();
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			System.out.println(name + " has started listening.");
			UserTCPSession currentSession;
			
			while (isRunning) {
				currentSession = new UserTCPSession(this, server.accept());
				usersSessions.add(currentSession);
				new Thread(currentSession).start();
			}
			
			server.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopServer() throws IOException {
		this.isRunning = false;
		this.server.close(); // This will interrupt the socket.accept method.
	}

	public String getName() {
		return name;
	}
	public void moveToCorrectPlace() {
		usersSessions.sort(new Comparator<UserTCPSession>() {

			@Override
			public int compare(UserTCPSession o1, UserTCPSession o2) {
				return o1.getUser().username.compareTo(o2.getUser().username);
			}
		});
	}
}
