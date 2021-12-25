package jp.ac.oit.Zemi;

import java.io.BufferedReader;
import java.io.IOException;

public class App {
	public static void main(String[] args) {
		BufferedReader bufferedReader = new ReadToken().openBufferedReader();
		String token;
		
		try {
			while((token = bufferedReader.readLine()) != null) {
				System.out.println(token);
			}
			bufferedReader.close();
		}catch(IOException e) {
			System.out.println(e);
		}
	}
}