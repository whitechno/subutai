#!/bin/bash

echo "TODAY: $(date '+%Y%m%d')"

: '
Important note about parsing datetime without explicit time zone
----------------------------------------------------------------
Parsing of datetimes is done using strptime.
Datetime like "2021-01-03T00:00:00" - without explicit time zone - is generally
parsed applying local time zone (as set at the kernel clock).
If datetime is implicitly assumed to be in a specific time zone, different from
local, then either
  1) Time zone should be added explicitly to datetime in %z format (like +0000).
     The %Z format (abbreviated name, like PST) specifier only accepts time zone
     abbreviations of the local time zone, or the value "GMT".
     This limitation is because of ambiguity due to the over loading of time zone
     abbreviations. One such example is EST which is both
     Eastern Standard Time and Eastern Australia Summer Time.
or
  2) TZ - the timezone environment variable - should be set to the assumed timezone.
     TZ format is a pathname relative to /usr/share/zoneinf
     (ls -lLA /usr/share/zoneinfo)
     and is a recognized time zone id, like America/New_York or UTC.
or
  3) If input and output are UTC, then use "date" with "-u" option.

Examples (local time zone is America/Los_Angeles)
Note, that %F=%Y-%m-%d and %T=%H:%M:%S

  1) Parsing without TZ
  date -j -f "%F %T" "2021-01-03 00:00:00"
    Sun Jan  3 00:00:00 PST 2021

  date -j -f "%F %T %z" "2021-01-03 00:00:00 +0000"
    Sat Jan  2 16:00:00 PST 2021

  date -ju -f "%F %T" "2021-01-03 00:00:00"
    Sun Jan  3 00:00:00 UTC 2021

  date -j -f "%F %T %Z" "2021-01-03 00:00:00 GMT"
    Sat Jan  2 16:00:00 PST 2021

  date -j -f "%F %T %Z" "2021-01-03 00:00:00 UTC"
    Fails to parse: illegal time format

  2) Parsing with TZ
  TZ=UTC date -j -f "%F %T" "2021-01-03 00:00:00"
    Sun Jan  3 00:00:00 UTC 2021

  TZ=UTC date -j -f "%F %T %Z" "2021-01-03 00:00:00 UTC"
    Sun Jan  3 00:00:00 UTC 2021

  TZ=PST8PDT date -j -f "%F %T" "2021-01-03 00:00:00"
    Sun Jan  3 00:00:00 PST 2021

  3) Convert from local to UTC using %z and -u
  date -j -f "%F %T" "2021-01-03 00:00:00"  "+%F %T %z"
    2021-01-03 00:00:00 -0800
  date -ju -f "%F %T %z" "2021-01-03 00:00:00 -0800"
    Sun Jan  3 08:00:00 UTC 2021

  4) Convert from EST (EST5EDT=America/New_York) to PST (PST8PDT=America/Los_Angeles)
     using %z and TZ
  TZ=EST5EDT date -j -f "%F %T" "2021-01-03 00:00:00" "+%F %T %z"
    2021-01-03 00:00:00 -0500
  TZ=PST8PDT date -j -f "%F %T %z" "2021-01-03 00:00:00 -0500"
    Sat Jan  2 21:00:00 PST 2021
'

# shellcheck disable=SC2016
: '
DATE1="2021-01-03T00:00:00"
INPUT_FMT="%Y-%m-%dT%H:%M:%S"
echo "For $DATE1:"

# default output format (if you are in San Francisco)
date -j -f "$INPUT_FMT" "$DATE1"                      # Sun Jan  3 00:00:00 PST 2021
date -j -f "$INPUT_FMT" "$DATE1" "+%a %b %e %T %Z %Y" # same as above
date -j -f "$INPUT_FMT" "$DATE1" "+%+"                # same as above
date -j -f "$INPUT_FMT" "$DATE1" "+%c"                # Sun Jan  3 00:00:00 2021
# parse default format and output Epoch seconds
date -j -f "%a %b %d %T %Z %Y" "Sun Jan  3 00:00:00 PST 2021" "+%s" # 1609660800

