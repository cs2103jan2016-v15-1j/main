//@@author A0131140E
grammar UserCommand;

@lexer::header { 
import java.util.Map;
}

@lexer::members {
    Map<String, Integer> aliases = null;

    public void setAliases(Map<String, Integer> aliases) {
        this.aliases = aliases;
    }
}

cmd:	delCmd
    |   markDoneCmd
    |   unmarkCmd
    |   searchCmd
    |   changeCmd
    |   hideSearchCmd
    |   undoCmd
    |   redoCmd
    |   helpCmd
    |   aliasCmd
    |   showHideCmd
    |   saveLocationCmd
    |   addCmd  // should be the last rule to check
	;
	
delCmd: DELETE ITEM_NUM;

markDoneCmd:    MARK ITEM_NUM (AS DONE)?;

unmarkCmd:  UNMARK ITEM_NUM;

searchCmd:  SEARCH (filter ',')* filter;

changeCmd:  (SHIFT|(CHANGE START?)|(CHANGE END))
            ITEM_NUM TO? (date|time|datetime)           # changeOneTime
        |   (SHIFT|CHANGE)
            ITEM_NUM TO? date FROM? time TO time        # changeStartEndCommonDate
        |   (SHIFT|CHANGE)
            ITEM_NUM TO? FROM? time TO time             # changeStartEndTimeOnly
        |   (SHIFT|CHANGE)
            ITEM_NUM TO? FROM? datetime TO datetime     # changeStartEndDateTime
        |   (RENAME|(CHANGE NAME?)) ITEM_NUM TO? string # rename
        ;

hideSearchCmd:   HIDE SEARCH;

undoCmd:    UNDO;

redoCmd:    REDO;

showHideCmd: (SHOW|HIDE) (OVERDUE|COMPLETED|DONE);

helpCmd:    HELP helpPage?;

aliasCmd:   ALIAS ADD aliasable (WORD|aliasable)    # aliasAdd
    |       ALIAS DELETE (WORD|aliasable)           # aliasDelete
    |       ALIAS (LIST|SHOW)                       # aliasList
    ;
    
saveLocationCmd:    SAVE TO string;

addCmd: string BY (date|time|datetime)      # addTask
    |   string ON date FROM? time TO time   # addEventCommonDate
    |   string (ON|AT|FROM)? datetime (TO (datetime|time))?   # addEvent
    |   string ON? date                     # addFullDayEvent
    |   string FROM? time TO time           # addEventWithoutDate
    |   string                              # addFloatingTask
    ;
    
aliasable:  DELETE
    |       UNMARK
    |       MARK
    |       SEARCH
    |       CONTAIN
    |       RENAME
    |       CHANGE
    |       HIDE
    |       UNDO
    |       REDO
    |       HELP
    |       ALIAS
    |       ADD
    |       LIST
    |       SHOW
    ;
    
helpPage:   DATE
    |       TIME
    |       COMMON
    |       ADD
    |       DELETE
    |       MARK
    |       UNMARK
    |       CHANGE
    |       SEARCH
    |       ALIAS
    |       WORD
    ;
	
string:   .+?;
/* Note: 10 Jan 11 will be understood as 10 Jan of the year 11
 * to specify 10 January, 11 o'lock, make 11 more explicit as a
 * time e.g. 11.00, 11 a.m., etc.
 * to specify 10 o'clock, 11 January, make 10 more explicit as a
 * time
 */ 
datetime:   date AT? time   # dateThenTime
        |   time ON? date   # timeThenDate
        ;
date:   TODAY                               # today
    |   TOMORROW                            # tomorrow
    |   YESTERDAY                           # yesterday
    |   (THIS|NEXT)? DAY_OF_WEEK            # dayOfWeek
    |   INT ('/'|'-') INT (('/'|'-') INT)?  # fullDate
    |   INT ORDINAL? ('/'|'-'|',')? MONTH_NAME (('/'|'-'|',')? INT)? # fullDateWordMonth
    |   MONTH_NAME ('/'|'-'|',')? INT ORDINAL? (('/'|'-'|',')? INT)? # fullDateWordMonthMonthFirst
    ;
