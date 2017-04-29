package lobby.handlers;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import database.Database;
import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.GuildScore;
import tools.ExtendedByteBuffer;

public class GetTopGuildsHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xF0;
	
	public GetTopGuildsHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public byte[] getResponse() throws SQLException {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_TOP_GUILDS_RESPONSE);
		// If your clan is in this list, then 9 0x4486 requests are being sent to the server.
		// Otherwise, 10 0x4486 requests are being sent to the server...
		
		Session session = Database.getSession();
		session.beginTransaction();
		List<GuildScore> guildScores = session.createQuery("FROM GuildScore WHERE serverID = :serverID ORDER BY score DESC")
				.setParameter("serverID", lobbyServer.server.getId()).list();
		
		for (int i = 0; i < 10 && i < guildScores.size(); i++) {
			output.putString(0x14 + 0xD * i, guildScores.get(i).getGuildName());
			output.putInt(0x98 + 4 * i, guildScores.get(i).getScore());
		}
		
		
//		Connection con = Database.getConnection();
//		PreparedStatement ps = con.prepareStatement("SELECT * FROM guild_score WHERE server_hostname=? AND server_port=? ORDER BY guild_score DESC LIMIT 10;");
//		ps.setString(1, lobbyServer.hostname);
//		ps.setInt(2, lobbyServer.port);
//		ResultSet rs = ps.executeQuery();
//		
//		for (int i = 0; i < 10 && rs.next(); i++) {
//			output.putString(0x14 + 0xD * i, rs.getString("guild_name"));
//			output.putInt(0x98 + 4 * i, rs.getInt("guild_score"));
//		}
//		
//		rs.close();
//		ps.close();
		
		// Load guild rank
        if (lobbyServer != null && userSession.getUser().guildName != null) {
			int myScore = 0;
			int myRank = 0;
			
			session = Database.getSession();
			Query query = session.createQuery("SELECT score FROM GuildScore WHERE serverID = :serverID AND guildName = :guildName");
			query.setParameter("serverID", lobbyServer.server.getId());
			query.setParameter("guildName", userSession.getUser().guildName);
			List<Integer> guildScores2 = query.list();
			
			if (!guildScores2.isEmpty()) {
				myScore = guildScores2.get(0);
				
				query = session.createQuery("SELECT count(*) FROM GuildScore WHERE serverID = :serverID AND score > :score");
				query.setParameter("serverID", lobbyServer.server.getId());
				query.setParameter("score", myScore);
				
				List<Long> count = query.list();
				
				myRank = count.get(0).intValue() + 1;
			}
			
			userSession.getUser().guildRank = myRank - 1;
			output.putInt(0xE8, myRank - 1);
			output.putInt(0xEC, myScore);
		}
			
		output.putInt(0xC0, 0); // another array (10 integers)
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
