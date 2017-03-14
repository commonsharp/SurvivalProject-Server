package login.server.messages;

import login.server.ServerGenericMessage;

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
	protected byte[] unused; //3
	protected int unknown2;
	
	public ServerInfoResponse(short channelType) {
		super(0x2908, 0);
		
		this.channelType = channelType;
	}

	@Override
	public void changeData() {
		ip = new String("10.0.0.2");
		channelID = 0;
		port = 21000;
		name = "Test Channel";
		bestGuildName = "HKGolden";
		population = 100;
		maxPopulation = 200;

		unknown1 = 0;
		unused = new byte[3];
		unknown2 = 0;
	}

	@Override
	public void addPayload() {
		payload.putShort(channelType);
		payload.putShort(channelID);
		payload.putString(ip, 15);
		payload.putInteger(port);
		payload.putInteger(population);
		payload.putString(name, 28);
		payload.putString(bestGuildName, 12);
		payload.putShort(unknown1);
		payload.putInteger(maxPopulation);
		payload.putByte(guildSomething);
		payload.putBytes(unused);
		payload.putInteger(unknown2);
	}
}
