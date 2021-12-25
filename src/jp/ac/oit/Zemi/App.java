package jp.ac.oit.Zemi;

import java.util.ArrayList;

public class App {
	public static void main(String[] args) {
		ArrayList<String> tokenList = TokenList.makeTokenList();

		for(String token : tokenList) {
			System.out.println(token);
		}
	}
} 