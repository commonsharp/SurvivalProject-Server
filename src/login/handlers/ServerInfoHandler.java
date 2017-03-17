package login.handlers;

import net.GenericMessage;

public class ServerInfoHandler extends GenericMessage {
	public static final int RESPONSE_ID = 0x2908;
	
	protected short channelType; // between 0 and 3
	protected short channelID; // between 0 and 39
	protected String ip; // 15+0
	protected int port;
	protected int population;
	protected String name; // 28+1
	protected String bestGuildName; //12+1
	protected short unknown1;
	protected int maxPopulation; // default 200??
	protected byte guildSomething;
	protected byte[] unused = new byte[3]; //3
	protected int unknown2;
	
	public ServerInfoHandler(byte[] messageBytes) {
		super(messageBytes, 0x68, RESPONSE_ID);
		// TODO Auto-generated constructor stub
	}

	protected int unknown10;

	@Override
	public void interpretBytes(byte[] messageBytes) {
		unknown10 = inputBuffer.getInt(0x14);
		channelType = (short) inputBuffer.getInt(0x18);
//		System.out.println("NOTTIME!!!Time: " + unknown1);
	}

	@Override
	public void processFields() {
		// Add to database and stuff
	}

	@Override
	public void changeData() {
		ip = new String("10.0.0.2");
		channelID = 0;
		port = 21001;
		name = "Test Channel " + channelType;
		bestGuildName = "Obamas";
		
		population = 50;
		maxPopulation = 200;
//		guildSomething = 100;
		unknown1 = 1;
		unknown2 = -1;
	}

	@Override
	public void addPayload() {
		outputBuffer.putShort(0x14, channelType);
		outputBuffer.putShort(0x16, channelID);
		outputBuffer.putString(0x18, ip);
		outputBuffer.putInt(0x28, port);
		outputBuffer.putInt(0x2C, population);
		outputBuffer.putString(0x30, name);
		outputBuffer.putString(0x4D, bestGuildName);
		outputBuffer.putShort(0x5A, unknown1);
		outputBuffer.putInt(0x5C, maxPopulation);
		outputBuffer.putByte(0x60, guildSomething);
		outputBuffer.putInt(0x64, unknown2);
	}
}
