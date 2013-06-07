package ircclient;

/**
 *
 * @author Lars Steen
 */
public class Message {
    private String prefix;
    private String command;
    private String[] parameters;
    private String msg;
	
    public Message(String prefix, String command, String[] parameters, String msg) {
    	this.prefix = prefix;
	this.command = command;
	this.parameters = parameters;
        this.msg = msg;
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
    
    public void setMsg(String msg)
    {
        this.msg = msg;
    }
    
    public String getMsg()
    {
        return msg;
    }
}

