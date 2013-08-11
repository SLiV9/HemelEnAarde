interface CommandFunc
{
	public String exec(Board B, String arg);
}

class CommandMake implements CommandFunc
{
	public String exec(Board B, String arg)
	{
		Empire e = Empires.getEmpire(arg.charAt(0));
		if (e == Empire.INVALID)
		{
			return "fail, unknown empire or invalid syntax";
		}

		BodyType bt = Body.getType(arg.charAt(1));
		if (bt == BodyType.INVALID)
		{
			return "fail, unknown body type or invalid syntax";
		}
		
		Space s = B.getSpace(arg.substring(2));
		if (s == null)
		{
			return "fail, unknown space or invalid syntax";
		}
		
		Piece P = new Piece(e, bt);
		
		if (s.addOccupant(P))
		{
			return "fail, space already occupied";
		}
		
		return "ok, " + P.Name() + " created";
	}
}

class CommandPut implements CommandFunc
{
	public String exec(Board B, String arg)
	{
		// TODO: implement put
		return "fail, put not yet implemented";
	}
}

class CommandRemove implements CommandFunc
{
	public String exec(Board B, String arg)
	{
		// TODO: implement remove
		return "fail, remove not yet implemented";
	}
}