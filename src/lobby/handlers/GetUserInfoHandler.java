package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.User;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

public class GetUserInfoHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4309;
	public static final int RESPONSE_ID = 0x4310;
	public static final int RESPONSE_LENGTH = 0xF0;
	
	int unknown1;
	String username;
	LobbyServer lobby;
	
	public GetUserInfoHandler(LobbyServer lobby, UserTCPSession userSession, byte[] messageBytes) {
		super(userSession, messageBytes);
		this.lobby = lobby;
	}

	@Override
	public void interpretBytes() {
		unknown1 = input.getInt(0x14); // can be 1 or 2. lots of functions use 1, only one function uses 2.
		username = input.getString(0x18);
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		User user = lobby.findUser(username);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		output.putByte(0x14, (byte) 1);
		output.putString(0x15, username);
		output.putInt(0x24, user.playerLevel);
		output.putByte(0x28, user.gender);
		output.putString(0x29, user.guildName); // guild?
		output.putString(0x36, user.guildDuty); // guild duty?
		output.putInt(0x44, user.activeCharacter);
		output.putLong(0x48, user.playerExperience);
		output.putInt(0x50, user.playerWins);
		output.putInt(0x54, user.playerLoses);
		output.putInt(0x58, user.playerKOs);
		output.putInt(0x5C, user.playerDowns);
		output.putLong(0x60, user.playerMoney);
		output.putLong(0x68, user.avatarMoney);
		
		output.putInt(0x70, user.playerCardItemId[user.magicIndex]);
		output.putInt(0x74, user.playerCardItemId[user.weaponIndex]);
		output.putInt(0x78, user.playerCardItemId[user.accessoryIndex]);
		output.putInt(0x7C, user.playerCardItemId[user.petIndex]);
		
		output.putInt(0x80, user.playerCardItemDays[user.magicIndex]);
		output.putInt(0x84, user.playerCardItemDays[user.weaponIndex]);
		output.putInt(0x88, user.playerCardItemDays[user.accessoryIndex]);
		output.putInt(0x8C, user.playerCardItemDays[user.petIndex]);
		
		output.putInt(0x90, user.playerCardItemLevelIdx[user.magicIndex]);
		output.putInt(0x94, user.playerCardItemLevelIdx[user.weaponIndex]);
		output.putInt(0x98, user.playerCardItemLevelIdx[user.accessoryIndex]);
		output.putInt(0x9C, user.playerCardItemLevelIdx[user.petIndex]);
		
		output.putInt(0xA0, user.playerCardItemSkill[user.magicIndex]);
		output.putInt(0xA4, user.playerCardItemSkill[user.weaponIndex]);
		output.putInt(0xA8, user.playerCardItemSkill[user.accessoryIndex]);
		output.putInt(0xAC, user.playerCardItemSkill[user.petIndex]);
		
		output.putInt(0xB0, user.missionLevel);
		output.putInt(0xB4, user.playerCardItemId[user.playerAvatarEquipIdx[0]]); // foot
		output.putInt(0xB8, user.playerCardItemId[user.playerAvatarEquipIdx[1]]); // body
		output.putInt(0xBC, user.playerCardItemId[user.playerAvatarEquipIdx[2]]); // hand 1
		output.putInt(0xC0, user.playerCardItemId[user.playerAvatarEquipIdx[3]]); // hand 2
		output.putInt(0xC4, user.playerCardItemId[user.playerAvatarEquipIdx[4]]); // face
		output.putInt(0xC8, user.playerCardItemId[user.playerAvatarEquipIdx[5]]); // hair
		output.putInt(0xCC, user.playerCardItemId[user.playerAvatarEquipIdx[6]]); // head
		output.putString(0xD0, "hellhat"); // ?
		output.putInt(0xEC, 0); // - mark?
		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
