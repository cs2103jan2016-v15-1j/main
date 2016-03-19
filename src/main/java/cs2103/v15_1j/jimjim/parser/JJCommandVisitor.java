package cs2103.v15_1j.jimjim.parser;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.antlr.v4.runtime.tree.TerminalNode;

import cs2103.v15_1j.jimjim.antlr4.UserCommandBaseVisitor;
import cs2103.v15_1j.jimjim.antlr4.UserCommandParser;
import cs2103.v15_1j.jimjim.command.*;
import cs2103.v15_1j.jimjim.searcher.*;

public class JJCommandVisitor extends UserCommandBaseVisitor<Command> {
	
	private LocalDateTime dateTime;
	private String string;
	private String userCommand;
	private ArrayList<Filter> filters;
	private ArrayList<String> keywords;
	
	public JJCommandVisitor(String userCommand) {
		this.userCommand = userCommand;
	}
	
	//----------TYPES OF COMMAND-------------

	@Override
	public Command visitAddFloatingTask(
			UserCommandParser.AddFloatingTaskContext ctx) {
		visit(ctx.string());
		return new AddCommand(string);
	}

	@Override
	public Command visitAddTask(UserCommandParser.AddTaskContext ctx) {
		visit(ctx.string());
		visit(ctx.datetime());
		return new AddCommand(string, dateTime);
	}

	@Override
	public Command visitAddEvent(UserCommandParser.AddEventContext ctx) {
		visit(ctx.string());
		dateTime = LocalDateTime.MIN;
		visit(ctx.datetime(0));
		LocalDateTime start = dateTime;
		dateTime = LocalDateTime.MIN;
		visit(ctx.datetime(1));
		LocalDateTime end = dateTime;
		return new AddCommand(string, start, end);
    }

	@Override
	public Command visitAddEventCommonDate(
	        UserCommandParser.AddEventCommonDateContext ctx) {
		visit(ctx.string());
		dateTime = LocalDateTime.MIN;
		visit(ctx.date());
		LocalDate date = dateTime.toLocalDate();
		visit(ctx.time(0));
		LocalDateTime start = dateTime;
		dateTime = date.atStartOfDay();
		visit(ctx.time(1));
		LocalDateTime end = dateTime;
		return new AddCommand(string, start, end);
    }

	@Override
    public Command visitDelCmd(UserCommandParser.DelCmdContext ctx) {
	    String itemNum = ctx.ITEM_NUM().getText().toLowerCase();
	    System.out.println(itemNum.charAt(0));
        return new DeleteCommand(itemNum.charAt(0),
                Integer.parseInt(itemNum.substring(1)));
    }

	@Override
	public Command visitMarkDoneCmd(UserCommandParser.MarkDoneCmdContext ctx) {
	    String itemNum = ctx.ITEM_NUM().getText().toLowerCase();
	    if (itemNum.charAt(0) == 'e') {
	        return new InvalidCommand(itemNum + " is not a valid task!");
	    } else {
	        return new MarkDoneCommand(itemNum.charAt(0),
                Integer.parseInt(itemNum.substring(1)));
	    }
    }
	
	@Override
	public Command visitSearchCmd(UserCommandParser.SearchCmdContext ctx) {
	    filters = new ArrayList<>();
	    keywords = new ArrayList<>();
	    visitChildren(ctx);
	    // combine all keyword filters into 1 for efficiency
	    if (keywords.size() > 0) {
	        filters.add(new KeywordFilter(keywords));
	    }
	    return new SearchCommand(filters);
	}
	
	//----------------STRING-----------------
	
	@Override
	public Command visitString(UserCommandParser.StringContext ctx) { 
		string = userCommand.substring(ctx.getStart().getStartIndex(),
				                       ctx.getStop().getStopIndex()+1);
		return null;
	}

	//----------------TYPES OF DATETIME----------------- 

	@Override
	public Command visitTimeOnly(UserCommandParser.TimeOnlyContext ctx) {
		dateTime = LocalDate.now().atStartOfDay();
		visit(ctx.time());
		return null;
	}

	@Override
	public Command visitDateOnly(UserCommandParser.DateOnlyContext ctx) {
	    dateTime = LocalDate.MIN.atTime(23, 59);
		visit(ctx.date());
		return null;
	}
	
	@Override
	public Command visitTimeThenDate(UserCommandParser.TimeThenDateContext ctx) {
		dateTime = LocalDateTime.MIN;
		visit(ctx.date());
		visit(ctx.time());
		return null;
	}
	
	@Override
	public Command visitDateThenTime(UserCommandParser.DateThenTimeContext ctx) {
		dateTime = LocalDateTime.MIN;
		visit(ctx.date());
		visit(ctx.time());
		return null;
	}

