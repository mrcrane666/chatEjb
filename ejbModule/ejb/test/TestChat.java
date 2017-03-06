package ejb.test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.AbstractMap.SimpleEntry;
import java.util.LinkedList;

import org.junit.Test;

public class TestChat {

	/**
	 * Tests the chechCommand() method for all possible emoticons and if they
	 * are translated correctly
	 */
	@Test
	public void testCheckCommand() {
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
		dummy = new SimpleEntry<String, String>("emoti", "o/ \\o");
		assertTrue("checkForCommand() failed at /highfive",
				dummy.equals(result));
	}

	/**
	 * Tests if the addMessage() method adds the Messages to the list without
	 * messing anything up.
	 */
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

	/**
	 * Tests checkForCommands() in case of private Messages with, without, and
	 * with mutiple emoticons
	 */
	@Test
	public void testPrivateChat() {
		ChatInterface chat = new ChatBean();
		// Test without emotis
		String dummyTarget = "target";
		String dummyMessage1 = "/tell " + dummyTarget + " haaaaaaaaaaallloooo";

		SimpleEntry<String, String> dummyResult = new SimpleEntry<String, String>(
				dummyTarget, "haaaaaaaaaaallloooo");

		SimpleEntry<String, String> result = chat
				.checkForCommands(dummyMessage1);

		assertTrue("The tragetUsername was not the same!", dummyResult.getKey()
				.equals(result.getKey()));
		assertTrue("The message was not the same! => " + dummyResult.getValue()
				+ " != " + result.getValue(),
				dummyResult.getValue().equals(result.getValue()));

		// Test with one emoti
		dummyMessage1 = "/tell " + dummyTarget + " /cat";
		dummyResult = new SimpleEntry<String, String>(dummyTarget, "=^_^=");
		result = chat.checkForCommands(dummyMessage1);
		assertTrue("The tragetUsername was not the same!", dummyResult.getKey()
				.equals(result.getKey()));
		assertTrue("The message was not the same! => " + dummyResult.getValue()
				+ " != " + result.getValue(),
				dummyResult.getValue().equals(result.getValue()));

		// Test with two emoti
		dummyMessage1 = "/tell " + dummyTarget + " /cat /cat";
		dummyResult = new SimpleEntry<String, String>(dummyTarget,
				"=^_^= =^_^=");
		result = chat.checkForCommands(dummyMessage1);
		assertTrue("The tragetUsername was not the same!", dummyResult.getKey()
				.equals(result.getKey()));
		assertTrue("The message was not the same! => " + dummyResult.getValue()
				+ " != " + result.getValue(),
				dummyResult.getValue().equals(result.getValue()));

	}

	/**
	 * Trys to cause an error Message with wrong commands
	 */
	@Test
	public void checkWrongCommands() {
		ChatInterface chat = new ChatBean();
		String dummyUser = "user";
		String dummyTime = "time";
		String fakeCommand = "";

		// A wrong command
		fakeCommand = "/im_not_command";
		chat.addMessage(dummyUser, dummyTime, fakeCommand);
		assertTrue("Error message was not set!",
				!chat.getErrMessage().equals(""));

		// A wrong /tell command like /tell123
		fakeCommand = "/tell123";
		chat.addMessage(dummyUser, dummyTime, fakeCommand);
		assertTrue("Error message was not set!",
				!chat.getErrMessage().equals(""));

	}
}
