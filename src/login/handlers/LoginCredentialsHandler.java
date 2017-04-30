package login.handlers;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;

import database.Database;
import login.LoginHandler;
import net.Messages;
import net.UserSession;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class LoginCredentialsHandler extends LoginHandler {
	public static final int RESPONSE_LENGTH = 0xA8;
	
	int response;
    long unknown1; // probably cash (premium money)
    String unknown3; // 24+0
    String unknown4; // 10+0
    int unknown5;
    int unknown6;
    int unknown7;
    
    String username;
    String password;
	
	public LoginCredentialsHandler(UserSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
	}
	
	@Override
	public void interpretBytes() {
		username = input.getString(0x3C);
		password = input.getString(0x49);
		
		System.out.println(input.getInt(0x38));
		System.out.println("Username: " + input.getString(0x3C));
		System.out.println("Password: " + input.getString(0x49));
		System.out.println(input.getString(0x58));
		System.out.println(input.getString(0x64)); // this is equals to unknown3 in the response (what3)
		System.out.println(input.getInt(0x80));
		System.out.println("Process ID: " + input.getInt(0x84));
	}

	@Override
	public void afterSend() throws IOException {
		sendTCPMessage(new Test2924Handler(userSession).getResponse());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void processMessage() throws SQLException {
		response = 1;
		unknown1 = 1234;
		unknown3 = "what3";
		unknown4 = "what4";
		unknown5 = 560;
		unknown6 = 10;
		unknown7 = 10;
		
//		response = DatabaseConnection.verifyUsernamePassword(userSession.getUser().username, userSession.getUser().password);
		
//		if (response == 1) {
//			DatabaseConnection.setUser(userSession.getUser());
//		}
		
		Session session = Database.getSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User where username = :username").setParameter("username", username).list();
		
		if (!users.isEmpty()) {
			String password = users.get(0).getPassword();
			response = password.equals(password) ? 1 : 0; // if the username and password matched, the response is set to 1. otherwise, wrong password
		}
		else {
			response = 1;
			
			// create new username
			User user = new User();
			user.setUsername(username);
			user.setPassword(password);
			
			session.save(user);
		}
		
		session.getTransaction().commit();
		session.close();

		if (response == 1) {
			userSession.setUser(User.loadUser(username, null));
		}
	}
	
	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.LOGIN_CREDENTIALS_RESPONSE);
		
		/*
		 * Response values:
		 * -6 - error in gibberish
		 * -5 - ban for x days
		 * -4 - "ID is already logged in..."
		 * -3 - "Wrong version"
		 * -2 - "ID is wrong. &#xA;Please try again."
		 * -1 - "An error  has occurred. &#xA;Please inquire the support team."
		 * 0 - "Incorrect password"
		 * 1 - good
		 * 2 - can't play after 6pm and before 6am. lol.
		 */
		output.putInt(0x14, response);
		
		if (response == 1) {
			output.putInt(0x18, userSession.getUser().getUserType());
			output.putInt(0x1C, userSession.getUser().getMainCharacter());
			output.putInt(0x20, userSession.getUser().getPlayerLevel());
			output.putInt(0x24, userSession.getUser().getUsuableCharacterCount());
			output.putInt(0x28, userSession.getUser().getIsMuted());
			output.putInt(0x2C, userSession.getUser().getBanDays());
			output.putInt(0x30, userSession.getUser().getAgeRestriction());
			output.putLong(0x38, userSession.getUser().getPlayerExperience());
			output.putLong(0x40, userSession.getUser().getPlayerCode());
			output.putString(0x48, userSession.getUser().getGuildName());
			output.putString(0x55, userSession.getUser().getGuildDuty());
			output.putLong(0x70, unknown1);
			output.putString(0x78, unknown3);
			output.putString(0x91, unknown4);
			output.putInt(0x9C, unknown5);
			output.putInt(0xA0, 580); // 560 and 580 are special values. not sure what happens though.
			output.putInt(0xA4, unknown7);
		}
		
		return output.toArray();
	}
}
