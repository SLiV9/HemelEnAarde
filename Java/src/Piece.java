public class Piece
{
	private Body B;
	private Character C;
	private Empire E;

	Board platform;
	Space position;

	private static int count = 0;
	private int index = 0;

	// TODO: isrevealed

	Piece(Empire e, BodyType bt)
	{
		E = e;
		B = Body.create(bt);
		C = Character.create();

		index = count;
		count++;
		System.out.println("\tnew: p" + name() + ";");
	}

	/* Queries */
	boolean canMove(Space S)
	{
		return B.canMove(S);
	}

	boolean canCapture(Space S)
	{
		return B.canCapture(S);
	}

	boolean canMoveX(Space S)
	{
		return C.canMove(S);
	}

	boolean canCaptureX(Space S)
	{
		return C.canCapture(S);
	}

	int getRank()
	{
		return B.rank();
	}

	boolean isOpposing(Piece other)
	{
		return (other.E != E);
	}

	BodyType getBodyType()
	{
		return B.type();
	}

	CharacterType getCharacterType()
	{
		return C.type();
	}

	String initials()
	{
		return "" + Empires.initial(E) + B.initial() + C.initial();
	}

	String name()
	{
		return "" + initials() + Integer.toString(index);
	}
}