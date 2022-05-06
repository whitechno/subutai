#!/bin/bash

function error_exit() {
  echo "$1" 1>&2
  exit 1
}

TMP_DIR=/tmp
TMP_EXTRACT=${TMP_DIR}/da_download
TODAY=$(date '+%Y%m%d')
echo $TODAY
if [ ! -d ${TMP_EXTRACT} ]; then
  mkdir ${TMP_EXTRACT}
  if [ "$?" -ne "0" ]; then error_exit "Could not create temporary directory: ${TMP_EXTRACT}"; fi
fi

echo "Cleaning up..."
rm ${TMP_EXTRACT}/*.*
if [ "$?" -ne "0" ]; then error_exit "Unable to delete contents of ${TMP_EXTRACT}"; fi
rmdir ${TMP_EXTRACT}
if [ "$?" -ne "0" ]; then error_exit "Unable to delete directory: ${TMP_EXTRACT}"; fi
echo "DONE"

exit 0
