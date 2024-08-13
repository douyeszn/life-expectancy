#!/bin/bash

file_path="user-store.txt"

cut -d ',' -f 13 "$file_path" | tail -n +2 | while read -r daysToLive; do
    if [ -z "$daysToLive" ]; then
        echo "null"
    else
        echo "$daysToLive"
    fi
done
