import java.util.Hashtable;

class CommandParser extends LineParser
{	
	private static Hashtable<String, CommandFunc> FuncTable;

	CommandParser()
	{
		FuncTable = new Hashtable<String, CommandFunc>();
		CommandFunc cmake, cput, cremove;
		cmake = new CommandMake();
		cput = new CommandPut();
		cremove = new CommandRemove();
		
		FuncTable.put("make", cmake);
		FuncTable.put("insert", cmake);
		FuncTable.put("put", cput);
		FuncTable.put("place", cput);
		FuncTable.put("remove", cremove);
		FuncTable.put("destroy", cremove);
		FuncTable.put("delete", cremove);
	}

	String parse(String command)
	{
		String func, arg;

		int posofarg = command.indexOf(" ");
		if (posofarg < 0)
		{
			func = command;
			arg = "";
		}
		else
		{
			func = command.substring(0, posofarg);
			arg = command.substring(posofarg + 1);
		}
		
		CommandFunc C = FuncTable.get(func);
		if (C == null)
		{
			return "what, invalid command";
		}
		
		return C.exec(arg);
	}
}