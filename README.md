# SurvivalProject-Server
Server files for the game Survival Project, written in Java.

For instructions on how to install Survival Project on Windows 10, go to the Win10 folder.

# TODO
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
* When someone joins a quest that already started, the quest restarts.
* Starting a tutorial crashes the game.
* The guild rank of new players in the room is '?' instead of being the true rank.

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
* Added a function
