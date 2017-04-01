package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserTCPSession;
import net.objects.User;
import tools.ExtendedByteBuffer;

public class GetUserInfoHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xF0;
	
	int unknown1;
	String username;
	
	public GetUserInfoHandler(LobbyServer lobbyServer, UserTCPSession userSession, byte[] messageBytes) {
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
		output.putInt(0x24, user.playerLevel);
		output.putBoolean(0x28, user.isMale);
		output.putString(0x29, user.guildName); // guild?
		output.putString(0x36, user.guildDuty); // guild duty?
		output.putInt(0x44, user.mainCharacter);
		output.putLong(0x48, user.playerExperience);
		output.putInt(0x50, user.playerWins);
		output.putInt(0x54, user.playerLoses);
		output.putInt(0x58, user.playerKOs);
		output.putInt(0x5C, user.playerDowns);
		output.putLong(0x60, user.playerCode);
		output.putLong(0x68, user.avatarMoney);
		
		if (user.getItemID(user.magicIndex) != -1) {
			output.putInt(0x70, user.getItemID(user.magicIndex));
		}
		
		if (user.getItemID(user.weaponIndex) != -1) {
			output.putInt(0x70, user.getItemID(user.weaponIndex));
		}
		
		if (user.getItemID(user.accessoryIndex) != -1) {
			output.putInt(0x70, user.getItemID(user.accessoryIndex));
		}
		
		if (user.getItemID(user.petIndex) != -1) {
			output.putInt(0x70, user.getItemID(user.petIndex));
		}
		
		output.putInt(0x80, user.getItemPremiumDays(user.magicIndex));
		output.putInt(0x84, user.getItemPremiumDays(user.weaponIndex));
		output.putInt(0x88, user.getItemPremiumDays(user.accessoryIndex));
		output.putInt(0x8C, user.getItemPremiumDays(user.petIndex));
		
		output.putInt(0x90, user.getItemLevel(user.magicIndex));
		output.putInt(0x94, user.getItemLevel(user.weaponIndex));
		output.putInt(0x98, user.getItemLevel(user.accessoryIndex));
		output.putInt(0x9C, user.getItemLevel(user.petIndex));
		
		output.putInt(0xA0, user.getItemSkill(user.magicIndex));
		output.putInt(0xA4, user.getItemSkill(user.weaponIndex));
		output.putInt(0xA8, user.getItemSkill(user.accessoryIndex));
		output.putInt(0xAC, user.getItemSkill(user.petIndex));
		
		output.putInt(0xB0, user.missionLevel);
		output.putInt(0xB4, user.getAvatarItemID(user.footIndex));
		output.putInt(0xB8, user.getAvatarItemID(user.bodyIndex));
		output.putInt(0xBC, user.getAvatarItemID(user.hand1Index));
		output.putInt(0xC0, user.getAvatarItemID(user.hand2Index));
		output.putInt(0xC4, user.getAvatarItemID(user.faceIndex));
		output.putInt(0xC8, user.getAvatarItemID(user.hairIndex));
		output.putInt(0xCC, user.getAvatarItemID(user.headIndex));
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
