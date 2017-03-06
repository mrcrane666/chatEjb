package ejb.test;

import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

public interface ChatInterface {

	public void addMessage(String pMessage, String username, String time);

	public ArrayList<String> getMessages();
	
	public void resetMessages();
	
	public SimpleEntry<String, String> checkForCommands(String pCommands);
	
	public String checkForEmoticons(String command);
	
	public String getErrMessage();
	
}
