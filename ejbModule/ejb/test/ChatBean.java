package ejb.test;

import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import com.ibm.ws.runtime.provisioning.commands;

@Remote(ChatInterface.class)
@Stateless
@DenyAll
public class ChatBean implements ChatInterface {

	// Have to change this if longer commands be added!
	private final int MAX_LENGTH_COMMANDS = 5;
	private ArrayList<String> messages;
	private HashMap<String, String> emoticons;
	private ArrayList<String> commands;
	private String errMessage;

	public ChatBean() {
		commands = new ArrayList<String>();
		commands.add("/tell");
		errMessage = "";
		messages = new ArrayList<String>();
		emoticons = new HashMap<String, String>();
		emoticons.put("/cat", "=^_^=");
		emoticons.put("/penguin", "<(\")");
		emoticons.put("/coffee", "c(_)");
		emoticons.put("/rose", "@>--}---");
		emoticons.put("/highfive", "o/ \\o");
	}

	@PermitAll
	@Override
	public void addMessage(String username, String time, String pMessage) {
		SimpleEntry<String, String> messageChecked = checkForCommands(pMessage);
		if (messageChecked != null) {
			if (!messageChecked.getKey().equals("message")) {
				if (messageChecked.getKey().equals("emoti")) {
					messages.add(username + " - " + time + ": "
							+ messageChecked.getValue());

				} else {
					messages.add("/" + messageChecked.getKey() + "&" + username
							+ " - " + time + ": " + messageChecked.getValue());
				}
			} else {
				messages.add(username + " - " + time + ": " + pMessage);
			}
		} else {

			errMessage = "No such a Command!";

		}

	}

	@PermitAll
	@Override
	public ArrayList<String> getMessages() {

		return messages;
	}

	@PermitAll
	@Override
	public String getErrMessage() {
		String tmp = errMessage;
		errMessage = "";
		return tmp;

	}

	@PermitAll
	@Override
	public void resetMessages() {
		messages.clear();
	}

	@Override
	public SimpleEntry<String, String> checkForCommands(String message) {
		if (message.startsWith("/")) {
			if (message.startsWith("/tell ")) {
				String tmp = message.substring(0, MAX_LENGTH_COMMANDS);
				for (String c : commands) {
					if (c.equals(tmp)) {
						// if its a private message
						return processCommand(message, c);
					}
				}
			}

			String emoti = checkForEmoticons(message);
			if (null != emoti) {
				// if its a emoti
				return new SimpleEntry<String, String>("emoti", emoti);
			}

			// If there is not such a command
			System.err.println("err");
			return null;

		} else {
			// if its just a message
			return new SimpleEntry<String, String>("message", message);
		}

	}

	/**
	 * Returns the input message with all emoties replaced with the acsii ones.
	 * 
	 * @return null in case of no emoties, else message with emoties replaced
	 */
	@Override
	public String checkForEmoticons(String command) {
		Set<String> emos = emoticons.keySet();
		String convertedMessage = command;
		for (int i = 0; i < emoticons.size(); i++) {
			if (convertedMessage.contains(emos.toArray()[i].toString())) {
				convertedMessage = convertedMessage.replace(
						emos.toArray()[i].toString(),
						emoticons.get(emos.toArray()[i].toString()));
			}
		}
		if (convertedMessage.equals(command)) {
			return null;
		} else {
			return convertedMessage;
		}

	}

	/**
	 * Returns a SimpleEntry with the the tragetuseranem and the message and it
	 * checks for emoticons in the message!
	 * 
	 * @param message
	 *            message with all commands
	 * @param command
	 *            command which will be processed
	 * @return SimpleEntry(targetUser, message)
	 */
	private SimpleEntry<String, String> processCommand(String message,
			String command) {
		if (command.equals("/tell")) {
			String pureMessageWithUsername = message.substring(6,
					message.length());
			String username = pureMessageWithUsername.substring(0,
					pureMessageWithUsername.indexOf(" "));
			String pureMessage = pureMessageWithUsername.substring(
					pureMessageWithUsername.indexOf(" ") + 1,
					pureMessageWithUsername.length());
			SimpleEntry<String, String> entry = new SimpleEntry<String, String>(
					username, pureMessage);
			String checkedPureMessage = checkForEmoticons(pureMessage);
			if (checkedPureMessage != null) {
				entry.setValue(checkedPureMessage);
			}

			return entry;
		}
		return null;
	}

}
