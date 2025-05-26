strftime vs other format specifications
=======================================

```text
strftime: Standard C Library, Bash date, Python
    joda: joda-time org.joda.time.format.DateTimeFormat
    simp: java.text.SimpleDateFormat
    java: java.time.format.DateTimeFormatter
     iso: ISO 8601-1:2019
Examples are for 'Sun Jan  3 04:05:06 UTC 2021' 
("2021-01-03T04:05:06+0000" in ISO 8601)

strftime         joda  simp  java  iso  example
--------         ----  ----  ----  ---  -------
%A               EEEE  EEEE  EEEE       'Sunday'
%a               E     E     E          'Sun'
%B               MMMM  MMMM  MMMM       'January'
%b               MMM   MMM   MMM        'Jan'
%C               C                      '20'
%c                                      'Sun Jan  3 04:05:06 2021'
%D=%m/%d/%y                             '01/03/21'
%d               dd    dd    dd    iso  '03'
%E* %O*                                 '
[ ]%e            d     d     d          ' 3'
%F=%Y-%m-%d                        iso  '2021-01-03'
%G               xxxx  YYYY  YYYY  iso  '2020'
%g               xx    YY    YY         '20'
%H               HH    HH    HH    iso  '04'
%h=%b                                   'Jan'
%I               hh    hh    hh         '04'
%j               DDD   DDD   DDD   iso  '003'
[ ]%k            H     H     H          ' 4'
[ ]%l            h     h     h          ' 4'
%M               mm    mm    mm    iso  '05'
%m               MM    MM    MM    iso  '01'
%n=\n                                   '
%p               a     a     a          'AM'
%R=%H:%M                           iso  '04:05'
%r=%I:%M:%S %p                          '04:05:06 AM'
%S               ss    ss    ss    iso  '06'
%s=EpochTime                            '1609646706'
%T=%H:%M:%S                        iso  '04:05:06'
%t=\t                                   '
%U                                      '01'
%u               e    u      e     iso  '7'
%V               ww   ww     ww    iso  '53'
%v=%e-%b-%Y                             ' 3-Jan-2021'
%W                                      '00'
%w                                      '0'
%X                                      '04:05:06'
%x                                      '01/03/21'
%Y               yyyy yyyy   yyyy  iso  '2021'
%y               yy   yy     yy         '21'
%Z               z    z      z          'UTC'
%z               Z    Z      Z     iso  '+0000'
%+                                      'Sun Jan  3 04:05:06 UTC 2021'
%-*                                     '
%_*                                     '
%0*                                     '
%%=%                                    ‘%’


strftime  joda                  java                  iso       example
--------  ----                  ----                  ----      -------
%z        Z                     Z                     tz-offset +0000
%FT%T     yyyy-MM-dd'T'HH:mm:ss yyyy-MM-dd'T'HH:mm:ss date-time 2021-01-03T04:05:06
%G-W%V-%u xxxx-'W'ww-e          YYYY-'W'ww-e          week-date 2020-W53-7
%Y-%j     yyyy-DDD              yyyy-DDD              ord-date  2021-003
```
