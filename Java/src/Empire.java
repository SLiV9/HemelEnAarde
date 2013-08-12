enum Empire { INVALID, SOUTH, NORTH };

class Empires
{
	static Empire getEmpire(char c)
	{
		if (c == 's')
			return Empire.SOUTH;
		else if (c == 'n')
			return Empire.NORTH;
		else
			return Empire.INVALID;
	}
	
	static Empire opponent(Empire e)
	{
		if (e == Empire.SOUTH)
			return Empire.NORTH;
		else if (e == Empire.NORTH)
			return Empire.SOUTH;
		else 
			return Empire.INVALID;
	}
	
	static char initial(Empire e)
	{
		if (e == Empire.SOUTH)
			return 's';
		else if (e == Empire.NORTH)
			return 'n';
		else
			return '-';
	}
}