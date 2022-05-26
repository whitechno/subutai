#!/bin/bash

function error_exit() {
  echo "$1" 1>&2
  exit 1
}

usage() {
  cat <<EndOfMessage
This is line 1.
  This is line 2.
 Line 3.
EndOfMessage
}
usage

# shellcheck disable=SC2016
echo '
Pretty-print JSON (to sort, add the `--sort-keys` flag to the end):'
echo '{"foo": "lorem", "bar": "ipsum"}' | python -m json.tool --sort-keys
# or use `jq`:
jq --sort-keys <<< '{ "foo": "lorem", "bar": "ipsum" }'

SCRIPT_DIR="${0%/*}"
echo "
Script directory:
$SCRIPT_DIR
"

TODAY=$(date '+%Y%m%d')
echo "$TODAY"
date -j -f '%m-%d-%Y' "09-01-2017" +'%A' # day of week
cal | head -1 | grep -oE "[A-Za-z]+"
cal | head -1 | grep -oE "[[:alpha:]]+" # same as above

TMP_DIR=/tmp
TMP_EXTRACT=${TMP_DIR}/ddd_test

if [ ! -d ${TMP_EXTRACT} ]; then
  mkdir ${TMP_EXTRACT} ||
    error_exit "Could not create temporary directory: ${TMP_EXTRACT}"
fi
touch "${TMP_EXTRACT}/${TODAY}"

echo "Cleaning up..."

# shellcheck disable=SC2016
: '
OUT=$(ls -A ${TMP_EXTRACT} 2>/dev/null) # check if dir is non-empty
echo "$?" # prints exit status of the last command
echo ">$OUT<" # prints output of the command
'

# check if dir is non-empty and delete its contents
# use either 'find'
# find ${TMP_EXTRACT} -type d -empty -exec echo {} is empty. \;
# or 'ls -A'
#if [ "$(ls -A ${TMP_EXTRACT})" ]; then
if LS_OUT="$(ls -A ${TMP_EXTRACT})" && [ "$LS_OUT" ]; then
  rm ${TMP_EXTRACT}/* || error_exit "Unable to delete contents of ${TMP_EXTRACT}"
else
  echo "${TMP_EXTRACT} is empty"
fi

rmdir ${TMP_EXTRACT} || error_exit "Unable to delete directory: ${TMP_EXTRACT}"

echo "DONE"
exit 0
