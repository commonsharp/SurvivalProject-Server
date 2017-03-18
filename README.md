# SurvivalProject-Server
Server files for the game Survival Project, written in Java.

# 19/03/2017
* Another big change in the game code... Removed 2 function from each handlers. That should be 500 functions total when the server's completed. @TODO - think about those things up ahead... :S

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
