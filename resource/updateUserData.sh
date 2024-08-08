#!/bin/bash

# Check if the correct number of arguments are provided
if [ "$#" -ne 4 ]; then
    echo "Usage: $0 <file> <UUID> <new_content> <position>"
    exit 1
fi

# Assign arguments to variables
FILE="$1"
UUID="$2"
NEW_CONTENT="$3"
POSITION="$4"

# Validate the position argument
if [[ "$POSITION" -lt 1 || "$POSITION" -gt 13 ]]; then
    echo "Position must be between 1 and 13 (for the CSV columns)."
    exit 1
fi

# Temporary file to hold modified data
TEMP_FILE=$(mktemp)

# Use awk to replace the value in the specified column for the specified UUID
awk -v uuid="$UUID" -v new_content="$NEW_CONTENT" -v position="$POSITION" -F, -v OFS=',' '
BEGIN {
    pos = position - 1;
}
{
    if ($1 == uuid) {
        $pos = new_content;
    }
    print $0;
}' "$FILE" > "$TEMP_FILE"

# Replace the original file with the modified data
mv "$TEMP_FILE" "$FILE"

echo "Updated row with UUID '$UUID' in column $POSITION with new content '$NEW_CONTENT'."
