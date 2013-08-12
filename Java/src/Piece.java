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
		if (B == null)
		{
			System.out.println("{ error: could not create body of type " + bt
					+ "}");
		}
		else
			B.owner = this;

		C = Character.create();
		if (C == null)
		{
			System.out.println("{ error: could not create default character }");
		}
		else
			C.owner = this;

		index = count;
		count++;
		System.out.println("\tnew: p" + name() + ";");
	}

	void setCharacter(Character c)
	{
		C = c;
		if (C == null)
		{
			System.out.println("{ error: character to set is null }");
		}
		else
			C.owner = this;
	}

	/* Queries */
	boolean canReach(Space S)
	{
		return B.canReach(S);
	}

	boolean canReachAttacking(Space S)
	{
		return B.canReachAttacking(S);
	}

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
	
	boolean canAim(Space S)
	{
		return C.canAim(S);
	}

	boolean isIgnoble(Space S)
	{
		return B.isIgnoble(S);
	}

	boolean isCapable(Piece T)
	{
		return B.isCapable(T);
	}

	int getRank()
	{
		return B.rank();
	}

	boolean isOpposing(Piece other)
	{
		return (other.E != E);
	}

	boolean inOpposingCity()
	{
		return (position.isCity() && platform.inEmpiresHalf(position,
				Empires.opponent(E)));
	}

	BodyType getBodyType()
	{
		return B.type();
	}

	CharacterType getCharacterType()
	{
		return C.type();
	}

	Empire getEmpire()
	{
		return E;
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