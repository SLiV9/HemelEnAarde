class LineParser
{
	private static CommandParser CP;
	private static MoveParser MP;

	LineParser()
	{

	}

	LineParser(CommandParser cp, MoveParser mp)
	{
		CP = cp;
		MP = mp;
	}
	String parse(Board B, String command)
	{
		String resultm, resultc;

		resultm = MP.parse(B, command);
		if (!resultm.contains("what"))
		{
			return resultm;
		}

		resultc = CP.parse(B, command);
		if (!resultc.contains("what"))
		{
			return resultc;
		}

		return "fail, " + resultm.substring(resultc.indexOf(" ") + 1) + " / "
				+ resultc.substring(resultc.indexOf(" ") + 1);
	}
}