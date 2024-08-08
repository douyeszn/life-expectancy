#!/bin/bash
data="$1"
field="$2"
echo "" >> user-store.txt
# echo $data >> user-store.txt
if [ $field -eq 8 ]; then
  echo -n "${data}" >> user-store.txt
else
  echo -n "${data}," >> user-store.txt
fi

# echo "" >> user-store.txt


