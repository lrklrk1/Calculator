import java.util.Scanner;

public class Parser {
	
	static Parser p = new Parser();
	static lexicalanalyzer l = new lexicalanalyzer();
	
	static Token lookAheadToken;
	static boolean stLookAhead;
	
	public Token getAheadToken() {
		if(stLookAhead) {
			stLookAhead = false;
			return lookAheadToken;
		} else {
			return l.getToken();
		}
	}
	
	public void ungetAheadToken(Token token) {
		stLookAhead = true;
		lookAheadToken = token;
	}
	
	private double parseLine() throws Exception {
		double value;
		stLookAhead = false;
		value = parseExpression();
		return value;
	}
	
	private double parseExpression() throws Exception {
		double v1, v2;
		Token token;
		
		v1 = parseTerm();
		while(true) {
			token = getAheadToken();
			if(!token.getKind().equals(TokenKind.ADD_OPERATOR_TOKEN) &&
					!token.getKind().equals(TokenKind.SUB_OPERATOR_TORKN)) {
				ungetAheadToken(token);
				break;
			}
			v2 = parseTerm();
			if(token.getKind().equals(TokenKind.ADD_OPERATOR_TOKEN)) {
				v1 += v2;
			} else if(token.getKind().equals(TokenKind.SUB_OPERATOR_TORKN)) {
				v1 -= v2;
			} else {
				ungetAheadToken(token);
				break;
			}
		}
		return v1;
	}
	
	private double parseTerm() throws Exception {
		double v1, v2;
		Token token;
		v1 = parsePrimary();
		while(true) {
			token = getAheadToken();
			if(!token.getKind().equals(TokenKind.DIV_OPERATOR_TOKRN) && 
					!(token.getKind().equals(TokenKind.MUL_OPERATOR_TOKEN))) {
				ungetAheadToken(token);
				break;
			}
			v2 = parsePrimary();
			if(token.getKind().equals(TokenKind.MUL_OPERATOR_TOKEN)) {
				v1 *= v2;
			} else if(token.getKind().equals(TokenKind.DIV_OPERATOR_TOKRN)) {
				v1 /= v2;
			}
		}
		return v1;
	}
	
	private double parsePrimary() throws Exception {
		Token token;
		double v1;
		boolean flag = false;
		while(true) {
			token = getAheadToken();
			
			if(token.getKind().equals(TokenKind.SUB_OPERATOR_TORKN)) {
				if(flag) {
					System.out.println(" - error!");
					throw new Exception(); 
				} else {
					flag = true;
				}
				continue;
			} 
			
			if(token.getKind().equals(TokenKind.NUMBER_TOKEN)) {
				v1 = token.getValue();
				break;
			} else if(token.getKind().equals(TokenKind.LP_TOKEN)) {
				v1 = parseExpression();
				token = getAheadToken();
				if(!token.getKind().equals(TokenKind.RP_TOKEN)) {
					System.out.println(" (  ) error!");
					throw new Exception(); 
				}
				break;
			} else {
				System.out.println("error !");
				throw new Exception();
			}
		}
		
		if(flag) {
			return -v1;
		} else {
			return v1;
		}
		
	}
	
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		while(sc.hasNext()) {
			String s = sc.nextLine() + "\0";
			char[] ss = s.toCharArray();
			l.setLine(ss);
			double value;
			try {
				value = p.parseLine();
				System.out.println(">>> " + value);
				System.out.println();
			} catch (Exception e) {
				System.out.println("axiba");
				e.printStackTrace();
			}
			
		}
		sc.close();
	}
	
}
