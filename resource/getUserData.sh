#!/bin/bash

user="$1"
data="$2"

# Extract the role from the 7th field
role=$(echo "$user" | cut -d ',' -f"$data")

# Output the role
echo "$role"