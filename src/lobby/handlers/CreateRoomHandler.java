package lobby.handlers;

import java.io.IOException;

import net.GenericHandler;
import net.UserTCPSession;

public class CreateRoomHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x4307;
	public static final int RESPONSE_ID = 0x4308;
	public static final int RESPONSE_LENGTH = 0x6C;
	
	protected int roomNumber; // starts with 0. room #1 - 0
	protected String roomName;
	protected int gameType;
	protected int gameMap;
	protected String password;
	protected int numberOfPlayers;
	protected byte isWithScrolls;
	protected byte isWithTeams;
	protected int cardsLimit;
	protected int unknown7;
	protected byte unknown8;
	protected byte unknown9;
	protected byte unknown10;
	
	public CreateRoomHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
	}

	@Override
	public void interpretBytes() {
		roomNumber = input.getInt(0x14);
		roomName = input.getString(0x18);
		gameType = input.getInt(0x38);
		gameMap = input.getInt(0x3C);
		password = input.getString(0x40);
		numberOfPlayers = input.getInt(0x50);
		isWithScrolls = input.getByte(0x54);
		isWithTeams = input.getByte(0x55);
		unknown7 = input.getInt(0x58);
		unknown8 = input.getByte(0x5C);
		unknown9 = input.getByte(0x5D);
		unknown10 = input.getByte(0x5E);
		
		System.out.println("Name:" + roomName);
		System.out.println("Password: " + password);
		System.out.println("room #: " + roomNumber);
		System.out.println("Game type: " + gameType);
		System.out.println("Game map: " + gameMap);
		System.out.println("#Players: " + numberOfPlayers);
		System.out.println("Items available: " + isWithScrolls);
		System.out.println("Teams : " + isWithTeams);
		System.out.println(unknown7);
		System.out.println(unknown8);
		System.out.println(unknown9);
		System.out.println(unknown10);
	}

	@Override
	public void processFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void changeData() {
		cardsLimit = -1;
	}

	@Override
	public void addPayload() {
		output.putInt(0x14, 2); // 0,1,3 or 4
		output.putInt(0x18, roomNumber);
		output.putString(0x1C, roomName);
		output.putInt(0x3C, gameType);
		output.putInt(0x40, gameMap);
		output.putInt(0x54, numberOfPlayers);
		output.putByte(0x58, isWithScrolls);
		output.putByte(0x59, isWithTeams);
		output.putInt(0x5C, 10);
		output.putByte(0x60, (byte) 10);
		output.putInt(0x64, cardsLimit);
		output.putShort(0x68, (short) 10);
		output.putByte(0x6A, (byte) 10);
		output.putByte(0x6B, (byte) 10);
	}

	@Override
	public void afterSend() throws IOException {
		sendTCPMessage(new PlayerJoinedRoomHandler(tcpServer, new byte[1280]).getResponse());
	}
}
