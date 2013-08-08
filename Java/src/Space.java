public class Space
{
	private enum SpaceType
	{
		NORMAL, GARDEN, CITY, GATE, INVALID;
	}

	private SpaceType type = SpaceType.INVALID;
	private Piece occupant = null;

	int row;
	int col;

	private static Space center = new Space(true);

	private Space(boolean b)
	{
		if (b)
		{
			row = 0;
			col = 0;
		}
	}

	public Space(int r, int c)
	{
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
	boolean isGarden()
	{
		return (type == SpaceType.GARDEN);
	}

	boolean isCity()
	{
		return (type == SpaceType.CITY);
	}

	boolean isPlaceable()
	{
		return (type == SpaceType.CITY || type == SpaceType.GATE);
	}

	boolean isOccupied()
	{
		return (occupant != null);
	}

	Piece getOccupant()
	{
		return occupant;
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