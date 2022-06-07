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

strftime         joda  simp  java iso  example
--------         ----  ----  ---- ---  -------
%A               EEEE  EEEE            'Sunday'
%a               E     E               'Sun'
%B               MMMM  MMMM            'January'
%b               MMM   MMM             'Jan'
%C               C                     '20'
%c                                     'Sun Jan  3 04:05:06 2021'
%D=%m/%d/%y                            '01/03/21'
%d               dd    dd         iso  '03'
%E* %O*                                '
[ ]%e            d     d               ' 3'
%F=%Y-%m-%d                       iso  '2021-01-03'
%G               xxxx  YYYY       iso  '2020'
%g               xx    YY              '20'
%H               HH    HH         iso  '04'
%h=%b                                  'Jan'
%I               hh    hh              '04'
%j               DDD   DDD        iso  '003'
[ ]%k            H     H               ' 4'
[ ]%l            h     h               ' 4'
%M               mm    mm         iso  '05'
%m               MM    MM         iso  '01'
%n=\n                                  '
%p               a     a               'AM'
%R=%H:%M                          iso  '04:05'
%r=%I:%M:%S %p                         '04:05:06 AM'
%S               ss    ss         iso  '06'
%s=EpochTime                           '1609646706'
%T=%H:%M:%S                       iso  '04:05:06'
%t=\t                                  '
%U                                     '01'
%u               e    u           iso  '7'
%V               ww   ww          iso  '53'
%v=%e-%b-%Y                            ' 3-Jan-2021'
%W                                     '00'
%w                                     '0'
%X                                     '04:05:06'
%x                                     '01/03/21'
%Y               yyyy yyyy        iso  '2021'
%y               yy   yy               '21'
%Z               z    z                'UTC'
%z               Z    Z           iso  '+0000'
%+                                     'Sun Jan  3 04:05:06 UTC 2021'
%-*                                    '
%_*                                    '
%0*                                    '
%%=%                                   ‘%’
%FT%T%z                           iso  '2021-01-03T04:05:06+0000'
%G-W%V-%u                         iso  '2020-W53-7'
%Y-%j                             iso  '2021-003'
```
