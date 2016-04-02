package cs2103.v15_1j.jimjim.command;

import java.time.LocalDate;
import java.time.LocalTime;

import cs2103.v15_1j.jimjim.controller.ControllerStates;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class ShiftCommand implements UndoableCommand {
    
    private int taskNum;
    private char prefix;
    private LocalDate newDate;
    private LocalTime newTime;
    
    public ShiftCommand(char prefix, int taskNum, LocalDate date, LocalTime time) {
        this.prefix = prefix;
        this.taskNum = taskNum;
        this.newDate = date;
        this.newTime = time;
    }
    
    public int getTaskNum() {
        return taskNum;
    }
    
    public char getPrefix() {
        return prefix;
    }
    
    public LocalDate getNewDate() {
        return newDate;
    }
    
    public LocalTime getNewTime() {
        return newTime;
    }
    
    @Override
    public UIFeedback undo(ControllerStates conStates) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UIFeedback execute(ControllerStates conStates) {
        // TODO Auto-generated method stub
        return null;
    }

}
