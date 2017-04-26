package login.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import login.LoginHandler;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class ServersInfoHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x68;
	
	protected short channelType;
	
	protected ArrayList<Server> servers;
	
	private class Server {
		private String hostname;
		private int port;
		private String name;
		private short channelType;
		private short serverID;
		private int population;
		private int maxPopulation;
		private String bestGuild;
		
		private Server(String hostname, int port, String name, short channelType, short serverID, int population, int maxPopulation) {
			this.hostname = hostname;
			this.port = port;
			this.name = name;
			this.channelType = channelType;
			this.serverID = serverID;
			this.population = population;
			this.maxPopulation = maxPopulation;
		}

		public String getHostname() {
			return hostname;
		}

		public int getPort() {
			return port;
		}

		public String getName() {
			return name;
		}

		public short getChannelType() {
			return channelType;
		}

		public short getServerID() {
			return serverID;
		}

		public int getPopulation() {
			return population;
		}

		public int getMaxPopulation() {
			return maxPopulation;
		}

		public String getBestGuild() {
			return bestGuild;
		}

		public void setBestGuild(String bestGuild) {
			this.bestGuild = bestGuild;
		}
	}
	
	public ServersInfoHandler(UserSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		// TODO Auto-generated constructor stub
	}

	protected int unknown10;

	@Override
	public void interpretBytes() {
		unknown10 = input.getInt(0x14);
		channelType = (short) (input.getInt(0x18) - 1);
		
		System.out.println("Server info: " + unknown10);
//		System.out.println("NOTTIME!!!Time: " + unknown1);
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		initializeServersList();
		
		for (Server server : servers) {
			sendTCPMessage(getResponse(server));
		}
	}
	
	public void initializeServersList() throws FileNotFoundException, IOException, SQLException {
		servers = new ArrayList<Server>(41 * 3);
		
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM servers WHERE channelType=?");
		ps.setShort(1, channelType);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
			servers.add(new Server(
					rs.getString("hostname"),
					rs.getInt("port"),
					rs.getString("name"),
					rs.getShort("channelType"),
					rs.getShort("serverID"),
					rs.getInt("population"),
					rs.getInt("maxPopulation")));
		}
		
		rs.close();
		
		for (Server server : servers) {
			ps = con.prepareStatement("SELECT guild_name FROM guild_score WHERE server_hostname = ? AND server_port = ? AND "
					+ "guild_score = (SELECT max(guild_score) FROM guild_score WHERE server_hostname = ? AND server_port = ?);");
			ps.setString(1, server.getHostname());
			ps.setInt(2, server.getPort());
			ps.setString(3, server.getHostname());
			ps.setInt(4, server.getPort());
			rs = ps.executeQuery();
			
			if (rs.next()) {
				server.setBestGuild(rs.getString("guild_name"));
			}
			
			rs.close();
		}
		
		
		ps.close();
		con.close();
	}

	@Override
	public byte[] getResponse() {
		return null;
	}
	
	public byte[] getResponse(Server server) {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.SERVERS_INFO_RESPONSE);
		output.putShort(0x14, (short)(server.getChannelType() + 1));
		output.putShort(0x16, server.getServerID());
		output.putString(0x18, server.getHostname());
		output.putInt(0x28, server.getPort());
		output.putInt(0x2C, server.getPopulation());
		output.putString(0x30, server.getName());
		output.putString(0x4D, server.getBestGuild());
		output.putShort(0x5A, (short) 4000);
		output.putInt(0x5C, server.getMaxPopulation());
		output.putByte(0x60, (byte) 3000);
		output.putInt(0x64, 2000);
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
