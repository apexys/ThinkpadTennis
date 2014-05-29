#1. Resolve dependencies
echo "Resolving dependencies"
cd dependencies
./get-dependencies.sh
cd ..

#2. Clean bin folder
echo "Cleaning bin folder"
rm -rf bin
mkdir bin

#2. Create NW package
echo "Building NW package"
cd src
zip -r ThinkpadTennisNW.zip .
cd ..
mv src/ThinkpadTennisNW.zip bin/ThinkpadTennisNW.nw

#3. Create Linux package
echo "Building Linux package"
cd bin
cp -r  ./../dependencies/node-webkit-v0.9.2-linux-x64 ThinkpadTennisNW
cp ThinkpadTennisNW.nw ./ThinkpadTennisNW/package.nw
zip -r ThinkpadTennisNW.zip ThinkpadTennisNW
