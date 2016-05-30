package ru.mirea.spo.lab1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UI {
	static UtilHelper utilHelper = new UtilHelper();
	public static void main(String[] args) throws Exception{
		
		
	validTest();
	
		
		
	}
	public static void validTest() throws Exception{
		//utilHelper.printFile("src/test-valid.input");
		process("src/test-valid.input");
		
	}

	
	public static void process(String filename) throws Exception{
		Lexer lexer = new Lexer();
		lexer.processInput(filename);
		List<Token> tokens = lexer.getTokens();
		Parser parser = new Parser();
		parser.setTokens(tokens);
		parser.lang();
		
		List<List<PostfixToken>> postfixtoken =  parser.getPostfixToken();
		for(List<PostfixToken> currentStr: postfixtoken){ 
			System.out.print("["); 
			for(PostfixToken currentToken: currentStr){ 
			System.out.print(currentToken.getValue()+" "); 
			} 
			System.out.print("]"); 
		}
		
		
		PolizProcessor processor = new PolizProcessor(postfixtoken); 
		
		processor.go();
		
		
	}
	

}
