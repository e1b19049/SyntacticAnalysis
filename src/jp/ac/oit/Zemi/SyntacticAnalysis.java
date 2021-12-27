package jp.ac.oit.Zemi;

import java.util.ArrayList;

public class SyntacticAnalysis {
	//íËêîêÈåæ
	final static String identifier = "1";
	final static String var = "2";
	final static String read = "3";
	final static String print = "4";
	final static String println = "5";
	final static String div = "6";
	final static String repeat = "7";
	final static String integer = "9";
	final static String realNum = "10";
	final static String sentence = "11";
	final static String plus = "12";
	final static String minus = "13";
	final static String asterisk = "14";
	final static String slash = "15";
	final static String percent = "16";
	final static String l_par = "17";
 	final static String r_par = "18";
 	final static String semi_colon = "19";
	final static String comma = "20";
	final static String at = "21";
	final static String assign = "22";
	
	static int current = 0;
	static ArrayList<Token> tokenList;
	
	public static void start(ArrayList<Token> input) throws NotCorrectToken {
		tokenList = input;
		while(tokenList.size() > current) {
			checkProgram();
		}
	}
	
	private static String getCurrentToken() throws NotCorrectToken {
		try {
			return tokenList.get(current).id;
		}catch(IndexOutOfBoundsException e) {
			current--;
			throw new NotCorrectToken(errorLine());
		}
	}
	
	private static String errorLine() {
		if(tokenList.get(current).line == null) {
			return null;
		}
		return "At Line: " + tokenList.get(current).line;
	}
	
	private static void checkProgram() throws NotCorrectToken {
		checkInterpretiveUnit();
		if(!getCurrentToken().equals(semi_colon)) {
			current--;
			throw new NotCorrectToken(errorLine());
		}
		current++;
	}
	 
	private static void checkInterpretiveUnit() throws NotCorrectToken {
		String token = getCurrentToken();
		
		if(token.equals(identifier)) {
			checkVarAssignment();
		}else if(token.equals(var)) {
			checkVarDeclaration();
		}else if(token.equals(read)) {
			checkVarInput();
		}else if(token.equals(print) || token.equals(println)) {
			checkOutputSpecification();
		}else if(token.equals(repeat)) {
			checkRepeatSentence();
		}else {
			throw new NotCorrectToken(errorLine());
		}
	}
	
	private static void checkVarAssignment() throws NotCorrectToken {
		checkVarName();
		if(!getCurrentToken().equals(assign)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
		checkFormula();
	}
	
	private static void checkVarName() throws NotCorrectToken {
		if(!getCurrentToken().equals(identifier)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
	}
	
	private static void checkFormula() throws NotCorrectToken{
		String token = getCurrentToken();
		if(token.equals(plus) || token.equals(minus)) {
			current++;
		}
		checkTerm();
		if(getCurrentToken().equals(plus) || getCurrentToken().equals(minus)) {
			checkFormula();
		}
	}
	
	private static void checkTerm() throws NotCorrectToken {
		checkFactor();
		if(getCurrentToken().equals(asterisk) || getCurrentToken().equals(slash) 
				|| getCurrentToken().equals(div) || getCurrentToken().equals(percent)) {
			current++;
			checkTerm();
		}
	}
	
	private static void checkFactor() throws NotCorrectToken {
		String token = getCurrentToken();
		
		if(token.equals(l_par)) {
			current++;
			checkFormula();
			if(!getCurrentToken().equals(r_par)) {
				throw new NotCorrectToken(errorLine());
			}
			current++;
		}else if(token.equals(integer)) {
			current++;
		}else if(token.equals(realNum)) {
			current++;
		}else if(token.equals(identifier)) {
			checkVarName();
		}else if(token.equals(at)) {
			checkCallFunction();
		}else {
			throw new NotCorrectToken(errorLine());
		}
	}
	
	private static void checkVarDeclaration() throws NotCorrectToken {
		if(!getCurrentToken().equals(var)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
		checkVarName();
		if(getCurrentToken().equals(assign)) {
			current++;
			checkFormula();
		}
	}
	
	private static void checkVarInput() throws NotCorrectToken {
		if(!getCurrentToken().equals(read)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
		if(!getCurrentToken().equals(l_par)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
		checkVarName();
		if(!getCurrentToken().equals(r_par)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
	}
	
	private static void checkOutputSpecification() throws NotCorrectToken {
		if(!getCurrentToken().equals(print) && !getCurrentToken().equals(println)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
		if(!getCurrentToken().equals(l_par)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
		checkOutputUnitLine();
		if(!getCurrentToken().equals(r_par)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
	}
	
	private static void checkOutputUnitLine() throws NotCorrectToken {
		if(tokenList.get(current).id.equals(r_par)) {
			return;
		}
		checkOutputUnit();
		if(getCurrentToken().equals(comma)) {
			current++;
			checkOutputUnitLine();
		}
	}
	
	private static void checkOutputUnit() throws NotCorrectToken {
		if(getCurrentToken().equals(sentence)) {
			current++;
			return;
		}
		String token = getCurrentToken();
		if(token.equals(plus) || token.equals(minus) || token.equals(l_par) 
				|| token.equals(integer) || token.equals(realNum) || token.equals(identifier) || token.equals(at)) {
			checkFormula();
		}
	}
	
	private static void checkRepeatSentence() throws NotCorrectToken {
		if(!getCurrentToken().equals(repeat)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
		checkFormula();
		checkVarAssignment();
	}
	
	private static void checkCallFunction() throws NotCorrectToken {
		if(!getCurrentToken().equals(at)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
		checkFunctionName();
		if(!getCurrentToken().equals(l_par)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
		checkFormulaLine();
		if(!getCurrentToken().equals(r_par)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
	}
	
	private static void checkFunctionName() throws NotCorrectToken {
		if(!getCurrentToken().equals(identifier)) {
			throw new NotCorrectToken(errorLine());
		}
		current++;
	}
	
	private static void checkFormulaLine() throws NotCorrectToken {
		if(getCurrentToken().equals(r_par)) {
			return;
		}
		checkFormula();
		if(getCurrentToken().equals(comma)) {
			current++;
			checkFormulaLine();
		}
	}
}
