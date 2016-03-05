grammar UserCommand;

cmd:    addCmd
    ;

addCmd: task BY datetime                # addTask
    |   task date FROM time TO time     # addEventCommonDate
    |   task FROM datetime TO datetime  # addEvent
    |   task                            # addFloatingTask
    ;
	
task:   .+?;
/* Note: 10 Jan 11 will be understood as 10 Jan of the year 11
 * to specify 10 January, 11 o'lock, make 11 more explicit as a
 * tim e.g. 11.00, 11 a.m., etc.
 */ 
datetime:   date        # dateOnly
        |   time        # timeOnly
        |   date time   # dateThenTime
        |   time date   # timeThenDate
        ;
date:   TODAY                               # today
    |   TOMORROW                            # tomorrow
    |   DAY_OF_WEEK                         # dayOfWeekOnly
    |   THIS DAY_OF_WEEK                    # thisDayOfWeek
    |   NEXT DAY_OF_WEEK                    # nextDayOfWeek
    |   INT ('/'|'-') INT ('/'|'-') INT     # fullDate
    |   INT ('/'|'-') INT                   # dayMonth
    |   INT ('/'|'-'|',')? MONTH ('/'|'-'|',')? INT # fullDateWordMonth
    |   INT ('/'|'-'|',')? MONTH                    # dayMonthWordMonth
    |   MONTH ('/'|'-'|',')? INT ('/'|'-'|',')? INT # fullDateWordMonthMonthFirst
    |   MONTH ('/'|'-'|',')? INT                    # dayMonthWordMonthMonthFirst
    ;
time:   INT                         # hourOnly
    |   INT ('.'|':') INT           # hourMinute
    |   INT (AM|PM)                 # hourNoon
    |   INT ('.'|':') INT (AM|PM)   # hourMinuteNoon
    ;


BY:	[Bb][Yy];
FROM: [Ff][Rr][Oo][Mm];
TO:	[Tt][Oo];
AT: [Aa][Tt];

AM: [Aa].?[Mm].?;
PM: [Pp].?[Mm].?;

TODAY: [Tt][Oo][Dd][Aa][Yy];
TOMORROW: [Tt][Oo][Mm][Oo][Rr][Rr][Oo][Ww];
THIS: [Tt][Hh][Ii][Ss];
NEXT: [Nn][Ee][Xx][Tt];

DAY_OF_WEEK:    [Mm][Oo][Nn]([Dd][Aa][Yy])?
            |   [Tt][Uu][Ee]([Ss][Dd][Aa][Yy])?
            |   [Ww][Ee][Dd]([Nn][Ee][Ss][Dd][Aa][Yy])?
            |   [Tt][Hh][Uu]([Rr][Ss][Dd][Aa][Yy])?
            |   [Ff][Rr][Ii]([Dd][Aa][Yy])?
            |   [Ss][Aa][Tt]([Uu][Rr][Dd][Aa][Yy])?
            |   [Ss][Uu][Nn]([Dd][Aa][Yy])?
            ;
MONTH:  [Jj][Aa][Nn]([Uu][Aa][Rr][Yy])?
    |   [Ff][Ee][Bb]([Rr][Uu][Aa][Rr][Yy])?
    |   [Mm][Aa][Rr]([Cc][Hh])?
    |   [Aa][Pp][Rr]([Ii][Ll])?
    |   [Mm][Aa][Yy]
    |   [Jj][Uu][Nn]([Ee])?
    |   [Jj][Uu][Ll]([Yy])?
    |   [Aa][Uu][Gg]([Uu][Ss][Tt])?
    |   [Ss][Ee][Pp]([Tt][Ee][Mm][Bb][Ee][Rr])?
    |   [Oo][Cc][Tt]([Oo][Bb][Ee][Rr])?
    |   [Nn][Oo][Vv]([Ee][Mm][Bb][Ee][Rr])?
    |   [Dd][Ee][Cc]([Ee][Mm][Bb][Ee][Rr])?
    ;
INT:[0-9]+;

WORD: [a-zA-Z0-9]+ ;
WS: [ \t\r\n]+ -> skip;