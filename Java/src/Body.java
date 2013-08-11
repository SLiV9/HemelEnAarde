import java.util.Hashtable;

public class Body
{
	/* Static queriables. */
	private static Hashtable<String, BodyType> dictionaryOfTypes;

	private static int count = 0;
	private int index = 0;

	/* Instance specific gadgets */
	protected Piece owner = null;

	Body()
	{
		if (dictionaryOfTypes != null && type() != BodyType.INVALID)
		{
			if (dictionaryOfTypes.get("" + initial()) == null)
				dictionaryOfTypes.put("" + initial(), type());
		}

		index = count;
		count++;
		System.out.println("\tnew: b" + initial() + Integer.toString(index)
				+ ";");
	}

	/* Make (or clear) the list of body types. */
	static void makeTables()
	{
		dictionaryOfTypes = new Hashtable<String, BodyType>();
	}

	static void fillTables()
	{
		/* Only add existing and valid body types. */
		new Dragon();
		new Elephant();
		new Panda();
		new Tiger();
		new Monkey();
		new Nightingale();
		new Lotus();
	}

	static Body create(BodyType bt)
	{
		switch (bt)
		{
		case DRAGON:
			return new Dragon();
		case ELEPHANT:
			return new Elephant();
		case PANDA:
			return new Panda();
		case TIGER:
			return new Tiger();
		case MONKEY:
			return new Monkey();
		case NIGHTINGALE:
			return new Nightingale();
		case LOTUS:
			return new Lotus();
		default:
			return null;
		}
	}

	static BodyType getType(char c)
	{
		if (dictionaryOfTypes == null)
			return BodyType.INVALID;

		BodyType t = dictionaryOfTypes.get("" + c);
		if (t != null)
			return t;
		else
			return BodyType.INVALID;
	}

	/* Body specific constants */
	BodyType type()
	{
		return BodyType.INVALID;
	}

	String name()
	{
		return "Unknown";
	}

	char initial()
	{
		return '?';
	}

	int rank()
	{
		return 0;
	}

	int speed()
	{
		return 3;
	}

	boolean isNoble()
	{
		return false;
	}

	/* Queries */
	boolean canReach(Space S)
	{
		return canReach(S, speed());
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
		if (S.isGarden() && !isNoble())
			return true;

		return false;
	}

	boolean canCapture(Space S)
	{
		return canCapture(S, speed());
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
		return (T.getRank() > rank());
	}
}

class Dragon extends Body
{
	BodyType type()
	{
		return BodyType.DRAGON;
	}

	String name()
	{
		return "Dragon";
	}

	char initial()
	{
		return 'D';
	}

	int rank()
	{
		return 4;
	}

	int speed()
	{
		return 3;
	}

	/* Dragon cannot capture Nightingale. */
	boolean outranks(Piece T)
	{
		if (T.getBodyType() == BodyType.NIGHTINGALE)
			return false;

		return super.outranks(T);
	}
}

class Elephant extends Body
{
	BodyType type()
	{
		return BodyType.ELEPHANT;
	}

	String name()
	{
		return "Elephant";
	}

	char initial()
	{
		return 'E';
	}

	int rank()
	{
		return 4;
	}

	int speed()
	{
		return 2;
	}

	protected int ATTACKRANGE = 4;

	/* The Elephant has longer range when attacking. */
	boolean canCapture(Space S)
	{
		return canCapture(S, ATTACKRANGE);
	}
}

class Panda extends Body
{
	BodyType type()
	{
		return BodyType.PANDA;
	}

	String name()
	{
		return "Panda";
	}

	char initial()
	{
		return 'P';
	}

	int rank()
	{
		return 4;
	}

	int speed()
	{
		return 1;
	}

	boolean isNoble()
	{
		return true;
	}
}

class Tiger extends Body
{
	BodyType type()
	{
		return BodyType.TIGER;
	}

	String name()
	{
		return "Tiger";
	}

	char initial()
	{
		return 'T';
	}

	int rank()
	{
		return 3;
	}

	int speed()
	{
		return 4;
	}
}

class Monkey extends Body
{
	BodyType type()
	{
		return BodyType.MONKEY;
	}

	String name()
	{
		return "Monkey";
	}

	char initial()
	{
		return 'M';
	}

	int rank()
	{
		return 3;
	}

	int speed()
	{
		return 2;
	}

	/* The Monkey can move in arcs. */
	boolean canReach(Space S)
	{
		if (!Space.isValid(S))
			return false;

		if (Space.distance(owner.position, S) > speed())
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
	BodyType type()
	{
		return BodyType.NIGHTINGALE;
	}

	String name()
	{
		return "Nightingale";
	}

	char initial()
	{
		return 'N';
	}

	int rank()
	{
		return 2;
	}

	int speed()
	{
		return 4;
	}

	boolean isNoble()
	{
		return true;
	}

	/* The Nightingale can fly. */
	boolean isBlocked(Space S)
	{
		return false;
	}

	/* Nightingale can capture Dragon. */
	boolean outranks(Piece T)
	{
		if (T.getBodyType() == BodyType.DRAGON)
			return true;

		return super.outranks(T);
	}
}

class Lotus extends Body
{
	BodyType type()
	{
		return BodyType.LOTUS;
	}

	String name()
	{
		return "Lotus";
	}

	char initial()
	{
		return 'L';
	}

	int rank()
	{
		return 1;
	}

	int speed()
	{
		return 3;
	}

	boolean isNoble()
	{
		return true;
	}
}