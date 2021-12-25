package jp.ac.oit.Zemi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

public class ReadToken {
	public BufferedReader openBufferedReader() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("input file name:");
		String fileName = scanner.nextLine();
		scanner.close();
		
		try {
			File file = new File("res/" + fileName);
			//File file = new File(fileName); //jar‚É‚·‚é‚Æ‚«‚Í‚±‚Á‚¿
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			return bufferedReader;
		}catch (FileNotFoundException e) {
			System.out.println(e);
			System.exit(1);
		}
		return null;
	}
}
