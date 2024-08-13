output_file="$1"
lines=("$@")

{
    for line in "${lines[@]}"; do
        echo "$line"
    done
} > "$output_file"

echo "Data has been exported to $output_file"