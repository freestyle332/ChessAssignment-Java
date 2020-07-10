package Chess.app;

import Chess.common.*;
import java.io.*;
import java.util.*;

public class ChessApp {

	public static final String FILENAME = "chessboard.txt";
	public static final String PROMPT_FORMAT = "Move %d for %s>";
	public static final String WELCOME_MESSAGE = "Welcome to the Chess application.";
	public static final String HELP_MESSAGE = "Enter your moves as: \n"
			+ " <current location of the piece> <target location of the piece>\n"
			+ "For example enter `e2 e4` to move piece located at e2 to e4\n"
			+ "Other commands are HELP, SAVE and LOAD\n" + "Enter 'q' to quit.";

	public static void main(String[] args) throws FileNotFoundException, IOException {

		ChessBoard board = new Chess.game.Board();
		Scanner scanner = new Scanner(System.in);
		System.out.println(WELCOME_MESSAGE);
		String line = "help";
		boolean gameFinished = false;

		while (!"q".equalsIgnoreCase(line)) {

			line = line.toLowerCase(Locale.ENGLISH);

			switch (line) {

			case "help":
				System.out.println(HELP_MESSAGE);
				break;

			case "save":
				if (board.isWhitesTurn()) {
					// We use try-with-resources to automatically close the file after save
					try (PrintWriter writer = new PrintWriter(FILENAME)) {
						board.save(writer);
						System.out.println("Board saved.");
					}
				} else {
					System.out.println("ERROR: Save is allowed only when it is white's turn.");
				}
				break;

			case "load":
				// We use try-with-resources to automatically close the file after load
				try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
					board.load(reader);
					System.out.println("Board loaded.");
				}
				break;

			default: {
				try {
					ChessPiece captured = board.move(line);
					if (captured != null) {
						System.out.println(String.format("Piece %s was captured", captured.toChar()));
						gameFinished = Character.toUpperCase(captured.toChar()) == 'K';
					}
				} catch (ChessException ex) {
					System.out.println("ERROR: " + ex.getMessage());
				}
			}
				break;
			}

			if (gameFinished) {
				System.out.println("Game is finished");
				break;
			} else {
				System.out.print(board.getDisplayString());
				System.out.format(PROMPT_FORMAT, board.getMoveCount(), board.isWhitesTurn() ? "White" : "Black");
				line = scanner.nextLine();
			}
		}

		System.out.println("Bye...");
	}
}
