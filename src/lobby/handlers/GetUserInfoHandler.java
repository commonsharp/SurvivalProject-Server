package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class GetUserInfoHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xF0;
	
	int unknown1;
	String username;
	
	public GetUserInfoHandler(LobbyServer lobbyServer, UserSession userSession, byte[] messageBytes) {
		super(lobbyServer, userSession, messageBytes);
	}

	@Override
	public void interpretBytes() {
		unknown1 = input.getInt(0x14); // can be 1 or 2. lots of functions use 1, only one function uses 2.
		username = input.getString(0x18);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		User user = lobbyServer.findUserSession(username).getUser();
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.GET_USER_INFO_RESPONSE);
		output.putByte(0x14, (byte) 1);
		output.putString(0x15, username);
		output.putInt(0x24, user.getPlayerLevel());
		output.putBoolean(0x28, user.isMale());
		output.putString(0x29, user.getGuildName());
		output.putString(0x36, user.getGuildDuty());
		output.putInt(0x44, user.getMainCharacter());
		output.putLong(0x48, user.getPlayerExperience());
		output.putInt(0x50, user.getPlayerWins());
		output.putInt(0x54, user.getPlayerLoses());
		output.putInt(0x58, user.getPlayerKOs());
		output.putInt(0x5C, user.getPlayerDowns());
		output.putLong(0x60, user.getPlayerCode());
		output.putLong(0x68, user.getAvatarMoney());
		
		if (user.getItemID(user.getMagicIndex()) != -1) {
			output.putInt(0x70, user.getItemID(user.getMagicIndex()));
		}
		
		if (user.getItemID(user.getWeaponIndex()) != -1) {
			output.putInt(0x74, user.getItemID(user.getWeaponIndex()));
		}
		
		if (user.getItemID(user.getAccessoryIndex()) != -1) {
			output.putInt(0x78, user.getItemID(user.getAccessoryIndex()));
		}
		
		if (user.getItemID(user.getPetIndex()) != -1) {
			output.putInt(0x7C, user.getItemID(user.getPetIndex()));
		}
		
		output.putInt(0x80, user.getItemPremiumDays(user.getMagicIndex()));
		output.putInt(0x84, user.getItemPremiumDays(user.getWeaponIndex()));
		output.putInt(0x88, user.getItemPremiumDays(user.getAccessoryIndex()));
		output.putInt(0x8C, user.getItemPremiumDays(user.getPetIndex()));
		
		output.putInt(0x90, user.getItemLevel(user.getMagicIndex()));
		output.putInt(0x94, user.getItemLevel(user.getWeaponIndex()));
		output.putInt(0x98, user.getItemLevel(user.getAccessoryIndex()));
		output.putInt(0x9C, user.getItemLevel(user.getPetIndex()));
		
		output.putInt(0xA0, user.getItemSkill(user.getMagicIndex()));
		output.putInt(0xA4, user.getItemSkill(user.getWeaponIndex()));
		output.putInt(0xA8, user.getItemSkill(user.getAccessoryIndex()));
		output.putInt(0xAC, user.getItemSkill(user.getPetIndex()));
		
		output.putInt(0xB0, user.getMissionLevel());
		output.putInt(0xB4, user.getAvatarItemID(user.getFootIndex()));
		output.putInt(0xB8, user.getAvatarItemID(user.getBodyIndex()));
		output.putInt(0xBC, user.getAvatarItemID(user.getHand1Index()));
		output.putInt(0xC0, user.getAvatarItemID(user.getHand2Index()));
		output.putInt(0xC4, user.getAvatarItemID(user.getFaceIndex()));
		output.putInt(0xC8, user.getAvatarItemID(user.getHairIndex()));
		output.putInt(0xCC, user.getAvatarItemID(user.getHeadIndex()));
		output.putString(0xD0, "hellhat"); // ?
		output.putInt(0xEC, 0); // - mark?
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
