grammar UserCommand;

cmd:    addCmd
    ;

addCmd: task BY datetime                # addTask
    |   task date FROM time TO time     # addEventCommonDate
    |   task FROM datetime TO datetime  # addEvent
    |   task                            # addFloatingTask
    ;
	
task:   .+?;
datetime:   time date   # timeThenDate
        |   date time   # dateThenTime
        |   date        # dateOnly
        |   time        # timeOnly
        ;
date:   TODAY                           # today
    |   TOMORROW                        # tomorrow
    |   DAY_OF_WEEK                     # dayOfWeekOnly
    |   THIS DAY_OF_WEEK                # thisDayOfWeek
    |   NEXT DAY_OF_WEEK                # nextDayOfWeek
    |   INT ('/'|'-') INT ('/'|'-') INT # fullDate
    |   INT ('/'|'-') INT               # dayMonth
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
INT:[0-9]+;

WORD: [a-zA-Z0-9]+ ;
WS: [ \t\r\n]+ -> skip;