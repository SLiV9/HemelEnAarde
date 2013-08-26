import java.awt.*;

import javax.swing.JFrame;

public class HaeUI extends JFrame
{
	private static final long serialVersionUID = 1L;

	BoardDrawer BD;

	HaeUI(String name, Board b)
	{
		super(name);

		JFrame.setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);

		Container cp = getContentPane();

		BD = new BoardDrawer(b);

		cp.add(BD);

		pack();
		setVisible(true);
	}

	public void repaint()
	{
		BD.repaint();
	}
}