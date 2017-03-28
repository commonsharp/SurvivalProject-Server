# Running Survival Project on Windows 10

Download alanlei's client from here:
https://drive.google.com/file/d/0BzjxtOOu36E_ZnVTRVdXMTFuSDg/view?hc_location=ufi

Install Survival Project. The password is "www.spgame.net".

The archive also contains a file called gng.ini. Put it in your SP folder in the config directory.

Make sure you change the IP to your own IP and the port to 21000 (in gng.ini).

Put sp2.exe and dinput.dll of B6oy's SPNET.net client in your SP installation directory (This will enable you to play on Windows 10).

After that, change the IP in /src/login/handlers/ServerInfoHandler.java to your own IP, compile the server files using any Java IDE and run /src/main/Main.java.

The server should be running now. You can login with your client and start playing. If you want other people to play with you, tell them to change the IP in gng.ini to your own IP.

To launch the game, run sp2.exe.

If you get an error while running sp2.exe, run it as administrator.

**Also, do not rename sp2.exe to anything else, or the game won't launch.**