# RFC 2822 output format with -R option
date -jR -f "$INPUT_FMT" "$DATE1"                      # Sun, 03 Jan 2021 00:00:00 -0800
date -j -f "$INPUT_FMT" "$DATE1" "+%a, %d %b %Y %T %z" # same as above
# parse RFC 2822 format
date -j -f "%a, %d %b %Y %T %z" "Sun, 03 Jan 2021 00:00:00 -0800" "+%s" # 1609660800

# ISO 8601 output format with -I[date|hours|minutes|seconds] option
date -j -Idate -f "$INPUT_FMT" "$DATE1"    # 2021-01-03
date -j -Iseconds -f "$INPUT_FMT" "$DATE1" # 2021-01-03T00:00:00-08:00
# ISO 8601 format for -I[hours|minutes|seconds] option cannot be parsed back
# because its time zone part is not of %z format (which doesnt have :).
# The closest format that can be parsed is without ":"
date -j -f "%FT%T%z" "2021-01-03T00:00:00-0900" # Sun Jan  3 01:00:00 PST 2021

# UTC
TZ=UTC date -j -f "$INPUT_FMT" "$DATE1" # Sun Jan  3 00:00:00 UTC 2021
date -ju -f "$INPUT_FMT" "$DATE1"       # same as above

# Epoch seconds for Sun Jan  3 00:00:00 UTC 2021
date -ju -f "$INPUT_FMT" "$DATE1" "+%s" # 1609632000

# Converting Epoch seconds to ISO 8601 hours for different time zones:
date -Ihours -r 1609632000                     # 2021-01-02T16-08:00
TZ=PST8PDT date -Ihours -r 1609632000          # same as above
TZ=America/New_York date -Ihours -r 1609632000 # 2021-01-02T19-05:00
TZ=UTC date -Ihours -r 1609632000              # 2021-01-03T00+00:00
date -u -Ihours -r 1609632000                  # same as above
'

# strfutc
# It takes UTC datetime and optional output format
# and returns UTC datetime in new format.
# Input datetime should be UTC and in ISO 8601 datetime YYYY-MM-DDThh:mm:ss format,
# which is specified as "%FT%T" (="%Y-%m-%dT%H:%M:%S"),
# like this: "2021-01-03T04:05:06".
# Optional output format should be as accepted by `date [+output_fmt]`.
# If output format is not provided then default `date` output format
# is applied, which is usually "%a %b %e %T %Z %Y",
# like this: "Sun Jan  3 04:05:06 UTC 2021".
# Examples:
# strfutc "2021-01-03T04:05:06"       -> Sun Jan  3 00:00:00 UTC 2021
# strfutc "2021-01-03T04:05:06" "+%A" -> Sunday
strfutc() {
  date -ju -f '%FT%T' "$1" "$2"
}

DATE2="2021-01-03T04:05:06"
#DATE2="2018-01-01T04:05:06"
strfutc "$DATE2" # Sun Jan  3 00:00:00 UTC 2021

fmts=(%A %a %B %b %C %c %D %d %e %F %G %g %H %h %I %j %k %l %M %m %p %R
  %r %S %s %T %U %u %V %v %W %w %X %x %Y %y %Z %z %+
  %FT%T%z %G-W%V-%u %Y-%j)
for fmt in "${fmts[@]}"; do
  echo "$fmt '$(strfutc "$DATE2" "+$fmt")'"
done

# calendar for 2021 with the number of the week below each week column
#ncal -w 01 2021
: '
    January 2021
Mo     4 11 18 25
Tu     5 12 19 26
We     6 13 20 27
Th     7 14 21 28
Fr  1  8 15 22 29
Sa  2  9 16 23 30
Su  3 10 17 24 31
   53  1  2  3  4
'
#cal | head -1 | grep -oE "[A-Za-z]+"    # current month
#cal | head -1 | grep -oE "[[:alpha:]]+" # same as above
