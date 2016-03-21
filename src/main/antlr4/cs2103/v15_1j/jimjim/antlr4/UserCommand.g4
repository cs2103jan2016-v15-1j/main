grammar UserCommand;

cmd:	delCmd
    |   markDoneCmd
    |   searchCmd
    |   clearCmd
    |   addCmd  // should be the last rule to check
	;
	
delCmd: DELETE ITEM_NUM;

markDoneCmd:    MARK ITEM_NUM (AS DONE)?;

searchCmd:  SEARCH (filter)+;

clearCmd:   CLEAR;

addCmd: string BY datetime                # addTask
    |   string ON? date FROM time TO time # addEventCommonDate
    |   string FROM datetime TO datetime  # addEvent
    |   string                            # addFloatingTask
    ;
	
string:   .+?;
/* Note: 10 Jan 11 will be understood as 10 Jan of the year 11
 * to specify 10 January, 11 o'lock, make 11 more explicit as a
 * time e.g. 11.00, 11 a.m., etc.
 * to specify 10 o'clock, 11 January, make 10 more explicit as a
 * time
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
    |   INT ('/'|'-'|',')? MONTH_NAME ('/'|'-'|',')? INT # fullDateWordMonth
    |   INT ('/'|'-'|',')? MONTH_NAME                    # dayMonthWordMonth
    |   MONTH_NAME ('/'|'-'|',')? INT ('/'|'-'|',')? INT # fullDateWordMonthMonthFirst
    |   MONTH_NAME ('/'|'-'|',')? INT                    # dayMonthWordMonthMonthFirst
    ;
time:   INT                         # hourOnly
    |   INT ('.'|':') INT           # hourMinute
    |   INT (AM|PM)                 # hourNoon
    |   INT ('.'|':') INT (AM|PM)   # hourMinuteNoon
    ;
filter: (BEFORE|AFTER) time             # timeRangeFilter
    |   BETWEEN time AND time           # betweenTimeFilter
    |   (BEFORE|AFTER) date             # dateRangeFilter
    |   BETWEEN date AND date           # betweenDateFilter
    |   (BEFORE|AFTER) datetime         # dateTimeRangeFilter
    |   BETWEEN datetime AND datetime   # betweenDateTimeFilter
    |   THIS WEEK                       # thisWeekFilter
    |   NEXT WEEK                       # nextWeekFilter
    |   THIS MONTH                      # thisMonthFilter
    |   NEXT MONTH                      # nextMonthFilter
    |   AT? time                        # timeFilter
    |   ON? date                        # dateFilter
    |   CONTAIN? string                 # keywordFilter
    ;


BY:	[Bb][Yy];
FROM: [Ff][Rr][Oo][Mm];
TO:	[Tt][Oo];
AT: [Aa][Tt];
ON: [Oo][Nn];
BEFORE: [Bb][Ee][Ff][Oo][Rr][Ee];
AFTER: [Aa][Ff][Tt][Ee][Rr];
BETWEEN: [Bb][Ee][Tt][Ww][Ee][Ee][Nn];
AND: [Aa][Nn][Dd];

AM: [Aa].?[Mm].?;
PM: [Pp].?[Mm].?;

DELETE: [Dd][Ee][Ll][Ee][Tt][Ee];
MARK: [Mm][Aa][Rr][Kk];
AS: [Aa][Ss];
DONE: [Dd][Oo][Nn][Ee];
SEARCH: [Ss][Ee][Aa][Rr][Cc][Hh];
CONTAIN: [Cc][Oo][Nn][Tt][Aa][Ii][Nn]([Ss])?;
CLEAR: [Cc][Ll][Ee][Aa][Rr];

TODAY: [Tt][Oo][Dd][Aa][Yy];
TOMORROW: [Tt][Oo][Mm][Oo][Rr][Rr][Oo][Ww];
THIS: [Tt][Hh][Ii][Ss];
NEXT: [Nn][Ee][Xx][Tt];

MONTH: [Mm][Oo][Nn][Tt][Hh];
WEEK: [Ww][Ee][Ee][Kk];

DAY_OF_WEEK:    [Mm][Oo][Nn]([Dd][Aa][Yy])?
            |   [Tt][Uu][Ee]([Ss][Dd][Aa][Yy])?
            |   [Ww][Ee][Dd]([Nn][Ee][Ss][Dd][Aa][Yy])?
            |   [Tt][Hh][Uu]([Rr][Ss][Dd][Aa][Yy])?
            |   [Ff][Rr][Ii]([Dd][Aa][Yy])?
            |   [Ss][Aa][Tt]([Uu][Rr][Dd][Aa][Yy])?
            |   [Ss][Uu][Nn]([Dd][Aa][Yy])?
            ;
MONTH_NAME:  [Jj][Aa][Nn]([Uu][Aa][Rr][Yy])?
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
ITEM_NUM: [FfEeDd][0-9]+;
INT:[0-9]+;

WORD: [a-zA-Z0-9]+ ;
WS: [ \t\r\n]+ -> skip;