#!/bin/bash

# Count the number of arguments
argsCount=$#

# Initialize a counter
argCounter=1

# Define the file variable correctly (no spaces around the `=` sign)
file="user_data.csv"

# Clear the file content at the start
echo "" > "$file"

# Iterate over all arguments
for i in "$@"
do
   # Write the argument to the file with or without a comma
   if [ $argCounter -eq $argsCount ]; then
      echo -n "$i" >> "$file"
   else
      echo -n "$i," >> "$file"
   fi

   # Increment the counter
   argCounter=$((argCounter + 1))
done

# Append a newline character to the file
echo "" >> "$file"
