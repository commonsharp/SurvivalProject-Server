package net.objects;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DatabaseConnection;
import lobby.LobbyServer;

public class User {
	public String username;
	public String password;
	
	public int userType;
	public int mainCharacter;
	public int playerLevel;
	public int usuableCharacterCount;
	public int isMuted = 13;
	public int banDays = 19;
	public int ageRestriction;
	public long playerExperience;
	public long playerCode;
	public long avatarMoney;
	public String guildName;
	public String guildDuty;
	public int guildRank;
	public long cash;
	
	public Card[] cards;
	
	/*
	 * event 8 - triple exp/code/elements
	 */
//	public byte playerEventFlags[] = {1, 1, 1, 1, 1, 1, 1, 1};
	public byte playerEventFlags[] = {0, 0, 0, 0, 0, 0, 0, 0};
	
	public int playerInventorySlots;
	public int playerType = 0; //set to 7 for GM... otherwise 0
	public int[] whiteCards;
	public int scrolls[];
	public int playerWins;
	public int playerLoses;
	public int playerKOs;
	public int playerDowns;
	
	public boolean isMale;
	
	public int magicIndex, weaponIndex, accessoryIndex, petIndex;
	public int footIndex, bodyIndex, hand1Index, hand2Index, faceIndex, hairIndex, headIndex;
	public int missionLevel;
	
	public boolean isInRoom = false;
	public boolean isInGame = false;
	public int roomIndex;
	public int roomSlot;
	public int roomTeam;
	public int roomCharacter;
	public byte roomReady;
	
	public int udpState;
	public InetAddress udpIPAddress;
	public int udpPort;
	
	public int encryptionVersion;
	
	public int lives;
	public int gameKO;
	
	public String[] friends;
	public boolean isJoined;
	
	public ArrayList<UserShop> userShopResults;
	public int userShopOffset;
	
	public int booster;
	public boolean extraLife;
	public boolean gameExtraLife;
	
	public BigUserShop bigUserShop;
	
	public int channelType;
	
	public User() {
		cards = new Card[96];
		whiteCards = new int[4];
		friends = new String[24];
		scrolls = new int[3];
	}
	
	public Card getCard(int index) {
		return cards[index];
	}
	
	public int getAvatarItemID(int index) {
		if (index == -1 || cards[index] == null) {
			return -1;
		}
		
		return cards[index].getId();
	}
	
	public int getItemID(int index) {
		if (index == -1 || cards[index] == null) {
			return -1;
		}
		
		return cards[index].getId();
	}
	
	public int getItemPremiumDays(int index) {
		if (index == -1 || cards[index] == null) {
			return 0;
		}
		
		return cards[index].getPremiumDays();
	}
	
	public int getItemLevel(int index) {
		if (index == -1 || cards[index] == null) {
			return 0;
		}
		
		return cards[index].getLevel();
	}
	
	public int getItemSkill(int index) {
		if (index == -1 || cards[index] == null) {
			return 0;
		}
		
		return cards[index].getSkill();
	}
	
