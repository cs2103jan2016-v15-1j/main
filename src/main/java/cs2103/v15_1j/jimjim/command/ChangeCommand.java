package cs2103.v15_1j.jimjim.command;

import java.time.LocalDate;
import java.time.LocalTime;

import cs2103.v15_1j.jimjim.model.DataLists;
import cs2103.v15_1j.jimjim.searcher.Searcher;
import cs2103.v15_1j.jimjim.storage.Storage;
import cs2103.v15_1j.jimjim.uifeedback.UIFeedback;

public class ChangeCommand implements Command {

    private int taskNum;
    private char prefix;
    private String newName;
    private LocalDate newDate;
    private LocalTime newTime;
    private LocalDate newEndDate;
    private LocalTime newEndTime;
    
    public ChangeCommand(char prefix, int num, String newName, LocalDate newDate,
            LocalTime newTime, LocalDate newEndDate, LocalTime newEndTime) {
        this.taskNum = num;
        this.prefix = prefix;
        this.newName = newName;
        this.newDate = newDate;
        this.newTime = newTime;
        this.newEndDate = newEndDate;
        this.newEndTime = newEndTime;
    }
    
    public int getTaskNum() {
        return this.taskNum;
    }
    
    public char getPrefix() {
        return this.prefix;
    }
    
    public String getNewName() {
        return newName;
    }
    
    public LocalDate getNewDate() {
        return newDate;
    }
    
    public LocalTime getNewTime() {
        return newTime;
    }
    
    public LocalDate getNewEndDate() {
        return newEndDate;
    }
    
    public LocalTime getNewEndTime() {
        return newEndTime;
    }

    @Override
    public UIFeedback undo(DataLists searchResultsList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public UIFeedback execute(DataLists searchResultsList, DataLists masterList, Storage storage, Searcher searcher) {
        // TODO Auto-generated method stub
        return null;
    }

}
