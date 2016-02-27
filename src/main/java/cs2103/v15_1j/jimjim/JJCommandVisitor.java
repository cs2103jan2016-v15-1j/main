package cs2103.v15_1j.jimjim;

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
	public Command visitTime(UserCommandParser.TimeContext ctx) {
		int hour = Integer.parseInt(ctx.INT().getText());
		timeMap.put(ctx, LocalTime.of(hour, 0));
		return null;
	}

}
