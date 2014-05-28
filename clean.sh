#1. Clean dependencies
echo "Cleaning dependencies"
mv dependencies/get-dependencies.sh get-dependencies.sh
rm -rf dependencies
mkdir dependencies
mv get-dependencies.sh dependencies/get-dependencies.sh

#2. Clean binaries
echo "Cleaning binaries"
rm -rf bin
mkdir bin
