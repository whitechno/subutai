strftime – format date and time
===============================

### strftime

Bash `date` uses `strftime` from Standard C Library for the output format.
See `$ man strftime`. (Note that parsing is done using strptime(3).)
The format string consists of zero or more conversion specifications and ordinary
characters.
All ordinary characters are copied directly into the buffer. A conversion
specification consists of a percent sign “‘%’” and one other character.

```text
Examples are for 'Sun Jan  3 04:05:06 UTC 2021'
     %A    is replaced by national representation of the full weekday name.
           'Sunday'

     %a    is replaced by national representation of the abbreviated weekday name.
           'Sun'

     %B    is replaced by national representation of the full month name.
           'January'

     %b    is replaced by national representation of the abbreviated month name.
           'Jan'

     %C    is replaced by century (year/100) as decimal number; 
           single digits are preceded by a zero.
           '20'

     %c    is replaced by national representation of time and date.
           "%a %b %e %T %Y"
           'Sun Jan  3 04:05:06 2021'

     %D    is equivalent to “%m/%d/%y”.
           '01/03/21'

     %d    is replaced by the day of the month as a decimal number (01-31). [%e]
           [ISO 8601 date %Y-%m-%d]
           '03'

     %E* %O*
           POSIX locale extensions. The sequences 
           %Ec %EC %Ex %EX %Ey %EY 
           %Od %Oe %OH %OI %Om %OM %OS %Ou %OU %OV %Ow %OW %Oy 
           are supposed to provide alternate representations.

           Additionally %OB implemented to represent alternative months names (used
           standalone, without day mentioned).

     %e    is replaced by the day of the month as a decimal number (1-31); 
           single digits are preceded by a blank. [%d]
           ' 3'

     %F    is equivalent to “%Y-%m-%d”.
           [ISO 8601 date %F]
           '2021-01-03'

     %G    is replaced by a week year as a decimal number with century. 
           This year is the one that contains the greater part of the week 
           (Monday as the first day of the week).
           [ISO 8601 week date %G-W%V-%u]
           '2020'

     %g    is replaced by the same year as in “%G”, but as a decimal number without 
           century (00-99).
           '20'

     %H    is replaced by the hour (24-hour clock) as a decimal number (00-23). [%k]
           [ISO 8601 time T%H:%M:%S]
           '04'

     %h    the same as %b.
           'Jan'

     %I    is replaced by the hour (12-hour clock) as a decimal number (01-12). [%l]
           '04'

     %j    is replaced by the day of the year as a decimal number (001-366).
           [ISO 8601 ordinal date %Y-%j]
           '003'

     %k    is replaced by the hour (24-hour clock) as a decimal number (0-23); 
           single digits are preceded by a blank. [%H]
           ' 4'

     %l    is replaced by the hour (12-hour clock) as a decimal number (1-12); 
           single digits are preceded by a blank. [%I]
           ' 4'

     %M    is replaced by the minute as a decimal number (00-59).
           [ISO 8601 time T%H:%M:%S]
           '05'

     %m    is replaced by the month as a decimal number (01-12).
           [ISO 8601 date %Y-%m-%d]
           '01'

     %n    is replaced by a newline.

     %O*   the same as %E*.

     %p    is replaced by national representation of either "ante meridiem" (a.m.) 
           or "post meridiem" (p.m.) as appropriate.
           'AM'

     %R    is equivalent to “%H:%M”.
           [ISO 8601 time T%R]
           '04:05'

     %r    is equivalent to “%I:%M:%S %p”.
           '04:05:06 AM' 

     %S    is replaced by the second as a decimal number (00-60).
           [ISO 8601 time T%H:%M:%S]
           '06'

     %s    is replaced by the number of seconds since the Epoch
           (00:00:00 UTC, January 1, 1970; see mktime(3)).
           '1609646706'

     %T    is equivalent to “%H:%M:%S”. 
           [ISO 8601 time T%T]
           '04:05:06'

     %t    is replaced by a tab.

     %U    is replaced by the week number of the year as a decimal number (00-53).
           (Sunday as the first day of the week) All days in a new year preceding 
           the first Sunday are considered to be in week 00.
           '01'

     %u    is replaced by the weekday as a decimal number (1-7).
           (Monday as the first day of the week)
           [ISO 8601 week date %G-W%V-%u]
           '7'

     %V    is replaced by the week number of the year as a decimal number (01-53).
           (Monday as the first day of the week)
           If the week containing January 1 has four or more days in the new year, 
           then it is week 01; otherwise it is the last week of the previous year, 
           and the next week is week 01.
           [ISO 8601 week date %G-W%V-%u]
           '53'

     %v    is equivalent to “%e-%b-%Y”.
           ' 3-Jan-2021'

     %W    is replaced by the week number of the year as a decimal number (00-53).
           (Monday as the first day of the week) All days in a new year preceding 
           the first Monday are considered to be in week 00.
           '00'

     %w    is replaced by the weekday as a decimal number (0-6).
           (Sunday as the first day of the week)
           '0'

     %X    is replaced by national representation of the time.
           '04:05:06'

     %x    is replaced by national representation of the date.
           '01/03/21'

     %Y    is replaced by the year with century as a decimal number. 
           [ISO 8601 date %Y-%m-%d]
           '2021'

     %y    is replaced by the year without century as a decimal number (00-99).
           '21'

     %Z    is replaced by the time zone name.
           'UTC'

     %z    is replaced by the time zone offset from UTC; 
           a leading plus sign stands for east of UTC, a minus sign for west of UTC, 
           hours and minutes follow with two digits each and no delimiter between 
           them (common form for RFC 822 date headers). 
           [ISO 8601 <time>±hhmm]
           '+0000'

     %+    is replaced by national representation of the date and time 
           (the format is similar to that produced by date(1)).
           "%a %b %e %T %Z %Y"
           'Sun Jan  3 04:05:06 UTC 2021'

     %-*   GNU libc extension. Do not do any padding when performing numerical outputs.

     %_*   GNU libc extension. Explicitly specify space for padding.

     %0*   GNU libc extension. Explicitly specify zero for padding.

     %%    is replaced by ‘%’.

```