	//----------------TYPES OF DATE---------------------
	
	@Override
	public Command visitToday(UserCommandParser.TodayContext ctx) {
	    dateTime = dateTime.with(LocalDate.now());
		return null;
	}

	@Override
	public Command visitTomorrow(UserCommandParser.TomorrowContext ctx) {
	    dateTime = dateTime.with(LocalDate.now()).plusDays(1);
		return null;
	}

	@Override
	public Command visitDayOfWeekOnly(UserCommandParser.DayOfWeekOnlyContext ctx) {
		int dayInt = getDayOfWeekInt(ctx);
		DayOfWeek.of(dayInt);	// check that dayInt is valid
		LocalDate today = LocalDate.now();
		int todayInt = today.getDayOfWeek().getValue();
		if (todayInt < dayInt) {
			// referring to this week
		    dateTime = dateTime.with(today.plusDays(dayInt - todayInt));
		} else {
			// referring to next week
		    dateTime = dateTime.with(today.plusDays(dayInt - todayInt + 7));
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
		dateTime = dateTime.with(LocalDate.of(year, month, day));
		return null;
	}

	@Override
	public Command visitDayMonth(UserCommandParser.DayMonthContext ctx) {
		int year = LocalDate.now().getYear();
		int day = Integer.parseInt(ctx.INT(0).getText());
		int month = Integer.parseInt(ctx.INT(1).getText());
		dateTime = dateTime.with(LocalDate.of(year, month, day));
		return null;
	}

	@Override
	public Command visitFullDateWordMonth(
	        UserCommandParser.FullDateWordMonthContext ctx) {
		int day = Integer.parseInt(ctx.INT(0).getText());
		int month = getMonth(ctx.MONTH());
		int year = Integer.parseInt(ctx.INT(1).getText());
		dateTime = dateTime.with(LocalDate.of(year, month, day));
		return null;
    }

	@Override
	public Command visitDayMonthWordMonth(
	        UserCommandParser.DayMonthWordMonthContext ctx) {
		int year = LocalDate.now().getYear();
		int month = getMonth(ctx.MONTH());
		int day = Integer.parseInt(ctx.INT().getText());
		dateTime = dateTime.with(LocalDate.of(year, month, day));
		return null;
    }

	@Override
	public Command visitFullDateWordMonthMonthFirst(
	        UserCommandParser.FullDateWordMonthMonthFirstContext ctx) {
		int day = Integer.parseInt(ctx.INT(0).getText());
		int month = getMonth(ctx.MONTH());
		int year = Integer.parseInt(ctx.INT(1).getText());
		dateTime = dateTime.with(LocalDate.of(year, month, day));
		return null;
    }

	@Override
	public Command visitDayMonthWordMonthMonthFirst(
	        UserCommandParser.DayMonthWordMonthMonthFirstContext ctx) {
		int year = LocalDate.now().getYear();
		int month = getMonth(ctx.MONTH());
		int day = Integer.parseInt(ctx.INT().getText());
		dateTime = dateTime.with(LocalDate.of(year, month, day));
		return null;
    }
	
	// date helper functions
	
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

	//----------------TYPES OF TIME------------------------

	@Override
	public Command visitHourOnly(UserCommandParser.HourOnlyContext ctx) {
		int hour = Integer.parseInt(ctx.INT().getText());
		dateTime = dateTime.with(LocalTime.of(hour, 0));
		return null;
	}
	
	@Override
	public Command visitHourMinute(UserCommandParser.HourMinuteContext ctx) {
		int hour = Integer.parseInt(ctx.INT(0).getText());
		int minute = Integer.parseInt(ctx.INT(1).getText());
		dateTime = dateTime.with(LocalTime.of(hour, minute));
		return null;
	}
    @Override
    public Command visitHourNoon(UserCommandParser.HourNoonContext ctx) {
        int hour = Integer.parseInt(ctx.INT().getText());
        hour = process12Hour(hour, ctx.AM() == null);
		dateTime = dateTime.with(LocalTime.of(hour, 0));
        return null;
    }
    
    @Override
    public Command visitHourMinuteNoon(UserCommandParser.HourMinuteNoonContext ctx) {
        int hour = Integer.parseInt(ctx.INT(0).getText());
        hour = process12Hour(hour, ctx.AM() == null);
        int minute = Integer.parseInt(ctx.INT(1).getText());
		dateTime = dateTime.with(LocalTime.of(hour, minute));
        return null;
    }
    
    // time helper functions
    
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
    
    //--------------TYPES OF FILTER-----------------
    
    @Override
    public Command visitKeywordFilter(UserCommandParser.KeywordFilterContext ctx) {
        visit(ctx.string());
        keywords.add(string);
        return null;
    }
	
}
