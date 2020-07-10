package Chess.game;

public class Knight extends Piece{

	boolean color;
	String location;

	Knight(boolean isWhite, String location){
		color = isWhite ? true:false;
		this.location = location;
	}

	@Override
	public boolean isWhite() {
		return color;
	}

	public char toChar(){
		if (color) return 'N'; else return 'n';
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

		if ((Math.abs(col1-col2) == 2) && (Math.abs(row1-row2) == 1))return true;
		if ((Math.abs(row1-row2) == 2) && (Math.abs(col1-col2) == 1))return true;

		return false;
	}
}
