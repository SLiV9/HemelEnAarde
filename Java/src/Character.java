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
		new Empress();
		new Farmer();
	}

	static Character create(CharacterType ct)
	{
		switch (ct)
		{
		case EMPRESS:
			return new Empress();
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

class Empress extends Character
{
	CharacterType type()
	{
		return CharacterType.EMPRESS;
	}

	String name()
	{
		return "Empress";
	}

	char initial()
	{
		return 'Q';
	}
	
	boolean canMove(Space S)
	{
		if (!Space.isValid(S))
		{
			System.out.println("\t[space invalid]");
			return false;
		}
		if (S.equals(owner.position))
		{
			System.out.println("\t[zero movement]");
			return false;
		}

		if (!owner.canReach(S))
		{
			System.out.println("\t[cannot reach]");
			return false;
		}

		if (!S.isGarden())
		{
			System.out.println("\t[not garden]");
			return false;
		}

		if (S.isOccupied())
		{
			System.out.println("\t[occupied]");
			return false;
		}

		return true;
	}
	
	boolean canCapture(Space S)
	{
		if (!Space.isValid(S))
		{
			System.out.println("\t[space invalid]");
			return false;
		}
		if (S.equals(owner.position))
		{
			System.out.println("\t[zero movement]");
			return false;
		}

		if (!owner.canReachAttacking(S))
		{
			System.out.println("\t[cannot reach]");
			return false;
		}

		if (!S.isGarden())
		{
			System.out.println("\t[not garden]");
			return false;
		}

		if (!S.isOccupied())
		{
			System.out.println("\t[not occupied]");
			return false;
		}
		
		Piece target = S.getOccupant();

		if (!owner.isOpposing(target))
		{
			System.out.println("\t[not opposing]");
			return false;
		}

		if (!owner.isCapable(target))
		{
			System.out.println("\t[not capable]");
			return false;
		}

		return true;
	}
}

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