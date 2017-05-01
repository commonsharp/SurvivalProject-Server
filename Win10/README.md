# Installing the client

* Download alanlei's client from here:
https://drive.google.com/file/d/0BzjxtOOu36E_ZnVTRVdXMTFuSDg/view?hc_location=ufi

* Install Survival Project. The password is "www.spgame.net".

* The archive also contains a file called gng.ini.
Open it and change the IP to the IP of the server you're connecting to (or 127.0.0.1 if you want to play alone) and the port to 21000.
When you're done, put it in your SP folder in the config directory.

* Install msxml.msi (it should be in your Survival Project folder). If you already have it installed, **uninstall it** and install it again.

**The following step is needed if you want to run the game on Windows 10:**

* Put sp2.exe and dinput.dll of B6oy's SPNET.net client in your SP installation directory (This will enable you to play on Windows 10).

To run the game, launch sp2.exe in Windows 10 or the normal launcher in any other Windows version.

If you get an error while running sp2.exe, run it as administrator.

**Also, do not rename sp2.exe to anything else, or the game won't launch.**

# Installing the server
**The server files are not completed yet, therefore you would have to compile them yourself using any Java IDE.**

**You would need to download the src folder, resources/hibernate.cfg.xml and MySQL/spgame.sql from this repository.**

**The instructions are for Eclipse IDE but it can work on any IDE**

* Install MySQL workbench. Create a new database called spgame. Import the tables from spgame.sql. Make sure you also install Connector-J.

* Change the IP in the server table (in your database) to your own IP (not the local one), or to 127.0.0.1 if you're playing alone. The channelType field in that table is as follows - 0 for beginner, 1 for hero and 2 for epic. The serverID field is should be 0 for the first server in the list (of the same channel), 1 for the second one and so on.

* Create a new Java project. Put the src folder in your project. Create a new folder called resources and put hibernate.cfg.xml in it.

* Change your SQL username and password in hibernate.cfg.xml to the ones you configured in the MySQL installation.

* Download Hibernate from here - http://hibernate.org/orm/downloads. Create a new folder in your source project called libs. Put all of the jars in the lib/required folder of your Hibernate rar in your libs folder.

* Copy mysql-connector-java-5.1.40-bin.jar from your MySQL installation folder to your libs folder.

* Right click your project, select properties, then select java build path. Under the source tab, click on add folder and add your resources folder. Under the Libraries tab, click on add jars and add the jars in your libs folder.

* Compile the server files and run /src/main/Main.java.

* The server should be running now, and you can run the client.

* If you're hosting a server for others to play, you would need to forward port 21000, 21001, 21002 and 21003 to your machine (or basically, 21000 and any other port you set in the server table). I can't help you with that as it's router-specific.
