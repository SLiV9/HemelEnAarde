import java.util.Hashtable;

public class Character
{
	/* Static queriables. */
	private static Hashtable<String, CharacterType> dictionaryOfTypes;
	
	private static int count = 0;
	private int index = 0;

	/* Character specific constants */
	protected static final CharacterType type = CharacterType.INVALID;
	protected static final String Name = "Unknown";
	protected static final char Initial = '?';

	/* Gadgets */
	protected Piece owner = null;

	Character()
	{
		if (dictionaryOfTypes != null && type != CharacterType.INVALID)
		{
			if (dictionaryOfTypes.get("" + Initial) == null)
				dictionaryOfTypes.put("" + Initial, type);
		}
		
		index = count;
		count++;
		System.out.println("new: " + Initial + Integer.toString(index) + ";");
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
		case F:
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

	/* Queries */
	CharacterType getType()
	{
		return type;
	}

	String getName()
	{
		return Name;
	}

	char getInitial()
	{
		return Initial;
	}

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
	protected static final CharacterType type = CharacterType.F;
	protected static final String Name = "Farmer";
	protected static final char Initial = 'F';
}