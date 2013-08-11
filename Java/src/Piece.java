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
		System.out.println("new: " + Name() + ";");
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
		return B.getRank();
	}

	boolean isOpposing(Piece other)
	{
		return (other.E != E);
	}

	BodyType getBodyType()
	{
		return B.getType();
	}

	CharacterType getCharacterType()
	{
		return C.getType();
	}

	String Initials()
	{
		return "" + Empires.Initial(E) + B.getInitial() + C.getInitial();
	}

	String Name()
	{
		return "" + Initials() + Integer.toString(index);
	}
}