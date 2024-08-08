countryISO="DZA"
yearsDelayedBeforeART="2"
ageOfInfection="20"
percentLifeDecrease="0.9"

countryLifeSpan=$(awk -F',' '$6 == "'"$countryISO"'" {print $7}' ./life-expectancy.csv)

if [ -z "$countryLifeSpan" ]; then
    echo "Country lifespan not found."
    exit 1
fi

lifeSpan=$(echo "$countryLifeSpan - $ageOfInfection" | bc)

powered=$(echo "scale=5; $percentLifeDecrease ^ $yearsDelayedBeforeART" | bc)

echo  "0.9  - $powered"