	public void loadUser(LobbyServer lobbyServer) throws SQLException {
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("Select * FROM users WHERE username = ?");
		ps.setString(1, username);
		ResultSet rs = ps.executeQuery();
		
		if (rs.next()) {
			username = rs.getString("username");
			password = rs.getString("password");
	        mainCharacter = rs.getInt("mainCharacter");
	        playerLevel = rs.getInt("playerLevel");
	        usuableCharacterCount = rs.getInt("usuableCharacterCount");
	        ageRestriction = rs.getInt("ageRestriction");
	        playerExperience = rs.getLong("experience");
	        playerCode = rs.getLong("code");
	        avatarMoney = rs.getLong("avatarMoney");
	        guildName = rs.getString("guildName");
	        guildDuty = rs.getString("guildDuty");
	        whiteCards[0] = rs.getInt("waterElements");
	        whiteCards[1] = rs.getInt("fireElements");
	        whiteCards[2] = rs.getInt("earthElements");
	        whiteCards[3] = rs.getInt("windElements");
	        isMale = rs.getBoolean("isMale");
	        magicIndex = rs.getInt("magicIndex");
	        weaponIndex = rs.getInt("weaponIndex");
	        accessoryIndex = rs.getInt("accessoryIndex");
	        petIndex = rs.getInt("petIndex");
	        footIndex = rs.getInt("footIndex");
	        bodyIndex = rs.getInt("bodyIndex");
	        hand1Index = rs.getInt("hand1Index");
	        hand2Index = rs.getInt("hand2Index");
	        faceIndex = rs.getInt("faceIndex");
	        hairIndex = rs.getInt("hairIndex");
	        headIndex = rs.getInt("headIndex");
	        playerWins = rs.getInt("winCount");
	        playerLoses = rs.getInt("loseCount");
	        playerKOs = rs.getInt("koCount");
	        playerDowns = rs.getInt("downCount");
	        cash = rs.getLong("cash");
	        playerInventorySlots = rs.getInt("inventorySlots");
	        scrolls[0] = rs.getInt("scroll0");
	        scrolls[1] = rs.getInt("scroll1");
	        scrolls[2] = rs.getInt("scroll2");
	        missionLevel = rs.getInt("missionLevel");
	        
	        userType = (playerLevel == 0) ? 0 : (playerLevel >= 17) ? 30 : (playerLevel >= 13) ? 20 : 10;
	        
	        ps.close();
	        rs.close();
	        
	        // load cards
	        ps = con.prepareStatement("Select * FROM cards WHERE username = ?");
	        ps.setString(1, username);
	        rs = ps.executeQuery();
	        
	        int i;
	        while (rs.next()) {
	        	i = rs.getInt("index");
	        	
	        	int id = rs.getInt("id");
	        	
	        	if (id != -1) { 
	        		cards[i] = new Card(id, rs.getInt("premiumDays"), rs.getInt("level"), rs.getInt("skill"));
	        	}
	        }
	        
	        rs.close();
	        ps.close();
	        
	        // load friends
	        ps = con.prepareStatement("Select * FROM friends WHERE username = ?");
	        ps.setString(1, username);
	        rs = ps.executeQuery();
	        i = 0;
	        
	        while (rs.next()) {
	        	friends[i++] = rs.getString("friend");
	        }
	        
	        rs.close();
	        ps.close();
	        
	        // Load guild rank
	        if (lobbyServer != null && guildName != null) {
				int myScore = 0;
				int myRank = 0;
				
				ps = con.prepareStatement("SELECT guild_score FROM guild_score WHERE server_hostname = ? AND server_port = ? AND guild_name = ?");
				ps.setString(1, lobbyServer.hostname);
				ps.setInt(2, lobbyServer.port);
				ps.setString(3, guildName);
				rs = ps.executeQuery();
				
				if (rs.next()) {
					myScore = rs.getInt("guild_score");
					rs.close();
					
					ps = con.prepareStatement("SELECT Count(*) FROM guild_score WHERE server_hostname = ? AND server_port = ? AND guild_score > ?");
					ps.setString(1, lobbyServer.hostname);
					ps.setInt(2, lobbyServer.port);
					ps.setInt(3, myScore);
					
					rs = ps.executeQuery();
					
					if (rs.next()) {
						myRank = rs.getInt(1) + 1;
					}
					
					rs.close();
				}
				
				ps.close();
				con.close();
				
				guildRank = myRank - 1;
			}
		}
		
		extraLife = false;
		
		if (booster == 0) {
			for (int i = 0; i < 96; i++) {
				if (cards[i] != null) {
					if (cards[i].getId() == 0x7D7 || cards[i].getId() == 0x7D8 || cards[i].getId() == 0x7DC) {
						booster = cards[i].getId();
					}
					else if (cards[i].getId() == Card.QUEST_LIFE) {
						extraLife = true;
					}
				}
			}
		}
		
		con.close();
	}
	
