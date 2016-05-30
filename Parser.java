package ru.mirea.spo.lab1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class Parser {
	private static final Object FOR_KW = null;
	private List<Token> tokens;
	private Token currentToken;
	
	private Stack<Token> bracketsStack = new Stack<Token>();
	private int currentTokenNumber = 0;
	
	
	public void setTokens(List<Token> tokens) {
		this.tokens = tokens;
		
	}
	
	
	public void lang() throws Exception {
		boolean exist = false;
		
		while (currentTokenNumber < tokens.size() && expr()){
			exist = true;
		}
		if(!exist){
			throw new Exception("error in lang");
		}
		
	}
	
	
	public boolean expr() throws Exception {
		if(declare()||assign()||for_loop()||switcher()){
			return true;
		}else {
			throw new Exception("declare, assign, for_loop or switch expected, but "
					+currentToken +"found.");
		}
		
		
	}
	
	

    public boolean declare() throws Exception{
        if(varKw()){
            if(ws()){
                if(var()){
                    if(!sm())
                        throw new Exception("Expected ';'but "
					+currentToken +"found.");
                    else{
                        return true;
                    }
                }else {                 
                    return false;
                }
            } else {
                return false;
            }
        }else {
            return false;
        }
    }
	
	public boolean assign() throws Exception{
		if(var()){
			if(assignOp()||less_op()||more_op()||lesseq_op()||moreeq_op()){
				if(stmt()){
                    if(!sm())
                        throw new Exception("Expected ';' but "
                                +currentToken +" found.");
                    else{
                        return true;}
				}
                else {
                    throw new Exception("stmt  expected, but "
                            +currentToken +"found.");
                }
			} else{
				throw new Exception("assignOp  expected, but "
						+currentToken +"found.");

			}
		}else {
			return false;
		}

	}
	public boolean switcher()throws Exception{
		if(switcher_decl()){
			if(switcher_body()){
				
					return true; 
					
				} else {
					return false;
				}
				
						
		}else 
			return false;
			
	}
	public boolean switcher_decl()throws Exception{
		if(switcherKw()){ 
			if(openBr()){
				if(var()){ 
					if(closeBr()){
						return true; 
					} else throw new Exception("Braket ')' expected but " +currentToken +"found.");
					} else return false;
			}else throw new Exception("Braket '(' expected but " +currentToken +"found.");
							
			} else return false;
		} 
	public boolean 	switcher_body() throws Exception{
		if(openBRECES()){
			if(exprSwi()){
				do {
				
			}while (exprSwi());
				
				if(defaultSwi()){
				if(closeBRECES()){return true;
			} else  throw new Exception("Braket '{' expected but " +currentToken +"found.");
				}else return false;
	}  else return false;
			
			
			
			
		}else throw new Exception("Braket '}' expected but " +currentToken +"found.");
		

}
	public boolean 	exprSwi() throws Exception{
		if(caseKw()){
			if(digit()){
				if(cl()){
					if(assign()){
						if(breakKw()){
							if (sm()){return true;
						} else throw new Exception("SM  expected but " +currentToken +"found.");
						}else throw new Exception("BreakKW  expected but " +currentToken +"found.");
					} else return false;
				} else throw new Exception("Colon ':' expected but " +currentToken +"found.");
				
			}else throw new Exception("Digit expected but " +currentToken +"found.");
			
		} else return false;
		
	}
	
	public boolean 	defaultSwi() throws Exception{
		if(defaultKw()){
				if(cl()){
						if(breakKw()){
							if (sm()){return true;
						} else throw new Exception("SM  expected but " +currentToken +"found.");
						}else throw new Exception("BreakKW  expected but " +currentToken +"found.");
					
				} else throw new Exception("Colon ':' expected but " +currentToken +"found.");
				
			
			
		} else return false;
	}
	
	public boolean for_loop()throws Exception{
		if(for_decl()){
			if(for_body()){
				return true; 
					
				} else {
					
					return false;  
				}
				
						
		}else 
			return false;
			
	}
	public boolean for_decl()throws Exception{
		if(forKw()){ 
			if(openBr()){
				if(assign()){ 
					if(assign()){
					if(for_incdec()){ 
					return true;
					}else{ 
						return false;}
					}else return false;
							} else return false;
			}else throw new Exception("Braket '(' expected but " +currentToken +"found.");
							
			} else return false;
		} 
	
	

	public boolean 	for_body() throws Exception{
		if(openBRECES()){
				if(assign()){
					do {
						
					}while (assign());
					if(closeBRECES()){
				return true;
					} else  throw new Exception("Braket '{' expected but " +currentToken +"found.");
							
			}  else return false;
		
		}else  throw new Exception("Braket '}' expected but " +currentToken +"found.");
	}
	
	public boolean for_incdec()throws Exception{
		if(var()){ 
			if(inc()||dec()){
					if(closeBr()){
				return true;
				}else throw new Exception("Braket ')' expected but " +currentToken +"found.");
				
				
			}System.out.println("badinc"+ currentToken);
			return false;
		}
		return false;
	}

	
	public boolean stmt() throws Exception{
		if(stmtUnit()){
			while(plus()||minus()||umn()||del()){
				if(!stmtUnit()){
					throw new Exception("stmt_unit  expected, but "
							+ currentToken +"found.");
				}
			} 
			if(bracketsStack.empty())
				return true;
			else
				throw new Exception("Braket ')' expected but "
					+ currentToken +"found.");
		}else {
			throw new Exception("stmt_unit  expected, but "
					+ currentToken +"found.");
		}
		
	}
	public boolean stmtUnit() throws Exception{
		if (openBr()){
			bracketsStack.push(currentToken);
			if(!stmt()){
				throw new Exception("stmt expected, but "
						+ currentToken +"found.");
			}else
				return true;
		}else if(digit()||var()){
			if(closeBr()){
				do{
					if(bracketsStack.empty())
						throw new Exception("anexpected ')'");
					bracketsStack.pop();
				}while(closeBr());
				
			}
			return true;
		}
		return false;
		
	}
	

	
	public boolean sm()  {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("SM"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("SM");
		
	}
	public boolean varKw(){
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("VAR_KW")){
			currentTokenNumber=temp;
		}
		return currentToken.getName().equals("VAR_KW");
	}
	public boolean forKw(){
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("FOR_KW")){
			currentTokenNumber=temp;
		}
		return currentToken.getName().equals("FOR_KW");
	}
	
	public boolean switcherKw(){
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("SWITCH_KW")){
			currentTokenNumber=temp;
		}
		return currentToken.getName().equals("SWITCH_KW");
	}
	public boolean caseKw(){
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("CASE_KW")){
			currentTokenNumber=temp;
		}
		return currentToken.getName().equals("CASE_KW");
	}
	
	public boolean defaultKw(){
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("DEFAULT_KW")){
			currentTokenNumber=temp;
		}
		return currentToken.getName().equals("DEFAULT_KW");
	}
	public boolean breakKw(){
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("BREAK_KW")){
			currentTokenNumber=temp;
		}
		return currentToken.getName().equals("BREAK_KW");
	}
	public boolean assignOp() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("ASSIGN_OP"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("ASSIGN_OP");
	}
	
	public boolean less_op() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("LESS"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("LESS");
	}
	public boolean more_op() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("MORE"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("MORE");
	}
	public boolean lesseq_op() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("LESSOREQUALLS"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("LESSOREQUALLS");
	}
	public boolean moreeq_op() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("MOREOREQUALLS"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("MOREOREQUALLS");
	}
	public boolean plus() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("PL"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("PL");
	}
	
	public boolean minus() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("MN"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("MN");
	}
	public boolean umn() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("UMN"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("UMN");
	}
	public boolean del() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("DL"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("DL");
	}
	public boolean inc() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("INC"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("INC");
	}
	private boolean dec() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("DEC"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("DEC");
	}

	public boolean openBr() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("BR_OP"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("BR_OP");	
	}
	
	public boolean closeBr() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("BR_CL"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("BR_CL");	
	}
	public boolean openBRECES() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("BRECES_OP"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("BRECES_OP");	
	}
	public boolean closeBRECES() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("BRECES_CL"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("BRECES_CL");	
	}
	public boolean cl() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("COLOB"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("COLOB");	
	}
	public boolean digit() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("DIGIT"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("DIGIT");
	}
	public boolean var() {
		int temp=currentTokenNumber; 
		wsIgnore();
		if(!currentToken.getName().equals("VAR"))
			currentTokenNumber=temp;
		return currentToken.getName().equals("VAR");
	}
	
	public boolean ws() {
		int temp=currentTokenNumber; 
		match();
		return currentToken.getName().equals("WS");
	}
	
	private void wsIgnore(){
		do
			match();
		while (currentToken.getName().equals("WS"));
	}
	
	public boolean match(){
		if (currentTokenNumber < tokens.size()) {
			currentToken = tokens.get(currentTokenNumber);
			currentTokenNumber++;
			return true;
		} else {
			return false;
		}
		
	}
	private int getPrior(Token op) {
		
		if(op.getName().equals("PL")||op.getName().equals("MN")||op.getName().equals("INC")){
			return 1;
		}else if(op.getName().equals("DL")||op.getName().equals("UMN")){
			return 2;
		}
		return 0;
		
	}

	public List<List<PostfixToken>> getPostfixToken() throws Exception {
		currentTokenNumber=0;
		
		List<List<PostfixToken>> arrpoliz = new ArrayList<List<PostfixToken>>();
	
		List<PostfixToken> poliz = new ArrayList<PostfixToken>();
		Stack<PostfixToken> stack = new Stack<PostfixToken>();
		Stack<PostfixToken> stackKW = new Stack<PostfixToken>();
		
		List<Integer> mark = new ArrayList<Integer>();
		List<Integer> andofcase = new ArrayList<Integer>();
	
		int untran=0;
		int metka=0;
		
		int caseindex=0;
		boolean dec=false;
		boolean inc=false;
		boolean forKw=false;
		boolean flag=false;
		Token var_SW = null;
		while(currentTokenNumber<tokens.size()){
			
			
			 if(forKw()){
				 forKw=true;
				 flag =true;
				 openBr();
			
			 } else if(switcherKw()){
				
				 openBr();
				 var();
				 var_SW = currentToken;
				 closeBr();
				 openBRECES();
				
				
			 } else if(defaultKw()){
				 	List<PostfixToken> temppoliz = new ArrayList<PostfixToken>(); 
					poliz.add(new PostfixToken(currentToken.getName(), currentToken.getValue()));
					temppoliz.addAll(poliz); 
					arrpoliz.add(temppoliz); 
					poliz.clear();
			 } else if(var()||digit()){
					poliz.add(new PostfixToken(currentToken.getName(), currentToken.getValue()));
					
					
				}
				else if (assignOp()||minus()||plus()||umn()||del()){
					if(!stack.empty()&&!stack.peek().equals("BR_OP")){
						
						
						while(!stack.empty()&&(getPrior(currentToken)<=getPrior(stack.peek()))){
							poliz.add(stack.pop());
							
						}
					}	
					stack.push(new PostfixToken(currentToken.getName(), currentToken.getValue()));
					
				}
				else if(less_op()||more_op()||lesseq_op()||moreeq_op()){
					
					untran = arrpoliz.size();
					stack.push(new PostfixToken(currentToken.getName(), currentToken.getValue()));
					
				
					
				} else if(inc()){ 
					poliz.clear();
					closeBr();
					inc=true;
				
					
				} else if(dec()){
					poliz.clear();
					closeBr();
					dec=true;
				}
				else if(closeBRECES()){
					if(forKw&&flag){
						
						List<PostfixToken> temppoliz = new ArrayList<PostfixToken>(); 
						poliz.add(new PostfixToken("VAR", "i"));
						if(inc){
						poliz.add(new PostfixToken("INC", "++"));
						} else if(dec){
							poliz.add(new PostfixToken("DEC", "--"));
						}
						temppoliz.addAll(poliz); 
						arrpoliz.add(temppoliz); 
						poliz.clear();
						flag=false;
						metka = arrpoliz.size();
					} 
					else {
						int exit= arrpoliz.size();
						List<PostfixToken> tempsw = arrpoliz.get(mark.get(caseindex-1));
						tempsw.add(new PostfixToken("Metka",  String.valueOf(exit)));
						tempsw.add(new PostfixToken("TrFlag", "!F"));
						for(int i1: andofcase){
							List<PostfixToken> tempsw1 = new ArrayList<PostfixToken>();
							tempsw1=arrpoliz.get(i1);
							tempsw1.add(new PostfixToken("Metka", String.valueOf(exit)));
							tempsw1.add(new PostfixToken("UnTrFlag", "!"));
							
						}
					}
					
				}else if (caseKw()){
					digit();
					
					mark.add(arrpoliz.size());
					if(mark.size()>1){
						
						List<PostfixToken> tempsw = arrpoliz.get(mark.get(mark.size()-2));
						tempsw.add(new PostfixToken("Metka",  String.valueOf(mark.get(mark.size()-1))));
						tempsw.add(new PostfixToken("TrFlag", "!F"));
						
					}
					
					poliz.add(new PostfixToken(var_SW.getName(), var_SW.getValue()));
					poliz.add(new PostfixToken(currentToken.getName(), currentToken.getValue()) );
					poliz.add(new PostfixToken("EQL", "=="));
					List<PostfixToken> temppoliz = new ArrayList<PostfixToken>() ;
					temppoliz.addAll(poliz); 
					arrpoliz.add(temppoliz); 
					poliz.clear();
				
					caseindex++;
				} else if(breakKw()){
					andofcase.add(arrpoliz.size()-1);
					sm();
					
				}else if(openBr()){ 
					if(forKw){
				
						 
					}
					else  {
					
					stack.push(new PostfixToken(currentToken.getName(), currentToken.getValue()));
					}
				}
				else if(closeBr()){
					
					if(inc){
						
						List<PostfixToken> temppoliz = new ArrayList<PostfixToken>(); 
					
						
						poliz.add(stack.pop());
						temppoliz.addAll(poliz); 
						arrpoliz.add(temppoliz); 
						poliz.clear();
							
					
					}else {
					PostfixToken temp;
					temp=stack.pop();
					
					while(!temp.getName().equals("BR_OP")){
						poliz.add(temp);
						temp=stack.pop();
					}
					}
				}
				else if(sm()){
					List<PostfixToken> temppoliz = new ArrayList<PostfixToken>(); 
					
					while(!stack.empty()){
						poliz.add(stack.pop());
						
					} 
					
					temppoliz.addAll(poliz); 
					arrpoliz.add(temppoliz); 
					poliz.clear();
					
				
		}else {
		
			
				currentTokenNumber++;}
			
			
		
		}
		while(!stack.empty()){
			poliz.add(stack.pop());
			
		} 
		if(forKw){
			
			List<PostfixToken> temp = arrpoliz.get(untran);
			temp.add(new PostfixToken("Metka", String.valueOf(metka)));
			temp.add(new PostfixToken("TrFlag", "!F"));
			
			List<PostfixToken> temp1 = arrpoliz.get(metka-1);
			temp1.add(new PostfixToken("Metka", String.valueOf(untran)));
			temp1.add(new PostfixToken("UnTrFlag", "!"));
		}

		return arrpoliz;
	}
}
