package game;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Mastermind {

	public static final String COLORS[] = {"R", "Y", "G", "B", "V", "W"};
	public static final String USED_SECRET = "X";
	public static final String USED_GUESS = "Z";
	public static final int NUM_POSITIONS = 6;
	public static final int NUM_ROUNDS = 12;
	public static final String INSTRUCTIONS = "Enter a code\n" +
			"Separate pins with a space\n" +
			"R = red, Y = yellow, G = green, B = blue, V = violet, W = white";
	private boolean game_won = false;
	
	public String[] secret_code = new String[NUM_POSITIONS];
	public static Scanner scan;
	public int round = 0;

	public Mastermind() {
		scan = new Scanner(System.in);
		generate_random_code();
		
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
	
	public void generate_random_code() {
		Random generator = new Random();
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			secret_code[i] = COLORS[generator.nextInt(NUM_POSITIONS)];
		}
	}

	public void user_make_guess() {

		System.out.println();
		System.out.println("Round " + String.valueOf(round + 1));
		System.out.println(INSTRUCTIONS);
		String[] code_str = scan.nextLine().split(" ");
		String [] code_guess = new String[6];

		int index = 0;
		int count_pins = 0;
		
		while(index < code_str.length && count_pins < 6) {
			if(code_str[index].compareTo("") != 0) {
				code_guess[count_pins] = code_str[index];
				++count_pins;
			}
			++index;
		}
		
		for(int i = count_pins; i < NUM_POSITIONS; ++i) {
			code_guess[i] = USED_GUESS;
		}


		int[] code_grade = grade(code_guess);
		if(code_grade[0] == NUM_POSITIONS) game_won = true;

		System.out.println(String.valueOf(code_grade[0]) + " pins with correct position and color");
		System.out.println(String.valueOf(code_grade[1]) + " pins with only the correct color");
	}

	public int[] grade(String[] code_guess) {
		int corr_pos = 0;
		int corr_color = 0;
		
		String[] copy = secret_code.clone();
		
		int num_entries = code_guess.length;


		// make it so it only counts correct color once
		for(int i = 0; i < num_entries; ++i) {
			if(code_guess[i].compareTo(secret_code[i]) == 0) {
				corr_pos++;
				copy[i] = USED_SECRET;
				code_guess[i] = USED_GUESS;
			}
		}
		for(int i = 0; i < num_entries; ++i) {
			for(int j = 0; j < NUM_POSITIONS; ++j) {
				if(code_guess[i].compareTo(copy[j]) == 0) {
					corr_color++;
					copy[j] = USED_SECRET;
					code_guess[i] = USED_GUESS;
				}
			}
		}
		
		return new int[] {corr_pos, corr_color};

	}


}
