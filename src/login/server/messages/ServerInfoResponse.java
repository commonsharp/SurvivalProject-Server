package login.server.messages;

import net.ServerGenericMessage;

public class ServerInfoResponse extends ServerGenericMessage {
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
	
	public ServerInfoResponse(short channelType) {
		super(0x68, 0x2908);
		
		this.channelType = channelType;
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
		buffer.putShort(0x14, channelType);
		buffer.putShort(0x16, channelID);
		buffer.putString(0x18, ip);
		buffer.putInt(0x28, port);
		buffer.putInt(0x2C, population);
		buffer.putString(0x30, name);
		buffer.putString(0x4D, bestGuildName);
		buffer.putShort(0x5A, unknown1);
		buffer.putInt(0x5C, maxPopulation);
		buffer.putByte(0x60, guildSomething);
		buffer.putInt(0x64, unknown2);
	}
}
