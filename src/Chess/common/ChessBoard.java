package Chess.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public interface ChessBoard {

	// returns captured piece or null if nothing was captured
	ChessPiece move(String moveText) throws ChessException;

	boolean isWhitesTurn();

	int getMoveCount();

	String getDisplayString();

	void load(BufferedReader reader) throws IOException;

	void save(PrintWriter writer);
}
