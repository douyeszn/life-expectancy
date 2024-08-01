#!/bin/bash
UUID=$1
user=$(cut -d "," -f1 ./user-store.txt | grep "^${UUID}$")
echo $user