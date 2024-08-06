#!/bin/bash

user="$1"

# Extract the role from the 7th field
role=$(echo "$user" | cut -d ',' -f6)

# Output the role
echo "$role"