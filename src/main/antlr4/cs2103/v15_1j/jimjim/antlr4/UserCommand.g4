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
date:	'today'
	|	'tomorrow'
	|	weekday
	|	'this' weekday
	|	'next' weekday
	|	INT ('/'|'-') INT ('/'|'-') INT
	|	INT ('/'|'-') INT
	;
weekday:	MON | TUE | WED | THU | FRI | SAT | SUN;
time:	INT					# hourOnly
	|	INT ('.'|':') INT	# hourMinute
	;


BY:	[Bb][Yy];
FROM: [Ff][Rr][Oo][Mm];
TO:	[Tt][Oo];
AT: [Aa][Tt];

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