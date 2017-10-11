package game;

import java.util.Random;
import java.util.Scanner;


public class Mastermind {

	public static final int NUM_POSITIONS = 4;
	public static final int NUM_ROUNDS = 12;
	
	private static final char[] COLORS = {'R', 'Y', 'G', 'B', 'V', 'W'};
	private static final char USED_SECRET = 'X';
	private static final char USED_GUESS = 'Z';
	private static final String INSTRUCTIONS = "Enter a code\n" +
			"Separate pins with a space\n" +
			"R = red, Y = yellow, G = green, B = blue, V = violet, W = white";
	private boolean game_won = false;
	private int round;
	
	public char[] secret_code = new char[NUM_POSITIONS];
	public static Scanner scan;
	

	public Mastermind() {
		scan = new Scanner(System.in);
		generate_random_code();
		round = 0;
		
	}
	
	public void computer_play_game() {
		
		MasterAlg alg = new MasterAlg();
		int[] computer_grade = grade(int_to_char(alg.first_guess()));
		print_code(secret_code);
		System.out.println();
		
		while(!game_won && round < NUM_ROUNDS) {
			char[] next_guess = int_to_char(alg.next_guess(computer_grade));
			print_code(next_guess);
			computer_grade = grade(next_guess);
			print_grade(computer_grade);
			++round;
			
			
		}
		
		if(game_won) {
			System.out.println("CONGRATULATIONS YOU WIN");
		}
		else {
			System.out.println("YOU LOSE :(");
		}
	}
	
	private char[] int_to_char(int[] int_code) {
		
		char[] char_code = new char[NUM_POSITIONS];
		
		for(int i = 0; i < int_code.length; ++i) {
			
			// I am rewritting the codes with the USED_ANSWER accidentally
			char_code[i] = COLORS[int_code[i]];
		}
		
		// fill in the rest of the char_code
		for(int j = int_code.length; j < NUM_POSITIONS; ++j) {
			char_code[j] = USED_GUESS;
		}
		
		return char_code;
	}
	
	public void user_play_game() {
		
		while(!game_won && round < NUM_ROUNDS) {
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
	
	private void generate_random_code() {
		Random generator = new Random();
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			secret_code[i] = COLORS[generator.nextInt(NUM_POSITIONS)];
		}
	}

	private void user_make_guess() {

		System.out.println();
		System.out.println("Round " + String.valueOf(round + 1));
		System.out.println(INSTRUCTIONS);
		String code_str = scan.nextLine();
		char[] code_guess = new char[6];

		int index = 0;
		
		// get rid of all white space in guess string
		code_str = code_str.replaceAll("\\s", "");
		
		while(index < code_str.length() && index < 6) {
			
			code_guess[index] = code_str.charAt(index);
			++index;
		}
		
		
		// fill the rest of the code_guess array with used chars
		for(int i = index; i < NUM_POSITIONS; ++i) {
			code_guess[i] = USED_GUESS;
		}
		
		print_code(code_guess);

		int[] code_grade = grade(code_guess);
		

		print_grade(code_grade);
	}
	
	private void print_grade(int[] code_grade) {
		System.out.println(String.valueOf(code_grade[0]) + " pins with correct position and color");
		System.out.println(String.valueOf(code_grade[1]) + " pins with only the correct color");
		System.out.println();
	}
	
	private void print_code(char[] code) {
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			System.out.print(String.valueOf(code[i]) + " ");
		}
		System.out.println();
	}

	/* 
	 * Score the code guess and return an int[]
	 * where int[] = {corr_pos, corr_col}
	 */
	
	private int[] grade(char[] code_guess) {
		
		int corr_pos = 0;
		int corr_color = 0;
		
		//TODO refactor to avoid clone()
		char[] copy = new char[NUM_POSITIONS];
		
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			copy[i] = secret_code[i];
		}
		
		

		// counts correct color & position once and overwrite them with used chars
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			if(code_guess[i] == copy[i]) {
				corr_pos++;
				copy[i] = USED_SECRET;
				code_guess[i] = USED_GUESS;
			}
		}
		
		// counts correct color if not correct position
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			for(int j = 0; j < NUM_POSITIONS; ++j) {
				if(code_guess[i] == copy[j]) {
					corr_color++;
					copy[j] = USED_SECRET;
					code_guess[i] = USED_GUESS;
				}
			}
		}
		
		if(corr_pos == NUM_POSITIONS) game_won = true;
		
		return new int[] {corr_pos, corr_color};

	}


}
