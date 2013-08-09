public class Body
{
	/* Body specific semi-constants */
	BodyType type = BodyType.L;
	String Name = "Unknown";
	char Initial = '?';

	protected int rank = 1;
	protected int speed = 3;
	protected boolean noble = false;

	/* Gadgets */
	protected Piece owner = null;

	/* Queries */
	int getRank()
	{
		return 0;
	}

	boolean isNoble()
	{
		return false;
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
	Dragon()
	{
		type = BodyType.D;
		Name = "Dragon";
		Initial = 'D';

		rank = 5;
		speed = 3;
	}

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
	static final int ATTACKRANGE = 4;

	Elephant()
	{
		type = BodyType.E;
		Name = "Elephant";
		Initial = 'E';

		rank = 4;
		speed = 2;
	}

	/* The Elephant has longer range when attacking. */
	boolean canCapture(Space S)
	{
		return canCapture(S, ATTACKRANGE);
	}
}

class Panda extends Body
{
	Panda()
	{
		type = BodyType.P;
		Name = "Panda";
		Initial = 'P';

		rank = 4;
		speed = 1;
		noble = true;
	}
}

class Tiger extends Body
{
	Tiger()
	{
		type = BodyType.T;
		Name = "Tiger";
		Initial = 'T';

		rank = 3;
		speed = 4;
	}
}

class Monkey extends Body
{
	Monkey()
	{
		type = BodyType.M;
		Name = "Monkey";
		Initial = 'M';

		rank = 3;
		speed = 2;
	}

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
	Nightingale()
	{
		type = BodyType.N;
		Name = "Nightingale";
		Initial = 'N';

		rank = 2;
		speed = 4;
		noble = true;
	}

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
	Lotus()
	{
		type = BodyType.L;
		Name = "Lotus";
		Initial = 'L';

		rank = 1;
		speed = 3;
		noble = true;
	}
}