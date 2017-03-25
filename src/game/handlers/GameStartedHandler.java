package game.handlers;

import java.io.IOException;

import game.GameHandler;
import game.GameServer;
import lobby.handlers.SoccerGoalHandler;
import lobby.handlers.SpawnHandler;
import net.GenericUDPServer;
import net.UserTCPSession;
import net.objects.Room;
import tools.ExtendedByteBuffer;

public class GameStartedHandler extends GameHandler {
	byte[] response;
	
	GameServer gameServer;
	int roomID;
	int slot;
	
	public GameStartedHandler(GameServer gameServer, GenericUDPServer udpServer, byte[] messageBytes) {
		super(udpServer, messageBytes);
		response = messageBytes;
		this.gameServer = gameServer;
	}
	
	public GameStartedHandler(GameServer gameServer, GenericUDPServer udpServer) {
		super(udpServer);
		this.gameServer = gameServer;
	}

	@Override
	public void interpretBytes() {
		roomID = input.getInt(0x14);
		slot = input.getInt(0x18);
	}

	@Override
	public byte[] getResponse() {
		// I don't want to send the message to itself. only to others. therefore this is null
		return null;
	}
	
	public byte[] getResponse2() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(response.length);
		output.putBytes(0x0, response);
		output.putInt(0x4, this.messageID);
		output.putInt(0x8, 0x1B2C);
		output.putInt(0xC, 0);
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		// Send the packet to everyone in the room
		gameServer.roomMessage(udpServer, roomID, slot, getResponse2());
		UserTCPSession userSession = gameServer.lobby.getRoom(roomID).getUser(slot);
		userSession.getUser().isInGame = true;
		
		Room currentRoom = gameServer.lobby.getRoom(roomID);
		// Do game started stuff. Also make sure the message is sent once.
		if (!currentRoom.isRoomCreatedMessageSent) {
			switch (currentRoom.getGameType()) {
			case SOCCER:
				gameServer.lobby.sendRoomMessage(userSession, new SoccerGoalHandler(gameServer.lobby, null).getResponse(roomID, 3), true);
				break;
			case HOKEY:
				gameServer.lobby.sendRoomMessage(userSession, new SpawnHandler(gameServer.lobby, null).getResponse(), true);
				break;
			default:
			}
			
			gameServer.lobby.getRoom(roomID).isRoomCreatedMessageSent = true;
		}
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}

}
