public class Character
{
	/* Character specific constants */
	protected static final CharacterType type = CharacterType.F;
	protected static final String Name = "Unknown";
	protected static final char Initial = '?';

	/* Gadgets */
	protected Piece owner = null;

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