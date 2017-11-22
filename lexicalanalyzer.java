import java.io.File;
import java.util.Scanner;

public class lexicalanalyzer implements Lex{
	
	static char[] stLine;
	static int stLinePos;
	
	private boolean isDigit(char c) {
		try {
			if(c == '\0') {
				return false;
			}
			Double.parseDouble(c+"");
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	private boolean isSpace(char c) {
		return File.separator.equals(c + "");
	}

	@Override
	public void setLine(char[] line) {
		stLine = line;
		stLinePos = 0;
	}

	@Override
	public Token getToken() {
		LexerStatus status = LexerStatus.INITIAL_STATUS;
		char currentChar;
		Token token = new Token();
		token.setKind(TokenKind.BAD_TOKEN);
		if(stLine.length == stLinePos) {
			token.setKind(TokenKind.END_OF_LINE_TOKEN);
			return token;
		}
		while(stLine.length-1 >= stLinePos) {
			currentChar = stLine[stLinePos];
			if((status == LexerStatus.IN_INT_PART_STATUS || status == LexerStatus.IN_FARC_PART_STATUS) &&
					!isDigit(currentChar) &&
					currentChar != '.') {
				token.setKind(TokenKind.NUMBER_TOKEN);
				token.setValue(Double.parseDouble(token.getStr()));
				return token;
			}
			if(isSpace(currentChar)) {
				if(stLine.length-1 == stLinePos) {
					token.setKind(TokenKind.END_OF_LINE_TOKEN);
					return token;
				}
				stLinePos++;
				continue;
			}
			
			token.setStr(token.getStr() + currentChar);
			stLinePos++;
			
			if(currentChar == '+') {
				token.setKind(TokenKind.ADD_OPERATOR_TOKEN);
				return token;
			} else if(currentChar == '-') {
				token.setKind(TokenKind.SUB_OPERATOR_TORKN);
				return token;
			} else if(currentChar == '*') {
				token.setKind(TokenKind.MUL_OPERATOR_TOKEN);
				return token;
			} else if(currentChar == '/') {
				token.setKind(TokenKind.DIV_OPERATOR_TOKRN);
				return token;
			} else if(isDigit(currentChar)) {
				if(status == LexerStatus.INITIAL_STATUS) {
					status = LexerStatus.IN_INT_PART_STATUS;
				} else if(status == LexerStatus.DOT_STATUS) {
					status = LexerStatus.IN_FARC_PART_STATUS;
				}
			} else if(currentChar == '.') {
				status = LexerStatus.DOT_STATUS;
			} else if(currentChar == '\0'){
				token.setKind(TokenKind.END_OF_LINE_TOKEN);
				return token;
			} else {
				System.out.println("error! bad character!");
				return token;
			}
			
		}
		token.setKind(TokenKind.END_OF_LINE_TOKEN);
		return token;
	}
	
	public void parseLine(char[] str) {
		setLine(str);
		while(true) {
			Token token;
			token = getToken();
			if(token.getKind() == TokenKind.END_OF_LINE_TOKEN) {
				break;
			} else {
				System.out.println(" token " + token.getKind() + " ; value " + token.getStr());
			}
		}
	}
	
	public static void main(String[] args) {
		lexicalanalyzer l = new lexicalanalyzer();
		Scanner sc = new Scanner(System.in);
		String ss = sc.nextLine() + "\0";
		if(ss != null) {
			char[] str = ss.toCharArray();
			l.parseLine(str);
		}
		sc.close();

	}
	
}
