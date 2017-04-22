# SurvivalProject-Server
Server files for the game Survival Project, written in Java.

**THE USE OF THE SERVER FILES IS ON YOUR OWN RESPONSIBILITY, AND YOU MAY FIND YOURSELF GUILTY OF COPYRIGHT INFRINGEMENT.**

For instructions on how to install Survival Project on Windows 10, go to the Win10 folder.

Time spent on the project: **6 weeks**.

For a weekly summary of the progress I'm making, follow me here:
https://www.reddit.com/r/survivalproject/comments/5zm36i/started_writing_a_private_server_in_java

**Not going to work on hokey mode because I can't fucking score a goal -_-"**

**~~Got a job interview next week so I'm going to take a break and continue the development next week~~** - Actually I have more time than I previously thought.

# Built-in chat commands
Apparently Survival Project has some built in commands. Unfortunately, most of them are in Korean.
Type /macro in-game to see those commands.

The english ones are: /open, /title, /pw, /block, /friend, /search and /memo.

There's also a command to change your character in mission training mode - /character [Character-name]. The problem is that the strings.xml translation is a bit off so that macro isn't working.

In order to fix that, go to images\strings.xml and change "Character" in line 4752 to "/character". This should fix the macro. 

# Chat commands
**After executing most of the commands, a reconnect is required in order to see the changes.**

**@gender** - changes your gender from male to female and vice versa. Reconnect to see the changes (others will see it instantly).

**@guild-join [guild-name]** - joins the guild [guild-name].

**@guild-leave** - leaves your guild.

