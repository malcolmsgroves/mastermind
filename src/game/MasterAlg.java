package game;


import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;

public class MasterAlg {

	private static final int[] FIRST_GUESS = {0, 0, 1, 1};
	private static final int NUM_POSITIONS = 4;
	private static final int NUM_COLORS = 6;
	private static final int USED_GUESS = -1;
	private static final int USED_ANSWER = -2;


	private HashSet<Guess> set;
	private Guess curr;

	// constructor for the algorithm
	public MasterAlg() {
		set = new HashSet<Guess>();
		curr = new Guess(FIRST_GUESS);
		instantiate_set();
	}

	// give the first guess
	public int[] first_guess() {
		return FIRST_GUESS;
	}

	// generate the next guess, give the code of the previous guess
	public int[] next_guess(int[] grade) {

		Iterator<Guess> iter = set.iterator();
		ArrayList<Guess> to_remove = new ArrayList<Guess>();

		
		
		// iterate through set of possibilities
		while(iter.hasNext()) {

			
			Guess next = iter.next();
			//Guess copy = new Guess(next);

			// if a possibility is true, it would give the given grade to the curr
			if(!equal(grade, calc_grade(next, curr))) {
				to_remove.add(next);
			}
		}

		// remove Guesses in to_remove
		for(Guess g : to_remove) {
			set.remove(g);
		}
		
		// reset iterator
		iter = set.iterator();
		
		Guess next_guess = null;
		int max_elim = -1;
		

		// iterate through and count eliminations for each
		while(iter.hasNext()) {
			// calculate the number of possibilities that could be eliminated by the code
			Guess next = iter.next();
			
			
			int elim_count = count_elim(next);
			
			if(max_elim < elim_count) {
				max_elim = elim_count;  // update max_elim
				next_guess = next;
			}
		}

		curr = next_guess;
		System.out.println(curr.to_string());
		return curr.get_code();
	}

	private int count_elim(Guess guess) {

		
		int count = 0;
		Iterator<Guess> iter = set.iterator();

		while(iter.hasNext()) {
			Guess next = iter.next();

			// try every grade
			for(int pos = 0; pos < NUM_POSITIONS; ++pos) {
				for(int col = 0; col < NUM_POSITIONS - pos; ++col) {

					int[] grade = {pos, col};

					// if a possibility is true, it would give the given grade to the curr
					if(!equal(grade, calc_grade(next, guess))) {
						count++;
					}
				}
			}
		}
		
		return count;
	}

	private void instantiate_set() {
		instantiate_set(0, new int[NUM_POSITIONS]);
	}

	private void instantiate_set(int index, int[] possible_code) {
		if(index == NUM_POSITIONS) {
			set.add(new Guess(possible_code));
			return;
		}

		for(int i = 0; i < NUM_COLORS; ++i) {
			int[] new_code = possible_code.clone();
			new_code[index] = i;
			instantiate_set(index + 1, new_code);
		}
	}

	private int[] calc_grade(Guess possibility, Guess target) {

		int corr_pos = 0;
		int corr_color = 0;
		
		Guess possibility_copy = new Guess(possibility);
		Guess target_copy = new Guess(target);
		
		int[] guess = possibility_copy.get_code();
		int[] answer = target_copy.get_code();


		// counts correct color & position once and overwrite them with used chars
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			if(guess[i] == answer[i]) {
				corr_pos++;
				answer[i] = USED_ANSWER;
				guess[i] = USED_GUESS;
			}
		}

		// counts correct color if not correct position
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			for(int j = 0; j < NUM_POSITIONS; ++j) {
				if(guess[i] == answer[j]) {
					corr_color++;
					answer[j] = USED_ANSWER;
					guess[i] = USED_GUESS;
				}
			}
		}
		

		return new int[] {corr_pos, corr_color};

	}

	private boolean equal(int[] first_grade, int[] second_grade) {
		return first_grade[0] == second_grade[0] && first_grade[1] == second_grade[1];
	}



}
