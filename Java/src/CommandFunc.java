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
		if (s.isOccupied())
		{
			return "fail, space already occupied";
		}
		
		Piece oldP = B.findPiece(e, bt);
		if (oldP != null)
		{
			return "fail, piece already exists";
		}
		
		Piece P = new Piece(e, bt);
		
		if (B.addPiece(P) == false)
		{
			return "fail, piece could not be added";
		}
		else if (s.addOccupant(P) == false)
		{
			B.removePiece(e, bt);
			return "fail, space could not be occupied";
		}
		
		return "ok, " + P.name() + " created at " + s.name();
	}
}

class CommandPut implements CommandFunc
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

		Piece P = B.findPiece(e, bt);
		if (P == null)
		{
			return "fail, piece does not exist";
		}
		Space oldpos = P.position;
		
		Space s = B.getSpace(arg.substring(2));
		if (s == null)
		{
			return "fail, unknown space or invalid syntax";
		}
		if (s.isOccupied())
		{
			return "fail, space already occupied";
		}

		if (s.addOccupant(P) == false)
		{
			return "fail, space could not be occupied";
		}
		oldpos.removeOccupant();
		
		return "ok, " + P.name() + " moved to " + s.name();
	}
}

class CommandRemove implements CommandFunc
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

		Piece P = B.findPiece(e, bt);
		if (P == null)
		{
			return "fail, piece does not exist";
		}
		
		B.removePiece(e, bt);
		
		return "ok, " + P.name() + "* removed";
	}
}

class CommandSet implements CommandFunc
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

		CharacterType ct = Character.getType(arg.charAt(2));
		if (ct == CharacterType.INVALID)
		{
			return "fail, unknown character type or invalid syntax";
		}
		
		Piece P = B.findPiece(e, bt);
		String oldname = P.name();
		
		P.setCharacter(Character.create(ct));
		
		return "ok, " + oldname + "* changed to " + P.name();
	}
}