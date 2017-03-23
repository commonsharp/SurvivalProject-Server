package lobby.handlers;

import java.io.IOException;

import lobby.LobbyServer;
import net.GenericHandler;
import net.Room;
import net.UserTCPSession;
import tools.ExtendedByteBuffer;

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
	protected byte isLimitAnger; // 0 = limit. other values = no limit.
	
	protected LobbyServer lobby;
	public CreateRoomHandler(LobbyServer lobby, UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes);
		this.lobby = lobby;
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
	public void afterSend() throws IOException {
		lobby.broadcastMessage(userSession, new LobbyRoomsChangedHandler(userSession).getResponse(lobby.getRoom(roomNumber)));
		sendTCPMessage(new RoomPlayersChangedHandler(lobby, userSession).getResponse(userSession.getUser()));
	}

	@Override
	public byte[] getResponse() {
		int[] characters = new int[10];
		characters[0] = 10;
		cardsLimit = -1;
		Room room = new Room(roomNumber, roomName, gameType, gameMap, numberOfPlayers, isWithScrolls, isWithTeams, cardsLimit, isLimitAnger, characters);
		lobby.setRoom(roomNumber, room);
		userSession.getUser().isInRoom = true;
		userSession.getUser().roomSlot = lobby.getRoom(roomNumber).getSlot();
		userSession.getUser().roomTeam = room.getTeam();
		userSession.getUser().roomCharacter = 10;
		userSession.getUser().roomReady = 0;
		userSession.getUser().roomFieldF4 = 2;
		
		userSession.getUser().roomIndex = roomNumber;
		lobby.getRoom(roomNumber).setUserSession(userSession.getUser().roomSlot, userSession);
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, RESPONSE_ID);
		// 0 = "There's no room that can be made"
		// 1 = "An error occurred. contact support"
		// 2 = okay.
		// 3 = "Observers can only enter king slayer and team mode rooms"
		// 4 = dunno
		output.putInt(0x14, 2); // 0,1,3 or 4
		output.putInt(0x18, roomNumber);
		output.putString(0x1C, roomName);
		output.putInt(0x3C, gameType);
		output.putInt(0x40, gameMap);
		output.putInt(0x54, numberOfPlayers);
		output.putByte(0x58, (byte) isWithScrolls);
		output.putByte(0x59, (byte) 0);
		output.putInt(0x5C, userSession.getUser().roomTeam); // team
		output.putByte(0x60, (byte) isWithTeams);
		output.putInt(0x64, cardsLimit);
		output.putShort(0x68, (short) 10); // guild rank. zero based
		output.putByte(0x6A, isLimitAnger);
		output.putByte(0x6B, (byte) 0);
		
		return output.toArray();
	}
}
