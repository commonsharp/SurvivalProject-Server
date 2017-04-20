package lobby.handlers;

import java.io.IOException;
import java.sql.SQLException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserSession;
import tools.ExtendedByteBuffer;

public class Test4473Handler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0x1BC;
	
	public Test4473Handler(LobbyServer lobbyServer, UserSession userSession) {
		super(lobbyServer, userSession);
	}

	@Override
	public void interpretBytes() {
		
	}

	@Override
	public void processMessage() throws SQLException {
		
	}

	@Override
	public byte[] getResponse() {
		ExtendedByteBuffer output = new ExtendedByteBuffer(RESPONSE_LENGTH);
		output.putInt(0x0, RESPONSE_LENGTH);
		output.putInt(0x4, 0x4473);
		
		/*
		 * 14-array
34
38
40,44-exp
48,4c-code
50,54-coins
58
5C
60
64
68
6C
70
74
78
7C
80
84
88
8C
90
94
98-array
B8-array
D8-array
F8
FC
100
104
108
10C
110
114
118
138
1B8
		 */
		int[] results = new int[8];
		
		output.putInts(0x14, results);
		
		output.putInt(0x34, 123); // ko
		output.putInt(0x38, 123);
		output.putLong(0x40, 123); // old points amount
		output.putLong(0x48, 456); // old code amount
		output.putLong(0x50, 80); // old coin amount
		
		int[] experienceGained = new int[8];
		int[] codeGained = new int[8];
		int[] elementType = new int[8];
		int[] elementCount = new int[8];
		int[] elementMultiplier = new int[8];
		int[] luckyMultiplier = new int[8];
		int[] newLevels = new int[8];
		
		output.putInts(0x58, experienceGained);
		output.putInts(0x78, codeGained);
		output.putInts(0x98, elementType);
		output.putInts(0xB8, elementCount);
		output.putInts(0xD8, elementMultiplier);
		output.putInts(0xF8, luckyMultiplier);
		output.putInts(0x118, newLevels);
		
		output.putInt(0x138, 10);
		output.putInt(0x1B8, -1); // no use it seems
//		
		return output.toArray();
	}

	@Override
	public void afterSend() throws IOException, SQLException {
		// TODO Auto-generated method stub
		
	}

}
