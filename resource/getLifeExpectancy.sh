countryISO="$1"

# Extract the lifespan for the given country ISO from the CSV file
countryLifeSpan=$(awk -F',' '$6 == "'"$countryISO"'" {print $7}' ./life-expectancy.csv)

# Check if the lifespan was found and print appropriate messages
if [ -z "$countryLifeSpan" ]; then
    echo "Country lifespan not found."
    exit 1
else
    echo "$countryLifeSpan"
    exit 0
fi