grammar UserCommand;

cmd:	addCmd
	;

addCmd: task BY datetime				# addTask
	|	task date FROM time TO time		# addEventCommonDate
	|	task FROM datetime TO datetime	# addEvent
	|	task							# addFloatingTask
	;
	
task:	.+?;
datetime: time date	# timeThenDate
	| date time		# dateThenTime
	| date			# dateOnly
	| time			# timeOnly
	;
date:	TODAY							# today
	|	TOMORROW						# tomorrow
	|	dayOfWeek						# dayOfWeekOnly
	|	THIS dayOfWeek					# thisDayOfWeek
	|	NEXT dayOfWeek					# nextDayOfWeek
	|	INT ('/'|'-') INT ('/'|'-') INT	# fullDate
	|	INT ('/'|'-') INT				# dayMonth
	;
dayOfWeek:	MON | TUE | WED | THU | FRI | SAT | SUN;
time:	INT					# hourOnly
	|	INT ('.'|':') INT	# hourMinute
	;


BY:	[Bb][Yy];
FROM: [Ff][Rr][Oo][Mm];
TO:	[Tt][Oo];
AT: [Aa][Tt];

TODAY: [Tt][Oo][Dd][Aa][Yy];
TOMORROW: [Tt][Oo][Mm][Oo][Rr][Rr][Oo][Ww];
THIS: [Tt][Hh][Ii][Ss];
NEXT: [Nn][Ee][Xx][Tt];

MON:	[Mm][Oo][Nn]([Dd][Aa][Yy])?;
TUE:	[Tt][Uu][Ee]([Ss][Dd][Aa][Yy])?;
WED:	[Ww][Ee][Dd]([Nn][Ee][Ss][Dd][Aa][Yy])?;
THU:	[Tt][Hh][Uu]([Rr][Ss][Dd][Aa][Yy])?;
FRI:	[Ff][Rr][Ii]([Dd][Aa][Yy])?;
SAT:	[Ss][Aa][Tt]([Uu][Rr][Dd][Aa][Yy])?;
SUN:	[Ss][Uu][Nn]([Dd][Aa][Yy])?;

INT:	[0-9]+;

WORD:	[a-zA-Z0-9]+ ;
WS: [ \t\r\n]+ -> skip;