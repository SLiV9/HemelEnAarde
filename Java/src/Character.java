import java.util.Hashtable;

public class Character
{
	/* Static queriables. */
	private static Hashtable<String, CharacterType> dictionaryOfTypes;

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
	}

	/* Make (or clear) the list of body types. */
	void makeTables()
	{
		dictionaryOfTypes = new Hashtable<String, CharacterType>();
	}

	CharacterType getType(char c)
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