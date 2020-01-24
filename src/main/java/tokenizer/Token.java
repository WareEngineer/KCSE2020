package tokenizer;

public class Token {
	private final TokenType type;
	private final String word;
	private final int line;
	
	public Token(TokenType type, String word, int line) {
		this.type = type;
		this.word = word;
		this.line = line;
	}
	
	public String getType() { return type.toString(); }
	public String getWord() { return word; }
	public int getLine() { return line; }
	
	public boolean isKeyword() {
		if (this.type == TokenType.KEYWORD) {
			return true;
		}
		return false;
	}
	
	public boolean isConst() {
		if (this.type == TokenType.CONST) {
			return true;
		}
		return false;
	}
	
	public boolean isIdentifier() {
		if (this.type == TokenType.IDENTIFIER) {
			return true;
		}
		return false;
	}
	
	public boolean isSymbol() {
		if (this.type == TokenType.SYMBOL) {
			return true;
		}
		return false;
	}
}
