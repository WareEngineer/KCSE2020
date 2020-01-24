package tokenizer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Pattern;

public class Tokenizer {
	private static final String IDENTIFIER_REGEX = "^[_a-zA-Z][0-9_a-zA-Z]*$";
	private static final String NUMBER_REGEX = "^[0-9][xX0-9a-f.lLfFdD]*$";
	private static final Set<String> SYMBOLS;
	private static final Set<String> KEYWORDS;
	private static final Set<String> LETERALS;
	
	static {
		SYMBOLS = new HashSet<String>(Arrays.asList(
			"+","+=","++","-","-=","--","*","*=","/","/=","//","/*","%","%=",
			"&","&=","&&","|","|=","||","^","^=",">","->",">=","<","<=","=","==","!","!=",
			"~",".",",","?",":",";","@","(",")","[","]","{","}","'","\""
			,">>",">>>","<<"
		));
		KEYWORDS = new HashSet<String>(Arrays.asList(
			"abstract","assert","boolean","break","byte","case","catch","char","class","continue",
			"default","do","double","else","enum","extends","final","finally","float","for",
			"if","implements","import","instanceof","int","interface","long","native","new",
			"package","private","protected","public","return","short","static","strictfp",
			"super","switch","synchronized","this","throw","throws","transient","try",
			"void","volatile","while"
		));	
		LETERALS = new HashSet<String>(Arrays.asList(
			"null","true","false"
		));
	}
	
	public static List<Token> tokenize(String source) {
		List<Token> tokens = new ArrayList<Token>();
		Stack<String> stack = new Stack<String>();
		
		int line = 1;
		char[] arr = source.toCharArray();
		for(int i=0; i<arr.length; i++) {
			TokenType type;
			String word = Character.toString(arr[i]);
			if(isAlphbet( arr[i] ) || arr[i]=='_') {
				while(Pattern.matches(IDENTIFIER_REGEX, word + arr[i+1])) {
					word += arr[++i];
				}
				if(LETERALS.contains(word)) {
					type = TokenType.CONST;
				} else if (KEYWORDS.contains(word)) {
					type = TokenType.KEYWORD;
				} else {
					type = TokenType.IDENTIFIER;
				}
			} else if (isNumber(arr[i])) {
				while(Pattern.matches(NUMBER_REGEX, word + arr[i+1])) {
					word += arr[++i];
				}
				type = TokenType.CONST;
			} else {
				if(SYMBOLS.contains(word) == false) {
					if ("\n".equals(word)) {
						line++;
					}
					continue;
				} else {
					switch(word) {
					case"<": stack.add(word);  break;
					case",": case"?": case">": break;
					default:
						if(!stack.isEmpty()) {
							if("<".equals(stack.peek())) {
								stack.pop();
							}
						}
					}
					
					while(i+1<arr.length && SYMBOLS.contains(word + arr[i+1])) {
						if(!stack.isEmpty()) {
							if(">".equals(word) && "<".equals(stack.peek())) {
								stack.pop();
								break;
							}
						}
						word += arr[++i];
					}
					
					if("//".equals(word)) {
						while(i+1<arr.length && arr[i+1]!='\n') {
							i++;
						}
						continue;
					} else if ("/*".equals(word)) {
						while(!(arr[i+1]=='*' && arr[i+2]=='/')) {
							i++;
						}
						i += 2;
						continue;
					} else if ("'".equals(word)) {
						do {
							if(arr[i]=='\\') {
								word += arr[++i];
							}
							word += arr[++i];
						} while(arr[i]!='\'');
						type = TokenType.CONST;
					} else if ("\"".equals(word)) {
						do {
							if(arr[i]=='\\') {
								word += arr[++i];
							}
							word += arr[++i];
						} while(arr[i]!='\"');
						type = TokenType.CONST;
					} else {
						type = TokenType.SYMBOL;
					}
				}
			}
			Token token = new Token(type, word, line);
			tokens.add(token);
		}
		
		return tokens;
	}

	private static boolean isAlphbet(char ch) {
		if(('a'<=ch&&ch<='z') || ('A'<=ch&&ch<='Z')) return true;
		return false;
	}

	private static boolean isNumber(char ch) {
		if('0'<=ch&&ch<='9') return true;
		return false;
	}
}