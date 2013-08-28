import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class ConsoleDrawer extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	LineParser LP;
	JTextArea inputCommand;
	JButton doCommand;
	
	ConsoleDrawer(LineParser lp)
	{
		LP = lp;
		
		setPreferredSize(new Dimension(200, 400));
		
		inputCommand = new JTextArea();
		doCommand = new JButton("Ok");
		
		add(inputCommand);
		add(doCommand);
	}
}