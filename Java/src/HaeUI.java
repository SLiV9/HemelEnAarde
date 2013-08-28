import java.awt.*;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class HaeUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	BoardDrawer BD;
	ConsoleDrawer CD;

	HaeUI(String name, Board b, LineParser lp)
	{
		super(name);

		// JFrame.setDefaultLookAndFeelDecorated(true);
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			System.out.println("UIManager failed: " + e);
		}

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;

		BD = new BoardDrawer(b);
		//CD = new ConsoleDrawer(lp);

		gbc.weightx = 2;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(BD, gbc);

		/*gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(CD, gbc);*/

		pack();
		setVisible(true);
	}

	public void repaint()
	{
		BD.repaint();
	}
}