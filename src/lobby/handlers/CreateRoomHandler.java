package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.Messages;
import net.UserSession;
import net.objects.GameMode;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class CreateRoomHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x6C;
	
	protected int roomNumber; // starts with 0. room #1 - 0
	protected String roomName;
	protected int gameType;
	protected int gameMap;
	protected String password;
	protected int numberOfPlayers;
	protected boolean isWithScrolls;
	protected boolean isWithTeams;
	protected int cardsLimit;
	protected int unknown7;
	protected byte unknown8;
	protected byte unknown9;
	protected byte unknown10;
	protected boolean isLimitAnger = true; // 0 = limit. other values = no limit.
	
	public CreateRoomHandler(LobbyServer lobbyServer, UserSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		roomNumber = input.getInt(0x14);
		roomName = input.getString(0x18);
		gameType = input.getInt(0x38);
//		gameType = 37;
		gameMap = input.getInt(0x3C);
		password = input.getString(0x40);
		System.out.println(password == null);
		System.out.println("password: " + password);
		numberOfPlayers = input.getInt(0x50);
		isWithScrolls = input.getBoolean(0x54);
		isWithTeams = input.getBoolean(0x55);
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
	public void afterSend() throws IOException, SQLException {
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				try {
//					Thread.sleep(3000);
//					sendTCPMessage(new StartCountdownHandler(lobbyServer, userSession).getResponse());
//					
////					sendTCPMessage(new Test4376Handler(lobbyServer, userSession).getResponse(2));
//				} catch (InterruptedException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}).start();
		lobbyServer.sendBroadcastMessage(userSession, new LobbyRoomsChangedHandler(lobbyServer, userSession).getResponse(lobbyServer.getRoom(roomNumber)));
		
		RoomPlayersUpdateHandler rpuh = new RoomPlayersUpdateHandler(lobbyServer, userSession);
		sendTCPMessage(rpuh.getResponse(userSession.getUser()));
//		rpuh.afterSend();
		sendTCPMessage(new NewMasterHandler(lobbyServer, userSession).getResponse());
		
		// Send your connectivity to anyone else in your guild
		lobbyServer.sendGuildMessage(userSession, new GuildMemberOnlineStatusHandler(lobbyServer, userSession).getResponse(userSession, true), true);
	}

	@Override
	public byte[] getResponse() {
		cardsLimit = -1;
		Room room = new Room(roomNumber, roomName, password, userSession.getUser().username, gameType, gameMap, numberOfPlayers, isWithScrolls, isWithTeams, cardsLimit, isLimitAnger);
		lobbyServer.setRoom(roomNumber, room);
		userSession.getUser().isInRoom = true;
		userSession.getUser().roomSlot = lobbyServer.getRoom(roomNumber).getSlot();
		userSession.getUser().roomTeam = room.getTeam();
		userSession.getUser().roomCharacter = userSession.getUser().mainCharacter;
		userSession.getUser().roomReady = 0;
		
		if (room.isTrainingType()) {
			userSession.getUser().roomCharacter = 10; // xyrho
			userSession.getUser().roomReady = 1;
		}
		
		if (room.getGameMode() == GameMode.MISSION) {
			room.missionLevel = userSession.getUser().missionLevel;
		}
		
		userSession.getUser().roomIndex = roomNumber;
		lobbyServer.getRoom(roomNumber).setUserSession(userSession.getUser().roomSlot, userSession);
		
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);

		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, Messages.CREATE_ROOM_RESPONSE);
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
		output.putString(0x44, password);
		output.putInt(0x54, numberOfPlayers);
		output.putBoolean(0x58, isWithScrolls);
		output.putByte(0x59, (byte) 1); // this is master card bonus
		output.putInt(0x5C, userSession.getUser().roomTeam); // team
		output.putBoolean(0x60, isWithTeams);
		output.putInt(0x64, cardsLimit);
		output.putShort(0x68, (short) userSession.getUser().guildRank); // guild rank. zero based. you get a bonus for server capture if you're high enough
		output.putBoolean(0x6A, isLimitAnger);
		output.putByte(0x6B, (byte) 1); // not used.
		
		return output.toArray();
	}

	@Override
	public void processMessage() {
		userSession.getUser().isJoined = false;
	}
}