# Game modes left to add
- [ ] Infinity survival mode
- [ ] Infinity symbol mode
- [ ] King slayer mode
- [ ] Fight club mode
- [ ] Tournament mode
- [ ] Ice hokey mode :(

# Premiums cards that are not yet working
- [ ] Master card
- [ ] Visit bonus x2
- [ ] Elemental bonus x3
- [ ] Premium Event
- [ ] SuperSilver
- [ ] CardSlot+18, CardSlot+30
- [x] Skill I fusion
- [x] Skill II fusion
- [ ] User shop I
- [ ] Packages

# TODO
- [ ] Gain elements after a failed level up fusion
- [ ] Change the infinity games timers to ticks-based instead of a new thread with a timer. It will also fix the bug that if you leave the room, the timer still goes on, and will get executed when finished.
- [ ] Random characters
- [ ] Change the server sockets to NIO sockets.
- [ ] Try to find a way to translate the korean macros and if I can't, change the built-in korean commands to my own commands.
- [ ] Change the guild points formula to the correct one.
- [ ] The option to send send a reject message (after an invitation) is grayed out for some reason. If I manage to fix it, I need to make sure it actually works.
- [ ] Some premium cards also give you elements when you purchase them.
- [ ] Change every saveUser call to a shorter SQL query to reduce database operations.
- [ ] Rename each SQL table according to the naming convention.
- [ ] The sorting of userSessions is done in O(nlgn) instead of O(n).
- [ ] Add foreign keys in the database.
- [ ] Lose code/cash/coins when you make a purchase.
- [ ] Unpack b6oy's sp2.exe and get rid of the badboys.
- [ ] Change every game over message to a round over message.
- [ ] Premium cards are only working when you buy them in the cards shop (will be changed once trades/user shops are completely implemented).
- [ ] Lose premium days when a day passes.
- [ ] Clean the game modes code a bit.

# List of bugs
* When you finish a mission, the mission level on the left of the screen doesn't change.
* Quest death experience/code gain is getting executed multiple times (because getResponse() is getting called multiple times).
* Death timer in big matches doesn't work.
* Quests with multiple players isn't working well. Sometimes they work, sometimes they don't.
* Big match experience/code gained doesn't work well (it seems slot 0 keeps getting exp).
* Join a crystal game mode, let a player leave (because he's afk), destroy a crystal, get null pointer exception.
* If someone leaves during a survival game, other players get a win message.

# 22/04/2017
* Started working on skills.
* Premium cards: Skill Fusion I and Skill Fusion II are now working.
* Infinity king mode: the first player to join the game of each team becomes the first king.
* Infinity king mode: if the king has 1 points and a new player joins his team, that new player becomes the new king.
* Infinity king mode: when the king dies, he goes back to having 1 point.
* Infinity king mode: when the king dies, the points the king had are split between the team members of the other team.
* Infinity king mode: when you kill a player who isn't the king, you get 1 point.
* Infinity king mode: treasures are randomly spawned.
* Infinity king mode: there's a timer of exactly 5 minutes. When the timer is over, so is the game.
* Infinity king mode: when a new players joins the game, he gets the correct timer in the game.
* Infinity king mode: when the timer reaches 0, the stats window pops up.
* Infinity king mode: when a game is finished, each player gets 8000 points and code, 5 elements and some coins. The last player gets 3 coins, the next one gets 4 and so on. If there are 8 players in the room, the first one gets 11 coins, not 10.
* Fixed the bug where if someone left a game, the other players would get a win message (in every game mode).

# 21/04/2017
* Finally found a way to get the infinity game modes to work. Still got a lot of work to get them to work properly.
* Hero mode is now working.
* Crystal capture mode is now working.
* You now gain elements every 30 minutes of gameplay. The amount increases every 30 minutes. The first 30 minutes you get 2 elements, then 4, then 8 and after 2 hours you get 16 elements every 30 minutes. I haven't played the game for more than 2 hours straight so I'm not sure if should actually stop at 16 elements. More testing will need to be done in the future...
* The premium card 'Time Bonus' is now working.
* Fixed a bug where if a player had a quest life, it would be used in battle modes as well, resulting in a never ending game.
* Fixed the bug where switching teams wasn't shown to the people in the lobby.
* Fixed a bug where kicking a player didn't work properly.

# 20/04/2017
* There are now 3 servers, one for each channel (beginner/hero/epic).
* The activity of every channel is now the correct one instead of a random one (if there are 100 connected players, and 80% of them are in the epic channel, the epic channel will show 80% of the bars full. That way you know where most of the players are).
* Find user is now working when the user to be found is on a different server.
* Fixed a bug where different features weren't locked on the beginner channel when they should've.
* Fixed a bug where the GetListOfRooms threw a null pointer exception sometimes.

# 19/04/2017
* Extensive research on every game packet in the game.
* The starting tutorial is now working perfectly. You gain 1 random weapon and 1 random accessory of the same element type and a random slime pet (green, purple or blue).

# 18/04/2017
* Game notifications: when a player successfuly level up a card to level 7+, other players in the game get a notification.
* Game notifications: when a level 30+ player joins the server, other players in the game get a notification.
* User mini shop: you can create a new user mini shop.
* User mini shop: you can enter a user mini shop and see its content.
* User mini shop: you can remove cards from the user mini shop.
* User mini shop: you can add new cards to the user mini shop.
* Fixed a bug where UDP packets were not always sent.
* Finally got a list of all the messages in the game. There's a long way to go :O

# 17/04/2017
* Found a few new packets related to events, which will not be implemented in the near future.
* Started working on game notifications. Right now there's an option to send a "player x upgraded a card to level y". 
* Gifts: you can now send and receive gifts. You can gift cards, elements or code.
* Gold force cards are now working.
* Rooms can now have passwords.
* You can change a room's password with the built-in /pw command. '/pw 123' will change the password to 123. '/pw' will remove the password.
* You can now change your character while being in a game in mission training mode with the built-in /character command.

# 16/04/2017
* Spent the entire day trying to fix a bug where the host of the server could not play the game, only to end up with the fact that the problem is with my router, and not the server. -_-

# 15/04/2017
* Coin bubbles are now randomly spawned throughout the game. Every player in the game adds 1 'tick' every 9 seconds. Every 10 ticks, a random coin bubble is spawned. That means if there's 1 player in the room, a coin bubble will spawn every 90 second, and if there are 2 players, a coin bubble will spawn every 45 seconds. You get 1000 coins for a regular coin bubble and 3000 coins for a triple coin bubble. The values are subject to change.
* You can now verify users in the send memo screen.
* You can now send and receive memos. If the recipient is disconnected, he will get the memo when he logs in.
* When the master of the room leaves, another player becomes the new master.
* When the king of the room leaves, another player becomes the new king.
* The first player who clicks on ready in a King Survival mode becomes the first king.
* Fixed a bug where if a user left the game and you pressed shift to see the list of users, the user would still be there.

# 14/04/2017
* Tried to get the infinity game modes to work with no success :(
* Elements are now randomly spawned throughout the game. Every player in the game adds 1 'tick' every 9 seconds. Every 10 ticks, a random element is spawned. That means if there's 1 player in the room, an element will spawn every 90 second, and if there are 2 players, an element will spawn every 45 seconds. The value might get changed in the future.
* Clicking on a room now shows the correct list of users.
* If a player's game crashes, the other players in the same room gets a LeavePlayer message.
* Fixed a bug where if you had 2 players with different mission levels, they would both be in the same game but each player had a different set of monsters lol

# 13/04/2017
* You now lose a mission (in mission impossible 300) if all of the players in the room are dead.
* You now win a mission training mode if you have more at least 1 kill, and lose otherwise.

# 12/04/2017
* Bug fix: If two players starts a game and a third one tries to join, his game automatically starts.

# 11/04/2017
* Invitation: You can now invite other people to your room.
* User shops: The card search feature is now working (by pressing F6 in the lobby).
* User shops: You can add an automatic user shop using the UserShop II card.
* User shops: You can buy a card in the card search window.
* User shops: You cannot remove a card from the shop due to a bug in the client (I think)... :S
* Premium card: User Shop II is working (you lose one use every time you use the card and the card disappears when you reach 0 uses).
* Premium cards: Skull Booster, Moon Booster and Star Booster are working.
* Premium card: Extra Life is working.
* Premium cards: Card Slot+6 and Card Slot+12 are working.
* Performance: The login credentials handler used 2 queries to login. Managed to remove one query.
* Bug fix: passwords were not case sensitive, e.g. if someone's password was 'test'. 'test' and 'Test' were both working.

# 10/04/2017
* Mission training mode is working.
* Changing the room's game mode (such as finishing a training and moving on to a different training) doesn't crash the game now.
* Fixed the bug where if a player died in symbol mode, his symbols would disappear.
* Fixed the bug where there was an SQL exception when disconnecting in the login screen.
* Fixed a bug where the guild points gain in big matches code would sometimes throw a null pointer exception.

# 09/04/2017
* You can now sell cards. You don't gain any code/coins when doing so yet.
* Guilds: Your guild now gains guild points when you get a kill in every working game mode. The amount a guild gains is not the correct one yet. It's simply the experience you gain at the moment.
* Guilds: Your correct guild rank is now shown, instead of a random value.
* Guilds: Get top guilds now shows the top guilds.
* Guilds: When you login, the correct best guild is shown.

# 08/04/2017
* Started working on guild points.
* Finally managed to fix the bug where the guild rank of new players in the room was '?'.
* Fixed a bug where you didn't get the users list once you logged in.

# 07/04/2017
* Guilds: You can join a guild using the guild-join chat command.
* Guilds: You can leave a guild using the guild-leave chat command.
* Guilds: You are notified when someone from your guild joins your server.
* Guilds: You are notified when someone from your guild leaves your server.
* Guilds: You can send guild messages now.
* Added multiple servers support - you can now run different servers on different hosts.
* Each server's population is now being shown correctly (instead of just a random value like it used to be).
* SQL: Added the tables servers, guild and guild_member.
* Fixed a bug where the userSessions sorting method was called for every message, which would result in a huge amount of calculations, resulting in terrible lags.
* Fixed a bug where the UDP game server would sometime throw a null pointer exception.

# 06/04/2017
* You now get the right amount of code/experience/elements when you kill another player/NPC in a big match.
* When you kill an NPC in big match survival, there's a small chance it would have an exp multiplier. The chances are: 80% to have no multiplier, 20% (1/5) to have 3x multiplier, 4% (1/25) to have 5x multiplier and 0.16% (1/625) to have a 10x multiplier. ~~You still don't get the right amount of experience for killing the NPC's with a multiplier, but they're still shown in game.~~
* You get 1 KO and 1 down when you get a kill/die in big matches.
* You get 1 win/lose when you win/lose any working game mode, except big match death match.
* Added 1 chat command - @gender. It changes your gender from male to female and vice versa. Need to think about more...
* Removed QuestDeathHandler and NewVersionQuestDeathHandler, and now there's only MonsterDeathHandler.
* Fixed a bug where using scrolls was not sent to other players in the room.

# 04/04/2017
* Mission impossible 300 is now working.
* You can now get scrolls in a game.

# 03/04/2017
* You now get a list of all users in the lobby when you join the lobby, leave a room or leave a game.
* You now get the right amount of code/experience/elements when you kill another player in every normal mode (not quests/big matches).
* When you're in a low leveled server, some of the game modes are locked.
* When you're in a low leveled server, the quest monsters and big match NPCs are low leveled as well.
* When you finish a survival/big match survival with 0 kills, you get a lose message and when you have at least 1 kill, you get a win message.
* The correct mission level is now presented when you login. (The game mode is still not working though)
* Didn't notice that different type cards have different fusion costs. Changed the costs accordingly.
* Didn't notice that gold charging a card doubles your fusion success rate. Changed the success rate accordingly.
* Cleaned the big match code a bit.
* Fixed a bug where only the first 22 rooms were shown in the lobby.
* Fixed a bug where GetUserInfo didn't show items properly.
* Fixed a bug where big matches didn't end properly if a player left in the middle of the game.
* Fixed a bug where if you had two players in a room, and both of them left the room, the master's lobby would still have that room in the rooms list.
* Fixed a bug where friends message would throw a null pointer exception.
* Fixed a bug where if you left the lobby, the login screen would show a tutorial mode instead of the list of servers.
* Fixed a bug where you had to click on the "refresh friends" when you first joined the lobby to get a list of friends, instead of being done automatically.

# 02/04/2017
* Finished the instructions on how to install the server.
* Started working on skills. Added a list of skillID to string in the Info directory.
* You can now fuse cards. Level up fusion is working perfectly (you also lose code/elements). Skill fusion is partially working. You lose the right amount of code/elements but you're not getting any skill at the moment.
* You now lose/gain code when purchasing/selling scrolls.
* Got the correct format of LeaveGameHandler/LeaveRoomHandler.
* UserSessions is now a sorted list, and the FindUser function uses binary search. Haven't tested it much but it should work. It means that finding users should be a lot faster now (less lags).
* Fixed a bug where FindUser was causing a null pointer exception.
* Fixed a bug where AddFriend was not working properly. Also made it more efficient (no need to go to the database). Also removed a column from the database thanks to this fix.
* Fixed the bug where chests gave experience and code instead of code only.

# 01/04/2017
* The quest of understanding every single packet continues...
* You can now add friends, remove friends and get a list of who's connected or not when you click on the refresh button in the friends tab. Do notice that the refresh button has a cooldown of 30 seconds, therefore you have to wait 30 seconds before clicking again.
* Friends chat is working.
* Whisper chat is working.
* You can now find users (F4). It currently only works for players in the same server.
* You can now kick players from the room.
* Fixed a bug where chat messages and emojis were not sent while you were in a room.

# 31/03/2017
* Training modes are now working, but after you finish a training, there's an option to change to another training mode, and that doesn't work yet.
* King survival mode is working.
* You can now buy, sell and use scrolls. You don't lose/gain money when you buy/sell a scroll yet.
* Fixed a bug where if the two teams had an equal number of players and a new player joined the room, he would be assigned to the red team, instead of the blue team.
* Fixed a bug where if you clicked on a player, and he had no items, it would display that he has no items (as it should) but once you click on the user's items you would get an error.

# 30/03/2017
* You can now enter the cards shop.
* You can buy cards in the cards shop. You don't lose any money yet.
* You can buy element cards (white cards) in the cards shop. You don't lose any money yet.
* You can gold charge/silver charge cards now to make last longer (or become gold charged if they weren't charged to begin with).
* In every gamemode other than big matches - when you kill a player, you get 1 KO. When you die, you get 1 down.
* When you join the lobby (through login, leaving a room or leaving a game) you get a list of all the rooms in the lobby.
* Fixed a bug where it said you had a card while you didn't.
* Fixed a bug where the avatar money (coin) wasn't loaded properly from the database.
* Fixed a bug where if there were at least 2 people in a quest, and the entire team died, the game would not finish (with a quest failed message).
* The default number of card slots changed from 0 to 12.

# 29/03/2017
* Added auto registration. If a username does not exist, a new username will be created with the password the user inserted.
* When playing a 2-teams mode, the game will now try to put you in the correct team. e.g. if 2 players move to the blue team, and a third one joins, he'll be assigned to the red team, to balance out the teams.
* Better instructions on how to play the game. Still need to add the MySQL installation step.
* The correct KO count is presented once you finish a game.
* When playing a 2-teams mode, the game will not start until the 2 teams have an equal number of players.
* Items are now called cards.
* Quests: Added experience. The total experience of the monster is the damage that was done to the monster / x where x is a random value between 4 and 6. Each player gets experience proportional to the damage he has dealt.
* Quests: Added lucky bonuses. You have 50% chance of getting x1 exp/code, 25% chance of getting x2 exp/code, 12.5% of getting x3 exp/code and so on. Seems pretty balanced to me.
* Quests: You now get elements after you kill a monster. 50% for 0 elements, 25% for 1, 12.5% for 2 and 6.25% for 3 and 4. It seems kind of high compared to the old Survival Project, so it might get changed in the future.
* Fixed a bug where you had a set of cards but when you started a game, you had a different set of cards.
* Fixed a bug where you wouldn't lose the game if all the players in a quest died.

**MySQL database support added:**
* users table was created.
* cards table was created.

* The username and password are now being verified once you login.
* You get the user information from the database once you login.
* Changing the main character updates the database.
* Changing cards in the lobby screen updates the database.
* After killing a quest monster, the database is updated with the user's new experience/code/level (~~might~~ will probably get changed in the future to update after the quest is completed).
* Once a user disconnects, the database is updated.

# 28/03/2017
* Added full instructions on how to install the game.
* Added a text file containing all of the korean phrases in the game that need translating - "korean phrases.txt".

# 27/03/2017
* Big match - auto team is working, but the death timer isn't working.
* Big match - death match is working, but the death timer isn't working.
* Big match - survival is working, but the death timer isn't working.
* Symbol Gain mode is working with a weird bug - when you kill someone (maybe the master of the room), the symbols disappear.
* Assault mode is working.
* Lucky 3 mode is working.
* Magic Lucky 3 mode is working.
* Survival mode is working.
* Teamplay mode is working.
* Duel mode is working.
* Dodge mode is working.
* Mole catching mode is working.
* Racing mode is working.
* Soccer mode is working.
* You can now put items in your automatic user shop (User Shop II), for others to see.
* You can partially search for items in user shops (by clicking F6 in the lobby). The search simply returns all the user shops at the moment, and I believe it only returns the first 5 shops. There's a lot more progress to be made in that part.
* Fixed a bug where game packets were sent multiple times instead of once.
* Fixed a bug where people who were disconnected were not removed from the list of users, resulting in multiple errors (when sending broadcast messages for example).
* Fixed a bug where changing the main character didn't work.

# 26/03/2017
* You can now chat in game.
* You can now chat in lobby. There's still no concept of guilds/friends/whisper so the message just gets sent to everyone in the lobby.
* Started working on the card shop and user shops.
* Fixed a bug where the "Quest completed" message was not sent to other people in the room.

# 25/03/2017
* Added a placeholder for all the *response* messages available. There are 94 more responses to process Q_Q
* Added the GameMasterBan handler.
* More work on big matches...
* Fixed the bug where the players in the room didn't get the room name change update.
* Fixed a bug where resurrection was not sent to other players in the room.
* Fixed a bug where the main character was not chosen.

# 24/03/2017
* Added PetKilledHandler (you can kill other player's pets in a game to make them yours).
* Started working on mission impossible 300.
* Fixed the bug where leaving a game with 2+ players didn't work properly.
* Fixed the bug where the rooms in the lobby weren't updated properly once a change in a room was made.
* Fixed the bug where you had to change items once before clicking ready in a room.
* Fixed a bug where the other users in a room couldn't see the items of the other players.
* Fixed a bug where avatar items didn't work properly.
* Fixed a bug where broadcast messages were sent to everyone in the room, including the current player, when it was not necessary.
* Fixed a bug where room messages were sent to everyone in the room, including the current player, when it was not necessary.

**Spring cleaning:**

A big change in the code, resulting in a cleaner and a better code.

* Moved every message request/response ID into a messages class.
* Added TCPHandler, UDPHandler, LoginHandler, LobbyHandler and GameHandler to extend GenericHandler.
* Added a function processPacket that's being called right after interpretBytes.
* Changed the big constants class into an enum.
* AttackUDP was renamed to ForwardMessageHandler.
* LoginHandler was renamed to LoginCredentialsHandler.
* newState was renamed to getNewState and was also moved to Cryptography class.
* Deleted ExtendedInputStream and used a normal InputStream instead.
* Renamed roomMessage to sendRoomMessage. Also change its signature to UserTCPSession userSession, byte[] message, boolean sendToSelf.
* Moved Room, User and Item to a new package - net.objects.

# 23/03/2017
* Added b6oy's encryption/decryption methods. The game now works with his client as well (win10 executable).
* Changed the messages format info file.
* Added a working debug-mode sp.exe. It also disables IOProtect, resulting in a non crashing game. The debug-mode sp.exe shows the size of every frame in the game, the framerate, the experience and code the player has and his event flags. Also, every single log is being printed in PP.log, instead of only a selected few.
* Added the sp2.exe and dinput.dll of B6oy's SPNET.net client. Paste those two files in your SP installation and you can run Survival Project on Windows 10. Simply launch sp2.exe. If you get an error, run it as administrator.
* Added GetUserInfoHandler.
* Added gold items.
* Added avatar items.
* Added pets.
* Premium characters are working.
* Fixed a bug where the game would crash after starting a game, leaving, and changing an item.
* Fixed the bug where there couldn't be 2+ players in a quest.
* Fixed a bug where game packets were getting sent to everyone in the room, players who aren't even ready yet, resulting in a game crash.
* Fixed a bug where there couldn't be more than 2 players in a room.

# 22/03/2017
* Added an option to send packets right after the game starts. That can be used "Soccer: Send timer" and many more game types.

* Soccer is semi-working. ~~The timer doesn't start when you start the game, but~~ you can score goals. After 3 goals the game ends, but the results don't say "win-lose" but "win-win".

# 21/03/2017
* Started working on soccer...
* Game screen: two players (maybe more, haven't tested yet) can be at the same game. When a player moves, stops moving, attacks, defends, and hits another player, the other player will see his actions.  
* Quests: You can start a quest game.
* Quests: You can finish a quest game.
* Quests: You can kill monsters/chests/crystals to get elements/code/exp.
* Quests: You can die in a quest.
* Quests: You can resurrect in a quest.
* Quests: You can change the quest map (levels/locations/monsters)

# 20/03/2017
* Started working on big match.
* Started working on quests.
* Added multiple users in a room support. Still buggy as hell.
* Added room messages support (messages that are being sent to anyone in the room, but not to everyone in the game).
* Fixed the bug where you had to change items once before entering a room, I think o_O - **Actually I didn't**
* Fixed a bug in ItemsChangedHandler

# 19/03/2017
* Another big change in the game code... Removed 2 function from each handler. That should be 500 functions total when the server's completed. @TODO - think about those things in advance... :S

* Added broadcast messages (you can send a broadcast to everyone in the game, except for yourself). It is used in messages such as "CreateRoom" (the other players need to see the new room).

* When a new room is created, the lobby of other players is changed (with the new room).
* We can change room names now.
* Changing a character in a room broadcasts to the other players in the lobby.

# 18/03/2017
* We can enter games! A shitload of bugs, but we can get into games! XD
* We can now create rooms, change characters in a room, change teams in a room, change items in a room and leave a room.
* Info: MyInfo structure added (incomplete). 
* Info: Message:LoginRequest added.

# 17/03/2017
* Major change in the code - each message is no longer divided into 2 classes, resulting in a much better performance, less memory use and a cleaner code. Instead, handlers are used. Also, the client buffer was changed to support absolute positions.
* Some work was done on rooms, still unfinished...
* Messages can now send multiple messages after the response (e.g, sending an "ItemsChanged" packet after "CreateRoom"). The code is messy though, need to clean it up soon...
* Handlers: Login: GetChannelUsersPercentageHandler added.
* Handlers: Login: GuildMarkHandler added.
* Handlers: Lobby: GetTopGuildsHandler added.
* Handlers: Lobby: GetTopGuildsMarkHandler added.

# 16/03/2017
* Big changes in the server packets code. Each packet is in a fixed size now and we can insert fields easier.
* Figured out how normal items (not premium) work. Still need to write code to handle them.
* Messages: GetTopGuildsRequest added.
* Messages: GetTopGuildsResponse added.

# 15/03/2017
* It seems that using the unpacked version of sp.exe (debug-mode or release-mode) causes the game to crash, unfortunately. I believe it's because the IOProtect isn't running, and sp.exe throws an exception after a few seconds (10?). Maybe there's a way to bypass it.

* Added multiple users support.

* Added multiple servers support.

* Added UDP servers support.

* We can get into the lobby :D



# 14/03/2017
* RequestServersInformationMessage added, and it works like a charm! :D

* Fixed a bug that didn't calculate the checksum properly.

* Login request message format added.

* Debug-mode sp.exe added (called sp2.exe)

* sp.exe program arguments added.


# 13/03/2017
* Support for multiple packets

* Finally managed to calculate the checksum!

* New info: MakeDigest

* Added the unpacked version of IOSocketDLL.dll

# 12/03/2017

* A skeleton for reading/sending packets.

* Basic information files

* Added the unpacked version of SP.exe
