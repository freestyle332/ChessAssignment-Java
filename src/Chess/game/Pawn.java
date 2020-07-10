package Chess.game;

public class Pawn extends Piece{
	
	boolean color;
	String location;


	Pawn(boolean isWhite, String location){
		color = isWhite ? true:false;
		this.location = location;
	}

	public boolean isWhite() {
		return color;
	}

	public char toChar(){
		if (color) return 'I'; else return 'i';
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

        if ((color && row2>row1) || (!color && row2<row1)) {
            if ((col1 != col2) && (Math.abs(col1 - col2) == 1) && (row1 != row2) && (Math.abs(row1 - row2) == 1))
                return true;
            if ((col1 == col2) && (Math.abs(row1 - row2) < 2)) return true;
            if (color && (row1 == '2') && (row2 == '4')) return true;
            if (!color && (row1 == '7') && (row2 == '5')) return true;
        }
		return false;
	}

}
