#!/bin/bash

CSV_FILE="life-expectancy.csv"
ISO_CODE=$1


# Search using awk
MATCH=$(awk -F, -v code="${ISO_CODE}" '$4 == code' "${CSV_FILE}")

# Check if the match was found
if [ -z "$MATCH" ]; then
    echo "null"
else
    echo "$MATCH"
fi