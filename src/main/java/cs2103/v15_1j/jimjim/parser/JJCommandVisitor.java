package cs2103.v15_1j.jimjim.parser;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.antlr.v4.runtime.tree.ParseTreeProperty;
import org.antlr.v4.runtime.tree.TerminalNode;

import cs2103.v15_1j.jimjim.antlr4.UserCommandBaseVisitor;
import cs2103.v15_1j.jimjim.antlr4.UserCommandParser;
import cs2103.v15_1j.jimjim.command.AddEventCommand;
import cs2103.v15_1j.jimjim.command.AddFloatingTaskCommand;
import cs2103.v15_1j.jimjim.command.AddDeadlineTaskCommand;
import cs2103.v15_1j.jimjim.command.Command;
import cs2103.v15_1j.jimjim.command.DeleteCommand;
import cs2103.v15_1j.jimjim.command.MarkDoneCommand;

public class JJCommandVisitor extends UserCommandBaseVisitor<Command> {
	
	private ParseTreeProperty<LocalDateTime> dateTimeMap = new ParseTreeProperty<>();
	private ParseTreeProperty<LocalDate> dateMap = new ParseTreeProperty<>();
	private ParseTreeProperty<LocalTime> timeMap = new ParseTreeProperty<>();
	private ParseTreeProperty<String> stringMap = new ParseTreeProperty<>();
	private String userCommand;
	
	public JJCommandVisitor(String userCommand) {
		this.userCommand = userCommand;
	}
	
	@Override
	public Command visitAddFloatingTask(
			UserCommandParser.AddFloatingTaskContext ctx) {
		visit(ctx.task());
		return new AddFloatingTaskCommand(stringMap.get(ctx.task()));
	}

	@Override
	public Command visitAddTask(UserCommandParser.AddTaskContext ctx) {
		visit(ctx.task());
		visit(ctx.datetime());
		return new AddDeadlineTaskCommand(stringMap.get(ctx.task()),
								  dateTimeMap.get(ctx.datetime()));
	}
	
	@Override
	public Command visitTask(UserCommandParser.TaskContext ctx) { 
		stringMap.put(ctx, userCommand.substring(ctx.getStart().getStartIndex(),
				ctx.getStop().getStopIndex()+1));
		return null;
	}
	
	@Override
	public Command visitTimeOnly(UserCommandParser.TimeOnlyContext ctx) {
		LocalDate today = LocalDate.now();
		visit(ctx.time());
		dateTimeMap.put(ctx, today.atTime(timeMap.get(ctx.time())));
		return null;
	}
	
	@Override
	public Command visitHourOnly(UserCommandParser.HourOnlyContext ctx) {
		int hour = Integer.parseInt(ctx.INT().getText());
		timeMap.put(ctx, LocalTime.of(hour, 0));
		return null;
	}
	
	@Override
	public Command visitHourMinute(UserCommandParser.HourMinuteContext ctx) {
		int hour = Integer.parseInt(ctx.INT(0).getText());
		int minute = Integer.parseInt(ctx.INT(1).getText());
		timeMap.put(ctx, LocalTime.of(hour, minute));
		return null;
	}
	
	@Override
	public Command visitDateOnly(UserCommandParser.DateOnlyContext ctx) {
		visit(ctx.date());
		dateTimeMap.put(ctx, dateMap.get(ctx.date()).atTime(23, 59));
		return null;
	}
	
	@Override
	public Command visitToday(UserCommandParser.TodayContext ctx) {
		dateMap.put(ctx, LocalDate.now());
		return null;
	}

	@Override
	public Command visitTomorrow(UserCommandParser.TomorrowContext ctx) {
		dateMap.put(ctx, LocalDate.now().plusDays(1));
		return null;
	}
	
	private int getDayOfWeekInt(UserCommandParser.DayOfWeekOnlyContext ctx) {
		String day = ctx.getText().substring(0, 3).toLowerCase();
		String[] days = {"", "mon", "tue", "wed", "thu", "fri", "sat", "sun"};
		for (int i=0; i<days.length; i++) {
			if (days[i].equals(day)) {
				return i;
			}
		}
		assert false; // shouldn't happen
		return 0;
	}

	@Override
	public Command visitDayOfWeekOnly(UserCommandParser.DayOfWeekOnlyContext ctx) {
		int dayInt = getDayOfWeekInt(ctx);
		DayOfWeek.of(dayInt);	// check that dayInt is valid
		LocalDate today = LocalDate.now();
		int todayInt = today.getDayOfWeek().getValue();
		if (todayInt < dayInt) {
			// referring to this week
			dateMap.put(ctx, today.plusDays(dayInt - todayInt));
		} else {
			// referring to next week
			dateMap.put(ctx, today.plusDays(dayInt - todayInt + 7));
		}
		return null;
	}

	@Override
	public Command visitThisDayOfWeek(UserCommandParser.ThisDayOfWeekContext ctx) {
		//TODO
		return visitChildren(ctx);
	}

	@Override
	public Command visitNextDayOfWeek(UserCommandParser.NextDayOfWeekContext ctx) {
		//TODO
		return visitChildren(ctx);
	}

	@Override
	public Command visitFullDate(UserCommandParser.FullDateContext ctx) {
		int day = Integer.parseInt(ctx.INT(0).getText());
		int month = Integer.parseInt(ctx.INT(1).getText());
		int year = Integer.parseInt(ctx.INT(2).getText());
		dateMap.put(ctx, LocalDate.of(year, month, day));
		return null;
	}

