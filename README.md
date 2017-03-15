# SurvivalProject-Server
Server files for the game Survival Project, written in Java.

# 15/03/2017
* It seems that using the unpacked version of sp.exe (debug-mode or release-mode) causes the game to crash, unfortunately. I believe it's because the IOProtect isn't running, and sp.exe throws an exception after a few seconds (10?). Maybe there's a way to bypass it.

* Added multiple users support.

* Added multiple servers support.



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
