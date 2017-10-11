package game;

import java.util.Scanner;

public class PlayMastermind {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		
		System.out.println("WELCOME TO MASTERMIND!");
		System.out.println("Do you want to play? (y/n)");
		String input = scan.nextLine();
		
		
		Mastermind game = new Mastermind(4, 12);
		
		if(input.toLowerCase().charAt(0) == 'y') {
			game.user_play_game();
		} else{
			game.computer_play_game();
		}
		scan.close();
	}

}
