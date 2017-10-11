package game;

/**
 * Represents a mastermind code Guess. Contains methods
 * to store and manipulate an int[] code array.
 * @author Malcolm Groves
 *
 */
public class Guess {
	
	private int[] code;
	
	/**	
	 * Constructs a Guess object that holds a mastermind code.
	 * @param	code	An integer array that represents
	 * 					the mastermind code
	 */
	public Guess(int[] code) {
		this.code = new int[code.length];
		for(int i = 0; i < code.length; ++i) {
			this.code[i] = code[i];
		}
	}
	
	/** Constructs a copy of a Guess object. 
	 * 
	 * @param 	guess	A guess object that serves as a 
	 * 					template for a deep copy
	 */
	public Guess(Guess guess) {
		this(guess.get_code());
	}
	
	
	
	/**
	 * Checks Guess equality. Overrides the native Object.equals() method.
	 * Compares <code> this </code> to another Object that is caste as a
	 * Guess object.
	 * @param 	obj Object that can be caste to a Guess object.
	 * @return 	boolean value that is true if the codes are the same and
	 * 			false otherwise.
	 */
	@Override
	public boolean equals(Object obj) {
		
		Guess other = (Guess)obj;
		
		if(code.length != other.get_code().length) {
			return false;
		}
		
		for(int i = 0; i < code.length; ++i) {
			if(code[i] != other.get_code()[i]) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Computes hash code for the Guess object. Treats the code as
	 * a base 10 integer so that if the code is [1, 4, 2, 3] the function
	 * returns 1423. Overrides the native hashCode() Object method.
	 * @return A hash code integer.
	 */
	@Override
	public int hashCode() {
		
		int hash_code = 0;
		
		for(int i = code.length - 1; i >= 0; --i) {
			int radix = code[i] * (int) Math.pow(10, i);
			hash_code += radix;
		}
		
		return hash_code;
	}
	
	/**
	 * Gets the Guess int code array.
	 * @return	int code array
	 */
	public int[] get_code() {
		return code;
	}
	
	/**
	 * Converts the Guess code to a string. To be used for
	 * printing the code.
	 * @return String code
	 */
	public String to_string() {
		
		String output = "";
		for(int i = 0; i < code.length; ++i) {
			output += (String.valueOf(code[i]) + " ");
		}
		return output;
	}
}
