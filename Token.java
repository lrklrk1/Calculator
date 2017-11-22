
public class Token {
		
	private TokenKind kind;
	private double value;
	public String str = "";
	
	public double getValue() {
		return this.value;
	}
	
	public void setValue(double value) {
		this.value = value;
	}
	
	public void setKind(TokenKind kind) {
		this.kind = kind;
	}
	
	public TokenKind getKind() {
		return this.kind;
	}
	
	public void setStr(String str) {
		this.str = str;
	}
	
	public String getStr() {
		return this.str;
	}
}
