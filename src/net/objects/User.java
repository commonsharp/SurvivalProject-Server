package net.objects;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.query.Query;

import database.Database;
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
	 * event 2 - premium event does something with it
	 * event 8 - triple exp/code/elements
	 */
//	public byte playerEventFlags[] = {1, 1, 1, 1, 1, 1, 1, 1};
	public byte playerEventFlags[] = {0, 0, 0, 1, 0, 0, 0, 0};
	
	public int playerInventorySlots;
	public int playerType = 0; // 0 - normal. 1 - demo. 7 - GM
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
	public int roomRandom;
	
	public int udpState;
	public InetAddress udpIPAddress;
	public int udpPort;
	
	public int encryptionVersion;
	
	public int lives;
	public int gameKO;
	
	public List<Friend> friends;
	public boolean isJoined;
	
	public List<UserShop> userShopResults;
	public int userShopOffset;
	
	public int booster;
	public boolean extraLife;
	public boolean gameExtraLife;
	
	public boolean timeBonus;
	
	public BigUserShop bigUserShop;
	
	public int channelType;
	public int totalTicks;
	
	public boolean isInitialLoginDataSent;
	
	public User() {
		cards = new Card[96];
		whiteCards = new int[4];
//		friends = new Friend[24];
		scrolls = new int[3];
		isInitialLoginDataSent = false;
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
	
	@SuppressWarnings("unchecked")
	public void loadUser(LobbyServer lobbyServer) throws SQLException {
		try {
			Thread.sleep(0);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Connection con = Database.getConnection();
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
	        Session session = Database.getSession();
	        session.beginTransaction();
	        friends = session.createQuery("FROM Friend WHERE username = :username").setParameter("username", username).list();
	        session.getTransaction().commit();
	        session.close();
	        
	        // Load guild rank
	        if (lobbyServer != null && guildName != null) {
				int myScore = 0;
				int myRank = 0;
				
				session = Database.getSession();
				Query query = session.createQuery("SELECT score FROM GuildScore WHERE serverID = :serverID AND guildName = :guildName");
				query.setParameter("serverID", lobbyServer.server.getId());
				query.setParameter("guildName", guildName);
				List<Integer> guildScores = query.list();
				
				if (!guildScores.isEmpty()) {
					myScore = guildScores.get(0);
					
					query = session.createQuery("SELECT count(*) FROM GuildScore WHERE serverID = :serverID AND score > :score");
					query.setParameter("serverID", lobbyServer.server.getId());
					query.setParameter("score", myScore);
					
					List<Long> count = query.list();
					
					myRank = count.get(0).intValue() + 1;
				}
				
				guildRank = myRank - 1;
			}
		}
		
		extraLife = false;
		timeBonus = false;
		booster = 0;
		
		for (int i = 0; i < 96; i++) {
			if (cards[i] != null) {
				if (cards[i].getId() == 0x7D7 || cards[i].getId() == 0x7D8 || cards[i].getId() == 0x7DC) {
					booster = cards[i].getId();
				}
				else if (cards[i].getId() == Card.QUEST_LIFE) {
					extraLife = true;
				}
				else if (cards[i].getId() == Card.TIME_BONUS) {
					timeBonus = true;
				}
			}
		}
		
		con.close();
	}
	
	public void saveUser() throws SQLException {
		if (username != null) {
			Connection con = Database.getConnection();
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
	
	public void addFriend(String friendName) {
		Friend friend = new Friend(username, friendName);
		friends.add(friend);
		
		Session session = Database.getSession();
		session.beginTransaction();
		session.save(friend);
		session.getTransaction().commit();
		session.close();
	}

	public void removeFriend(String friendName) {
		for (Friend friend : friends) {
			if (friend.getFriendName().equals(friendName)) {
				Session session = Database.getSession();
				session.beginTransaction();
				session.delete(friend);
				session.getTransaction().commit();
				session.close();
				
				friends.remove(friend);
			}
		}
	}

	public int getElementType() {
		if (mainCharacter >= 250) {
			return (mainCharacter - 250) / 10 + 1;
		}
		int character = mainCharacter;
		
		if (character >= 130) {
			character -= 120;
		}
		
		switch (mainCharacter) {
		case 30:
		case 40:
		case 110:
			return 1;
		case 10:
		case 20:
		case 90:
			return 2;
		case 50:
		case 80:
		case 100:
			return 3;
		case 60:
		case 70:
		case 120:
			return 4;
		}
		
		return -1;
	}
}
