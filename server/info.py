#web page that explains the server
import conf

src = """<!DOCTYPE HTML>
<html>
<head>
<title>ThinkpadTennis - Pong powered by hdaps</title>
</head>
<body>
<h1>ThinkpadTennis</h1>
<h2>Pong powered by <a href="http://en.wikipedia.org/wiki/hdaps">hdaps</a></h2>
<h3>How does it work?</h3>
<p>Thinkpads (and various other, especially "business grade" notebooks) contain a gyroscopic sensor, much like the one in smartphones or the Wii controller. This project attempts to use this sensor as a means to controll various little games, in this case the classic Pong.</p>
<h3>What do I need?</h3>
<p>You need: <ul>
<li>A thinkpad or other device with comparable hdaps support</li>
<li>An operating system based on the linux kernel</li>
<li>The <a href="https://github.com/apexys/ThinkpadTennis/wiki/Kernel-Module-hdaps">hdaps kernel module</a></li>
<li>One of the game clients from the repository: <a href="https://github.com/apexys/ThinkpadTennis">apexys/ThinkpadTennis</a></li>
<li>Enough trust in your motoric skills to be sure you don't throw your precious portable computer into walls or on the floor</li>
</ul>
<h3>Something doesn't work!</h3>
<p>Well, that's bad. You can PM us on twitter (@apexys, @iamflemming) or, at least during the KIF on freenode (#kif, apexys and funco). funco is also constantly online at irc.installgentoo.com in #2d, and sometimes #installgentoo.</p>
</body>
</html>"""

