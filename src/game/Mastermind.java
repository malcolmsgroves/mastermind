package game;

import java.util.Random;
import java.util.Scanner;

/**
 * Console application for playing the game mastermind.
 * Enables both user and computer-optimized play. Has-a
 * MasterAlg that implements a minimax search for the
 * optimal play each round.
 * @author Malcolm Groves
 *
 */
public class Mastermind {

	private static final char[] COLORS = {'R', 'Y', 'G', 'B', 'V', 'W'};
	private static final char USED_SECRET = 'X';
	private static final char USED_GUESS = 'Z';
	private static final String INSTRUCTIONS = "Enter a code\n" +
			"Separate pins with a space\n" +
			"R = red, Y = yellow, G = green, B = blue, V = violet, W = white";

	private boolean game_won = false;
	private int round;
	private char[] secret_code;
	public static Scanner scan;
	private int num_positions;
	private int num_rounds;


	/**
	 * Constructs a new Mastermind object with default
	 * values for num_positions and num_rounds.
	 */
	public Mastermind() {
		num_positions = 4;
		num_rounds = 12;
		round = 0;
		secret_code = new char[num_positions];
		scan = new Scanner(System.in);
		generate_random_code();
	}

	/**
	 * Constructs a new Mastermind objects with parameter-
	 * provided values for the number of positions on the
	 * board and the number of rounds of play.
	 * @param num_positions	The number of pin slots on the 
	 * 						mastermind board.
	 * @param num_rounds	The number of rounds of play for
	 * 						the game.
	 */
	public Mastermind(int num_positions, int num_rounds) {
		this.num_positions = num_positions;
		this.num_rounds = num_rounds;
		round = 0;
		secret_code = new char[num_positions];
		scan = new Scanner(System.in);
		generate_random_code();
	}

	/**
	 * Allows the computer to play the Mastermind game against
	 * a randomly generated code. Solves the code with the MasterAlg
	 * class.
	 */
	public void computer_play_game() {

		System.out.println("Computer is playing...");
		System.out.print("Secret Code: ");
		print_code(secret_code);
		System.out.println();

		MasterAlg alg = new MasterAlg(num_positions);

		// Get the first move from MasterAlg
		char[] first_guess = int_to_char(alg.first_guess());
		print_code(first_guess);
		int[] computer_grade = grade(first_guess);
		print_grade(computer_grade);

		while(!game_won && round < num_rounds) {
			char[] next_guess = int_to_char(alg.next_guess(computer_grade));
			print_code(next_guess);
			computer_grade = grade(next_guess);
			print_grade(computer_grade);
			++round;
		} // while game has not been won and round < num_rounds

		// Print game ending message
		if(game_won) {
			System.out.println("CONGRATULATIONS YOU WIN");
		} else {
			System.out.println("YOU LOSE :(");
		}
	}

	public void user_play_game() {

		while(!game_won && round < num_rounds) {
			user_make_guess();
			++round;
		}
		if(game_won) {
			System.out.println("CONGRATULATIONS YOU WIN");
		}
		else {
			System.out.println("YOU LOSE :(");
		}
	}


	/*
	 * Converts int[] to char[] using the int[] values
	 * as indices in the COLORS char[].
	 * Parameters: int[] int_code containing the code
	 * Return:	char[] containing the code represented
	 * 			by chars.
	 */
	private char[] int_to_char(int[] int_code) {

		char[] char_code = new char[num_positions];

		for(int i = 0; i < int_code.length; ++i) {
			char_code[i] = COLORS[int_code[i]];
		}

		// fill in the rest of the char_code if not full
		for(int j = int_code.length; j < num_positions; ++j) {
			char_code[j] = USED_GUESS;
		}

		return char_code;
	}

	/*
	 * Sets the secret code to a random permutation of COLORS[] chars
	 */
	private void generate_random_code() {
		Random generator = new Random();
		for(int i = 0; i < num_positions; ++i) {
			secret_code[i] = COLORS[generator.nextInt(num_positions)];
		} // for all positions in code
	}

	/*
	 * Takes input code from the user, grades the code against
	 * the secret code, and prints results to the console.
	 */
	private void user_make_guess() {

		System.out.println();
		System.out.println("Round " + String.valueOf(round + 1));
		System.out.println(INSTRUCTIONS);
		String code_str = scan.nextLine();
		
		char[] code_guess = new char[6];

		int index = 0;

		// get rid of all white space in guess string and make it uppercase
		code_str = code_str.replaceAll("\\s", "").toUpperCase();

		while(index < code_str.length() && index < num_positions) {
			code_guess[index] = code_str.charAt(index);
			++index;
		} // while index is in code_str and less than num_positions

		// fill the rest of the code_guess array with used chars
		for(int i = index; i < num_positions; ++i) {
			code_guess[i] = USED_GUESS;
		}

		print_code(code_guess);
		int[] code_grade = grade(code_guess);
		print_grade(code_grade);
	}

	/*
	 * Prints the int[] code_grade to System.out
	 */
	private void print_grade(int[] code_grade) {
		System.out.println(String.valueOf(code_grade[0]) + " pins with correct position and color");
		System.out.println(String.valueOf(code_grade[1]) + " pins with only the correct color");
		System.out.println();
	}

	/*
	 * Prints the char[] code to System.out
	 */
	private void print_code(char[] code) {
		for(int i = 0; i < num_positions; ++i) {
			System.out.print(String.valueOf(code[i]) + " ");
		}
		System.out.println();
	}

	/* 
	 * Score the code guess against the secret code and 
	 * return the score.
	 * Parameters: 	char[] code_guess that contains the 
	 * 				code guess to be graded.
	 * Return: 	int[] = {corr_pos, corr_col} where corr_pos
	 * 			is the number of pins with correct
	 * 			position and color, while corr_col
	 * 			is the number of pins with only the
	 * 			correct color.
	 */
	private int[] grade(char[] code_guess) {

		int corr_pos = 0;
		int corr_color = 0;

		// make a copy of the code_guess
		char[] copy = new char[num_positions];
		for(int i = 0; i < num_positions; ++i) {
			copy[i] = secret_code[i];
		}

		// counts correct color & position once and overwrite them with used chars
		for(int i = 0; i < num_positions; ++i) {
			if(code_guess[i] == copy[i]) {
				corr_pos++;
				copy[i] = USED_SECRET;
				code_guess[i] = USED_GUESS;
			}
		}

		// counts correct color if not correct position
		for(int i = 0; i < num_positions; ++i) {
			for(int j = 0; j < num_positions; ++j) {
				if(code_guess[i] == copy[j]) {
					corr_color++;
					copy[j] = USED_SECRET;
					code_guess[i] = USED_GUESS;
				}
			}
		}

		if(corr_pos == num_positions) game_won = true;

		return new int[] {corr_pos, corr_color};
	}
}
