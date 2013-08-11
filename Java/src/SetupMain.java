import java.util.Scanner;

public class SetupMain
{
	public static void main(String[] args)
	{
		System.out.println("[ Heaven & Earth ]\n");

		Board B = new Board();
		LineParser LP = new LineParser(new CommandParser(), new MoveParser());
		LP.setPlatform(B);
		Scanner sc = new Scanner(System.in);

		// TODO: player initiation

		// TODO: piece placement

		// TODO: game loop

		String command, result;
		for (int i = 0; i < 1000; i++)
		{
			System.out.print(":: ");
			command = sc.nextLine();

			if (command.contains("exit"))
			{
				break;
			}

			result = LP.parse(command);
			System.out.println("\t>> " + result);
			
			if (result.contains("ok"))
			{
				B.print();
			}
		}

		System.out.println("\n[ done ]");
	}
}