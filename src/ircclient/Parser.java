package ircclient;

import java.util.Arrays;

/**
 *
 * @author Lars Steen
 */
public class Parser {
    
    public static Message parse(String message)
    {
	String prefix = null;
	String command = new String();
	String trailing = null;
	String[] parameters = new String[0];

	int prefixEnd = -1;
	int trailingStart = message.length();
		
	if (message.startsWith(":"))
	{
		prefixEnd = message.indexOf(" ");
        	prefix = message.substring(1, prefixEnd);
	}
		
	trailingStart = message.indexOf(" :");
	if (trailingStart >= 0)
	{
		trailing = message.substring(trailingStart + 2);
	} 
	else 
	{
		trailingStart = message.length();
	}
	
	int start = prefixEnd + 1;
	int end = trailingStart;
	String[] commandAndParameters = message.substring(start, end).split(" ");
	command = commandAndParameters[0];
		
	if(commandAndParameters.length > 1)
	{
		parameters = Arrays.copyOfRange(commandAndParameters, 1, commandAndParameters.length, String[].class);
	}
		
	if (trailing != null)
	{
		String[] tmp = new String[parameters.length + 1];
		for (int i = 0; i < parameters.length; i++) {
			tmp[i] = parameters[i];
		}
		tmp[tmp.length - 1] = trailing;
		parameters = tmp;
	}
		
	Message m = new Message(prefix, command, parameters);
	if (prefix != null)
	{
		m.setNick(prefix.substring(0, prefix.indexOf("!")));
	}
		
	return m;
    }
}