	@Override
	public Command visitDayMonth(UserCommandParser.DayMonthContext ctx) {
		int year = LocalDate.now().getYear();
		int day = Integer.parseInt(ctx.INT(0).getText());
		int month = Integer.parseInt(ctx.INT(1).getText());
		dateMap.put(ctx, LocalDate.of(year, month, day));
		return null;
	}
	
	@Override
	public Command visitTimeThenDate(UserCommandParser.TimeThenDateContext ctx) {
		visit(ctx.date());
		visit(ctx.time());
		dateTimeMap.put(ctx, LocalDateTime.of(dateMap.get(ctx.date()),
											  timeMap.get(ctx.time())));
		return null;
	}

	@Override
	public Command visitDateThenTime(UserCommandParser.DateThenTimeContext ctx) {
		visit(ctx.date());
		visit(ctx.time());
		dateTimeMap.put(ctx, LocalDateTime.of(dateMap.get(ctx.date()),
											  timeMap.get(ctx.time())));
		return null;
	}

    @Override
    public Command visitHourNoon(UserCommandParser.HourNoonContext ctx) {
        int hour = Integer.parseInt(ctx.INT().getText());
        hour = process12Hour(hour, ctx.AM() == null);
        timeMap.put(ctx, LocalTime.of(hour, 0));        
        return null;
    }
    
    @Override
    public Command visitHourMinuteNoon(UserCommandParser.HourMinuteNoonContext ctx) {
        int hour = Integer.parseInt(ctx.INT(0).getText());
        hour = process12Hour(hour, ctx.AM() == null);
        int minute = Integer.parseInt(ctx.INT(1).getText());
        timeMap.put(ctx, LocalTime.of(hour, minute));        
        return null;
    }
    
    private int process12Hour(int hour, boolean isPm) {
        if ((hour > 12) || (hour <= 0)) {
            throw new DateTimeException(
                    "Invalid time, please use correct 12-hour format: " + hour);
        }
        int offset = isPm ? 12 : 0;
        hour += offset;
        if (hour == 12) {   // those are corner cases
            // 12am
            hour = 0;
        } else if (hour == 24) {
            // 12pm
            hour = 12;
        }
        return hour;
    }

	@Override
	public Command visitFullDateWordMonth(
	        UserCommandParser.FullDateWordMonthContext ctx) {
		int day = Integer.parseInt(ctx.INT(0).getText());
		int month = getMonth(ctx.MONTH());
		int year = Integer.parseInt(ctx.INT(1).getText());
		dateMap.put(ctx, LocalDate.of(year, month, day));
		return null;
    }

	@Override
	public Command visitDayMonthWordMonth(
	        UserCommandParser.DayMonthWordMonthContext ctx) {
		int year = LocalDate.now().getYear();
		int month = getMonth(ctx.MONTH());
		int day = Integer.parseInt(ctx.INT().getText());
		dateMap.put(ctx, LocalDate.of(year, month, day));
		return null;
    }

	@Override
	public Command visitFullDateWordMonthMonthFirst(
	        UserCommandParser.FullDateWordMonthMonthFirstContext ctx) {
		int day = Integer.parseInt(ctx.INT(0).getText());
		int month = getMonth(ctx.MONTH());
		int year = Integer.parseInt(ctx.INT(1).getText());
		dateMap.put(ctx, LocalDate.of(year, month, day));
		return null;
    }

	@Override
	public Command visitDayMonthWordMonthMonthFirst(
	        UserCommandParser.DayMonthWordMonthMonthFirstContext ctx) {
		int year = LocalDate.now().getYear();
		int month = getMonth(ctx.MONTH());
		int day = Integer.parseInt(ctx.INT().getText());
		dateMap.put(ctx, LocalDate.of(year, month, day));
		return null;
    }
	
	private int getMonth(TerminalNode terminalNode) {
		String month = terminalNode.getText().substring(0, 3).toLowerCase();
		String[] months = {"", "jan", "feb", "mar", "apr", "may", "jun", "jul",
		        "aug", "sep", "oct", "nov", "dec"};
		for (int i=0; i<months.length; i++) {
			if (months[i].equals(month)) {
				return i;
			}
		}
		assert false; // shouldn't happen
		return 0;
	}

	@Override
	public Command visitAddEventCommonDate(
	        UserCommandParser.AddEventCommonDateContext ctx) {
		visit(ctx.task());
		visit(ctx.date());
		visit(ctx.time(0));
		visit(ctx.time(1));
		LocalDate date = dateMap.get(ctx.date());
		return new AddEventCommand(stringMap.get(ctx.task()),
								  LocalDateTime.of(date, timeMap.get(ctx.time(0))),
								  LocalDateTime.of(date, timeMap.get(ctx.time(1))));
    }

	@Override
	public Command visitAddEvent(UserCommandParser.AddEventContext ctx) {
		visit(ctx.task());
		visit(ctx.datetime(0));
		visit(ctx.datetime(1));
		return new AddEventCommand(stringMap.get(ctx.task()),
								  dateTimeMap.get(ctx.datetime(0)),
								  dateTimeMap.get(ctx.datetime(1)));
    }
	
	@Override
    public Command visitDelCmd(UserCommandParser.DelCmdContext ctx) {
        return new DeleteCommand(Integer.parseInt(ctx.INT().getText()));
    }

	@Override
	public Command visitMarkDoneCmd(UserCommandParser.MarkDoneCmdContext ctx) {
        return new MarkDoneCommand(Integer.parseInt(ctx.INT().getText()));
    }
}
