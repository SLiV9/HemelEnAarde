import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BoardDrawer extends JPanel implements MouseListener
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
	static final Color C_MOVE = new Color(130, 190, 220);
	static final Color C_MOVEX = new Color(200, 50, 210);
	static final Color C_CAPTURE = new Color(240, 180, 130);
	static final Color C_CAPTUREX = new Color(240, 130, 250);
	static final Color C_REMOVE = new Color(240, 130, 250);
	static final Color C_INSPECT = new Color(220, 220, 220);

	private int centerx, centery, boardwh;
	private int hfr, hgr, hxr, dw, dh, diskr;

	private Space selectedS;
	private Piece selectedP;

	Board B;

	BoardDrawer(Board b)
	{
		B = b;

		setPreferredSize(new Dimension(400, 400));

		addMouseListener(this);
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
		hxr = hfr / 3;
		dw = (int) (hfr * BoardDrawer.SQRT3);
		dh = 3 * hfr;
		diskr = hfr * 3 / 2;
		g.fillRect(centerx - boardwh, centery - boardwh, 2 * boardwh,
				2 * boardwh);

		drawBoard(g);
	}

	private void drawBoard(Graphics2D g)
	{
		if (B == null || B.spacesOnBoard == null || B.piecesOnBoard == null)
			return;

		Color cback, cring;
		Polygon hexapol = new Polygon();
		hexapol.addPoint(0, -2 * hfr);
		hexapol.addPoint(dw, -hfr);
		hexapol.addPoint(dw, +hfr);
		hexapol.addPoint(0, 2 * hfr);
		hexapol.addPoint(-dw, +hfr);
		hexapol.addPoint(-dw, -hfr);

		Font fnt = new Font("Times New Roman", Font.BOLD, 24);
		g.setFont(fnt);
		FontMetrics fm = g.getFontMetrics();
		int dx, dy;
		String str;

		g.translate(centerx, centery);

		/* DRAWBOARD: SPACES */
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

		/* DRAWBOARD: PIECES */
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
			if (P == selectedP)
			{
				g.setColor(Color.blue);
				g.setStroke(new BasicStroke(4));
			}
			else
			{
				g.setColor(Color.black);
				g.setStroke(new BasicStroke(3));
			}
			g.drawOval(-diskr, -diskr, 2 * diskr, 2 * diskr);

			str = P.image(Empire.SOUTH);
			dx = (int) (-fm.getStringBounds(str, g).getWidth() / 2);
			dy = (fm.getAscent() - fm.getDescent()) / 2;

			g.setColor(Color.black);
			g.drawString(str, dx, dy);
			g.translate(-S.col * dw, -S.row * dh);
		}

		/* DRAWBOARD: EDGES AND RINGS */
		Piece.setShowCannots(false);
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

				cring = null;
				if (S == selectedS)
				{
					cring = Color.blue;
				}

				if (selectedP != null && Space.isValid(selectedP.position))
				{
					if (selectedP.canMove(S))
					{
						cring = C_MOVE;
					}
					else if (selectedP.canCapture(S))
					{
						cring = C_CAPTURE;
					}
					else if (selectedP.canMoveX(S))
					{
						cring = C_MOVEX;
					}
					else if (selectedP.canCaptureX(S))
					{
						cring = C_CAPTUREX;
					}
				}

				if (cring != null)
				{
					g.setColor(cring);
					g.setStroke(new BasicStroke(6));
					g.drawOval(-hfr, -hfr, 2 * hfr, 2 * hfr);
				}

				if (selectedP != null && Space.isValid(selectedP.position))
				{
					if (selectedP.canAim(S))
					{
						switch (selectedP.getCharacterType())
						{
						case STRATEGIST:
							g.setColor(C_INSPECT);
							g.fillRect(hfr / 2, -1 * hxr, 2 * hxr, 2 * hxr);
							break;
						case ALCHEMIST:
							g.setColor(C_REMOVE);
							g.setStroke(new BasicStroke(8));
							g.drawLine(2 * hxr, 0, 4 * hxr, 2 * hxr);
							g.drawLine(4 * hxr, 0, 2 * hxr, 2 * hxr);
							break;
						}
					}
				}

				g.translate(-S.col * dw, -S.row * dh);
			}
			else
			{
				System.out.println("Invalid space.");
			}
		}

		/* DRAWBOARD: END */
		g.translate(-centerx, -centery);
	}

	/* Mouse Listening */
	public void mouseClicked(MouseEvent me)
	{
	}

	public void mouseEntered(MouseEvent me)
	{
	}

	public void mouseExited(MouseEvent me)
	{
	}

	public void mousePressed(MouseEvent me)
	{
		int mousex, mousey;
		mousex = me.getX();
		mousey = me.getY();

		selectedS = null;
		selectedP = null;

		if (mousex < centerx - boardwh || mousex > centerx + boardwh
				|| mousey < centery - boardwh || mousey > centery + boardwh)
		{
			return;
		}

		int sx, sy, dx, dy;
		for (Space S : B.spacesOnBoard)
		{
			sx = centerx + S.col * dw;
			sy = centery + S.row * dh;
			dx = mousex - sx;
			dy = mousey - sy;

			if (dx * dx + dy * dy < diskr * diskr)
			{
				if (S.isOccupied())
				{
					selectedP = S.getOccupant();
				}
				else
				{
					selectedS = S;
				}
			}
		}

		if (selectedP != null || selectedS != null)
		{
			repaint();
		}
	}

	public void mouseReleased(MouseEvent me)
	{
	}
}