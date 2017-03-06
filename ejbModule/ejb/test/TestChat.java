package ejb.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

import org.junit.Test;

public class TestChat {

	@Test
	public void testCheckCommandClass() {
		ChatInterface chat = new ChatBean();

		SimpleEntry<String, String> result = chat.checkForCommands("/cat");
		SimpleEntry<String, String> dummy = new SimpleEntry<String, String>(
				"emoti", "=^_^=");
		assertTrue("checkForCommand() failed at /cat", dummy.equals(result));

		result = chat.checkForCommands("/penguin");
		dummy = new SimpleEntry<String, String>("emoti", "<(\")");
		assertTrue("checkForCommand() failed at /penguin", dummy.equals(result));

		result = chat.checkForCommands("/coffee");
		dummy = new SimpleEntry<String, String>("emoti", "c(_)");
		assertTrue("checkForCommand() failed at /coffee", dummy.equals(result));

		result = chat.checkForCommands("/rose");
		dummy = new SimpleEntry<String, String>("emoti", "@>--}---");
		assertTrue("checkForCommand() failed at /rose", dummy.equals(result));

		result = chat.checkForCommands("/highfive");
		dummy =new SimpleEntry<String, String>("emoti", "o/ \\o"); 
		assertTrue("checkForCommand() failed at /highfive", dummy.equals(result));

		result = chat
				.checkForCommands("/tell wasuser testetstestetstteststetestestes");
		dummy = new SimpleEntry<String, String>("wasuser", "testetstestetstteststetestestes");
		assertTrue("checkForCommand() failed at /tell", dummy.equals(result));
	}

	@Test
	public void testAddMessages() {
		ChatInterface chat = new ChatBean();
		LinkedList<String> dummyList = new LinkedList<String>();
		String dummyUser1 = "user";
		String dummyTime1 = "time";
		String dummyMessage1 = "Im a Message!";

		String dummyUser2 = "user2";
		String dummyTime2 = "time2";
		String dummyMessage2 = "/cat";

		dummyList.add(dummyUser1 + " - " + dummyTime1 + ": " + dummyMessage1);
		dummyList.add(dummyUser2 + " - " + dummyTime2 + ": " + "=^_^=");

		chat.addMessage(dummyUser1, dummyTime1, dummyMessage1);
		chat.addMessage(dummyUser2, dummyTime2, dummyMessage2);

		for (int i = 0; i < dummyList.size(); i++) {
			assertTrue("Add Messages fails at: " + dummyList.get(i), dummyList
					.get(i).equals(chat.getMessages().get(i)));
		}
	}

	@Test
	public void testErrMessade() {
		ChatInterface chat = new ChatBean();
		LinkedList<String> dummyList = new LinkedList<String>();
		String dummyUser1 = "user";
		String dummyTime1 = "time";
		String dummyMessage1 = "/ImNotA_Command";
		dummyList.add(dummyUser1 + " - " + dummyTime1 + ": " + dummyMessage1);
		chat.addMessage(dummyUser1, dummyTime1, dummyMessage1);
		assertTrue("ErrMessage was not set!", !chat.getErrMessage().equals(""));
	}
}
