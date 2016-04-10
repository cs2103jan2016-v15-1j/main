package cs2103.v15_1j.jolt.uifeedback;

import static org.junit.Assert.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import cs2103.v15_1j.jolt.model.DeadlineTask;
import cs2103.v15_1j.jolt.model.Event;
import cs2103.v15_1j.jolt.model.FloatingTask;

/* @@author A0139963N */
public class UIFeedbackTest {

	Event event;
	Event eventClashing;
	FloatingTask fTask;
	DeadlineTask dTask;
	MainViewControllerStub con;

	@Before
	public void setUp() throws Exception {
		event = new Event("Meeting", LocalDateTime.of(2016, 4, 30, 12, 00), LocalDateTime.of(2016, 4, 30, 13, 00));
		eventClashing = new Event("Meeting Again", LocalDateTime.of(2016, 4, 30, 12, 30),
				LocalDateTime.of(2016, 4, 30, 13, 00));
		fTask = new FloatingTask("Get milk");
		dTask = new DeadlineTask("Buy oranges", LocalDateTime.of(2016, 4, 30, 12, 00));
		con = new MainViewControllerStub();
	}

	@Test
	public void addFeedbackTest() {
		UIFeedback fbE = new AddFeedback(event);
		UIFeedback fbE2 = new AddFeedback(event);
		UIFeedback fbClash = new AddFeedback(eventClashing);
		UIFeedback fbF = new AddFeedback(fTask);
		UIFeedback fbD = new AddFeedback(dTask);

		fbE.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"" + event.getName() + "\" has been added."));

		fbClash.execute(con);
		assertThat(con.getNotification(),
				IsEqual.equalTo("\"" + eventClashing.getName() + "\" clashes with another event!"));

		fbF.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"" + fTask.getName() + "\" has been added."));

		fbD.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"" + dTask.getName() + "\" has been added."));

		assertThat(fbE.equals(fbE2), IsEqual.equalTo(true));
		assertThat(fbE.equals(fbF), IsEqual.equalTo(false));
	}

	@Test
	public void aliasAddFeedbackTest() {

		UIFeedback fb = new AliasAddFeedback("ch");
		UIFeedback fb2 = new AliasAddFeedback("del", "delete");
		UIFeedback fb3 = new AliasAddFeedback("delAgain", "delete");
		UIFeedback fb4 = new AliasAddFeedback("del", "deleteAgain");

		fb.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"ch\" has been added as an alias."));

		fb2.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"del\" has been added as an alias for delete."));

		assertThat(fb2.equals(fb3), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
		assertThat(fb2.equals(fb4), IsEqual.equalTo(false));
	}

	@Test
	public void aliasDeleteFeedbackTest() {

		UIFeedback fb = new AliasDeleteFeedback("ch");
		UIFeedback fb2 = new AliasDeleteFeedback("ch");
		UIFeedback fb3 = new AliasDeleteFeedback("ch3");

		fb.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"ch\" has been deleted as an alias."));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}

	@Test
	public void aliasListFeedbackTest() {

		Map<String, String> aliasList = new HashMap<>();
		aliasList.put("del", "delete");
		aliasList.put("ad", "add");

		UIFeedback fb = new AliasListFeedback(aliasList);

		Map<String, String> aliasList2 = new HashMap<>();
		aliasList2.put("del", "delete");
		aliasList2.put("ad", "add");

		UIFeedback fb2 = new AliasListFeedback(aliasList2);

		Map<String, String> aliasList3 = new HashMap<>();
		aliasList3.put("del", "delete");

		UIFeedback fb3 = new AliasListFeedback(aliasList3);

		fb.execute(con);

		ArrayList<String> aliasListString = new ArrayList<String>();
		aliasListString.add("ad = add");
		aliasListString.add("del = delete");

		assertThat(con.getAliasList(), IsEqual.equalTo(aliasListString));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}

	@Test
	public void changeFeedbackTest() {
		UIFeedback fb = new ChangeFeedback("Event time has been changed");
		UIFeedback fb2 = new ChangeFeedback("Event time has been changed");
		UIFeedback fb3 = new ChangeFeedback("Event time has not been changed");

		fb.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("Event time has been changed"));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}

	@Test
	public void deleteFeedbackTest() {
		UIFeedback fb = new DeleteFeedback(event);
		UIFeedback fb2 = new DeleteFeedback(event);
		UIFeedback fb3 = new DeleteFeedback(dTask);

		fb.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"" + event.getName() + "\" has been deleted."));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}

	@Test
	public void failureFeedbackTest() {
		UIFeedback fb = new FailureFeedback("Command Failure");
		UIFeedback fb2 = new FailureFeedback("Command Failure");
		UIFeedback fb3 = new FailureFeedback("Command Disaster");

		fb.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("Command Failure"));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}

	@Test
	public void helpFeedbackTest() {
		UIFeedback fb = new HelpFeedback("common");
		UIFeedback fb2 = new HelpFeedback("common");
		UIFeedback fb3 = new HelpFeedback("add");

		fb.execute(con);
		assertThat(con.getPage(), IsEqual.equalTo("common"));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}

	@Test
	public void hideSearchFeedbackTest() {
		UIFeedback fb = new HideSearchFeedback();

		fb.execute(con);
		assertThat(con.getShowSearch(), IsEqual.equalTo(false));
	}

	@Test
	public void markFeedbackTest() {
		UIFeedback fb = new MarkFeedback(event);
		UIFeedback fb2 = new MarkFeedback(event);
		UIFeedback fb3 = new MarkFeedback(dTask);

		fb.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"" + event.getName() + "\" has been completed."));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}

	@Test
	public void saveLocationFeedbackTest() {
		UIFeedback fb = new SaveLocationFeedback("..\\Downloads\\save_file.json");
		UIFeedback fb2 = new SaveLocationFeedback("..\\Downloads\\save_file.json");
		UIFeedback fb3 = new SaveLocationFeedback("..\\Documents\\save_file.json");

		fb.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"..\\Downloads\\save_file.json\" has been set as the save path."));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}

	@Test
	public void shiftFeedbackTest() {
		LocalDateTime start = LocalDateTime.of(2016, 4, 30, 12, 00);
		LocalDateTime end = LocalDateTime.of(2016, 4, 30, 13, 00);

		UIFeedback fb = new ShiftFeedback("Get Milk", start, end);
		UIFeedback fb2 = new ShiftFeedback("Get Milk", start, end);
		UIFeedback fb3 = new ShiftFeedback("Get Milk", end, end);

		String output = "\"Get Milk\" is now from: " + start.toString() + " to " + end.toString();

		fb.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo(output));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}

	@Test
	public void showHideCompletedFeedbackTest() {
		UIFeedback fb = new ShowHideCompletedFeedback(true);
		UIFeedback fb2 = new ShowHideCompletedFeedback(false);

		fb.execute(con);
		assertThat(con.getShowCompleted(), IsEqual.equalTo(true));
		assertThat(con.getNotification(), IsEqual.equalTo("Showing Completed Task and Events."));

		fb2.execute(con);
		assertThat(con.getShowCompleted(), IsEqual.equalTo(false));
		assertThat(con.getNotification(), IsEqual.equalTo("Hiding Completed Task and Events."));
	}

	@Test
	public void showHideOverdueFeedbackTest() {
		UIFeedback fb = new ShowHideOverdueFeedback(true);
		UIFeedback fb2 = new ShowHideOverdueFeedback(false);

		fb.execute(con);
		assertThat(con.getShowOverdue(), IsEqual.equalTo(true));
		assertThat(con.getNotification(), IsEqual.equalTo("Showing Overdue Task and Events."));

		fb2.execute(con);
		assertThat(con.getShowOverdue(), IsEqual.equalTo(false));
		assertThat(con.getNotification(), IsEqual.equalTo("Hiding Overdue Task and Events."));
	}

	@Test
	public void unmarkFeedbackTest() {
		UIFeedback fb = new UnmarkFeedback(event);
		UIFeedback fb2 = new UnmarkFeedback(event);
		UIFeedback fb3 = new UnmarkFeedback(dTask);

		fb.execute(con);
		assertThat(con.getNotification(), IsEqual.equalTo("\"" + event.getName() + "\" has been marked as not completed."));

		assertThat(fb.equals(fb2), IsEqual.equalTo(true));
		assertThat(fb.equals(fb3), IsEqual.equalTo(false));
	}
}
