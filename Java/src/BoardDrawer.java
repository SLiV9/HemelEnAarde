import javax.swing.JPanel;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;

public class BoardDrawer extends JPanel
{
	private static final long serialVersionUID = 1L;

	static final double SQRT3 = Math.sqrt(3);
	static final double FRAC6 = 2 * Math.PI / 6;

	static final Color C_FELT = new Color(90, 100, 110);
	static final Color C_LAND = new Color(230, 230, 210);
	static final Color C_CITY = new Color(220, 180, 180);
	static final Color C_GARDEN = new Color(170, 200, 170);
	static final Color C_SOUTH = new Color(220, 50, 50);
	static final Color C_NORTH = new Color(50, 200, 50);

	private int centerx, centery, boardwh;
	private int hfr, hgr, dw, dh, size, diskr;

	Board B;

	BoardDrawer(Board b)
	{
		B = b;

		setPreferredSize(new Dimension(400, 400));
	}

	public void paint(Graphics gg)
	{
		Graphics2D g = (Graphics2D) gg;

		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		g.setColor(C_FELT);

		centerx = getWidth() / 2;
		centery = getHeight() / 2;
		boardwh = Math.min(getWidth(), getHeight()) * 9 / 20;
		hfr = Math.min(getWidth(), getHeight()) / 40;
		hgr = hfr * 3 / 4;
		dw = (int) (hfr * BoardDrawer.SQRT3);
		dh = 3 * hfr;
		size = 2 * hfr;
		diskr = hfr * 3 / 2;
		g.fillRect(centerx - boardwh, centery - boardwh, 2 * boardwh,
				2 * boardwh);

		drawSpaces(g);
		drawPieces(g);
	}

	private void drawSpaces(Graphics2D g)
	{
		if (B == null || B.spacesOnBoard == null)
			return;

		Color cback;
		Polygon hexapol = new Polygon();
		for (int i = 0; i < 6; i++)
		{
			hexapol.addPoint(
					((int) (Math.round(size * Math.cos((i + 0.5) * FRAC6)))),
					((int) (Math.round(size * Math.sin((i + 0.5) * FRAC6)))));
		}

		g.translate(centerx, centery);
		for (Space S : B.spacesOnBoard)
		{
			if (Space.isValid(S))
			{
				if (S.isGarden())
				{
					cback = C_GARDEN;
				}
				else if (S.isCity())
				{
					cback = C_CITY;
				}
				else
				{
					cback = C_LAND;
				}
				g.translate(S.col * dw, S.row * dh);
				g.setColor(cback);
				g.fillPolygon(hexapol);

				if (S.isGate())
				{
					g.setColor(C_CITY);
					g.fillOval(-hgr, -hgr, 2 * hgr, 2 * hgr);
					g.setColor(Color.darkGray);
					g.setStroke(new BasicStroke(1));
					g.drawOval(-hgr, -hgr, 2 * hgr, 2 * hgr);
				}
				g.translate(-S.col * dw, -S.row * dh);
			}
			else
			{
				System.out.println("Invalid space.");
			}
		}
		for (Space S : B.spacesOnBoard)
		{
			if (Space.isValid(S))
			{
				g.translate(S.col * dw, S.row * dh);
				if (S.isGarden() || S.isCity())
				{
					g.setColor(Color.darkGray);
					g.setStroke(new BasicStroke(2));
					g.drawPolygon(hexapol);
				}
				else
				{
					g.setColor(Color.darkGray);
					g.setStroke(new BasicStroke(1));
					g.drawPolygon(hexapol);
				}
				g.translate(-S.col * dw, -S.row * dh);
			}
			else
			{
				System.out.println("Invalid space.");
			}
		}
		g.translate(-centerx, -centery);
	}

	private void drawPieces(Graphics2D g)
	{
		if (B == null || B.piecesOnBoard == null)
		{
			return;
		}

		Color cback;
		Font fnt = new Font("Times New Roman", Font.BOLD, 24);
		g.setFont(fnt);
		FontMetrics fm = g.getFontMetrics();
		FontRenderContext frc = g.getFontRenderContext();
		int dx, dy;
		
		String str;
		g.translate(centerx, centery);
		for (Piece P : B.piecesOnBoard)
		{
			switch (P.getEmpire())
			{
			case SOUTH:
				cback = C_SOUTH;
				break;
			case NORTH:
				cback = C_NORTH;
				break;
			default:
				cback = Color.pink;
			}

			Space S = P.position;
			g.translate(S.col * dw, S.row * dh);
			g.setColor(cback);
			g.fillOval(-diskr, -diskr, 2 * diskr, 2 * diskr);
			g.setColor(Color.black);
			g.setStroke(new BasicStroke(3));
			g.drawOval(-diskr, -diskr, 2 * diskr, 2 * diskr);

			str = P.image(Empire.SOUTH);
			dx = (int) (- fm.getStringBounds(str, g).getWidth() / 2);
			dy = (fm.getAscent() - fm.getDescent()) / 2;
			
			g.setColor(Color.black);
			g.drawString(str, dx, dy);
			g.translate(-S.col * dw, -S.row * dh);
		}
		g.translate(-centerx, -centery);
	}
}