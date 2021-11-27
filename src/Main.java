/**
 *	The main is used only as an endpoint that initiates
 *		the execution of the program and does not contain
 *   	any actual program specific logic.
 */

public class Main {
	public static void main(String[] args) {
		// initiate game menu
		Menu game = new Menu();
		game.Run();
	}
}
