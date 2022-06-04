strftime – format date and time
===============================

Bash `date` uses `strftime` from Standard C Library. The format string consists of
zero or more conversion specifications and ordinary characters. All ordinary
characters are copied directly into the buffer. A conversion specification consists
of a percent sign “‘%’” and one other character.

```text

     %A    is replaced by national representation of the full weekday name.

     %a    is replaced by national representation of the abbreviated weekday name.

     %B    is replaced by national representation of the full month name.

     %b    is replaced by national representation of the abbreviated month name.

     %C    is replaced by (year / 100) as decimal number; single digits are preceded by a
           zero.

     %c    is replaced by national representation of time and date.

     %D    is equivalent to “%m/%d/%y”.

     %d    is replaced by the day of the month as a decimal number (01-31).

     %E* %O*
           POSIX locale extensions.  The sequences %Ec %EC %Ex %EX %Ey %EY %Od %Oe %OH %OI
           %Om %OM %OS %Ou %OU %OV %Ow %OW %Oy are supposed to provide alternate
           representations.

           Additionally %OB implemented to represent alternative months names (used
           standalone, without day mentioned).

     %e    is replaced by the day of the month as a decimal number (1-31); single digits are
           preceded by a blank.

     %F    is equivalent to “%Y-%m-%d”.

     %G    is replaced by a year as a decimal number with century.  This year is the one that
           contains the greater part of the week (Monday as the first day of the week).

     %g    is replaced by the same year as in “%G”, but as a decimal number without century
           (00-99).

     %H    is replaced by the hour (24-hour clock) as a decimal number (00-23).

     %h    the same as %b.

     %I    is replaced by the hour (12-hour clock) as a decimal number (01-12).

     %j    is replaced by the day of the year as a decimal number (001-366).

     %k    is replaced by the hour (24-hour clock) as a decimal number (0-23); single digits
           are preceded by a blank.

     %l    is replaced by the hour (12-hour clock) as a decimal number (1-12); single digits
           are preceded by a blank.

     %M    is replaced by the minute as a decimal number (00-59).

     %m    is replaced by the month as a decimal number (01-12).

     %n    is replaced by a newline.

     %O*   the same as %E*.

     %p    is replaced by national representation of either "ante meridiem" (a.m.)  or "post
           meridiem" (p.m.)  as appropriate.

     %R    is equivalent to “%H:%M”.

     %r    is equivalent to “%I:%M:%S %p”.

     %S    is replaced by the second as a decimal number (00-60).

     %s    is replaced by the number of seconds since the Epoch, UTC (see mktime(3)).

     %T    is equivalent to “%H:%M:%S”.

     %t    is replaced by a tab.

     %U    is replaced by the week number of the year (Sunday as the first day of the week)
           as a decimal number (00-53).

     %u    is replaced by the weekday (Monday as the first day of the week) as a decimal
           number (1-7).

     %V    is replaced by the week number of the year (Monday as the first day of the week)
           as a decimal number (01-53).  If the week containing January 1 has four or more
           days in the new year, then it is week 1; otherwise it is the last week of the
           previous year, and the next week is week 1.

     %v    is equivalent to “%e-%b-%Y”.

     %W    is replaced by the week number of the year (Monday as the first day of the week)
           as a decimal number (00-53).

     %w    is replaced by the weekday (Sunday as the first day of the week) as a decimal
           number (0-6).

     %X    is replaced by national representation of the time.

     %x    is replaced by national representation of the date.

     %Y    is replaced by the year with century as a decimal number.

     %y    is replaced by the year without century as a decimal number (00-99).

     %Z    is replaced by the time zone name.

     %z    is replaced by the time zone offset from UTC; a leading plus sign stands for east
           of UTC, a minus sign for west of UTC, hours and minutes follow with two digits
           each and no delimiter between them (common form for RFC 822 date headers).

     %+    is replaced by national representation of the date and time (the format is similar
           to that produced by date(1)).

     %-*   GNU libc extension.  Do not do any padding when performing numerical outputs.

     %_*   GNU libc extension.  Explicitly specify space for padding.

     %0*   GNU libc extension.  Explicitly specify zero for padding.

     %%    is replaced by ‘%’.

```

