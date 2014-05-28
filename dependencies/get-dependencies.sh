#!/bin/bash
#Resolves all dependencies for the finals-web app

file="DEPENDENCIES_RESOLVED"
if [ -f "$file" ]
then
	echo "Dependencies already downloaded"
else
	echo "Downloading dependencies"
	#1 node-webkit-linux
	echo "Downloading node-webkit for linux"
	#1.1 Download node-webkit-linux
	wget http://dl.node-webkit.org/v0.9.2/node-webkit-v0.9.2-linux-x64.tar.gz
	#1.2 Unpack it
	tar -xzf node-webkit-v0.9.2-linux-x64.tar.gz
	#1.3 Remove compressed file
	rm node-webkit-v0.9.2-linux-x64.tar.gz
	#1.4 Quickfix for missing libudev.so.0
	cd node-webkit-v0.9.2-linux-x64
	sed -i 's/\x75\x64\x65\x76\x2E\x73\x6F\x2E\x30/\x75\x64\x65\x76\x2E\x73\x6F\x2E\x31/g' nw
	cd ..

	#2. D3.js
	echo "Downloading D3.js"
	#2.1 Download d3.js
	wget https://github.com/mbostock/d3/releases/download/v3.4.3/d3.zip
	#2.2 Unpack it
	unzip d3.zip -d d3js
	#2.3 Remove compressed file
	rm d3.zip
	#2.4 Copy compressed d3js file to source folder
	cp d3js/d3.min.js ./../src/d3.js
	
	#3. jQuery 2.1.0
	echo "Downloading jquery"
	#3.1 Download JQuery
	wget -O jquery.js http://code.jquery.com/jquery-2.1.0.min.js
	#3.2 Copy compressed d3js file to source folder
	cp jquery.js ./../src/jquery.js
	
	#4. JSZip 
	echo "Downloading JSZip"
	#4.1 Download JSZip
	wget -O JSZip.zip http://github.com/Stuk/jszip/zipball/master
	#4.2 Unpack it
	unzip JSZip.zip -d JSZip
	#4.3 Remove compressed file
	rm JSZip.zip
	#4.4 Copy conpressed JSZip file to source folder
	cp ./JSZip/Stuk-jszip-ee499e6/dist/jszip.min.js ./../src/jszip.js
	
	#3. Pako
	echo "Downloading pako"
	#3.1 Download Pako
	wget -O pako.js https://rawgithub.com/nodeca/pako/master/dist/pako.js
	#3.2 Copy compressed d3js file to source folder
	cp pako.js ./../src/pako.js
	
	#Write that down so we don't have to download all of this again
	touch DEPENDENCIES_RESOLVED
fi
#Done

