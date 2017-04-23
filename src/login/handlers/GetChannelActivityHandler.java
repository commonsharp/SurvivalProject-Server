package login.handlers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.DatabaseConnection;
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

	@Override
	public void processMessage() throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT sum(population) FROM servers;");
		ResultSet rs = ps.executeQuery();
		
		playersPercentage = new int[3];
		
		if (rs.next()) {
			totalUsers = rs.getInt(1);
			
			if (totalUsers != 0) {
				for (int i = 0; i < 3; i++) {
					ps = con.prepareStatement("SELECT sum(population) FROM servers WHERE channelType = ?;");
					ps.setInt(1, i);
					rs = ps.executeQuery();
					
					if (rs.next()) {
						playersPercentage[i] = rs.getInt(1) * 100 / totalUsers;
					}
				}
			}
		}
		
		rs.close();
		ps.close();
		con.close();
	}
}
