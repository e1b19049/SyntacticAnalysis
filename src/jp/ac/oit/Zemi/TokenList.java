package jp.ac.oit.Zemi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TokenList {
	static ArrayList<String> tokenList = new ArrayList<>();
	
	public static ArrayList<String> makeTokenList() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("input file name:");
		String fileName = scanner.nextLine();
		scanner.close();
		
		try {
			File file = new File("res/" + fileName);
			//File file = new File(fileName); jar‚É‚·‚é‚Æ‚«‚Í‚±‚Á‚¿
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String token;
			while((token = bufferedReader.readLine()) != null) {
				tokenList.add(token);
			}
			bufferedReader.close();
		}catch(FileNotFoundException e) {
			System.out.println(e);
		}catch(IOException e) {
			System.out.println(e);
		}
		
		return tokenList;
	}
}
