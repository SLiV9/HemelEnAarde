import java.util.ArrayList;
import java.util.EnumMap;

class Board
{
	private static final int WIDTH = 9;
	private static final int HEIGHT = 5;

	private Space[][] hex = new Space[2 * HEIGHT + 1][2 * WIDTH + 1];
	private EnumMap<Empire, EnumMap<BodyType, Piece>> piecesLookup;
	ArrayList<Space> spacesOnBoard;
	ArrayList<Piece> piecesOnBoard;
	Empire empiresOnBoard[] = {Empire.SOUTH, Empire.NORTH};

	private final boolean southAtBottom = true;

	Board()
	{
		spacesOnBoard = new ArrayList<Space>();
		piecesOnBoard = new ArrayList<Piece>();

		/*
		 * The rows range from -5 to +5. The columns range from -9 to +9. The
		 * center, 'f0', is (0, 0). Even rows have even width.
		 */
		Space S;
		for (int r = -HEIGHT; r <= HEIGHT; r++)
		{
			int rowwidth = 8;
			if (r < 0)
				rowwidth = 10 + r;
			if (r > 0)
				rowwidth = 10 - r;

			for (int c = -rowwidth; c <= rowwidth; c += 2)
			{
				S = new Space(this, r, c);
				setHex(r, c, S);
				spacesOnBoard.add(S);
			}
		}

		piecesLookup = new EnumMap<Empire, EnumMap<BodyType, Piece>>(
				Empire.class);
		piecesLookup.put(Empire.SOUTH, new EnumMap<BodyType, Piece>(
				BodyType.class));
		piecesLookup.put(Empire.NORTH, new EnumMap<BodyType, Piece>(
				BodyType.class));
	}

	private void setHex(int r, int c, Space S)
	{
		hex[HEIGHT + r][WIDTH + c] = S;
	}

	/* Queries */
	boolean inEmpiresHalf(Space S, Empire e)
	{
		if (southAtBottom)
		{
			if (S.row < 0 && e == Empire.SOUTH)
				return true;
			else if (S.row > 0 && e == Empire.NORTH)
				return true;
		}
		else
		{
			if (S.row > 0 && e == Empire.SOUTH)
				return true;
			else if (S.row < 0 && e == Empire.NORTH)
				return true;
		}

		return false;
	}

	Space getHex(int r, int c)
	{
		return hex[HEIGHT + r][WIDTH + c];
	}

	Space getSpace(String name)
	{
		if (name.length() < 2)
			return null;

		int r, c;
		char a, b;
		a = name.charAt(0);
		b = name.charAt(1);

		if (a >= 'a' && a <= 'k' && b >= '0' && b <= '9')
		{
			r = a - 'f';
			c = b - '0';
			return getHex(r, c);
		}
		else if (b >= 'a' && b <= 'k' && a >= '0' && a <= '9')
		{
			r = b - 'f';
			c = '0' - a;
			return getHex(r, c);
		}

		return null;
	}

	static String spacename(int r, int c)
	{
		if (c >= 0)
		{
			return "" + ((char) ('f' + r)) + ((char) ('0' + c));
		}
		else
		{
			return "" + ((char) ('0' - c)) + ((char) ('f' + r));
		}
	}

	/* Pieces on board */
	Piece findPiece(Empire e, BodyType bt)
	{
		EnumMap<BodyType, Piece> poe = piecesLookup.get(e);
		if (poe == null)
		{
			return null;
		}

		return poe.get(bt);
	}

	boolean addPiece(Piece P)
	{
		if (P == null)
			return false;

		EnumMap<BodyType, Piece> poe = piecesLookup.get(P.getEmpire());
		if (poe != null)
		{
			Piece oldp = poe.get(P.getBodyType());
			if (oldp != null)
			{
				return false;
			}

			poe.put(P.getBodyType(), P);
			piecesOnBoard.add(P);
		}

		return true;
	}

	void removePiece(Piece P)
	{
		if (P == null)
			return;

		removePiece(P.getEmpire(), P.getBodyType());
	}

	void removePiece(Empire e, BodyType bt)
	{
		EnumMap<BodyType, Piece> poe = piecesLookup.get(e);
		if (poe != null)
		{
			Piece P = poe.get(bt);

			Space S = P.position;
			if (S != null)
			{
				S.removeOccupant();
			}

			poe.remove(bt);
			piecesOnBoard.remove(P);
		}
	}

	/* Debug printing */
	void print()
	{
		Space S;

		System.out.println("{{");
		for (int r = 2 * HEIGHT; r >= 0; r--)
		{
			System.out.print("[ ");
			for (int c = 0; c < 2 * WIDTH + 1; c++)
			{
				S = hex[r][c];

				if (Space.isValid(S))
				{
					if (S.isOccupied())
					{
						Piece P = S.getOccupant();
						System.out.print("" + P.initials());
					}
					else if (S.isGarden() || S.isPlaceable())
						System.out.print("<#>");
					else
						System.out.print("< >");
				}
				else
					System.out.print("   ");
			}
			System.out.println(" ]");
		}
		System.out.println("}}");
	}
}