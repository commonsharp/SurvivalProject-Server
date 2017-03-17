package login.handlers;

import net.GenericHandler;
import net.UserTCPSession;

public class ServerInfoHandler extends GenericHandler {
	public static final int REQUEST_ID = 0x2907;
	public static final int RESPONSE_ID = 0x2908;
	public static final int RESPONSE_LENGTH = 0x68;
	
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
	
	public ServerInfoHandler(UserTCPSession tcpServer, byte[] messageBytes) {
		super(tcpServer, messageBytes, RESPONSE_LENGTH, RESPONSE_ID);
		// TODO Auto-generated constructor stub
	}

	protected int unknown10;

	@Override
	public void interpretBytes() {
		unknown10 = input.getInt(0x14);
		channelType = (short) input.getInt(0x18);
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
		output.putShort(0x14, channelType);
		output.putShort(0x16, channelID);
		output.putString(0x18, ip);
		output.putInt(0x28, port);
		output.putInt(0x2C, population);
		output.putString(0x30, name);
		output.putString(0x4D, bestGuildName);
		output.putShort(0x5A, unknown1);
		output.putInt(0x5C, maxPopulation);
		output.putByte(0x60, guildSomething);
		output.putInt(0x64, unknown2);
	}

	@Override
	public void afterSend() {
		// TODO Auto-generated method stub
		
	}
}
