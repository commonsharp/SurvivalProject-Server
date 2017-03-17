package lobby.client.messages;

import lobby.server.messages.GetTopGuildsMarkResponse;
import net.GenericClientMessage;

public class GetTopGuildsMarkRequest extends GenericClientMessage {
	protected String guildName;
	protected int window;
	
	public GetTopGuildsMarkRequest(byte[] messageBytes) {
		super(messageBytes);
	}

	@Override
	public void interpretBytes(byte[] messageBytes) {
		guildName = buffer.readNullTerminatedString(15); //0x14
		window = buffer.readInt(); // 0x24
	}

	@Override
	public void processFields() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public byte[] getResponse() {
		return new GetTopGuildsMarkResponse().getResponse();
	}

}
