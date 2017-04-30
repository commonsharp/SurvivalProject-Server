package net.objects;

import java.security.InvalidParameterException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.Session;
import org.hibernate.query.Query;

import database.Database;
import lobby.LobbyServer;

@Entity
@Table(name = "user")
public class User {
	private String username;
	private String password;
	private int mainCharacter;
	private int playerLevel;
	private int usuableCharacterCount;
	private int ageRestriction;
	private long playerExperience;
	private long playerCode;
	private long avatarMoney;
	private String guildName;
	private String guildDuty;
	private long cash;
	
	private Card[] cards;
	private int userType;
	private int isMuted = 13;
	private int banDays = 19;
	private int guildRank;
	/*
	 * event 2 - premium event card changes this event somehow
	 * event 8 - triple exp/code/elements
	 */
//	public byte playerEventFlags[] = {1, 1, 1, 1, 1, 1, 1, 1};
	private byte playerEventFlags[] = {0, 0, 0, 1, 0, 0, 0, 0};
	
	private int playerInventorySlots;
	private int playerType = 0; // 0 - normal. 1 - demo. 7 - GM
	private int water;
	private int fire;
	private int earth;
	private int wind;
	private int scroll0;
	private int scroll1;
	private int scroll2;
	private int playerWins;
	private int playerLoses;
	private int playerKOs;
	private int playerDowns;
	
	private boolean male;
	private boolean connected;
	
	private int magicIndex;
	private int weaponIndex;
	private int accessoryIndex;
	private int petIndex;
	
	private int footIndex;
	private int bodyIndex;
	private int hand1Index;
	private int hand2Index;
	private int faceIndex;
	private int hairIndex;
	private int headIndex;
	private int missionLevel;
	
	private boolean isInRoom = false;
	private boolean isInGame = false;
	private int roomIndex;
	private int roomSlot;
	private int roomTeam;
	private int roomCharacter;
	private byte roomReady;
	private int roomRandom;
	
	private int lives;
	private int gameKO;
	
	private List<Friend> friends;
	private boolean isJoined;
	
	private List<UserShop> userShopResults;
	private int userShopOffset;
	
	private int booster;
	private boolean extraLife;
	private boolean gameExtraLife;
	
	private boolean timeBonus;
	
	private BigUserShop bigUserShop;
	
	private int channelType;
	private int totalTicks;
	
	private boolean isInitialLoginDataSent;
	
	private Server server;
	
	public User() {
		cards = new Card[96];
//		friends = new Friend[24];
		setInitialLoginDataSent(false);
	}
	
	public void removeCard(int index) {
		cards[index] = null;
	}
	
	public int addCard(Card card) {
		int emptySlot = getEmptyCardSlot();
		card.setCardIndex(emptySlot);
		cards[emptySlot] = card;
		
		return emptySlot;
	}
	
	@Transient
	public Card getCard(int index) {
		for (Card card : cards) {
			if (card != null && card.getCardIndex() == index) {
				return card;
			}
		}
		
		return null;
	}
	
	@Transient
	public int getAvatarItemID(int index) {
		if (index == -1 || getCard(index) == null) {
			return -1;
		}
		
		return getCard(index).getCardID();
	}
	
	@Transient
	public int getItemID(int index) {
		if (index == -1 || getCard(index) == null) {
			return -1;
		}
		
		return getCard(index).getCardID();
	}
	
	@Transient
	public int getItemPremiumDays(int index) {
		if (index == -1 || getCard(index) == null) {
			return 0;
		}
		
		return getCard(index).getCardPremiumDays();
	}
	
	@Transient
	public int getItemLevel(int index) {
		if (index == -1 || getCard(index) == null) {
			return 0;
		}
		
		return getCard(index).getCardLevel();
	}
	
	@Transient
	public int getItemSkill(int index) {
		if (index == -1 || getCard(index) == null) {
			return 0;
		}
		
		return getCard(index).getCardSkill();
	}
	
