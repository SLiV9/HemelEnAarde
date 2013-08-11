class Board
{
	private static final int WIDTH = 9;
	private static final int HEIGHT = 5;

	private Space[][] hex = new Space[2 * HEIGHT + 1][2 * WIDTH + 1];

	Board()
	{
		/*
		 * The rows range from -5 to +5. The columns range from -9 to +9. The
		 * center, 'f0', is (0, 0). Even rows have even width.
		 */
		for (int r = -HEIGHT; r <= HEIGHT; r++)
		{
			int rowwidth = 8;
			if (r < 0)
				rowwidth = 10 + r;
			if (r > 0)
				rowwidth = 10 - r;

			for (int c = -rowwidth; c <= rowwidth; c += 2)
			{
				setHex(r, c, new Space(this, r, c));
			}
		}
	}

	private void setHex(int r, int c, Space S)
	{
		hex[HEIGHT + r][WIDTH + c] = S;
	}

	/* Queries */
	Space getHex(int r, int c)
	{
		return hex[HEIGHT + r][WIDTH + c];
	}
	
	Space getSpace(String name)
	{
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
			return "" + ('f' + r) + ('0' + c);
		}
		else
		{
			return "" + ('0' - c) + ('f' + r);
		}
	}

	/* Debug printing */
	void print()
	{
		Space S;

		System.out.println("\n{{");
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
						System.out.print("" + P.Initials());
					}
					else if (S.isGarden() || S.isPlaceable())
						System.out.print("##");
					else
						System.out.print("<>");
				}
				else
					System.out.print("  ");
			}
			System.out.println(" ]");
		}
		System.out.println("}}");
	}
}