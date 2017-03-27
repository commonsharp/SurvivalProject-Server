package lobby.handlers;

import java.io.IOException;

import lobby.LobbyHandler;
import lobby.LobbyServer;
import net.UserTCPSession;
import net.objects.GameMode;
import net.objects.Room;

public class MonsterDeathHandler extends LobbyHandler {
	public static final int RESPONSE_LENGTH = 0xB0;
	
	int monsterIndex;
	int molePoints;
	
	public MonsterDeathHandler(LobbyServer lobbyServer, UserTCPSession tcpServer, byte[] messageBytes) {
		super(lobbyServer, tcpServer, messageBytes);
	}

	@Override
	public void interpretBytes() {
		monsterIndex = input.getInt(0x14);
		molePoints = input.getInt(0x44);
		System.out.println("Monster index: " + monsterIndex);
		/*
		 	14 - monster index
			18
			1C
			20
			24
			28
			2C
			30
			34
			38
			3C
			40 - monster type
			42 - monster level
			44 - integer
		 */
		
		System.out.println(input.getInt(0x18));
		System.out.println(input.getInt(0x1C));
		System.out.println(input.getInt(0x20));
		System.out.println(input.getInt(0x24));
		System.out.println(input.getInt(0x28));
		System.out.println(input.getInt(0x2C));
		System.out.println(input.getInt(0x30));
		System.out.println(input.getInt(0x34));
		System.out.println(input.getInt(0x38));
		System.out.println(input.getInt(0x3C));
		System.out.println(input.getShort(0x40));
		System.out.println(input.getShort(0x42));
	}

	@Override
	public byte[] getResponse() {
		return null;
	}

	@Override
	public void afterSend() throws IOException {
		Room room = lobbyServer.getRoom(userSession.getUser().roomIndex);
		if (room.isQuestType()) {
			lobbyServer.sendRoomMessage(userSession, new QuestDeathHandler(lobbyServer, userSession).getResponse(monsterIndex), true);
		}
		// this is enjoy game mode - race/mole
		else if (room.getGameMode() == GameMode.RACE || room.getGameMode() == GameMode.MOLE) {
			lobbyServer.sendRoomMessage(userSession, new EnjoyModeDeathHandler(lobbyServer, userSession).getResponse(monsterIndex, molePoints), true);
			
			if (room.getGameMode() == GameMode.RACE && monsterIndex == 33) {
				int[] results = new int[8];
				
				int winningTeam = userSession.getUser().roomTeam;
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUser(i) != null) {
						results[i] = (room.getUser(i).getUser().roomTeam == winningTeam) ? 1 : 2;
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
			}
			else if (room.getGameMode() == GameMode.MOLE) {
				int userTeam = userSession.getUser().roomTeam;
				
				if (userTeam == 10) {
					room.blueScore += molePoints;
					
					if (room.blueScore < 0) {
						room.blueScore = 0;
					}
				}
				else {
					room.redScore += molePoints;
					
					if (room.redScore < 0) {
						room.redScore = 0;
					}
				}
				
				if (room.blueScore >= 50 || room.redScore >= 50) {
					int winningTeam = (room.blueScore >= 50) ? 10 : 20;
					
					int[] results = new int[8];
					
					for (int i = 0; i < 8; i++) {
						results[i] = -1;
						
						if (room.getUser(i) != null) {
							results[i] = (room.getUser(i).getUser().roomTeam == winningTeam) ? 1 : 2;
						}
					}
					
					lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
				}
			}
		}
		else if (room.getGameMode() == GameMode.SYMBOL) {
			lobbyServer.sendRoomMessage(userSession, new EnjoyModeDeathHandler(lobbyServer, userSession).getResponse(monsterIndex, molePoints), true);
			
			room.symbols[monsterIndex] = userSession.getUser().roomTeam;
			
			int firstSymbol = room.symbols[0];
			boolean isDone = true;
			
			for (int i = 0; i < 4; i++) {
				if (room.symbols[i] == 0 || room.symbols[i] != firstSymbol) {
					isDone = false;
				}
			}
			
			if (isDone) {
				int[] results = new int[8];
				
				for (int i = 0; i < 8; i++) {
					results[i] = -1;
					
					if (room.getUser(i) != null) {
						results[i] = (room.getUser(i).getUser().roomTeam == firstSymbol) ? 1 : 2;
					}
				}
				
				lobbyServer.sendRoomMessage(userSession, new RoundCompletedHandler(lobbyServer, userSession).getResponse(results, -1), true);
			}
		}
	}

	@Override
	public void processMessage() {
		// TODO Auto-generated method stub
		
	}
}
