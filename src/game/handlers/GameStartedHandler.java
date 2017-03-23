package game.handlers;

import java.io.IOException;

import game.GameServer;
import lobby.handlers.SoccerGoalHandler;
import lobby.handlers.SpawnHandler;
import net.Constants;
import net.GenericHandler;
import net.GenericUDPServer;
import net.Room;
import tools.ExtendedByteBuffer;

public class GameStartedHandler extends GenericHandler {
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
		
		Room currentRoom = gameServer.lobby.getRoom(roomID);
		// Do game started stuff. Also make sure the message is sent once.
		if (!currentRoom.isRoomCreatedMessageSent) {
			switch (currentRoom.getGameType()) {
			case Constants.SOCCER_MODE:
				gameServer.lobby.roomMessage(roomID, new SoccerGoalHandler(gameServer.lobby, null).getResponse(roomID, 3));
			case Constants.HOKEY_MODE:
				gameServer.lobby.roomMessage(roomID, new SpawnHandler(null).getResponse());
				break;
			}
			
			gameServer.lobby.getRoom(roomID).isRoomCreatedMessageSent = true;
		}
	}

}
