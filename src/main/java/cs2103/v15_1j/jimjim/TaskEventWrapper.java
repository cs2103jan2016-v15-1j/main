package cs2103.v15_1j.jimjim;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import cs2103.v15_1j.jimjim.model.Task;

/**
 * Helper class to wrap a list of persons. This is used for saving the
 * list of persons to XML.
 */
@XmlRootElement(name = "taskEvents")
public class TaskEventWrapper {
	private List<TaskEvent> list;
	
    @XmlElement(name = "taskEvent")
    public List<TaskEvent> getTaskEvents() {
        return list;
    }

    public void setTaskEvents(List<TaskEvent> list) {
        this.list = list;
    }
}
