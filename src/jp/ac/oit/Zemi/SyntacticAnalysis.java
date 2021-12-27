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
		while(tokenList.size() != current) {
			checkProgram();
		}
	}
	
	private static String getCurrentToken(int mode) {
		String token = tokenList.get(current).id;
		if(mode != 0) {
			//0ÇÃÇ∆Ç´ÇÕå©ÇÈÇæÇØ
			current++;
		}
		return token;
	}
	
	private static String errorLine() {
		if(tokenList.get(current).line == null) {
			return null;
		}
		return "At Line: " + tokenList.get(current).line;
	}
	
	private static void checkProgram() throws NotCorrectToken {
		checkInterpretiveUnit();
		if(!getCurrentToken(1).equals(semi_colon)) {
			throw new NotCorrectToken(errorLine());
		}
	}
	 
	private static void checkInterpretiveUnit() throws NotCorrectToken {
		String token = getCurrentToken(0);
		
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
		if(!getCurrentToken(1).equals(assign)) {
			throw new NotCorrectToken(errorLine());
		}
		checkFormula();
	}
	
	private static void checkVarName() throws NotCorrectToken {
		if(!getCurrentToken(1).equals(identifier)) {
			throw new NotCorrectToken(errorLine());
		}
	}
	
	private static void checkFormula() throws NotCorrectToken{
		String token = getCurrentToken(0);
		if(token.equals(plus) || token.equals(minus)) {
			token = getCurrentToken(1);
		}
		checkTerm();
		if(getCurrentToken(0).equals(plus) || getCurrentToken(0).equals(minus)) {
			checkFormula();
		}
	}
	
	private static void checkTerm() throws NotCorrectToken {
		checkFactor();
		if(getCurrentToken(0).equals(asterisk) || getCurrentToken(0).equals(slash) 
				|| getCurrentToken(0).equals(div) || getCurrentToken(0).equals(percent)) {
			getCurrentToken(1);
			checkTerm();
		}
	}
	
	private static void checkFactor() throws NotCorrectToken {
		String token = getCurrentToken(0);
		
		if(token.equals(l_par)) {
			getCurrentToken(1);
			checkFormula();
			if(!getCurrentToken(1).equals(r_par)) {
				throw new NotCorrectToken(errorLine());
			}
		}else if(token.equals(integer)) {
			getCurrentToken(1);
		}else if(token.equals(realNum)) {
			getCurrentToken(1);
		}else if(token.equals(identifier)) {
			checkVarName();
		}else if(token.equals(at)) {
			checkCallFunction();
		}else {
			throw new NotCorrectToken(errorLine());
		}
	}
	
	private static void checkVarDeclaration() throws NotCorrectToken {
		if(!getCurrentToken(1).equals(var)) {
			throw new NotCorrectToken(errorLine());
		}
		checkVarName();
		if(getCurrentToken(0).equals(assign)) {
			getCurrentToken(1);
			checkFormula();
		}
	}
	
	private static void checkVarInput() throws NotCorrectToken {
		if(!getCurrentToken(1).equals(read)) {
			throw new NotCorrectToken(errorLine());
		}
		if(!getCurrentToken(1).equals(l_par)) {
			throw new NotCorrectToken(errorLine());
		}
		checkVarName();
		if(!getCurrentToken(1).equals(r_par)) {
			throw new NotCorrectToken(errorLine());
		}
	}
	
	private static void checkOutputSpecification() throws NotCorrectToken {
		String token = getCurrentToken(1);
		if(!token.equals(print) && !token.equals(println)) {
			throw new NotCorrectToken(errorLine());
		}
		token = getCurrentToken(1);
		if(!token.equals(l_par)) {
			throw new NotCorrectToken(errorLine());
		}
		checkOutputUnitLine();
		token = getCurrentToken(1);
		if(!token.equals(r_par)) {
			throw new NotCorrectToken(errorLine());
		}
	}
	
	private static void checkOutputUnitLine() throws NotCorrectToken {
		if(tokenList.get(current).id.equals(r_par)) {
			return;
		}
		checkOutputUnit();
		if(getCurrentToken(0).equals(comma)) {
			getCurrentToken(1);
			checkOutputUnitLine();
		}
	}
	
	private static void checkOutputUnit() throws NotCorrectToken {
		if(getCurrentToken(0).equals(sentence)) {
			getCurrentToken(1);
			return;
		}
		String token = getCurrentToken(0);
		if(token.equals(plus) || token.equals(minus) || token.equals(l_par) 
				|| token.equals(integer) || token.equals(realNum) || token.equals(identifier) || token.equals(at)) {
			checkFormula();
		}
	}
	
	private static void checkRepeatSentence() throws NotCorrectToken {
		if(!getCurrentToken(1).equals(repeat)) {
			throw new NotCorrectToken(errorLine());
		}
		checkFormula();
		checkVarAssignment();
	}
	
	private static void checkCallFunction() throws NotCorrectToken {
		if(!getCurrentToken(1).equals(at)) {
			throw new NotCorrectToken(errorLine());
		}
		checkFunctionName();
		if(!getCurrentToken(1).equals(l_par)) {
			throw new NotCorrectToken(errorLine());
		}
		checkFormulaLine();
		if(!getCurrentToken(1).equals(r_par)) {
			throw new NotCorrectToken(errorLine());
		}
	}
	
	private static void checkFunctionName() throws NotCorrectToken {
		if(!getCurrentToken(1).equals(identifier)) {
			throw new NotCorrectToken(errorLine());
		}
	}
	
	private static void checkFormulaLine() throws NotCorrectToken {
		if(getCurrentToken(0).equals(r_par)) {
			return;
		}
		checkFormula();
		if(getCurrentToken(0).equals(comma)) {
			getCurrentToken(1);
			checkFormulaLine();
		}
	}
}
