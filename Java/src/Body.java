public class Body
{
	/* Body specific constants */
	protected static final BodyType type = BodyType.L;
	protected static final String Name = "Unknown";
	protected static final char Initial = '?';

	protected static final int rank = 1;
	protected static final int speed = 3;
	protected static final boolean noble = false;

	/* Gadgets */
	protected Piece owner = null;

	/* Queries */
	BodyType getType()
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
	
	int getRank()
	{
		return rank;
	}

	boolean canReach(Space S)
	{
		return canReach(S, speed);
	}

	protected boolean canReach(Space S, int spd)
	{
		if (!Space.isValid(S))
			return false;

		if (Space.distance(owner.position, S) > spd)
			return false;
		if (!Space.isStraight(owner.position, S))
			return false;

		if (isBlocked(S))
			return false;

		return true;
	}

	boolean isBlocked(Space S)
	{
		if (S.equals(owner.position))
			return false;
		if (Space.distance(owner.position, S) <= 1)
			return false;

		int dr, dc, sr, sc, adc, opr, opc;
		opr = owner.position.row;
		opc = owner.position.col;
		dr = S.row - opr;
		dc = S.col - opc;
		sr = Integer.signum(dr);
		sc = Integer.signum(dc);
		adc = Math.abs(dc);

		for (int i = 1; i < adc; i++)
		{
			if (isBlocked(opr + sr * i, opc + sc * i))
				return true;
		}

		return false;
	}

	protected boolean isBlocked(int r, int c)
	{
		Space A;
		A = owner.platform.getHex(r, c);
		if (A.isOccupied())
		{
			if (A.getOccupant().isOpposing(owner))
				return true;
		}

		return false;
	}

	boolean canMove(Space S)
	{
		if (!Space.isValid(S))
			return false;
		if (S.equals(owner.position))
			return false;

		if (!canReach(S))
			return false;

		if (isIgnoble(S))
			return false;

		if (S.isOccupied())
			return false;

		return true;
	}

	boolean isIgnoble(Space S)
	{
		if (S.isGarden() && !noble)
			return true;

		return false;
	}

	boolean canCapture(Space S)
	{
		return canCapture(S, speed);
	}

	protected boolean canCapture(Space S, int spd)
	{
		if (!Space.isValid(S))
			return false;
		if (S.equals(owner.position))
			return false;

		if (!canReach(S, spd))
			return false;

		if (isIgnoble(S))
			return false;

		if (!S.isOccupied())
			return false;

		Piece target = S.getOccupant();

		if (!owner.isOpposing(target))
			return false;

		if (!outranks(target))
			return false;

		return true;
	}

	boolean outranks(Piece T)
	{
		return (T.getRank() > rank);
	}
}

class Dragon extends Body
{
	protected static final BodyType type = BodyType.D;
	protected static final String Name = "Dragon";
	protected static final char Initial = 'D';

	protected static final int rank = 5;
	protected static final int speed = 3;

	/* Dragon cannot capture Nightingale. */
	boolean outranks(Piece T)
	{
		if (T.getBodyType() == BodyType.N)
			return false;

		return super.outranks(T);
	}
}

class Elephant extends Body
{
	protected static final BodyType type = BodyType.E;
	protected static final String Name = "Elephant";
	protected static final char Initial = 'E';

	protected static final int rank = 4;
	protected static final int speed = 2;
	
	protected static final int ATTACKRANGE = 4;
	
	/* The Elephant has longer range when attacking. */
	boolean canCapture(Space S)
	{
		return canCapture(S, ATTACKRANGE);
	}
}

class Panda extends Body
{
	protected static final BodyType type = BodyType.P;
	protected static final String Name = "Panda";
	protected static final char Initial = 'P';

	protected static final int rank = 4;
	protected static final int speed = 1;
	protected static final boolean noble = true;
}

class Tiger extends Body
{
	static final BodyType type = BodyType.T;
	static final String Name = "Tiger";
	static final char Initial = 'T';

	protected static final int rank = 3;
	protected static final int speed = 4;
}

class Monkey extends Body
{
	protected static final BodyType type = BodyType.M;
	protected static final String Name = "Monkey";
	protected static final char Initial = 'M';

	protected static final int rank = 3;
	protected static final int speed = 2;

	/* The Monkey can move in arcs. */
	boolean canReach(Space S)
	{
		if (!Space.isValid(S))
			return false;

		if (Space.distance(owner.position, S) > speed)
			return false;
		// Purposefully removed: isStraight

		if (isBlocked(S))
			return false;

		return true;
	}

	boolean isBlocked(Space S)
	{
		if (S.equals(owner.position))
			return false;
		if (Space.distance(owner.position, S) <= 1)
			return false;

		if (Space.isStraight(owner.position, S))
			return super.isBlocked(S);

		// Thus the Monkey is moving in an arc.
		int dr, dc, sr, sc, opr, opc;
		opr = owner.position.row;
		opc = owner.position.col;
		dr = S.row - opr;
		dc = S.col - opc;
		sr = Integer.signum(dr);
		sc = Integer.signum(dc);

		if (dc == 0)
		{
			if (isBlocked(opr + sr, opc - 1) && isBlocked(opr + sr, opc + 1))
				return true;
		}
		else
		{
			if (isBlocked(opr + sr, opc + sc) && isBlocked(opr, opc + 2 * sc))
				return true;
		}

		return false;
	}
}

class Nightingale extends Body
{
	protected static final BodyType type = BodyType.N;
	protected static final String Name = "Nightingale";
	protected static final char Initial = 'N';

	protected static final int rank = 2;
	protected static final int speed = 4;
	protected static final boolean noble = true;

	/* The Nightingale can fly. */
	boolean isBlocked(Space S)
	{
		return false;
	}

	/* Nightingale can capture Dragon. */
	boolean outranks(Piece T)
	{
		if (T.getBodyType() == BodyType.D)
			return true;

		return super.outranks(T);
	}
}

class Lotus extends Body
{
	protected static final BodyType type = BodyType.L;
	protected static final String Name = "Lotus";
	protected static final char Initial = 'L';

	protected static final int rank = 1;
	protected static final int speed = 3;
	protected static final boolean noble = true;
}