	@SuppressWarnings("unchecked")
	public static User loadUser(String username, LobbyServer lobbyServer) throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User where username = :username").setParameter("username", username).list();
		User result = null;
		session.getTransaction().commit();
		session.close();
		
		if (!users.isEmpty()) {
			result = users.get(0);
			result.setUserType((result.getPlayerLevel() == 0) ? 0 : (result.getPlayerLevel() >= 17) ? 30 : (result.getPlayerLevel() >= 13) ? 20 : 10);
			
			// load cards
			session = Database.getSession();
			session.beginTransaction();
			List<Card> cardsList = session.createQuery("from Card where username = :username").setParameter("username", username).list();
			
			for (Card card : cardsList) {
				if (card.getCardIndex() != -1) {
					result.cards[card.getCardIndex()] = card;
				}
			}
			
			// New user
			if (cardsList.isEmpty()) {
				for (int i = 0; i < 96; i++) {
					session.save(new Card(i, -1, -1, -1, -1));
				}
			}
			
			session.getTransaction().commit();
			session.close();
	        
	        // load friends
	        session = Database.getSession();
	        session.beginTransaction();
	        result.setFriends(session.createQuery("FROM Friend WHERE username = :username").setParameter("username", result.getUsername()).list());
	        session.getTransaction().commit();
	        session.close();
	        
	        // Load guild rank
	        if (lobbyServer != null && result.getGuildName() != null) {
				int myScore = 0;
				int myRank = 0;
				
				session = Database.getSession();
				session.beginTransaction();
				Query<?> query = session.createQuery("SELECT score FROM GuildScore WHERE serverID = :serverID AND guildName = :guildName");
				query.setParameter("serverID", lobbyServer.server.getId());
				query.setParameter("guildName", result.getGuildName());
				List<Integer> guildScores = (List<Integer>) query.list();
				
				if (!guildScores.isEmpty()) {
					myScore = guildScores.get(0);
					
					query = session.createQuery("SELECT count(*) FROM GuildScore WHERE serverID = :serverID AND score > :score");
					query.setParameter("serverID", lobbyServer.server.getId());
					query.setParameter("score", myScore);
					
					List<Long> count = (List<Long>) query.list();
					
					myRank = count.get(0).intValue() + 1;
				}
				
				result.setGuildRank(myRank - 1);
				
				session.getTransaction().commit();
				session.close();
			}
	        
	        result.setExtraLife(false);
	        result.setTimeBonus(false);
	        result.setBooster(0);
			
			for (int i = 0; i < 96; i++) {
				if (result.getCards()[i] != null) {
					if (result.getCards()[i].getCardID() == 0x7D7 || result.getCards()[i].getCardID() == 0x7D8 || result.getCards()[i].getCardID() == 0x7DC) {
						result.setBooster(result.getCards()[i].getCardID());
					}
					else if (result.getCards()[i].getCardID() == Card.QUEST_LIFE) {
						result.setExtraLife(true);
					}
					else if (result.getCards()[i].getCardID() == Card.TIME_BONUS) {
						result.setTimeBonus(true);
					}
				}
			}
			
		}
		
