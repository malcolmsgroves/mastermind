package game;

public class Guess {
	
	private int[] code;
	
	/*
	 * Constructor
	 */
	public Guess(int[] code) {
		this.code = new int[code.length];
		for(int i = 0; i < code.length; ++i) {
			this.code[i] = code[i];
		}
	}
	
	/*
	 * Copy constructor
	 */
	public Guess(Guess guess) {
		this(guess.get_code());
	}
	
	
	
	
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
	
	@Override
	public int hashCode() {
		int hash_code = 0;
		//System.out.println("First: " + String.valueOf(code[0]));
		for(int i = code.length - 1; i >= 0; --i) {
			int radix = code[i] * (int) Math.pow(10, i);
			hash_code += radix;
		}
		//System.out.println(hash_code);
		return hash_code;
	}
	
	public int[] get_code() {
		return code;
	}
	
	public String to_string() {
		
		String output = "";
		for(int i = 0; i < code.length; ++i) {
			output += (String.valueOf(code[i]) + " ");
		}
		return output;
	}
}