time:   timeWithPeriod
    |   timeWithoutPeriod
    ;
timeWithPeriod: INT (('.'|':') INT)? (AM|PM)
    ;
timeWithoutPeriod:  (INT OCLOCK) | (INT ('.'|':') INT) 
    ;


filter: (BEFORE|AFTER) date             # dateRangeFilter
    |   BETWEEN date AND date           # betweenDateFilter
    |   (BEFORE|AFTER) time             # timeRangeFilter
    |   BETWEEN time AND time           # betweenTimeFilter
    |   (BEFORE|AFTER) datetime         # dateTimeRangeFilter
    |   BETWEEN datetime AND datetime   # betweenDateTimeFilter
    |   THIS WEEK                       # thisWeekFilter
    |   NEXT WEEK                       # nextWeekFilter
    |   THIS MONTH                      # thisMonthFilter
    |   NEXT MONTH                      # nextMonthFilter
    |   AT? time                        # timeFilter
    |   ON? date                        # dateFilter
    |   OVERDUE                         # overdueFilter
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

ORDINAL: ([Ss][Tt]) | ([Nn][Dd]) | ([Rr][Dd]) | ([Tt][Hh]);
OCLOCK: [Oo]['\''']?[Cc][Ll][Oo][Cc][Kk];
OVERDUE: [Oo][Vv][Ee][Rr][Dd][Uu][Ee];

DELETE: [Dd][Ee][Ll][Ee][Tt][Ee];
UNMARK: [Uu][Nn][Mm][Aa][Rr][Kk];
MARK: [Mm][Aa][Rr][Kk];
AS: [Aa][Ss];
DONE: [Dd][Oo][Nn][Ee];
COMPLETED: [Cc][Oo][Mm][Pp][Ll][Ee][Tt][Ee][Dd];
SEARCH: [Ss][Ee][Aa][Rr][Cc][Hh];
CONTAIN: [Cc][Oo][Nn][Tt][Aa][Ii][Nn]([Ss])?;
RENAME: [Rr][Ee][Nn][Aa][Mm][Ee];
CHANGE: [Cc][Hh][Aa][Nn][Gg][Ee];
SHIFT: [Ss][Hh][Ii][Ff][Tt];
HIDE: [Hh][Ii][Dd][Ee];
UNDO: [Uu][Nn][Dd][Oo];
REDO: [Rr][Ee][Dd][Oo];
HELP: [Hh][Ee][Ll][Pp];
ALIAS: [Aa][Ll][Ii][Aa][Ss];
ADD: [Aa][Dd][Dd];
LIST: [Ll][Ii][Ss][Tt];
SHOW: [Ss][Hh][Oo][Ww];
SAVE: [Ss][Aa][Vv][Ee];

DATE: [Dd][Aa][Tt][Ee];
TIME: [Tt][Ii][Mm][Ee];
COMMON: [Cc][Oo][Mm][Mm][Oo][Nn];

NAME: [Nn][Aa][Mm][Ee];
START: [Ss][Tt][Aa][Rr][Tt];
END: [Ee][Nn][Dd];

TODAY: [Tt][Oo][Dd][Aa][Yy];
TOMORROW: [Tt][Oo][Mm][Oo][Rr][Rr][Oo][Ww];
YESTERDAY: [Yy][Ee][Ss][Tt][Ee][Rr][Dd][Aa][Yy];
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

ESCAPED_WORD: '\\'[a-zA-Z]+;

WORD: [a-zA-Z]+
    {
        String word = getText().toLowerCase();
        if ((aliases != null) && (aliases.containsKey(word))) {
            setType(aliases.get(word));
        }
    }
    ;
WS: [ \t\r\n]+ -> skip;
OTHER: .;