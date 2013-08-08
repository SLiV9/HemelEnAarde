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
		if (!Space.isValid(S))
			return false;

		if (Space.distance(owner.position, S) > speed)
			return false;
		if (!Space.isStraight(owner.position, S))
			return false;

		if (isBlocked(S))
			return false;

		return true;
	}

	boolean isBlocked(Space S)
	{
		// TODO: line of sight blocking

		return false;
	}

	boolean canMove(Space S)
	{
		if (!Space.isValid(S))
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
		if (!Space.isValid(S))
			return false;

		if (!canReach(S))
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
		// TODO: change this to have longer range
		return super.canCapture(S);
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
		// TODO: line of sight blocking for monkey

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