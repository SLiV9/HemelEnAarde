import java.util.Hashtable;

public class Character
{
	/* Debugging */
	protected static boolean showCannots = false;
	
	/* Static queriables. */
	private static Hashtable<String, CharacterType> dictionaryOfTypes;

	private static int count = 0;
	private int index = 0;

	/* Gadgets */
	protected Piece owner = null;

	protected Character()
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
		new Emperor();
		new Empress();
		new General();
		new Strategist();
		new Hunter();
		new Alchemist();
		new Farmer();
	}

	static Character create(CharacterType ct)
	{
		switch (ct)
		{
		case EMPEROR:
			return new Emperor();
		case EMPRESS:
			return new Empress();
		case GENERAL:
			return new General();
		case STRATEGIST:
			return new Strategist();
		case HUNTER:
			return new Hunter();
		case ALCHEMIST:
			return new Alchemist();
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
	
	boolean canAim(Space S)
	{
		return false;
	}
}

class Emperor extends Character
{
	CharacterType type()
	{
		return CharacterType.EMPEROR;
	}

	String name()
	{
		return "Emperor";
	}

	char initial()
	{
		return 'K';
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
			if (showCannots)
				System.out.println("\t[space invalid]");
			return false;
		}
		if (S.equals(owner.position))
		{
			if (showCannots)
				System.out.println("\t[zero movement]");
			return false;
		}

		if (!owner.canReach(S))
		{
			if (showCannots)
				System.out.println("\t[cannot reach]");
			return false;
		}

		// Empress is always noble.
		if (!S.isGarden())
		{
			if (showCannots)
				System.out.println("\t[not garden]");
			return false;
		}

		if (S.isOccupied())
		{
			if (showCannots)
				System.out.println("\t[occupied]");
			return false;
		}

		return true;
	}

	boolean canCapture(Space S)
	{
		if (!Space.isValid(S))
		{
			if (showCannots)
				System.out.println("\t[space invalid]");
			return false;
		}
		if (S.equals(owner.position))
		{
			if (showCannots)
				System.out.println("\t[zero movement]");
			return false;
		}

		if (!owner.canReachAttacking(S))
		{
			if (showCannots)
				System.out.println("\t[cannot reach]");
			return false;
		}

		// Empress is always noble.
		if (!S.isGarden())
		{
			if (showCannots)
				System.out.println("\t[not garden]");
			return false;
		}

		if (!S.isOccupied())
		{
			if (showCannots)
				System.out.println("\t[not occupied]");
			return false;
		}

		Piece target = S.getOccupant();

		if (!owner.isOpposing(target))
		{
			if (showCannots)
				System.out.println("\t[not opposing]");
			return false;
		}

		if (!owner.isCapable(target))
		{
			if (showCannots)
				System.out.println("\t[not capable]");
			return false;
		}

		return true;
	}
}

class General extends Character
{
	CharacterType type()
	{
		return CharacterType.GENERAL;
	}

	String name()
	{
		return "General";
	}

	char initial()
	{
		return 'G';
	}

	boolean canCapture(Space S)
	{
		if (!Space.isValid(S))
		{
			if (showCannots)
				System.out.println("\t[space invalid]");
			return false;
		}
		if (S.equals(owner.position))
		{
			if (showCannots)
				System.out.println("\t[zero movement]");
			return false;
		}

		if (!owner.canReachAttacking(S))
		{
			if (showCannots)
				System.out.println("\t[cannot reach]");
			return false;
		}

		if (owner.isIgnoble(S))
		{
			if (showCannots)
				System.out.println("\t[ignoble]");
			return false;
		}

		if (!S.isOccupied())
		{
			if (showCannots)
				System.out.println("\t[not occupied]");
			return false;
		}

		Piece target = S.getOccupant();

		if (!owner.isOpposing(target))
		{
			if (showCannots)
				System.out.println("\t[not opposing]");
			return false;
		}

		// General is always capable.

		return true;
	}
}

class Strategist extends Character
{
	CharacterType type()
	{
		return CharacterType.STRATEGIST;
	}

	String name()
	{
		return "Strategist";
	}

	char initial()
	{
		return 'S';
	}
	
	boolean canAim(Space S)
	{
		if (!Space.isValid(S))
		{
			if (showCannots)
				System.out.println("\t[space invalid]");
			return false;
		}
		if (S.equals(owner.position))
		{
			if (showCannots)
				System.out.println("\t[zero movement]");
			return false;
		}

		if (!owner.canReach(S))
		{
			if (showCannots)
				System.out.println("\t[cannot reach]");
			return false;
		}

		// Strategist ignores the Garden

		if (!S.isOccupied())
		{
			if (showCannots)
				System.out.println("\t[not occupied]");
			return false;
		}

		Piece target = S.getOccupant();

		if (!owner.isOpposing(target))
		{
			if (showCannots)
				System.out.println("\t[not opposing]");
			return false;
		}

		// Strategist is always capable.

		return true;
	}
}

class Hunter extends Character
{
	CharacterType type()
	{
		return CharacterType.HUNTER;
	}

	String name()
	{
		return "Hunter";
	}

	char initial()
	{
		return 'H';
	}

	boolean canCapture(Space S)
	{
		if (!Space.isValid(S))
		{
			if (showCannots)
				System.out.println("\t[space invalid]");
			return false;
		}
		if (S.equals(owner.position))
		{
			if (showCannots)
				System.out.println("\t[zero movement]");
			return false;
		}

		if (!owner.canReachAttacking(S))
		{
			if (showCannots)
				System.out.println("\t[cannot reach]");
			return false;
		}

		if (owner.isIgnoble(S))
		{
			if (showCannots)
				System.out.println("\t[ignoble]");
			return false;
		}

		if (!S.isOccupied())
		{
			if (showCannots)
				System.out.println("\t[not occupied]");
			return false;
		}

		Piece target = S.getOccupant();

		if (!owner.isOpposing(target))
		{
			if (showCannots)
				System.out.println("\t[not opposing]");
			return false;
		}

		// Hunter is capable of capturing Rank 4.
		if (target.getRank() != 4)
		{
			if (showCannots)
				System.out.println("\t[not capable]");
			return false;
		}

		return true;
	}
}

class Alchemist extends Character
{
	CharacterType type()
	{
		return CharacterType.ALCHEMIST;
	}

	String name()
	{
		return "Alchemist";
	}

	char initial()
	{
		return 'A';
	}
	
	boolean canAim(Space S)
	{
		if (!Space.isValid(S))
		{
			if (showCannots)
				System.out.println("\t[space invalid]");
			return false;
		}
		if (S.equals(owner.position))
		{
			if (showCannots)
				System.out.println("\t[zero movement]");
			return false;
		}

		if (!owner.position.isAdjacent(S))
		{
			if (showCannots)
				System.out.println("\t[not adjacent]");
			return false;
		}

		// Alchemist ignores the Garden

		if (!S.isOccupied())
		{
			if (showCannots)
				System.out.println("\t[not occupied]");
			return false;
		}

		Piece target = S.getOccupant();

		if (!owner.isOpposing(target))
		{
			if (showCannots)
				System.out.println("\t[not opposing]");
			return false;
		}

		// Alchemist is always capable.

		return true;
	}
}

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