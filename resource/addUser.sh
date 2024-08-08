#!/bin/bash
argsCount=$#

argCounter=1
echo "" >> user-store.txt
for i in "$@"
do
   if [ $argCounter -eq 5 ]; then
      i=$(openssl passwd -6 "$i")
   fi

  if [ $argCounter -eq $argsCount ]; then
    echo -n "${i}" >> user-store.txt
  else
    echo -n "${i}," >> user-store.txt
  fi
  argCounter=$((argCounter + 1))
done
# echo "" >> user-store.txt


