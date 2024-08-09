isHIVPositive="$1"
age="$2"
yearsDelayedBeforeART="$3"
countryISO="$4"
percentLifeDecrease="0.9"

countryLifeSpan=$(awk -F',' '$6 == "'"$countryISO"'" {print $7}' ./life-expectancy.csv)

if [ -z "$countryLifeSpan" ]; then
    echo "Country lifespan not found."
    exit 1
fi

if [ "$isHIVPositive" = false ]; then
    echo "$countryLifeSpan"
    exit 0
fi

if [ "$yearsDelayedBeforeART" -gt 5 ]; then
    echo "0"
    exit 0
fi

lifeSpan=$(echo "$countryLifeSpan - $age" | bc)

damping=$(echo "scale=3; $percentLifeDecrease ^ $yearsDelayedBeforeART" | bc)

lifespan=$(echo "scale=1; $lifeSpan * $damping" | bc)

echo "$lifespan"