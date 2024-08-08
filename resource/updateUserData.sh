#!/bin/bash

# # Check if the correct number of arguments are provided
# if [ "$#" -ne 4 ]; then
#     echo "Usage: $0 <file> <UUID> <new_content> <position>"
#     exit 1
# fi

# # Assign arguments to variables
# FILE="$1"
# UUID="$2"
# NEW_CONTENT="$3"
# POSITION="$4"

# # Validate the position argument
# if [[ "$POSITION" -lt 1 || "$POSITION" -gt 13 ]]; then
#     echo "Position must be between 1 and 13 (for the CSV columns)."
#     exit 1
# fi

# # Temporary file to hold modified data
# TEMP_FILE=$(mktemp)

# # Use awk to replace the value in the specified column for the specified UUID
# awk -v uuid="$UUID" -v new_content="$NEW_CONTENT" -v position="$POSITION" -F, -v OFS=',' '
# BEGIN {
#     pos = position;
# }
# {
#     if ($1 == uuid) {
#         $pos = new_content;
#     }
#     print $0;
# }' "$FILE" > "$TEMP_FILE"

# # Replace the original file with the modified data
# mv "$TEMP_FILE" "$FILE"

# echo "Updated row with UUID '$UUID' in column $POSITION with new content '$NEW_CONTENT'."
#!/bin/bash

# Check if the correct number of arguments are provided

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

# Hash the NEW_CONTENT if it's the 5th argument (change logic if needed)
argCounter=0
i="$NEW_CONTENT"

# Increment counter and apply conditional hashing
((argCounter++))
if [ "$argCounter" -eq 5 ]; then
    i=$(openssl passwd -6 "$i")
fi

# Columns to skip (positions 1, 4, 6, and 13)
SKIP_COLUMNS="1 4 6 13"

# Temporary file to hold modified data
TEMP_FILE=$(mktemp)

# Use awk to replace the value in the specified column for the specified UUID
awk -v uuid="$UUID" -v new_content="$i" -v position="$POSITION" -v skip_columns="$SKIP_COLUMNS" -F, -v OFS=',' '
BEGIN {
    split(skip_columns, skips, " ");
    pos = position;
}
{
    # Check if the current position is one of the columns to skip
    skip = 0;
    for (i in skips) {
        if (pos == skips[i]) {
            skip = 1;
            break;
        }
    }
    
    # Update the field if the column is not in the skip list
    if ($1 == uuid && !skip) {
        $pos = new_content;
    }
    
    print $0;
}' "$FILE" > "$TEMP_FILE"

# Replace the original file with the modified data
mv "$TEMP_FILE" "$FILE"

# Echo status message
# echo "Updated row with UUID '$UUID' in column $POSITION with new content '$NEW_CONTENT', skipping columns $SKIP_COLUMNS."
