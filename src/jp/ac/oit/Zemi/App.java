package jp.ac.oit.Zemi;

import java.util.ArrayList;

public class App {
	public static void main(String[] args) throws NotCorrectToken {
		ArrayList<String> tokenList = TokenList.makeTokenList();
		SyntacticAnalysis.start(tokenList);
		System.out.println("Correct!!");
	}
} 