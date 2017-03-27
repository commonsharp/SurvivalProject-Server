# SurvivalProject-Server
Server files for the game Survival Project, written in Java.

For instructions on how to install Survival Project on Windows 10, go to the Win10 folder.

**Not going to work on hokey mode because I can't fucking score a goal -_-"**

**When I say that a game mode (such as soccer) is working, I mean that you can start a game, the rounds system is working (in this case - goals), you can finish a game and see the results of who won and who lost. The elements/code/experience/guild experience gained is not implemented yet. Also you don't get kills/downs/wins/loses. It just means the game mode is opertional.** 

# TODO
- [ ] Clean the big battle death code a bit. Only use one array for everyone's lives instead of 2 (one for slots and one for npc).
- [ ] Rename GameType to GameMode.
- [ ] In a team game, when everyone is ready, the game starts. We need to also make sure that the teams are balanced. Right now it can start as a 4vs0, instead of 2vs2.
- [ ] Add sendFriendsMessage and sendGuildMessage when the time comes.
- [ ] Change the tutorial item class to net.objects.Item.
- [ ] Get a list of all the request messages.
- [ ] Add putBoolean/getBoolean in ExtendedByteBuffer.
- [ ] Change the login credentials message format.
- [ ] Finish the guild mark handler.
- [ ] Add timed events (such as a random element flying around).
- [ ] Download a list of all available rooms once you log in.
- [ ] Downlad a list of all the users in the server once you log in.
- [ ] Rename "ResultsHandler" and make sure it's sending the right results.
- [ ] There are more fields in LeaveRoomHandler. Need to change...
- [ ] Change the teams in quests to 10, instead of 0.

# List of bugs
* Death timer in big matches doesn't work.
* When a player (I think the master) dies in symbol mode, his symbols disappear. o_o
* When you finish a survival game, it says draw. Not sure if it should say that or win.
* Possible bug - broadcast messages will get sent to other servers as well. So someone sending a chat message would send it to people in other servers as well. Need to check that.
* When someone joins a quest that already started, the quest restarts.
* Starting a tutorial crashes the game.
* The guild rank of new players in the room is '?' instead of being the true rank.
* Quests - if player 1 kills everyone, player 2 gets ko too (in the winning screen).

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
