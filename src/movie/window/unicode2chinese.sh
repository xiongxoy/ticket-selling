#!/usr/bin/env bash

for i in $(ls); do
  filename=$(basename "$i")
  extension="${filename##*.}"
  echo "Hello"
  if [[ "$extension" = "java" ]]; then
    echo "Hello"
    native2ascii -reverse $i $i
  fi
done
