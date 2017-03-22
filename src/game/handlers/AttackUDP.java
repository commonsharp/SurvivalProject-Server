package game.handlers;

import java.io.IOException;

import game.GameServer;
import net.GenericHandler;
import net.GenericUDPServer;
import tools.ExtendedByteBuffer;

public class AttackUDP extends GenericHandler {
	byte[] response;
	
	GameServer gameServer;
	int roomID;
	int slot;
	
	public AttackUDP(GameServer gameServer, GenericUDPServer udpServer, byte[] messageBytes) {
		super(udpServer, messageBytes);
		response = messageBytes;
		this.gameServer = gameServer;
	}
	
	public AttackUDP(GameServer gameServer, GenericUDPServer udpServer) {
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
		return null;
	}
	
	public byte[] getResponse2() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(response.length);
		output.putBytes(0x0, response);
		
		output.putInt(0x4, this.messageID);
		output.putInt(0x8, 0x1B2C);
		output.putInt(0xC, 0);
//		output.putInt(0x10, 0);
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException {
		gameServer.roomMessage(udpServer, roomID, slot, getResponse2());
	}

}
