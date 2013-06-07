package ircclient;

/**
 *
 * @author Lars Steen
 */
public class Message {
    private String prefix;
    private String command;
    private String[] parameters;
	
    public Message(String prefix, String command, String[] parameters) {
    	this.prefix = prefix;
	this.command = command;
	this.parameters = parameters;
    }

    public String getPrefix() {
	return prefix;
}

    public void setPrefix(String prefix) {
   	this.prefix = prefix;
    }

    public String getCommand() {
	return command;
    }

    public void setCommand(String command) {
	this.command = command;
    }

    public String[] getParameters() {
    	return parameters;
    }

    public void setParameters(String[] parameters) {
    	this.parameters = parameters;
    }
}

