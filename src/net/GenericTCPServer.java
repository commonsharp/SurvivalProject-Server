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
//	protected Thread isAliveTimerThread;
	
	protected ArrayList<UserSession> userSessions;
	
	public abstract GenericHandler processPacket(UserSession tcpServer, int messageID, byte[] messageBytes);
	public abstract void onUserDisconnect(UserSession userSession) throws SQLException, IOException;
	
	public GenericTCPServer(String name, int port, int initialCapacity) {
		this.name = name;
		this.port = port;
		
		userSessions = new ArrayList<UserSession>(initialCapacity);
	}
	
	public void startServer() throws IOException {
		serverThread = new Thread(this);
		serverThread.start();
		
//		isAliveTimerThread = new Thread(new IsAliveTimer(this));
	}
	
	@Override
	public void run() {
		try {
			server = new ServerSocket(port);
			System.out.println(name + " has started listening.");
			UserSession currentSession;
			
			while (isRunning) {
				currentSession = new UserSession(this, server.accept());
				userSessions.add(currentSession);
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
		userSessions.sort(new Comparator<UserSession>() {
			@Override
			public int compare(UserSession o1, UserSession o2) {
				return o1.getUser().getUsername().compareTo(o2.getUser().getUsername());
			}
		});
	}
}