### python

[python datetime.strftime](
https://docs.python.org/3/library/datetime.html#strftime-and-strptime-behavior)

### joda-time org.joda.time.format.DateTimeFormat

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
Examples are for "2021-01-03T00:05:06 America/Los_Angeles"

 Symbol  Meaning                      Presentation  Examples
 ------  -------                      ------------  -------
 G       era                          text          AD
 GGGG                                               AD
 C       century of era (>=0)         number        20
 Y       year of era (>=0)            year          2021
 YY                                                 21
 YYYY                                               2021
 
 x       weekyear                     year          2020
 xx                                                 20
 xxxx                                               2020
 w       week of weekyear             number        53
 ww                                                 53
 e       day of week 1=Monday,7=Sunday number       7 
 E       day of week                  text          Sun
 EEEE                                               Sunday
 
 y       year                         year          2021
 yy                                                 21
 yyyy                                               2021
 D       day of year                  number        3
 DDD                                                003
 M       month of year                month         1
 MM                                                 01
 MMM                                                Jan
 MMMM                                               January
 d       day of month                 number        3
 dd                                                 03

 a       halfday of day               text          AM
 aaaa                                               AM
 K       hour of halfday (0~11)       number        0
 KK                                                 00
 h       clockhour of halfday (1~12)  number        12
 hh                                                 12

 H       hour of day (0~23)           number        0
 HH                                                 00
 k       clockhour of day (1~24)      number        24
 kk                                                 24
 m       minute of hour               number        5
 mm                                                 05
 s       second of minute             number        6
 ss                                                 06
 S       fraction of second           millis        978

 z       time zone name               text          PST
 zzzz                                               Pacific Standard Time
 Z       time zone offset/id          zone          -0800
 ZZ                                                 -08:00
 ZZZ                                                America/Los_Angeles

 '       escape for text              delimiter
 ''      single quote                 literal       '
 
yyyy-MM-dd'T'HH:mm:ss                 iso date-time 2021-01-03T00:05:06 
xxxx-'W'ww-e                          iso week-date 2020-W53-7
yyyy-DDD                              iso ord-date  2021-003
```

### java.text.SimpleDateFormat

[java.text.SimpleDateFormat](
https://docs.oracle.com/javase/8/docs/api/java/text/SimpleDateFormat.html)
and [DateTimeFormatterBuilder](
https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatterBuilder.html#appendPattern--)

All letters 'A' to 'Z' and 'a' to 'z' are reserved as pattern letters.

```text
Symbol  Meaning                     Presentation   Examples
------  -------                     ------------   -------
 G       era                         text           AD; Anno Domini; A
 u       year                        year           2004; 04
 y       year-of-era                 year           2004; 04
 D       day-of-year                 number         189
 M/L     month-of-year               number/text    7; 07; Jul; July; J
 d       day-of-month                number         10

 Q/q     quarter-of-year             number/text    3; 03; Q3; 3rd quarter
 Y       week-based-year             year           1996; 96
 w       week-of-week-based-year     number         27
 W       week-of-month               number         4
 E       day-of-week                 text           Tue; Tuesday; T
 e/c     localized day-of-week       number/text    2; 02; Tue; Tuesday; T
 F       week-of-month               number         3

 a       am-pm-of-day                text           PM
 h       clock-hour-of-am-pm (1-12)  number         12
 K       hour-of-am-pm (0-11)        number         0
 k       clock-hour-of-am-pm (1-24)  number         0

 H       hour-of-day (0-23)          number         0
 m       minute-of-hour              number         30
 s       second-of-minute            number         55
 S       fraction-of-second          fraction       978
 A       milli-of-day                number         1234
 n       nano-of-second              number         987654321
 N       nano-of-day                 number         1234000000

 V       time-zone ID                zone-id        America/Los_Angeles; Z; -08:30
 z       time-zone name              zone-name      Pacific Standard Time; PST
 O       localized zone-offset       offset-O       GMT+8; GMT+08:00; UTC-08:00;
 X       zone-offset 'Z' for zero    offset-X       Z; -08; -0830; -08:30; -083015; -08:30:15;
 x       zone-offset                 offset-x       +0000; -08; -0830; -08:30; -083015; -08:30:15;
 Z       zone-offset                 offset-Z       +0000; -0800; -08:00;

 p       pad next                    pad modifier   1

 '       escape for text             delimiter
 ''      single quote                literal        '
 [       optional section start
 ]       optional section end
 #       reserved for future use
 {       reserved for future use
 }       reserved for future use
```

### java.time.format.DateTimeFormatter

[java.time.format.DateTimeFormatter](
https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html#patterns
)