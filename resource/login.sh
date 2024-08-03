#!/bin/bash
email="$1"
pwd=$2
role=$3

userRow=$(awk -F',' '$4 == "'"$email"'" {print $0}' ./user-store.txt)

if [ -z "$userRow" ]; then
  echo "Login failed: UUID not found"
  exit 1
fi

storedPwd=$(echo "$userRow" | cut -d ',' -f5)

salt="gishwati"
hashPwd=$(openssl passwd -6 -salt "$salt" "$pwd")

storedRole=$(echo "$userRow" | cut -d ',' -f6)

if [ "$role" != "$storedRole" ]; then
  echo "User does not exist."
  exit 0
fi

echo  "hash $hashPwd $storedPwd"
if [ "$hashPwd" == "$storedPwd" ]; then
  echo "Login successful for UUID: $1"
  exit 0
else
  echo "Incorrect password"
  exit 1
fi