	public void saveUser() throws SQLException {
		if (username != null) {
			Connection con = DatabaseConnection.getConnection();
			PreparedStatement ps = con.prepareStatement("Update users SET username=?, mainCharacter=?, playerLevel=?, usuableCharacterCount=?, "
					+ "ageRestriction=?, experience=?, code=?, avatarMoney=?, guildName=?, guildDuty=?, waterElements=?, "
					+ "fireElements=?, earthElements=?, windElements=?, isMale=?, magicIndex=?, weaponIndex=?, accessoryIndex=?, petIndex=?, "
					+ "footIndex=?, bodyIndex=?, hand1Index=?, hand2Index=?, faceIndex=?, hairIndex=?, headIndex=?, password=?, "
					+ "winCount=?, loseCount=?, koCount=?, downCount=?, cash=?, inventorySlots=?, scroll0=?, scroll1=?, scroll2=?, missionLevel=? WHERE username=?");
			
			ps.setString(1, username);
			ps.setInt(2, mainCharacter);
			ps.setInt(3, playerLevel);
			ps.setInt(4, usuableCharacterCount);
			ps.setInt(5, ageRestriction);
			ps.setLong(6, playerExperience);
			ps.setLong(7, playerCode);
			ps.setLong(8, avatarMoney);
			ps.setString(9, guildName);
			ps.setString(10, guildDuty);
			ps.setInt(11, whiteCards[0]);
			ps.setInt(12, whiteCards[1]);
			ps.setInt(13, whiteCards[2]);
			ps.setInt(14, whiteCards[3]);
			ps.setBoolean(15, isMale);
			ps.setInt(16, magicIndex);
			ps.setInt(17, weaponIndex);
			ps.setInt(18, accessoryIndex);
			ps.setInt(19, petIndex);
			ps.setInt(20, footIndex);
			ps.setInt(21, bodyIndex);
			ps.setInt(22, hand1Index);
			ps.setInt(23, hand2Index);
			ps.setInt(24, faceIndex);
			ps.setInt(25, hairIndex);
			ps.setInt(26, headIndex);
			ps.setString(27, password);
			ps.setInt(28, playerWins);
			ps.setInt(29, playerLoses);
			ps.setInt(30, playerKOs);
			ps.setInt(31, playerDowns);
			ps.setLong(32, cash);
			ps.setInt(33, playerInventorySlots);
			ps.setInt(34, scrolls[0]);
			ps.setInt(35, scrolls[1]);
			ps.setInt(36, scrolls[2]);
			ps.setInt(37, missionLevel);
			
			ps.setString(38, username);
			ps.executeUpdate();
			ps.close();
			
			for (int i = 0; i < cards.length; i++) {
				ps = con.prepareStatement("INSERT INTO cards VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = ?, premiumDays = ?, level = ?, skill = ?");
				ps.setString(1, username);
				ps.setInt(2, i);
				
				if (cards[i] != null) {
					ps.setInt(3, cards[i].getId());
					ps.setInt(4, cards[i].getPremiumDays());
					ps.setInt(5, cards[i].getLevel());
					ps.setInt(6, cards[i].getSkill());
					ps.setInt(7, cards[i].getId());
					ps.setInt(8, cards[i].getPremiumDays());
					ps.setInt(9, cards[i].getLevel());
					ps.setInt(10, cards[i].getSkill());
				}
				else {
					ps.setInt(3, -1);
					ps.setInt(4, -1);
					ps.setInt(5, -1);
					ps.setInt(6, -1);
					ps.setInt(7, -1);
					ps.setInt(8, -1);
					ps.setInt(9, -1);
					ps.setInt(10, -1);
				}
				ps.executeUpdate();
				ps.close();
			}
			
			con.close();
		}
	}
	
	public int getEmptyCardSlot() {
		for (int i = 0; i < 96; i++) {
			if (cards[i] == null) {
				return i;
			}
		}
		
		return -1;
	}
	
	public int getEmptyScrollSlot() {
		for (int i = 0; i < 3; i++) {
			if (scrolls[i] == 0) {
				return i;
			}
		}
		
		return -1;
	}
	
	public void addFriend(String friendName) throws SQLException {
		int i;
		
		for (i = 0; i < 24; i++) {
			if (friends[i] == null) {
				break;
			}
		}
		
		friends[i] = friendName;
		
		Connection con = DatabaseConnection.getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO friends VALUES (?, ?)");
		ps.setString(1, username);
		ps.setString(2, friendName);
		ps.executeUpdate();
		
		ps.close();
		con.close();
	}

	public void removeFriend(String friendName) throws SQLException {
		for (int i = 0; i < 24; i++) {
			if (friends[i] != null && friends[i].equals(friendName)) {
				friends[i] = null;
				
				Connection con = DatabaseConnection.getConnection();
				PreparedStatement ps = con.prepareStatement("DELETE FROM friends WHERE username = ? AND friend = ?");
				ps.setString(1, username);
				ps.setString(2, friendName);
				ps.executeUpdate();
				
				ps.close();
				con.close();
				return;
			}
		}
	}
}
