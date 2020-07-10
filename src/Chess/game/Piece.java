package Chess.game;

public abstract class Piece implements Chess.common.ChessPiece {

	abstract public boolean isWhite();

	abstract public char toChar();

	abstract public void setLocation(String location);

	abstract public String getLocation();

	abstract public boolean isMoveValid(String targetSqr);
}
