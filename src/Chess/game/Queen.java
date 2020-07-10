package Chess.game;

public class Queen extends Piece{

	boolean color;
	String location;

	Queen(boolean isWhite, String location){
		color = isWhite ? true : false;
		this.location = location;
	}

	public boolean isWhite() {
		return color;
	}

	public char toChar(){
			if (color) return 'Q'; else return 'q';
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return this.location;
	}

	public boolean isMoveValid(String targetSqr) {
		char col1 = this.location.charAt(0);
		char row1 = this.location.charAt(1);

		char col2 = targetSqr.charAt(0);
		char row2 = targetSqr.charAt(1);

		if ((col1 == col2) || (row1 == row2)) return true;
		if (Math.abs(col1 - col2) == Math.abs(row1 - row2)) return true;

		return false;
	}
}
