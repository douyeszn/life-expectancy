#!/bin/bash
email=$1
pwd=$2
role=$3

userRow=$(grep "^.*,$email,.*$" ./user-store.txt)

if [ -z "$userRow" ]; then
  echo "Login failed: Email not found"
  exit 1
fi

uuid=$(echo "$userRow" | cut -d ',' -f1)
storedPwd=$(echo "$userRow" | cut -d ',' -f5)

storedRole=$(echo "$userRow" | cut -d ',' -f6)

if [ "$role" != "$storedRole" ]; then
  echo "User does not exist"
  exit 0
fi

if [ "$pwd" == "$storedPwd" ]; then
  echo "Login successful for UUID: $uuid"
#  echo "$uuid $role"
  exit 0
else
  echo "Incorrect password"
  exit 1
fi