```text
strftime: bash
joda-time: joda
java.text.SimpleDateFormat: simp

bash            joda  simp
----            ----  ----
%A              EEEE  EEEE
%a              E     E
%B              MMMM  MMMM
%b              MMM   MMM
%C              C
%c
%D=%m/%d/%y
%d              dd    dd
%E* %O*
[ ]%e           d     d
%F=%Y-%m-%d
%G              xxxx  YYYY
%g              xx    YY
%H              HH    HH
%h=%b
%I              hh    hh
%j              DDD   DDD
[ ]%k           H     H
[ ]%l           h     h
%M              mm    mm
%m              MM    MM
%n=\n
%p              a     a
%R=%H:%M
%r=%I:%M:%S %p
%S              ss    ss
%s=EpochTime
%T=%H:%M:%S
%t=\t
%U
%u              e    u
%V    
%v=%e-%b-%Y
%W              ww   ww
%w
%X
%x
%Y              yyyy yyyy
%y              yy   yy
%Z              z    z
%z              Z    Z
%+
%-*
%_*
%0*
%%=%
```

### joda-time DateTimeFormat

[joda-time DateTimeFormat](
https://www.joda.org/joda-time/apidocs/org/joda/time/format/DateTimeFormat.html
)

All ASCII letters are reserved as pattern letters. Any characters in the pattern that
are not in the ranges of ['a'..'z'] and ['A'..'Z'] will be treated as quoted text.
For instance, characters like ':', '.', ' ', '#' and '?' will appear in the resulting
time text even if they are not embraced within single quotes.

The count of pattern letters determine the format.

Text: If the number of pattern letters is 4 or more, the full form is used; otherwise
a short or abbreviated form is used if available.

Number: The minimum number of digits. Shorter numbers are zero-padded to this amount.
When parsing, any number of digits are accepted.

Year: Numeric presentation for year and weekyear fields are handled specially. For
example, if the count of 'y' is 2, the year will be displayed as the zero-based year
of the century, which is two digits.

Month: 3 or over, use text, otherwise use number.

Millis: The exact number of fractional digits. If more millisecond digits are
available then specified the number will be truncated, if there are fewer than
specified then the number will be zero-padded to the right. When parsing, only the
exact number of digits are accepted.

Zone: 'Z' outputs offset without a colon, 'ZZ' outputs the offset with a colon, 'ZZZ'
or more outputs the zone id.

Zone names: Time zone names ('z') cannot be parsed.

```text

 Symbol  Meaning                      Presentation  Examples
 ------  -------                      ------------  -------
 G       era                          text          AD
 C       century of era (>=0)         number        20
 Y       year of era (>=0)            year          1996

 x       weekyear                     year          1996
 w       week of weekyear             number        27
 e       day of week                  number        2 (1 = Monday, ..., 7 = Sunday)
 E       day of week                  text          Tuesday; Tue

 y       year                         year          1996
 D       day of year                  number        189
 M       month of year                month         July; Jul; 07
 d       day of month                 number        10

 a       halfday of day               text          PM
 K       hour of halfday (0~11)       number        0
 h       clockhour of halfday (1~12)  number        12

 H       hour of day (0~23)           number        0
 k       clockhour of day (1~24)      number        24
 m       minute of hour               number        30
 s       second of minute             number        55
 S       fraction of second           millis        978

 z       time zone name               text          Pacific Standard Time; PST
 Z       time zone offset/id          zone          -0800; -08:00; America/Los_Angeles

 '       escape for text              delimiter
 ''      single quote                 literal       '
```

### java.text.SimpleDateFormat

[java.text.SimpleDateFormat](
https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html)

### java.time.format.DateTimeFormatter

[java.time.format.DateTimeFormatter](
https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#patterns
)