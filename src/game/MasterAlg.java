package game;


import java.util.HashSet;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * An algorithm that can solve mastermind in 5 moves or less.
 * Implements Knuth's minimax algorithm for solving the np-complete
 * mastermind problem.
 * Contains a HashSet to store all guess objects.
 * @author mac2
 */
public class MasterAlg {

	// [0, 0, 1, 1] is shown to be the most effective first guess
	private static final int[] FIRST_GUESS = {0, 0, 1, 1};
	private static final int NUM_POSITIONS = 4;
	private static final int NUM_COLORS = 6;
	private static final int USED_GUESS = -1;
	private static final int USED_ANSWER = -2;


	private HashSet<Guess> set;
	private Guess curr;

	/**
	 * Constructs a MasterAlg object and initializes instances of
	 * set, curr, and instantiates the set.
	 */
	public MasterAlg() {
		set = new HashSet<Guess>();
		curr = new Guess(FIRST_GUESS);
		instantiate_set();
	}

	/**
	 * Gets the first guess.
	 * @return	First Guess int[] code
	 */
	public int[] first_guess() {
		return FIRST_GUESS;
	}

	/**
	 * Generates the next Guess for the MasterAlg. Given a grade,
	 * iterates through all remaining possible Guess objects and removes 
	 * all Guess objects that, if true, would not give the same
	 * grade to the current Guess.
	 * @param 	grade	grade[0] is the number of pins with the correct
	 * 					color and position. grade[1] is the number of pins
	 * 					with the correct color, but incorrect position.
	 * @return	int[] code that has the maximum minimum elimination count
	 * 			in the set of possible guesses.
	 */
	public int[] next_guess(int[] grade) {

		Iterator<Guess> iter = set.iterator();
		ArrayList<Guess> to_remove = new ArrayList<Guess>();

		while(iter.hasNext()) {
			
			Guess next = iter.next();
			
			// if a possibility is true, it would give the given grade to the curr
			if(!equal_grade(grade, calc_grade(next, curr))) {
				to_remove.add(next);
			}
		}

		// remove Guesses in to_remove
		for(Guess g : to_remove) {
			set.remove(g);
		}
		
		iter = set.iterator();
		
		Guess next_guess = null;
		int max_elim = -1;
		
		// iterate through and count eliminations for each
		while(iter.hasNext()) {
			
			Guess next = iter.next();
			
			int elim_count = count_elim(next);
			
			if(max_elim < elim_count) {
				max_elim = elim_count;  // update max_elim
				next_guess = next;
			}
		}

		curr = next_guess;
		
		return curr.get_code();
	}

	/**
	 * Counts the minimum number of Guess objects in the set of all remaining
	 * possible Guesses that would be eliminated for each possible grade.
	 * @param 	guess	Possible Guess to check for eliminations.
	 * @return	int Number of Guesses that would be eliminated for each possible
	 * 			grade.
	 */
	private int count_elim(Guess guess) {

		
		int count = 0;
		Iterator<Guess> iter = set.iterator();

		while(iter.hasNext()) {
			Guess next = iter.next();

			
			for(int pos = 0; pos < NUM_POSITIONS; ++pos) {
				for(int col = 0; col < NUM_POSITIONS - pos; ++col) {

					int[] grade = {pos, col};

					if(!equal_grade(grade, calc_grade(next, guess))) {
						count++;
					} // if next would be eliminated
				} // for every color grade
			} // for every position grade
		} // while iterator has next
		
		return count;
	}

	/*
	 * Instantiate the set of all possible moves using
	 * a recursive helper method.
	 */
	private void instantiate_set() {
		instantiate_set(0, new int[NUM_POSITIONS]);
	}

	/*
	 * Instantiates the set of all possible Guesses recursively so
	 * that set contains all possible code permutations.
	 * Parameters: 	Index in the code array and the array that is
	 * 				being constructed.
	 */
	private void instantiate_set(int index, int[] possible_code) {
		
		// base case when code array is full
		if(index == NUM_POSITIONS) {
			set.add(new Guess(possible_code));
			return;
		}

		// Add a pin of each color to the next position
		for(int i = 0; i < NUM_COLORS; ++i) {
			
			int[] new_code = new int[NUM_POSITIONS];
			for(int j = 0; j < possible_code.length; ++j) {
				new_code[j] = possible_code[j];
			}
			
			new_code[index] = i;
			instantiate_set(index + 1, new_code);
		}
	}

	/*
	 * Calculates the grade given a Guess possibility and a Guess
	 * target that is assumed to be the answer. 
	 * Parameters: Guess possibility and Guess target
	 * Return: 	int[] grade where grade[0] is the number of pins in
	 * 			the correct position with the correct color and
	 * 			grade[1] is the number of pins with only the
	 * 			correct color.
	 */
	private int[] calc_grade(Guess possibility, Guess target) {

		int corr_pos = 0;
		int corr_color = 0;
		
		Guess possibility_copy = new Guess(possibility);
		Guess target_copy = new Guess(target);
		
		int[] guess = possibility_copy.get_code();
		int[] answer = target_copy.get_code();


		// correct color and position
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			if(guess[i] == answer[i]) {
				corr_pos++;
				answer[i] = USED_ANSWER;
				guess[i] = USED_GUESS;
			} // if same position and color
		} // for every position in the array

		// correct color only
		for(int i = 0; i < NUM_POSITIONS; ++i) {
			for(int j = 0; j < NUM_POSITIONS; ++j) {
				if(guess[i] == answer[j]) {
					corr_color++;
					answer[j] = USED_ANSWER;
					guess[i] = USED_GUESS;
				} // if same color
			} // for every position in target
		} // for every target in guess
		
		return new int[] {corr_pos, corr_color};

	}

	/*
	 * Checks if the values in two int arrays are equal
	 * Return: 	True if the grade arrays are identical, 
	 * 			false otherwise.
	 */
	private boolean equal_grade(int[] first_grade, int[] second_grade) {
		return first_grade[0] == second_grade[0] && first_grade[1] == second_grade[1];
	}



}
