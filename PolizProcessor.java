package ru.mirea.spo.lab1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.*;

public class PolizProcessor {
	
	private Map<String, Integer> varTable = new HashMap<String, Integer>();
	 public static final String ASOP = "ASSIGN_OP";
	 public static final String OP_PL = "PL";
	 public static final String OP_MN = "MN";
	 public static final String OP_UMN = "UMN";
	 public static final String OP_DL = "DL";
	 public static final String VAR = "VAR";
	 public static final String DIG = "DIGIT";
	 public static final String TRAN = "TrFlag";
	 public static final String UNTRAN = "UnTrFlag";
	 public static final String METKA = "Metka";
	 public static final String LESS = "LESS";
	 public static final String INC = "INC";
	 public static final String DEC = "DEC";
	 public static final String MORE = "MORE";
	 public static final String LESSOREQ = "LESSOREQUALLS";
	 public static final String MOREOREQ = "MOREOREQUALLS";
	 
	 public static final String EQ = "EQL";
	 Stack<PostfixToken> stack = new Stack<PostfixToken>();
	
	 String mapvalue;
	 String mapname;
	private List<List<PostfixToken>> postfixTokens;
	public PolizProcessor(List<List<PostfixToken>> postfixTokens2) {
		
		this.postfixTokens = postfixTokens2;
		
	}

	public void go() {
		for(int i = 0;i <postfixTokens.size();i++)	{
			List<PostfixToken> currentStr = postfixTokens.get(i);
			for(PostfixToken a: currentStr){
			
			if(a.getName().equals(VAR)||a.getName().equals(DIG)||a.getName().equals(METKA)){
				stack.push(a);
				
		} else if(a.getName().equals(LESS)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			if(selectionvalue(second)<selectionvalue(fist)){
				
				stack.push(new PostfixToken("DIGIT", "1"));
			} else 
				stack.push(new PostfixToken("DIGIT", "0"));
			
		} else if(a.getName().equals(MORE)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			if(selectionvalue(second)>selectionvalue(fist)){
				
				stack.push(new PostfixToken("DIGIT", "1"));
			} else 
				stack.push(new PostfixToken("DIGIT", "0"));
			
		} else if(a.getName().equals(LESSOREQ)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			if(selectionvalue(second)<=selectionvalue(fist)){
				
				stack.push(new PostfixToken("DIGIT", "1"));
			} else 
				stack.push(new PostfixToken("DIGIT", "0"));
			
		}else if(a.getName().equals(INC)){
			PostfixToken  fist = stack.pop();
			
			
			varTable.put(fist.getValue(), Integer.valueOf(selectionvalue(fist))+1);
	
		
			
		}
		else if(a.getName().equals(DEC)){
			PostfixToken  fist = stack.pop();
			
			
			varTable.put(fist.getValue(), Integer.valueOf(selectionvalue(fist))-1);
			
			
			
		}
		else if(a.getName().equals(MOREOREQ)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			if(selectionvalue(second)>=selectionvalue(fist)){
				
				stack.push(new PostfixToken("DIGIT", "1"));
			} else 
				stack.push(new PostfixToken("DIGIT", "0"));
			
		}else if(a.getName().equals(EQ)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			if(selectionvalue(second)==selectionvalue(fist)){
				
				stack.push(new PostfixToken("DIGIT", "1"));
			} else 
				stack.push(new PostfixToken("DIGIT", "0"));
			
		}
		else if(a.getName().equals(TRAN)){
			
			PostfixToken  fist = stack.pop();
			
			PostfixToken  second = stack.pop();
		
			if(selectionvalue(second)==0){
				
				i=Integer.valueOf(fist.getValue())-1;
				
			}
			
		}else if(a.getName().equals(UNTRAN)){
			PostfixToken  fist = stack.pop();
			i=Integer.valueOf(fist.getValue())-1;
			
			
		}else if(a.getName().equals(ASOP)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			varTable.put(second.getValue(),Integer.valueOf(fist.getValue()));
			
		} else if(a.getName().equals(OP_PL)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			stack.push(new PostfixToken("DIGIT", String.valueOf(selectionvalue(fist)+selectionvalue(second))));
		}else if(a.getName().equals(OP_MN)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			stack.push(new PostfixToken("DIGIT", String.valueOf(selectionvalue(fist)-selectionvalue(second))));
		} else if(a.getName().equals(OP_UMN)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			stack.push(new PostfixToken("DIGIT", String.valueOf(selectionvalue(fist)*selectionvalue(second))));
		} else if(a.getName().equals(OP_DL)){
			PostfixToken  fist = stack.pop();
			PostfixToken  second = stack.pop();
			stack.push(new PostfixToken("DIGIT", String.valueOf(selectionvalue(fist)/selectionvalue(second))));
		} 
	}	
	
		}System.out.println("");
		System.out.println("Решение:");	
		printHashMap();
		
	}
	private int selectionvalue(PostfixToken token){
		if(token.getName().equals("DIGIT"))
			return Integer.valueOf(token.getValue());
		if(token.getName().equals("VAR")){
			return Integer.valueOf(varTable.get(token.getValue()));

		} else return 0;

	}
	
	public void printHashMap(){
		Set set = varTable.entrySet();
		Iterator i = set.iterator();
		 while(i.hasNext()){

		      Map.Entry me = (Map.Entry)i.next();

		      System.out.println(me.getKey() + "=" + me.getValue() );

		    }
	}
	public void assign(String name, int value){
		varTable.put(name, value);
		
	}
	

	}

