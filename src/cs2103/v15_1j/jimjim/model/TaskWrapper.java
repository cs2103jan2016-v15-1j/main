package cs2103.v15_1j.jimjim.model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import javafx.collections.FXCollections;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 */
@XmlRootElement(name = "tasks")
public class TaskWrapper {

    private List<Task> tasks = FXCollections.<Task>observableArrayList();

    @XmlElement(name = "task")
    public List<Task> getTasks() {
        return tasks;
    }

    public void setPersons(List<Task> tasks) {
        this.tasks = tasks;
    }
}