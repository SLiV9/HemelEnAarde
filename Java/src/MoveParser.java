class MoveParser extends LineParser
{
	MoveParser()
	{

	}

	String parse(Board B, String arg)
	{
		if (arg.length() < 4)
			return "fail, syntax too short";

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
				if (P.inOpposingCity())
				{
					P.reveal();
					return "ok, player wins!";
				}

				return "fail, not in opponents city";
			}

			argpos++;
		}

		char movetype = 'm';
		if (argpos < arg.length() - 2)
		{
			switch (arg.charAt(argpos))
			{
			case 'x':
			case 'q':
			case 'r':
				movetype = arg.charAt(argpos);
				argpos++;
				break;
			}
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
					P.reveal();
					return tryMove(P, oldpos, S);
				}
				else
					return "fail, illegal move";
			}
			else if (movetype == 'x')
			{
				if (P.canCaptureX(S))
				{
					P.reveal();
					return tryCapture(P, oldpos, S);
				}
				else
					return "fail, illegal move";
			}
			else
			{
				return "fail, must move or capture";
			}
		}
		else if (ct == CharacterType.GENERAL || ct == CharacterType.HUNTER)
		{
			if (movetype == 'x')
			{
				if (P.canCaptureX(S))
				{
					P.reveal();
					return tryCapture(P, oldpos, S);
				}
				else
					return "fail, illegal move";
			}
			else
			{
				return "fail, must capture";
			}
		}
		else if (ct == CharacterType.STRATEGIST)
		{
			if (movetype != 'q')
			{
				return "fail, must inspect";
			}

			if (P.canAim(S))
			{
				P.reveal();
				return tryInspect(P, oldpos, S);
			}
			else
				return "fail, illegal move";
		}
		else if (ct == CharacterType.ALCHEMIST)
		{
			if (movetype != 'r')
			{
				return "fail, must remove";
			}

			if (P.canAim(S))
			{
				P.reveal();
				return tryShoot(P, oldpos, S);
			}
			else
				return "fail, illegal move";
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

	private String tryShoot(Piece P, Space oldpos, Space S)
	{
		Piece oldpiece = S.getOccupant();
		S.removeOccupant();

		return "ok, " + P.name() + " removed " + oldpiece.name() + "* at "
				+ S.name();
	}

	private String tryInspect(Piece P, Space oldpos, Space S)
	{
		Piece oldpiece = S.getOccupant();
		oldpiece.reveal();

		return "ok, " + P.name() + " inspected " + oldpiece.name() + " at "
				+ S.name();
	}
}