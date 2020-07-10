package Chess.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import Chess.common.*;

public class Board implements ChessBoard {
	
	public Piece[] pieces = new Piece[32];
	final String REGEX = "[a-hA-H][1-8] [a-hA-H][1-8]";
	public int moveCounter=1;
	public boolean whitesTurn=true;
	public int counterForTwoMoves=0;

	public Board() {
		pieces[0] = new Rook(false, "a8");
		pieces[1] = new Knight(false, "b8");
		pieces[2] = new Bishop(false, "c8");
		pieces[4] = new King(false, "e8" );
		pieces[3] = new Queen(false, "d8");
		pieces[5] = new Bishop(false, "f8");
		pieces[6] = new Knight(false, "g8");
		pieces[7] = new Rook(false, "h8");
		pieces[8] = new Pawn(false, "a7");
		pieces[9] = new Pawn(false, "b7");
		pieces[10] = new Pawn(false, "c7");
		pieces[11] = new Pawn(false, "d7");
		pieces[12] = new Pawn(false, "e7");
		pieces[13] = new Pawn(false, "f7");
		pieces[14] = new Pawn(false, "g7");
		pieces[15] = new Pawn(false, "h7");
		
		pieces[16] = new Rook(true, "a1");
		pieces[17] = new Knight(true, "b1");
		pieces[18] = new Bishop(true, "c1");
		pieces[20] = new King(true, "e1");
		pieces[19] = new Queen(true, "d1");
		pieces[21] = new Bishop(true, "f1");
		pieces[22] = new Knight(true, "g1");
		pieces[23] = new Rook(true, "h1");
		pieces[24] = new Pawn(true, "a2");
		pieces[25] = new Pawn(true, "b2");
		pieces[26] = new Pawn(true, "c2");
		pieces[27] = new Pawn(true, "d2");
		pieces[28] = new Pawn(true, "e2");
		pieces[29] = new Pawn(true, "f2");
		pieces[30] = new Pawn(true, "g2");
		pieces[31] = new Pawn(true, "h2");
	}

