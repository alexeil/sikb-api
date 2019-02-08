#!/usr/bin/env sh
echo "\n=== CHECK SUREFIRE REPORTS ===\n"

error=false

for F in **/target/surefire-reports/*.txt
do
  if grep -q "FAILURE" "$F";
  then
    echo $F
    cat $F
    error=true
  fi
done


if $error
then
  echo "Errors detected"
  exit 1
fi