#!/bin/bash
email=$1
email=Doe
#echo "findUser($1)"
cut -d "," -f3 ./user-store.txt | grep "^${email}$"