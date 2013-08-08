public class Character
{
	/* Character specific semi-constants */
	CharacterType type = CharacterType.F;
	String Name = "Unknown";
	char Initial = '?';

	/* Gadgets */
	protected Piece owner = null;

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
	Farmer()
	{
		type = CharacterType.F;
		Name = "Farmer";
		Initial = 'L';
	}
}