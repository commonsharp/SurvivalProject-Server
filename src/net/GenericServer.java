package net;

import java.io.IOException;

import log.Log;
import tools.HexTools;

public abstract class GenericServer implements Runnable {
	protected String name;
	protected boolean isRunning = true;

	public abstract void initiate() throws IOException;
	public abstract byte[] receive() throws IOException;
	public abstract void send(byte[] response) throws IOException;
	public abstract GenericHandler processPacket(GenericServer server, int messageID, byte[] messageBytes);
	
	@Override
	public void run() {
		try {
			initiate();
			System.out.println(name + " has started listening.");
			
			while (isRunning) {
				byte[] messageBytes = receive();
				
				Cryptography.decryptMessage(messageBytes);
				
				int messageID = HexTools.getIntegerInByteArray(messageBytes, 4);
				int state = HexTools.getIntegerInByteArray(messageBytes, 16);
				
				GenericHandler message = processPacket(this, messageID, messageBytes);
				if (message == null) {
					Log.log(name + ": New client packet: 0x" + HexTools.integerToHexString(messageID) + " - unimplemented yet");
				}
				else {
					Log.log(name + ": New client packet: 0x" + HexTools.integerToHexString(messageID) + " - " + message.getClass().getCanonicalName());
				}
				
				if (message != null) {
					byte[] response = message.getResponse();
					
					// Response can be null if there is no response (not implemented)
					if (response != null) {
						sendMessage(response, state);
					}
					
					message.afterSend();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void stopServer() throws IOException {
		this.isRunning = false;
	}

	public String getName() {
		return name;
	}
	
	public void sendMessage(byte[] response, int state) throws IOException {
		// Change the state
		HexTools.putIntegerInByteArray(response, 16, state);
		
		// Change the checksum
		HexTools.putIntegerInByteArray(response, 12, Cryptography.getDigest(response));
		// Encrypt and send
		Cryptography.encryptMessage(response);
		
		send(response);
		
		// Change the state
		state = newState(state);
	}
	
	public static int newState(int oldState) {
		if (oldState == -1)
			return 0;
		
		oldState = (~oldState + 0x14fb) * 0x1f;
		return Math.abs((oldState >> 16) ^ oldState);
	}
}
