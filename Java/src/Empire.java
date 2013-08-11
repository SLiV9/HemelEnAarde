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
	
	static char Initial(Empire e)
	{
		if (e == Empire.SOUTH)
			return 's';
		else if (e == Empire.NORTH)
			return 'n';
		else
			return '-';
	}
}