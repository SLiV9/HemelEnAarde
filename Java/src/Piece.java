public class Piece
{
	private Body B;
	private Character C;
	private Empire E;
	
	Board platform;
	Space position;
	
	// TODO: isrevealed
	
	/* Queries */
	boolean canMove(Space S)
	{
		return B.canMove(S);
	}
	
	boolean canCapture(Space S)
	{
		return B.canCapture(S);
	}
	
	boolean canMoveX(Space S)
	{
		return C.canMove(S);
	}
	
	boolean canCaptureX(Space S)
	{
		return C.canCapture(S);
	}
	
	int getRank()
	{
		return B.getRank();
	}
	
	boolean isOpposing(Piece other)
	{
		return (other.E != E);
	}
	
	BodyType getBodyType()
	{
		return B.getType();
	}
	
	CharacterType getCharacterType()
	{
		return C.getType();
	}
	
	String Initials()
	{
		return "" + B.getInitial() + C.getInitial();
	}
}