package jp.ac.oit.Zemi;

public class Token {
	String line;
	String id;
	
	public Token(String line, String id) {
		this.line = line;
		this.id = id;
	}
	
	public Token(String id) {
		this.id = id;
	}
}
