class LineParser
{
	protected static Board platform;

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

	void setPlatform(Board B)
	{
		platform = B;
	}

	String parse(String command)
	{
		String resultm, resultc;

		resultm = MP.parse(command);
		if (!resultm.contains("what"))
		{
			return resultm;
		}

		resultc = CP.parse(command);
		if (!resultc.contains("what"))
		{
			return resultc;
		}

		return "fail, " + resultm.substring(resultc.indexOf(" ") + 1) + " / "
				+ resultc.substring(resultc.indexOf(" ") + 1);
	}
}