class MoveParser extends LineParser
{
	MoveParser()
	{

	}

	String parse(Board B, String arg)
	{
		Empire e = Empires.getEmpire(arg.charAt(0));
		if (e == Empire.INVALID)
		{
			return "fail, unknown empire or invalid syntax";
		}

		BodyType bt = Body.getType(arg.charAt(1));
		if (bt == BodyType.INVALID)
		{
			return "fail, unknown body type or invalid syntax";
		}

		Piece P = B.findPiece(e, bt);
		if (P == null)
		{
			return "fail, piece does not exist";
		}
		Space oldpos = P.position;
		if (oldpos == null)
		{
			return "error, piece has no position";
		}

		int argpos = 2;
		CharacterType ct = Character.getType(arg.charAt(argpos));
		if (ct != CharacterType.INVALID)
		{
			if (P.getCharacterType() != ct)
			{
				return "fail, wrong character";
			}

			if (ct == CharacterType.EMPEROR)
			{
				// TODO: emperor wins
				return "fail, emperor not implemented";
			}

			argpos++;
		}

		char movetype = 'm';
		switch (arg.charAt(argpos))
		{
		case 'x':
		case 'q':
		case 'r':
			movetype = arg.charAt(argpos);
			argpos++;
			break;
		}

		Space S = B.getSpace(arg.substring(argpos));
		if (S == null)
		{
			return "fail, unknown space or invalid syntax";
		}

		/* Interpret move */
		if (ct == CharacterType.EMPRESS)
		{
			if (movetype == 'm')
			{
				if (P.canMoveX(S))
				{
					return tryMove(P, oldpos, S);
				}
				else
					return "fail, illegal move";
			}
			else if (movetype == 'x')
			{
				if (P.canCaptureX(S))
				{
					return tryCapture(P, oldpos, S);
				}
				else
					return "fail, illegal move";
			}
			else
			{
				return "fail, empress must move or capture";
			}
		}
		else if (ct == CharacterType.GENERAL)
		{
			if (movetype != 'x')
			{
				return "fail, general must capture";
			}

			// TODO: general captures
			return "fail, general not implemented";
		}
		else if (ct == CharacterType.HUNTER)
		{
			if (movetype != 'x')
			{
				return "fail, hunter must capture";
			}

			// TODO: hunter captures
			return "fail, hunter not implemented";
		}
		else if (ct == CharacterType.STRATEGIST)
		{
			if (movetype != 'q')
			{
				return "fail, strategist must inspect";
			}

			// TODO: strategist inspects
			return "fail, strategist not implemented";
		}
		else if (ct == CharacterType.ALCHEMIST)
		{
			if (movetype != 'r')
			{
				return "fail, alchemist must remove";
			}

			// TODO: alchemist removes
			return "fail, alchemist not implemented";
		}
		else
		{
			if (movetype == 'm')
			{
				if (P.canMove(S))
				{
					return tryMove(P, oldpos, S);
				}
				else
					return "fail, illegal move";
			}
			else if (movetype == 'x')
			{
				if (P.canCapture(S))
				{
					return tryCapture(P, oldpos, S);
				}
				else
					return "fail, illegal move";
			}
			else
			{
				return "fail, invalid movetype";
			}
		}
	}

	private String tryMove(Piece P, Space oldpos, Space S)
	{

		if (S.addOccupant(P) == false)
		{
			return "fail, space could not be occupied";
		}
		oldpos.removeOccupant();

		return "ok, " + P.name() + " to " + S.name();
	}

	private String tryCapture(Piece P, Space oldpos, Space S)
	{
		Piece oldpiece = S.getOccupant();
		S.removeOccupant();
		if (S.addOccupant(P) == false)
		{
			S.addOccupant(oldpiece);
			return "error, space could not be taken over";
		}
		oldpos.removeOccupant();

		return "ok, " + P.name() + " captured " + oldpiece.name() + "* at "
				+ S.name();
	}
}