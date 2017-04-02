# Installing the client

* Download alanlei's client from here:
https://drive.google.com/file/d/0BzjxtOOu36E_ZnVTRVdXMTFuSDg/view?hc_location=ufi

* Install Survival Project. The password is "www.spgame.net".

* The archive also contains a file called gng.ini.
Open it and change the IP to the IP of the server you're connecting to (or 127.0.0.1 if you want to play alone) and the port to 21000 (the server doesn't support any port number at the moment).
When you're done, put it in your SP folder in the config directory.

**The following step is needed if you want to run the game on Windows 10:**

* Put sp2.exe and dinput.dll of B6oy's SPNET.net client in your SP installation directory (This will enable you to play on Windows 10).

To run the game, launch sp2.exe in Windows 10 or the normal launcher in any other Windows version.

If you get an error while running sp2.exe, run it as administrator.

**Also, do not rename sp2.exe to anything else, or the game won't launch.**

# Installing the server
**The server files are not completed yet, therefore you would have to compile them yourself using any Java IDE.**

* Install MySQL workbench. Create a new database called spgame. Import the tables included in MySQL directory in this git. Make sure your MySQL port is 3306. If you change it to a different port, make sure you change it in DatabaseConnection.java in the server files as well.

* Change the IP in /src/login/handlers/ServerInfoHandler.java to your own IP (not the local one), or to 127.0.0.1 if you're playing alone.

* Compile the server files using any Java IDE and run /src/main/Main.java.

* The server should be running now, and you can run the client.

* If you're hosting a server for others to play, you would need to forward port 21000. I can't help you with that as it's router-specific.
