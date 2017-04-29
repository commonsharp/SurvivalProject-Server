package login.handlers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import database.Database;
import login.LoginHandler;
import net.Messages;
import net.UserSession;
import net.objects.Server;
import tools.ExtendedByteBuffer;

public class ServersInfoHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x68;
	
	protected short channelType;
	
	protected List<Server> servers;
	
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
	
	@SuppressWarnings("unchecked")
	public void initializeServersList() throws FileNotFoundException, IOException, SQLException {
		servers = new ArrayList<Server>(41 * 3);
		
		Session session = Database.getSession();
		session.beginTransaction();
		servers = session.createQuery("FROM Server WHERE channelType = :channelType").setParameter("channelType", channelType).list();
		session.getTransaction().commit();
		session.close();
		
		for (Server server : servers) {
			session = Database.getSession();
			session.beginTransaction();
			
			
			List<String> l = session.createQuery("SELECT guildName FROM GuildScore WHERE serverID = :serverID AND score = "
					+ "(SELECT max(score) FROM GuildScore WHERE serverID = :serverID)").
					setParameter("serverID", server.getId()).list();
			
			if (!l.isEmpty()) {
				server.setBestGuild(l.get(0));
			}
			
//			List l = session.createQuery("SELECT Guild.guildName FROM Guild WHERE GuildScore.serverID = :serverID AND GuildScore.score = "
//					+ "(SELECT max(score) FROM GuildScore WHERE serverID = :serverID)").
//					setParameter("serverID", server.getId()).list();
//			System.out.println(l);
		}
		
		session.getTransaction().commit();
		session.close();
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
		output.putShort(0x16, server.getServerIndex());
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
