package cs2103.v15_1j.jimjim;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.antlr.v4.runtime.tree.ParseTreeProperty;

import cs2103.v15_1j.jimjim.antlr4.UserCommandBaseVisitor;
import cs2103.v15_1j.jimjim.antlr4.UserCommandParser;

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
		return new AddTaskCommand(stringMap.get(ctx.task()), null);
	}

	@Override
	public Command visitAddTask(UserCommandParser.AddTaskContext ctx) {
		visit(ctx.task());
		visit(ctx.datetime());
		return new AddTaskCommand(stringMap.get(ctx.task()),
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
	
	private int getDayOfWeekInt(UserCommandParser.DayOfWeekContext ctx) {
		String day = ctx.getText().substring(0, 3).toLowerCase();
		String[] days = {"", "mon", "tue", "wed", "thu", "fri", "sat", "sun"};
		for (int i=0; i<days.length; i++) {
			if (days[i].equals(day)) {
				return i;
			}
		}
		return 0;
	}

	@Override
	public Command visitDayOfWeekOnly(UserCommandParser.DayOfWeekOnlyContext ctx) {
		int dayInt = getDayOfWeekInt(ctx.dayOfWeek());
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
		return null;	}


}
