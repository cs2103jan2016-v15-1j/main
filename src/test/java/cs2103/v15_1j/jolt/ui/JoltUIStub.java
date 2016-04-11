package cs2103.v15_1j.jolt.ui;

import cs2103.v15_1j.jolt.model.DataLists;
import cs2103.v15_1j.jolt.model.FloatingTask;
import cs2103.v15_1j.jolt.uifeedback.AddFeedback;
import cs2103.v15_1j.jolt.uifeedback.UIFeedback;

public class JoltUIStub extends JoltUI {

	private DataLists masterList;
	private MainViewController con;
	
	public JoltUIStub(DataLists masterList) {
		super();
		this.masterList = masterList;
	}
	
	public void setMainViewController(MainViewController con){
		this.con = con;
	}

	public void executeCommand(String input){
		if(input.equals("Do Homework")){
			FloatingTask temp = new FloatingTask("Do Homework");
			masterList.add(temp);
			UIFeedback fb = new AddFeedback(temp);
			fb.execute(con);
		}
		
		con.refreshData();
	}
}
