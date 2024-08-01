#!/bin/bash
email=$1
pwd=$2
userRow=$(grep "^.*,$email,.*$" ./user-store.txt)

if [ -z "$userRow" ]; then
  echo "Login failed: Email not found"
  exit 1
fi

uuid=$(echo "$userRow" | cut -d ',' -f1)
storedPwd=$(echo "$userRow" | cut -d ',' -f5)

role=$(echo "$userRow" | cut -d ',' -f6)

if [ "$pwd" == "$storedPwd" ]; then
  echo "Login successful for UUID: $uuid"
#  echo "$uuid $role"
  exit 0
else
  echo "Incorrect password"
  exit 1
fi
