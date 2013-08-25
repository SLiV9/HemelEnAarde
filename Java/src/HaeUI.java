import java.awt.*;

import javax.swing.JFrame;

public class HaeUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	HaeUI(String name, Board b)
	{
		super(name);
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBackground(Color.white);
		
		Container cp = getContentPane();
		
		BoardDrawer bd = new BoardDrawer(b);
		
		cp.add(bd);
		
		pack();
		setVisible(true);
	}
}