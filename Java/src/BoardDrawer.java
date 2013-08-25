import javax.swing.JPanel;

import java.awt.*;
import java.util.ArrayList;

public class BoardDrawer extends JPanel
{
	private static final long serialVersionUID = 1L;

	static final double SQRT3 = Math.sqrt(3);

	private static int centerx, centery;

	Board B;

	BoardDrawer(Board b)
	{
		B = b;

		setPreferredSize(new Dimension(400, 400));
		centerx = 200;
		centery = 200;
	}

	public void paint(Graphics gg)
	{
		Graphics2D g = (Graphics2D) gg;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g.setColor(Color.black);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.blue);
		drawSpaces(g, B.spacesOnBoard);
	}

	private static void drawSpaces(Graphics2D g, ArrayList<Space> als)
	{
		Color c;

		for (Space S : als)
		{
			if (Space.isValid(S))
			{
				if (S.isGarden())
				{
					c = Color.green;
				}
				else if (S.isCity())
				{
					c = Color.red;
				}
				else if (S.isGate())
				{
					c = Color.blue;
				}
				else
				{
					c = Color.white;
				}
				g.setColor(c);
				drawHexagon(g, S.row, S.col);
			}
			else
			{
				System.out.println("Invalid space.");
			}
		}
	}

	private static void drawHexagon(Graphics2D g, int row, int col)
	{

		/*g.fillOval(centerx + col * 20 - size, centery
				+ ((int) (row * 20 * BoardDrawer.SQRT3)) - size, 2 * size,
				2 * size);*/

		double frac = 2 * Math.PI / 6;
		int dw, dh, size;
		dw = 20;
		dh = (int) (dw * BoardDrawer.SQRT3);
		size = dw;
		
		Polygon p = new Polygon();
		for (int i = 0; i < 6; i++)
		{
			p.addPoint(
					centerx + col * dw + ((int) (size * Math.cos((i + 0.5) * frac))),
					centery + row * dh + ((int) (size * Math.sin((i + 0.5) * frac))));
		}
		g.fillPolygon(p);
	}
}