		return result;
	}
	
	public static void saveUser(User user) throws SQLException {
		// Update user information
		Session session = Database.getSession();
		session.beginTransaction();
		session.update(user);
		session.getTransaction().commit();
		session.close();
	}
	
	@SuppressWarnings("unchecked")
	public static void saveCards(User user, int... cardIndexes) {
		Session session = Database.getSession();
		session.beginTransaction();

		for (int i = 0; i < cardIndexes.length; i++) {
			if (user.cards[cardIndexes[i]] != null) {
				user.cards[cardIndexes[i]].setUsername(user.getUsername());
				List<Card> cardsList = session.createQuery("from Card where username = :username and cardIndex = :cardIndex").
						setParameter("username", user.getUsername()).setParameter("cardIndex", cardIndexes[i]).list();
				
				if (!cardsList.isEmpty()) {
					cardsList.get(0).setCardID(user.cards[cardIndexes[i]].getCardID());
					cardsList.get(0).setCardPremiumDays(user.cards[cardIndexes[i]].getCardPremiumDays());
					cardsList.get(0).setCardLevel(user.cards[cardIndexes[i]].getCardLevel());
					cardsList.get(0).setCardSkill(user.cards[cardIndexes[i]].getCardSkill());
					
					session.update(cardsList.get(0));
				}
			}
			else {
				session.createQuery("update Card set cardID = -1, cardPremiumDays = -1, cardLevel = -1, cardSkill = -1 where cardIndex = :cardIndex").
					setParameter("cardIndex", cardIndexes[i]).executeUpdate();
			}
		}
				
		session.getTransaction().commit();
		session.close();
		
	}
	@Transient
	public int getEmptyCardSlot() {
		boolean[] isUsed = new boolean[96];
		
		for (Card card : cards) {
			if (card.getCardID() != -1) {
				isUsed[card.getCardIndex()] = true;
			}
		}
		
		for (int i = 0; i < 96; i++) {
			if (!isUsed[i]) {
				return i;
			}
		}
		
		return -1;
	}
	
	@Transient
	public int getEmptyScrollSlot() {
		if (getScroll0() == 0) {
			return 0;
		}
		if (getScroll1() == 0) {
			return 1;
		}
		if (getScroll2() == 0) {
			return 2;
		}
		
		return -1;
	}
	
	public void addFriend(String friendName) {
		Friend friend = new Friend(getUsername(), friendName);
		friends.add(friend);
		
		Session session = Database.getSession();
		session.beginTransaction();
		session.save(friend);
		session.getTransaction().commit();
		session.close();
	}

	public void removeFriend(String friendName) {
		Iterator<Friend> iter = friends.iterator();
		
		while (iter.hasNext()) {
			Friend friend = iter.next();
			
			if (friend.getFriendName().equals(friendName)) {
				Session session = Database.getSession();
				session.beginTransaction();
				session.delete(friend);
				session.getTransaction().commit();
				session.close();
			
				iter.remove();
			}
		}
	}

	@Transient
	public int getElementType() {
		if (getMainCharacter() >= 250) {
			return (getMainCharacter() - 250) / 10 + 1;
		}
		int character = getMainCharacter();
		
		if (character >= 130) {
			character -= 120;
		}
		
		switch (getMainCharacter()) {
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

	@Id
	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "main_character")
	public int getMainCharacter() {
		return mainCharacter;
	}

	public void setMainCharacter(int mainCharacter) {
		this.mainCharacter = mainCharacter;
	}

	@Column(name = "level")
	public int getPlayerLevel() {
		return playerLevel;
	}

	public void setPlayerLevel(int playerLevel) {
		this.playerLevel = playerLevel;
	}

	@Column(name = "usuable_character_count")
	public int getUsuableCharacterCount() {
		return usuableCharacterCount;
	}

	public void setUsuableCharacterCount(int usuableCharacterCount) {
		this.usuableCharacterCount = usuableCharacterCount;
	}

	@Column(name = "age_restriction")
	public int getAgeRestriction() {
		return ageRestriction;
	}

	public void setAgeRestriction(int ageRestriction) {
		this.ageRestriction = ageRestriction;
	}

	@Column(name = "points")
	public long getPlayerExperience() {
		return playerExperience;
	}

	public void setPlayerExperience(long playerExperience) {
		this.playerExperience = playerExperience;
	}

	@Column(name = "code")
	public long getPlayerCode() {
		return playerCode;
	}

	public void setPlayerCode(long playerCode) {
		this.playerCode = playerCode;
	}

	@Column(name = "coins")
	public long getAvatarMoney() {
		return avatarMoney;
	}

	public void setAvatarMoney(long avatarMoney) {
		this.avatarMoney = avatarMoney;
	}

	@Column(name = "guild_name")
	public String getGuildName() {
		return guildName;
	}

	public void setGuildName(String guildName) {
		this.guildName = guildName;
	}

	@Column(name = "guild_duty")
	public String getGuildDuty() {
		return guildDuty;
	}

	public void setGuildDuty(String guildDuty) {
		this.guildDuty = guildDuty;
	}

	@Column(name = "cash")
	public long getCash() {
		return cash;
	}

	public void setCash(long cash) {
		this.cash = cash;
	}

	@Column(name = "inventory_slots")
	public int getPlayerInventorySlots() {
		return playerInventorySlots;
	}

	public void setPlayerInventorySlots(int playerInventorySlots) {
		this.playerInventorySlots = playerInventorySlots;
	}

	public int getWhiteCard(int index) {
		switch (index) {
		case 0: return getWater();
		case 1: return getFire();
		case 2: return getEarth();
		case 3: return getWind();
		}
		
		return -1;
	}
	
	@Transient
	public int[] getWhiteCards() {
		int[] whiteCards = new int[4];
		
		whiteCards[0] = water;
		whiteCards[1] = fire;
		whiteCards[2] = earth;
		whiteCards[3] = wind;
		
		return whiteCards;
	}
	
	public void setWhiteCard(int index, int value) {
		switch (index) {
		case 0:
			setWater(value);
			break;
		case 1:
			setWater(value);
			break;
		case 2:
			setWater(value);
			break;
		case 3:
			setWater(value);
			break;
		default:
			throw new InvalidParameterException("Wrong element type.");
		}
	}
//	@Column(name = "white_cards")
//	public int[] getWhiteCards() {
//		return whiteCards;
//	}
//
//	public void setWhiteCards(int[] whiteCards) {
//		this.whiteCards = whiteCards;
//	}

//	@Column(name = "scrolls")
//	public int[] getScrolls() {
//		return scrolls;
//	}
//
//	public void setScrolls(int scrolls[]) {
//		this.scrolls = scrolls;
//	}
	
	public int getScroll(int index) {
		switch (index) {
		case 0: return getScroll0();
		case 1: return getScroll1();
		case 2: return getScroll2();
		}
		
		return -1;
	}
	
	public void setScroll(int index, int value) {
		switch (index) {
		case 0:
			setScroll0(value);
			break;
		case 1:
			setScroll1(value);
			break;
		case 2:
			setScroll2(value);
			break;
		default:
			throw new InvalidParameterException("Wrong element type.");
		}
	}
	
	@Transient
	public int[] getScrolls() {
		int[] scrolls = new int[3];
		scrolls[0] = scroll0;
		scrolls[1] = scroll1;
		scrolls[2] = scroll2;
		
		return scrolls;
	}

	@Column(name = "wins")
	public int getPlayerWins() {
		return playerWins;
	}

	public void setPlayerWins(int playerWins) {
		this.playerWins = playerWins;
	}

	@Column(name = "loses")
	public int getPlayerLoses() {
		return playerLoses;
	}

	public void setPlayerLoses(int playerLoses) {
		this.playerLoses = playerLoses;
	}

	@Column(name = "kills")
	public int getPlayerKOs() {
		return playerKOs;
	}

	public void setPlayerKOs(int playerKOs) {
		this.playerKOs = playerKOs;
	}

	@Column(name = "downs")
	public int getPlayerDowns() {
		return playerDowns;
	}

	public void setPlayerDowns(int playerDowns) {
		this.playerDowns = playerDowns;
	}

	@Column(name = "is_male")
	public boolean isMale() {
		return male;
	}

	public void setMale(boolean male) {
		this.male = male;
	}

	@Column(name = "magic_index")
	public int getMagicIndex() {
		return magicIndex;
	}

	public void setMagicIndex(int magicIndex) {
		this.magicIndex = magicIndex;
	}

	@Column(name = "weapon_index")
	public int getWeaponIndex() {
		return weaponIndex;
	}

	public void setWeaponIndex(int weaponIndex) {
		this.weaponIndex = weaponIndex;
	}

	@Column(name = "accessory_index")
	public int getAccessoryIndex() {
		return accessoryIndex;
	}

	public void setAccessoryIndex(int accessoryIndex) {
		this.accessoryIndex = accessoryIndex;
	}

	@Column(name = "pet_index")
	public int getPetIndex() {
		return petIndex;
	}

	public void setPetIndex(int petIndex) {
		this.petIndex = petIndex;
	}

	@Column(name = "foot_index")
	public int getFootIndex() {
		return footIndex;
	}

	public void setFootIndex(int footIndex) {
		this.footIndex = footIndex;
	}

	@Column(name = "body_index")
	public int getBodyIndex() {
		return bodyIndex;
	}

	public void setBodyIndex(int bodyIndex) {
		this.bodyIndex = bodyIndex;
	}

	@Column(name = "hand1_index")
	public int getHand1Index() {
		return hand1Index;
	}

	public void setHand1Index(int hand1Index) {
		this.hand1Index = hand1Index;
	}

	@Column(name = "hand2_index")
	public int getHand2Index() {
		return hand2Index;
	}

	public void setHand2Index(int hand2Index) {
		this.hand2Index = hand2Index;
	}

	@Column(name = "face_index")
	public int getFaceIndex() {
		return faceIndex;
	}

	public void setFaceIndex(int faceIndex) {
		this.faceIndex = faceIndex;
	}

	@Column(name = "hair_index")
	public int getHairIndex() {
		return hairIndex;
	}

	public void setHairIndex(int hairIndex) {
		this.hairIndex = hairIndex;
	}

	@Column(name = "head_index")
	public int getHeadIndex() {
		return headIndex;
	}

	public void setHeadIndex(int headIndex) {
		this.headIndex = headIndex;
	}

	@Column(name = "mission_level")
	public int getMissionLevel() {
		return missionLevel;
	}

	public void setMissionLevel(int missionLevel) {
		this.missionLevel = missionLevel;
	}

	@ManyToOne
	@JoinColumn(name = "server_id")
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	@Column(name = "is_connected")
	public boolean isConnected() {
		return connected;
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	@Transient
	public Card[] getCards() {
		return cards;
	}

	public void setCards(Card[] cards) {
		this.cards = cards;
	}

	@Transient
	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	@Transient
	public int getIsMuted() {
		return isMuted;
	}

	public void setIsMuted(int isMuted) {
		this.isMuted = isMuted;
	}

	@Transient
	public int getBanDays() {
		return banDays;
	}

	public void setBanDays(int banDays) {
		this.banDays = banDays;
	}

	@Transient
	public int getGuildRank() {
		return guildRank;
	}

	public void setGuildRank(int guildRank) {
		this.guildRank = guildRank;
	}

	@Transient
	public byte[] getPlayerEventFlags() {
		return playerEventFlags;
	}

	public void setPlayerEventFlags(byte playerEventFlags[]) {
		this.playerEventFlags = playerEventFlags;
	}

	@Transient
	public int getPlayerType() {
		return playerType;
	}

	public void setPlayerType(int playerType) {
		this.playerType = playerType;
	}

	@Transient
	public boolean isInRoom() {
		return isInRoom;
	}

	public void setInRoom(boolean isInRoom) {
		this.isInRoom = isInRoom;
	}

	@Transient
	public boolean isInGame() {
		return isInGame;
	}

	public void setInGame(boolean isInGame) {
		this.isInGame = isInGame;
	}

	@Transient
	public int getRoomIndex() {
		return roomIndex;
	}

	public void setRoomIndex(int roomIndex) {
		this.roomIndex = roomIndex;
	}

	@Transient
	public int getRoomSlot() {
		return roomSlot;
	}

	public void setRoomSlot(int roomSlot) {
		this.roomSlot = roomSlot;
	}

	@Transient
	public int getRoomTeam() {
		return roomTeam;
	}

	public void setRoomTeam(int roomTeam) {
		this.roomTeam = roomTeam;
	}

	@Transient
	public int getRoomCharacter() {
		return roomCharacter;
	}

	public void setRoomCharacter(int roomCharacter) {
		this.roomCharacter = roomCharacter;
	}

	@Transient
	public byte getRoomReady() {
		return roomReady;
	}

	public void setRoomReady(byte roomReady) {
		this.roomReady = roomReady;
	}

	@Transient
	public int getRoomRandom() {
		return roomRandom;
	}

	public void setRoomRandom(int roomRandom) {
		this.roomRandom = roomRandom;
	}

	@Transient
	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	@Transient
	public int getGameKO() {
		return gameKO;
	}

	public void setGameKO(int gameKO) {
		this.gameKO = gameKO;
	}

	@Transient
	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	@Transient
	public boolean isJoined() {
		return isJoined;
	}

	public void setJoined(boolean isJoined) {
		this.isJoined = isJoined;
	}

	@Transient
	public List<UserShop> getUserShopResults() {
		return userShopResults;
	}

	public void setUserShopResults(List<UserShop> userShopResults) {
		this.userShopResults = userShopResults;
	}

	@Transient
	public int getUserShopOffset() {
		return userShopOffset;
	}

	public void setUserShopOffset(int userShopOffset) {
		this.userShopOffset = userShopOffset;
	}

	@Transient
	public int getBooster() {
		return booster;
	}

	public void setBooster(int booster) {
		this.booster = booster;
	}

	@Transient
	public boolean isExtraLife() {
		return extraLife;
	}

	public void setExtraLife(boolean extraLife) {
		this.extraLife = extraLife;
	}

	@Transient
	public boolean isGameExtraLife() {
		return gameExtraLife;
	}

	public void setGameExtraLife(boolean gameExtraLife) {
		this.gameExtraLife = gameExtraLife;
	}

	@Transient
	public boolean isTimeBonus() {
		return timeBonus;
	}

	public void setTimeBonus(boolean timeBonus) {
		this.timeBonus = timeBonus;
	}

	@Transient
	public BigUserShop getBigUserShop() {
		return bigUserShop;
	}

	public void setBigUserShop(BigUserShop bigUserShop) {
		this.bigUserShop = bigUserShop;
	}

	@Transient
	public int getChannelType() {
		return channelType;
	}

	public void setChannelType(int channelType) {
		this.channelType = channelType;
	}

	@Transient
	public int getTotalTicks() {
		return totalTicks;
	}

	public void setTotalTicks(int totalTicks) {
		this.totalTicks = totalTicks;
	}

	@Transient
	public boolean isInitialLoginDataSent() {
		return isInitialLoginDataSent;
	}

	public void setInitialLoginDataSent(boolean isInitialLoginDataSent) {
		this.isInitialLoginDataSent = isInitialLoginDataSent;
	}

	@Column(name = "water")
	public int getWater() {
		return water;
	}

	public void setWater(int water) {
		this.water = water;
	}

	@Column(name = "fire")
	public int getFire() {
		return fire;
	}

	public void setFire(int fire) {
		this.fire = fire;
	}

	@Column(name = "earth")
	public int getEarth() {
		return earth;
	}

	public void setEarth(int earth) {
		this.earth = earth;
	}

	@Column(name = "wind")
	public int getWind() {
		return wind;
	}

	public void setWind(int wind) {
		this.wind = wind;
	}

	@Column(name = "scroll0")
	public int getScroll0() {
		return scroll0;
	}

	public void setScroll0(int scroll0) {
		this.scroll0 = scroll0;
	}

	@Column(name = "scroll1")
	public int getScroll1() {
		return scroll1;
	}

	public void setScroll1(int scroll1) {
		this.scroll1 = scroll1;
	}

	@Column(name = "scroll2")
	public int getScroll2() {
		return scroll2;
	}

	public void setScroll2(int scroll2) {
		this.scroll2 = scroll2;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean isUserExists(String username) throws SQLException {
		Session session = Database.getSession();
		session.beginTransaction();
		List<User> users = session.createQuery("from User where username = :username").setParameter("username", username).list();
		
		boolean isExists = !users.isEmpty();
		
		session.getTransaction().commit();
		session.close();
		
		return isExists;
	}
}