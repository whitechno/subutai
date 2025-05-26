#!/bin/bash

: '
The ideal argument parser will recognize both short and long option flags,
preserve the order of positional arguments, and allow both options and arguments
to be specified in any order relative to each other.
In other words both of these commands should result in the same parsed arguments:

  $ bash arg-parser.sh -a 111 -b "3 5" "222 444"
  $ bash arg-parser.sh "222 444" 111 -a --my-flag-with-argument="3 5"
Both commands should result in the following output:
  MY_FLAG_OPT: >0<
  MY_FLAG_ARG: >3 5<
  PARAMS: > "111" "222 444"<
  1: 111
  2: 222 444

  $ bash arg-parser.sh -b 1 -b 2
  MY_FLAG_ARG: >2<
'

help() {
  echo "Usage:
 $(basename "$0") [ -a | --my-boolean-flag ]
               [ -b | --my-flag-with-argument | --my-flag-with-argument= arg]
               [ -h | --help  ]"
  exit 2
}

PARAMS=""
while (("$#")); do
  case "$1" in
  -a | --my-boolean-flag)
    MY_FLAG_OPT=0
    shift
    ;;
  -b | --my-flag-with-argument)
    if [ -n "$2" ] && [ ${2:0:1} != "-" ]; then
      MY_FLAG_ARG=$2
      shift 2
    else
      echo "Error: Argument for $1 is missing" >&2
      exit 1
    fi
    ;;
  --my-flag-with-argument=*)
    MY_FLAG_ARG=${1#*=}
    [ -z "$MY_FLAG_ARG" ] && {
      echo "Error: Argument for $1 is missing" >&2
      exit 1
    }
    shift
    ;;
  -h | --help)
    help
    ;;
  -* | --*=) # unsupported flags
    echo "Error: Unsupported flag $1" >&2
    exit 1
    ;;
  *) # preserve positional arguments
    PARAMS="$PARAMS \"$1\""
    shift
    ;;
  esac
done

# set positional arguments in their proper place
eval set -- "$PARAMS"

# output:
[ $MY_FLAG_OPT ] && echo "MY_FLAG_OPT: >$MY_FLAG_OPT<"
[ "$MY_FLAG_ARG" ] && echo "MY_FLAG_ARG: >$MY_FLAG_ARG<"
[ "$PARAMS" ] && echo "PARAMS: >$PARAMS<"
# print positional arguments in their proper order
i=1
for arg in "$@"; do
  echo "$i: $arg"
  i=$((i + 1))
done
