interface CommandFunc
{
	public String exec(String arg);
}

class CommandMake implements CommandFunc
{
	public String exec(String arg)
	{
		return "fail, make not yet implemented";
	}
}

class CommandPut implements CommandFunc
{
	public String exec(String arg)
	{
		return "fail, put not yet implemented";
	}
}

class CommandRemove implements CommandFunc
{
	public String exec(String arg)
	{
		return "fail, remove not yet implemented";
	}
}