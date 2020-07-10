package Chess.game;

public class Bishop extends Piece{

	boolean color;
	String location;

	Bishop(boolean isWhite, String location){
		this.color = isWhite ? true:false;
		this.location = location;
	}

	@Override
	public boolean isWhite() {
		return this.color;
	}

	public char toChar(){
		if (color) return 'B'; else return 'b';
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

		if (Math.abs(col1 - col2) == Math.abs(row1 - row2)) return true;
		return false;
	}
}
