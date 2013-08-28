import java.util.Scanner;

public class SetupMain
{
	public static void main(String[] args)
	{
		System.out.println("[ Heaven & Earth ]\n");

		Board B = new Board();
		LineParser LP = new LineParser(new CommandParser(), new MoveParser());
		Body.makeTables();
		Body.fillTables();
		Character.makeTables();
		Character.fillTables();
		
		B.print();

		HaeUI H = new HaeUI("HAE Test", B, LP);
		
		Scanner sc = new Scanner(System.in);
		String command, result;
		for (int i = 0; i < 1000; i++)
		{
			System.out.println();
			System.out.print(":: ");
			command = sc.nextLine();

			if (command.contains("exit"))
			{
				break;
			}

			result = LP.parse(B, command);
			System.out.println(">> " + result);
			
			if (result.contains("ok"))
			{
				B.print();
				H.repaint();
			}
		}
		
		// TODO: player initiation

		// TODO: piece placement

		// TODO: game loop

		System.out.println("\n[ done ]");
	}
}