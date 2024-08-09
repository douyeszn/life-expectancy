#!/bin/bash

input_file="./user-store.txt"
output_file="patients.csv"

header=$(head -n 1 "$input_file")

echo "$header" > "$output_file"

awk -F, -v OFS=',' '
NR==1 {next}
$6=="PATIENT" {
    print $1, $2, $3, $4, $6, $7, $8, $9, $10, $11, $12, $13, $14
}' "$input_file" >> "$output_file"
