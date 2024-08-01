#!/bin/bash
argsCount=$#

argCounter=1
for i in "$@"
do
  if [ $argCounter -eq $argsCount ]; then
    echo -n "${i}" >> user-store.txt
  else
    echo -n "${i}," >> user-store.txt
  fi
  argCounter=$((argCounter + 1))
  echo ""
done
echo "" >> user-store.txt
