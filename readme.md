## JMediaController

JMediaController is a Java implementation of the popular MediaController idea used at ISW, a group of applied informatics students at UCLL. (https://isw.student.khleuven.be, http://www.ucll.be)

The general idea is that it runs as a server to which people can connect (currently only via a plain socket, I recommend netcat) and send commands to make it play music. I've tried to write it fairly modular so you can create your own plug-ins (currently it supports YouTube and some internet radio's), your own server interfaces and you can swap out libvlc for a player of your choice.

It should work on both GNU/Linux and Windows although it has only been really used on a Linux box and has seen very limited to no Windows testing.

User messages are in Dutch, sorry for that. I have no intention of translating it.

### Installation

#### Build

1. Make sure maven is installed.
2. Enter the following command in the root directory (the one with pom.xml): mvn clean compile assembly:single

#### Install

1. Put the created jar found in the target folder on your machine.
2. Start the mediacontroller to let it create a default configuration file.
3. Add your YouTube API key and an admin password (it should be a SHA-256 hash) to the configuration file (~/.mediacontroller/mc.conf).
4. Restart the mediacontroller (preferably in tmux or screen).
5. Connect to the mediacontroller using netcat and execute the command "admin login password" and "admin update" to install all the dependencies.