package jp.ac.oit.Zemi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TokenList {
	static ArrayList<Token> tokenList = new ArrayList<>();

	public static ArrayList<Token> makeTokenList() {
		Scanner scanner = new Scanner(System.in);

		System.out.print("input file name:");
		String fileName = scanner.nextLine();
		scanner.close();

		try {
			File file = new File("res/" + fileName);
			//File file = new File(fileName); //jar�̂Ƃ��͂�����
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			
			/*1�s�ڂœ��̓t�@�C���̌`���𔻒f
			 * �^�u�ŋ�؂�ꂽ�s����ID������ꍇ��
			 * ID�݂̂�����ꍇ�ŕ�����
			 */
			String line = bufferedReader.readLine();
			if(line.indexOf('\t') == -1) {
				Token token = new Token(line);
				tokenList.add(token);
				while((line = bufferedReader.readLine()) != null) {
					token = new Token(line);
					tokenList.add(token);
				}
			}else {
				String[] tokenData = line.split("\t");
				Token token = new Token(tokenData[0], tokenData[1]);
				tokenList.add(token);
				while((line = bufferedReader.readLine()) != null) {
					tokenData = line.split("\t");
					token = new Token(tokenData[0], tokenData[1]);
					tokenList.add(token);
				}
			}
			bufferedReader.close();
			
			return tokenList;
		}catch(FileNotFoundException e) {
			System.out.println(e);
			System.exit(1);
		}catch(IOException e) {
			System.out.println(e);
			System.exit(1);
		}
		return null;
	}
}