	//Empty chess board
   static final String[][] EMPTY_CHESS_BOARD = new String[][] {
			{ " ", " ", " a ", " ", " b ", " ", " c ", " ", " d ", " ", " e ", " ", " f ", " ", " g ", " ", " h ", " ", " " },
			{ " ", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", " " },
			{ "8", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "8" },
			{ " ", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", " " },
			{ "7", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", "7" },
			{ " ", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", " " },
			{ "6", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "6" },
			{ " ", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", " " },
			{ "5", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", "5" },
			{ " ", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", " " },
			{ "4", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "4" },
			{ " ", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", " " },
			{ "3", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", "3" },
			{ " ", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", " " },
			{ "2", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "2" },
			{ " ", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", " " },
			{ "1", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", " . ", "|", "   ", "|", "1" },
			{ " ", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", "---", "+", " " },
			{ " ", " ", " a ", " ", " b ", " ", " c ", " ", " d ", " ", " e ", " ", " f ", " ", " g ", " ", " h ", " ", " " } };

	// returns captured piece or null if nothing was captured
	public ChessPiece move(String moveText) throws ChessException{
		
		String[] moveCheck = moveText.split(" ");
		Piece pieceInLocation = getPiece(moveCheck[0]); //piece in the start square

		//FIRST: Checks if entry is correct
		if (!moveText.matches(REGEX) || moveCheck[0].equals(moveCheck[1]))
			throw new ChessException(ChessException.ChessError.MOVE_PARSE_ERROR);
		
		//SECOND: Checks if any piece exists in the source square
		if (!isAnyPieceAtTheLocation(moveCheck[0]))
		    throw new ChessException(ChessException.ChessError.NO_PIECE_TO_MOVE);

		//THIRD: Checks if player moves his piece (not opponent's!)
		if ((!whitesTurn && pieceInLocation.isWhite()) || (whitesTurn && !pieceInLocation.isWhite()))
		    throw new ChessException(ChessException.ChessError.CANNOT_MOVE_OPPONENTS_PIECE);

		//FOURTH: Checks if it is a valid move
		if (!pieceInLocation.isMoveValid(moveCheck[1]))
			throw new ChessException(ChessException.ChessError.MOVE_RULE_VIOLATION);

		if ((Character.toLowerCase(pieceInLocation.toChar()) == 'i') && itsShamefulToWriteAMethodOnlyToCheckOneSpecificMove(moveCheck[0], moveCheck[1]))
            throw new ChessException(ChessException.ChessError.MOVE_RULE_VIOLATION);

		//FIFTH: Checks if any piece on the way (except for the knight)
        if (isAnyPieceOnTheWay(moveCheck[0], moveCheck[1], pieceInLocation.toChar()))
            throw new ChessException(ChessException.ChessError.JUMP_OVER_PIECE_NOT_ALLOWED);

        Piece pieceAtTargetLocation = getPiece(moveCheck[1]);//piece in the target square

        //SIXTH: Checks if the piece at the target square is not own piece
        if (pieceAtTargetLocation != null){
            if ((whitesTurn && pieceAtTargetLocation.isWhite()) || ((!whitesTurn && !pieceAtTargetLocation.isWhite())) )
                throw new ChessException(ChessException.ChessError.CANNOT_CAPTURE_OWN_PIECE);
        }

		counterForTwoMoves++;
		whitesTurn = false;
		
		if (counterForTwoMoves==2) {
			whitesTurn = true;
			counterForTwoMoves=0;
			moveCounter++;
		}

        if (pieceAtTargetLocation != null){
            for (int i = 0; i<32; i++ ) {
                if ((pieces[i] != null) && (pieces[i].getLocation().equals(moveCheck[1]))){
                    pieces[i] = null;
                    break;
                }
            }
        }

        //Checks if a pawn reaches row 1 or 8 (if so promote it to queen)
        if ((Character.toLowerCase(pieceInLocation.toChar()) == 'i') && ((moveCheck[1].charAt(1) == '1') || moveCheck[1].charAt(1) == '8'))
            promotePawnToQueen(pieceInLocation, moveCheck[1]);

        //if the move is all valid then set the new location for the piece
        pieceInLocation.setLocation(moveCheck[1]);


        return pieceAtTargetLocation;

	}

	public void promotePawnToQueen(Piece piece, String location){
        for (int i=0; i<32; i++) {
            if (pieces[i] != null)
                if ((pieces[i] != null) && pieces[i].getLocation().equals(piece.getLocation())) {
                    if (pieces[i].isWhite())
                        pieces[i] = new Queen(true, location);
                    else pieces[i] = new Queen(false, location);
                    break;
                }
        }

    }

	public Piece getPiece(String location){
        for (int i=0; i<32; i++) {
            if (pieces[i] != null)
                if ((pieces[i] != null) && pieces[i].getLocation().equals(location)) {
                    return pieces[i];
                }
        }
        return null;
    }

	public boolean isAnyPieceAtTheLocation(String location){
	    for (int i=0; i<32; i++) {
            if (pieces[i] != null)
            if ((pieces[i] != null) && pieces[i].getLocation().equals(location)) {
                return true;
            }
        }
        return false;
    }

    public boolean itsShamefulToWriteAMethodOnlyToCheckOneSpecificMove(String startSqr, String targetSqr){

	    char col1 = startSqr.charAt(0);
        char row1 = startSqr.charAt(1);

        char col2 = targetSqr.charAt(0);
        char row2 = targetSqr.charAt(1);

        if (((col1 != col2) && (Math.abs(col1 - col2) == 1) && (row1 != row2) && (Math.abs(row1 - row2) == 1))
                && !isAnyPieceAtTheLocation(targetSqr))
               return true;

        return false;
    }


	public boolean isAnyPieceOnTheWay(String startPos, String endPos, char pieceType){

        String startCoordination = convertToCoordination(startPos);
        String endCoordination = convertToCoordination(endPos);

        int col1 = Character.getNumericValue(startCoordination.charAt(0));
        int row1 = Character.getNumericValue(startCoordination.charAt(1));

        int col2 = Character.getNumericValue(endCoordination.charAt(0));
        int row2 = Character.getNumericValue(endCoordination.charAt(1));

//System.out.println("start: "+startPos+" end: "+endPos+" col1-row1: "+col1+row1+ " col2-row2: "+col2+row2);
        switch (pieceType){
            case 'r':
            case 'R':
                 if (col1 == col2)
                     if (row1 > row2) {
                     for (int i = row1-1; i > row2; i--) {
                         if (isAnyPieceAtTheLocation(convertToLocation(col1, i))) return true;
                     }
                     } else {
                         for (int i =row1+1; i < row2; i++){
                         if (isAnyPieceAtTheLocation(convertToLocation(col1, i))) return true;
                         }
                     }
                     if (row1 == row2)
                        if (col1 > col2) {
                            for (int i = col1-1; i > col2; i--) {
                                if (isAnyPieceAtTheLocation(convertToLocation(i, row1))) return true;
                            }
                        } else {
                            for (int i =col1+1; i < col2; i++){
                                if (isAnyPieceAtTheLocation(convertToLocation(i, row1))) return true;
                            }
                        }
                        break;
            case 'b':
            case 'B':
                if ((col1 > col2) && (row1 > row2))
                    for (int i = col1-1; i> col2; i--)
                        if (isAnyPieceAtTheLocation(convertToLocation(i, --row1))) return true;

                if ((col1 < col2) && (row1 > row2))
                    for (int i = col1+1; i< col2; i++)
                        if (isAnyPieceAtTheLocation(convertToLocation(i, --row1))) return true;

                if ((col1 > col2) && (row1 < row2))
                    for (int i = col1-1; i> col2; i--)
                        if (isAnyPieceAtTheLocation(convertToLocation(i, ++row1))) return true;

                if ((col1 < col2) && (row1 < row2))
                    for (int i = col1+1; i< col2; i++)
                        if (isAnyPieceAtTheLocation(convertToLocation(i, ++row1))) return true;
                break;
            case 'q':
            case 'Q':
                if (col1 == col2)
                    if (row1 > row2) {
                        for (int i = row1-1; i > row2; i--) {
                            if (isAnyPieceAtTheLocation(convertToLocation(col1, i))) return true;
                        }
                    } else {
                        for (int i =row1+1; i < row2; i++){
                            if (isAnyPieceAtTheLocation(convertToLocation(col1, i))) return true;
                        }
                    }
                if (row1 == row2)
                    if (col1 > col2) {
                        for (int i = col1-1; i > col2; i--) {
                            if (isAnyPieceAtTheLocation(convertToLocation(i, row1))) return true;
                        }
                    } else {
                        for (int i =col1+1; i < col2; i++){
                            if (isAnyPieceAtTheLocation(convertToLocation(i, row1))) return true;
                        }
                    }
                if ((col1 > col2) && (row1 > row2))
                    for (int i = col1-1; i> col2; i--)
                        if (isAnyPieceAtTheLocation(convertToLocation(i, --row1))) return true;

                if ((col1 < col2) && (row1 > row2))
                    for (int i = col1+1; i< col2; i++)
                        if (isAnyPieceAtTheLocation(convertToLocation(i, --row1))) return true;

                if ((col1 > col2) && (row1 < row2))
                    for (int i = col1-1; i> col2; i--)
                        if (isAnyPieceAtTheLocation(convertToLocation(i, ++row1))) return true;

                if ((col1 < col2) && (row1 < row2))
                    for (int i = col1+1; i< col2; i++)
                        if (isAnyPieceAtTheLocation(convertToLocation(i, ++row1))) return true;
                break;
            case 'i':
            case 'I':
                if (col1 == col2)
                    if (isAnyPieceAtTheLocation(endPos)) return true;
                break;

        }

        return false;
    }


	public boolean isWhitesTurn() {
		return whitesTurn;
	}

	public int getMoveCount() {
		return moveCounter;
	}


	public String convertToCoordination(String location) {
        String coordination = "";

        switch(location.charAt(0)){
            case 'a': coordination = "1"; break;
            case 'b': coordination = "2"; break;
            case 'c': coordination = "3"; break;
            case 'd': coordination = "4"; break;
            case 'e': coordination = "5"; break;
            case 'f': coordination = "6"; break;
            case 'g': coordination = "7"; break;
            case 'h': coordination = "8"; break;
        }

        switch(location.charAt(1)){
            case '1': coordination += "8"; break;
            case '2': coordination += "7"; break;
            case '3': coordination += "6"; break;
            case '4': coordination += "5"; break;
            case '5': coordination += "4"; break;
            case '6': coordination += "3"; break;
            case '7': coordination += "2"; break;
            case '8': coordination += "1"; break;
        }

        return coordination;
    }

    public String convertToLocation(int k, int l) {
        String loc = "";

        switch(k){
            case 1: loc = "a"; break;
            case 2: loc = "b"; break;
            case 3: loc = "c"; break;
            case 4: loc = "d"; break;
            case 5: loc = "e"; break;
            case 6: loc = "f"; break;
            case 7: loc = "g"; break;
            case 8: loc = "h"; break;
        }

        switch(l){
            case 1: loc += "8"; break;
            case 2: loc += "7"; break;
            case 3: loc += "6"; break;
            case 4: loc += "5"; break;
            case 5: loc += "4"; break;
            case 6: loc += "3"; break;
            case 7: loc += "2"; break;
            case 8: loc += "1"; break;
        }

        return loc;
    }


	public String getDisplayString() {
		String coord, s= "";
		String[][] b = new String[19][19];

        int coord1, coord2 = 0;

        for (int i = 0; i < 19; i++) {
            for (int j = 0; j < 19; j++)
                b[i][j] = EMPTY_CHESS_BOARD[i][j];
        }

		for (int x=0; x<32; x++){
            if (pieces[x]!=null) {
                coord = convertToCoordination(pieces[x].getLocation());
                coord1 = Character.getNumericValue(coord.charAt(0));
                coord2 = Character.getNumericValue(coord.charAt(1));
                b[coord2 * 2][coord1 * 2] = " " + (pieces[x].toChar()) + " ";
            }
        }

		for (int i = 0; i < 19; i++) {
			for (int j = 0; j < 19; j++)
				s += b[i][j];
			s += "\n";
		}
		
		return s;
	}

	public void load(BufferedReader reader) throws IOException {
		String s="";
        int index = 0;

        for (int k = 1; k < 9; k++) {
            s = reader.readLine();

            for (int l = 1; l < 9; l++) {
              switch (s.charAt(l-1)) {
                case 'r':
                    pieces[index++] = new Rook(false, convertToLocation(l , k));
                    break;
                case 'b':
                    pieces[index++] = new Bishop(false, convertToLocation(l, k));
                    break;
                case 'n':
                    pieces[index++] = new Knight(false, convertToLocation(l , k));
                    break;
                case 'q':
                    pieces[index++] = new Queen(false, convertToLocation(l, k));
                    break;
                case 'k':
                    pieces[index++] = new King(false, convertToLocation(l, k));
                    break;
                case 'R':
                    pieces[index++] = new Rook(true, convertToLocation(l, k));
                    break;
                case 'B':
                    pieces[index++] = new Bishop(true, convertToLocation(l, k));
                    break;
                case 'N':
                    pieces[index++] = new Knight(true, convertToLocation(l, k));
                    break;
                case 'K':
                    pieces[index++] = new King(true, convertToLocation(l, k));
                    break;
                case 'Q':
                    pieces[index++] = new Queen(true, convertToLocation(l, k));
                    break;
                case 'i':
                    pieces[index++] = new Pawn(false, convertToLocation(l, k));
                    break;
                case 'I':
                    pieces[index++] = new Pawn(true, convertToLocation(l, k));
                    break;
                }

            }
        }

        while (index<32)
            {pieces[index++] = null;}

        reader.close();

        moveCounter=1;
        whitesTurn=true;
	}
		

	public void save(PrintWriter writer) {

        String coord, s = "";
        String[][] temp = new String[8][8];

        int coord1, coord2 = 0;

        for (int x = 0; x < 32; x++) {
            if (pieces[x]!=null) {
                coord = convertToCoordination(pieces[x].getLocation());
                coord1 = Character.getNumericValue(coord.charAt(0));
                coord2 = Character.getNumericValue(coord.charAt(1));
                temp[coord2-1][coord1-1] = Character.toString((pieces[x].toChar()));
            }
        }

        for (int k = 0; k < 8; k++) {
            for (int l = 0; l < 8; l++) {
                if (temp[k][l]!=null)
                    s += temp[k][l];
                else s += ".";
            }
            writer.println(s);
            s = "";
        }
		writer.close();;
	}


}
