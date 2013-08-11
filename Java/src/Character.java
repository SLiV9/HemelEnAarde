import java.util.Hashtable;

public class Character
{
	/* Static queriables. */
	private static Hashtable<String, CharacterType> dictionaryOfTypes;

	private static int count = 0;
	private int index = 0;
	
	/* Gadgets */
	protected Piece owner = null;

	Character()
	{
		if (dictionaryOfTypes != null && type() != CharacterType.INVALID)
		{
			if (dictionaryOfTypes.get("" + initial()) == null)
				dictionaryOfTypes.put("" + initial(), type());
		}

		index = count;
		count++;
		System.out.println("\tnew: c" + initial() + Integer.toString(index)
				+ ";");
	}

	/* Make (or clear) the list of body types. */
	static void makeTables()
	{
		dictionaryOfTypes = new Hashtable<String, CharacterType>();
	}

	static void fillTables()
	{
		/* Only add existing and valid body types. */
		new Farmer();
	}

	static Character create(CharacterType ct)
	{
		switch (ct)
		{
		case FARMER:
			return new Farmer();
		default:
			return null;
		}
	}

	static Character create()
	{
		return new Farmer();
	}

	static CharacterType getType(char c)
	{
		if (dictionaryOfTypes == null)
			return CharacterType.INVALID;

		CharacterType t = dictionaryOfTypes.get("" + c);
		if (t != null)
			return t;
		else
			return CharacterType.INVALID;
	}

	/* Character specific constants */
	CharacterType type()
	{
		return CharacterType.INVALID;
	}

	String name()
	{
		return "Unknown";
	}

	char initial()
	{
		return '?';
	}

	/* Queries */
	boolean canMove(Space S)
	{
		return false;
	}

	boolean canCapture(Space S)
	{
		return false;
	}
}

// TODO: andere characters

// TODO: speciale acties van strategist en alchemist

class Farmer extends Character
{
	CharacterType type()
	{
		return CharacterType.FARMER;
	}

	String name()
	{
		return "Farmer";
	}

	char initial()
	{
		return 'F';
	}
}