package login.handlers;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import database.Database;
import login.LoginHandler;
import net.Messages;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class GetChannelActivityHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0x24;
	
	int totalUsers;
	int[] playersPercentage;
	
	public GetChannelActivityHandler(UserSession userSession) {
		super(userSession);
	}
	
	public GetChannelActivityHandler(UserSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		// no bytes
	}

	@Override
	public void afterSend() {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_CHANNEL_ACTIVITY_RESPONSE);
		
		output.putInt(0x14, 0); // tutorial channel. not used
		output.putInts(0x18, playersPercentage);
		
		return output.toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processMessage() throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		List<Long> sum = session.createQuery("SELECT sum(population) FROM Server").list();
		playersPercentage = new int[3];
		
		if (!sum.isEmpty()) {
			System.out.println(sum.get(0).getClass());
			totalUsers = sum.get(0).intValue();
			
			if (totalUsers != 0) {
				for (int i = 0; i < 3; i++) {
					List<Long> population = session.createQuery("SELECT sum(population) FROM Server WHERE channelType = :channelType").
							setParameter("channelType", (short) i).list();
					
					if (!population.isEmpty()) {
						playersPercentage[i] = population.get(0).intValue() * 100 / totalUsers;
					}
				}
			}
		}
	}
}
