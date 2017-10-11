package game;

public class Guess {
	
	private int[] code;
	protected int elim_count;
	protected boolean is_guessed;
	
	public Guess(int[] code) {
		this.code = code;
		elim_count = 0;
	}
	
	public void clean() {
		elim_count = 0;
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
	
	public void set_elim_count(int count) {
		this.elim_count = count;
	}
	
	public int get_elim_count() {
		return elim_count;
	}

}
