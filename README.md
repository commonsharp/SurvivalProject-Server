# SurvivalProject-Server
Server files for the game Survival Project, written in Java.

To install Survival Project on Windows 10, go to the Win10 folder.

# TODO
- [ ] Download a list of all available rooms once you log in.
- [ ] Downlad a list of all the users in the server once you log in.
- [ ] Rename "ResultsHandler" and make sure it's sending the right results.
- [x] Rename "EnterExistingRoom".
- [ ] Make user tcp session and udp server have the same superclass.
- [ ] Clean the code for fucks sake
- [ ] There are more fields in LeaveRoomHandler. Need to change...

# List of bugs
* ~~Soccer timer doesn't start.~~
* You have to change items at least once before clicking ready in a room.
* The rooms in the lobby aren't being updates properly once a change in a room is made.
* The guild rank of new players in the room is '?' instead of being the true rank.
* When changing the room name, the other users in the room don't see the changes.
* ~~Can't start a quest with 2+ people.~~

# 23/03/2017
* Changed the messages format info file.
* Added a working debug-mode sp.exe. It also disables IOProtect, resulting in a non crashing game. The debug-mode sp.exe shows the size of every frame in the game, the framerate, the experience and code the player has and his event flags. Also, every single log is being printed in PP.log, instead of only a selected few.
* Added the sp2.exe and dinput.dll of B6oy's SPNET.net client. Paste those two files in your SP installation and you can run Survival Project on Windows 10. Simply launch sp2.exe. If you get an error, run it as administrator.
* Added GetUserInfoHandler.
* Added gold items.
* Added avatar items.
* Added pets.
* Premium characters working.
* Fixed a bug where the game would crash after starting a game, leaving, and changing an item.
* Fixed the bug where there couldn't be 2+ players in a quest.
* Fixed a bug where game packets were getting sent to everyone in the room, people who aren't even ready yet, resulting in a game crash.
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
