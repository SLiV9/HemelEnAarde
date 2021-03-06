public class Space
{
	private enum SpaceType
	{
		NORMAL, GARDEN, CITY, GATE, INVALID;
	}

	private SpaceType type = SpaceType.INVALID;
	private Board platform = null;
	private Piece occupant = null;

	int row;
	int col;

	private static Space center = new Space(true);

	private Space(boolean isorigin)
	{
		if (isorigin)
		{
			row = 0;
			col = 0;
		}
	}

	public Space(Board b, int r, int c)
	{
		platform = b;
		row = r;
		col = c;

		if (row == 5 || row == -5)
		{
			type = SpaceType.CITY;
		}
		else if (col == 0 && (row == -4 || row == 4))
		{
			type = SpaceType.GATE;
		}
		else if (distance(center, this) <= 1)
		{
			type = SpaceType.GARDEN;
		}
		else
			type = SpaceType.NORMAL;
	}

	/* Common queries. */
	String name()
	{
		return Board.spacename(row, col);
	}

	boolean isGarden()
	{
		return (type == SpaceType.GARDEN);
	}

	boolean isCity()
	{
		return (type == SpaceType.CITY);
	}
	
	boolean isGate()
	{
		return (type == SpaceType.GATE);
	}

	boolean isPlaceable()
	{
		return (type == SpaceType.CITY || type == SpaceType.GATE);
	}

	boolean isOccupied()
	{
		return (occupant != null);
	}
	
	boolean isAdjacent(Space other)
	{
		return (distance(this, other) == 1);
	}

	Piece getOccupant()
	{
		return occupant;
	}

	boolean addOccupant(Piece P)
	{
		if (occupant != null)
			return false;

		occupant = P;
		P.platform = platform;
		P.position = this;

		return true;
	}

	boolean removeOccupant()
	{
		if (occupant == null)
			return false;

		if (occupant.position == this)
		{
			occupant.platform = null;
			occupant.position = null;
			platform.removePiece(occupant);
		}
		occupant = null;

		return true;
	}

	/* A nifty validity checker. */
	static boolean isValid(Space S)
	{
		if (S == null)
			return false;

		return (S.type != SpaceType.INVALID);
	}

	/*
	 * Given two (valid) spaces, returns whether they lie in a straight line.
	 */
	static boolean isStraight(Space A, Space B)
	{
		int dr, dc;
		dr = Math.abs(A.row - B.row);
		dc = Math.abs(A.col - B.col);

		if (dr == 0)
			return true;

		if (dc == dr)
			return true;

		return false;
	}
	
	/*
	 * Given two (valid) spaces, returns the distance (in spaces) between them.
	 */
	static int distance(Space A, Space B)
	{
		int dr, dc;
		dr = Math.abs(A.row - B.row);
		dc = Math.abs(A.col - B.col);

		return (dr + Math.max(0, (dc - dr) / 2));
	}
}