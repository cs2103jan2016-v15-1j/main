package cs2103.v15_1j.jimjim;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import cs2103.v15_1j.jimjim.model.Task;
import cs2103.v15_1j.jimjim.model.TaskEvent;

public class AddTaskCommandTest {
    
    ArrayList<TaskEvent> displayList;
    StubStorage storage;

    @Before
    public void setUp() throws Exception {
        this.displayList = new ArrayList<>();
        this.storage = new StubStorage();
    }

    @Test
    public void testExecute() {
        AddTaskCommand command =
                new AddTaskCommand("Buy oranges",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        String result = command.execute(displayList, storage, null);
        assertEquals("Task added", result);
        assertEquals(1, displayList.size());
        assertEquals("Buy oranges", displayList.get(0).getName());
        assertEquals(LocalDateTime.of(2016, 4, 30, 12, 00),
                ((Task)displayList.get(0)).getDateTime());
    }
    
    @Test
    public void testStorageError() {
        AddTaskCommand command =
                new AddTaskCommand("Storage error",
                                   LocalDateTime.of(2016, 4, 30, 12, 00));
        String result = command.execute(displayList, storage, null);
        assertEquals("Some error has occured. Please try again.", result);
        assertEquals(true, displayList.isEmpty());
    }

}

class StubStorage implements Storage {

    @Override
    public List<TaskEvent> getAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean create(TaskEvent taskEvent) {
        Task task = (Task) taskEvent;
        if (task.getName().equals("Storage error")) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public TaskEvent read(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean update(int id, TaskEvent taskEvent) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean delete(int id) {
        // TODO Auto-generated method stub
        return false;
    }
    
}