interface CommandFunc
{
	public String exec(String arg);
}

class CommandMake implements CommandFunc
{
	public String exec(String arg)
	{
		return "fail, not yet implemented";
	}
}

class CommandPut implements CommandFunc
{
	public String exec(String arg)
	{
		return "fail, not yet implemented";
	}
}

class CommandRemove implements CommandFunc
{
	public String exec(String arg)
	{
		return "fail, not yet implemented";
	}
}