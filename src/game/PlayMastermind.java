package game;

public class PlayMastermind {

	public static void main(String[] args) {
		Mastermind game = new Mastermind();
		
		for(int i = 0; i < 6; ++i) {
			game.secret_code[i] = Mastermind.COLORS[i];
		}
		for(int i = 0; i < 3; i++) {
			game.make_guess();
		}
	}

}
