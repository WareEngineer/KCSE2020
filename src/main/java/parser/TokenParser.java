package parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import tokenizer.Token;

public class TokenParser {
	private static final Set<String> primitiveType;
	
	static {
		primitiveType = new HashSet<String>(Arrays.asList(
				"boolean","char","byte","short","int","long","float","double"
		));
	}
	
	public static Map<Integer, Map<Info, String>> getTokenInfo(List<Token> tokens) {
		Map<Integer, Map<Info, String>> tokenInfo = new HashMap<Integer, Map<Info, String>>();
		Stack<String> stack = new Stack<String>();
		List<Integer> list = null;
		String type = null;
		String name = null;
		String chain = null;
		String temp = null;
		
		for(int i=0; i<tokens.size(); i++) {
			Token token = tokens.get(i);
			String word = token.getWord();
			
			if(token.isKeyword()) {
				if("package".equals(word) || "import".equals(word)) {
					while(i+1 < tokens.size()) {
						String nextWord = tokens.get(i+1).getWord();
						if(";".equals(nextWord)) break;
						else i++;
					}
				} else if("class".equals(word) || "enum".equals(word) || "interface".equals(word)) {
					if("class".equals(word) && chain != null) {
						name = chain + word;
					} else {
						while(i+1 < tokens.size()) {
							String nextWord = tokens.get(i+1).getWord();
							if("{".equals(nextWord)) break;
							else i++;
						}
					}
				} else if(primitiveType.contains(word)) {
					if("(".equals(stack.peek())) {
						type = "PARAMETER";
					} else {
						type = "DECLARE";
					}
				} else if("this".equals(word) || "super".equals(word)) {
					name = word;
					list = new ArrayList<Integer>(Arrays.asList(i));
					chain = null;
				} else {
					name = null;
					list = null;
					chain = null;
				}
			} else if (token.isIdentifier()) {
				if(chain != null) {
					if(".".equals(chain)) {
						name = null;
					} else {
						name = chain + word;
						chain = null;
						list.add(i);
					}
				} else {
					if(temp != null) {
						type = temp;
						temp = null;
					}
					if(name != null) {
						if("(".equals(stack.peek())) {
							type = "PARAMETER";
						} else {
							type = "DECLARE";
						}
					}
					name = word;
					list = new ArrayList<Integer>(Arrays.asList(i));
					chain = null;
				}
			} else {
				switch(word) {
				case "@":
					if(i+1<tokens.size() && "interface".equals(tokens.get(i+1).getWord())==false) {
						i++;
						int semaphore = 0;
						do {
							if(i+1<tokens.size()) break;
							i++;
							String nWord = tokens.get(i).getWord();
							if("(".equals(nWord)) semaphore++;
							else if(")".equals(nWord)) semaphore--;
						} while(semaphore > 0);
					}
					break;
				case ".":
					if(name != null) {
						chain = name + ".";
						list.add(i);
					} else {
						chain = ".";
					}
					continue;
				case ",":
					if("{".equals(stack.peek())) temp = "COMMA";
					break;
				case "{":
					stack.push("{");
					break;
				case "}":
					stack.pop();
					break;
				case "(":	// method
					stack.push("(");
					if(name != null) {
						if("this".equals(name) || name.startsWith("this.") || "super".equals(name) || name.startsWith("super.")) {
							for(int index : list) {
								if(tokenInfo.containsKey(index)==false) {
									tokenInfo.put(index, new HashMap<Info, String>());
								}
								tokenInfo.get(index).put(Info.NAME, name);
								tokenInfo.get(index).put(Info.TYPE, "MC");
							}
						}
						
						int index = name.lastIndexOf(".");
						if(index == -1) {
							name = null;
							list = null;
							chain = null;
						} else {
							name = name.substring(0, index);
							int size = list.size();
							for(int k=size-1; k>=size-2; k--) {
								list.remove(k);
							}
						}
					}
					break;
				case ")":
					stack.pop();
					break;
				case "[" :	
					if(i+1 < tokens.size() && "]".equals(tokens.get(i+1).getWord())) {
						i++;
						if(i+1 < tokens.size() && "{".equals(tokens.get(i+1).getWord())) {
							name = null;
						}
						continue;
					}
					break;
				case "<" :
					int semaphore = 1;
					int start = i;
					boolean isContinue = true;
					while(i+1<tokens.size() && isContinue) {
						i++;
						Token nToken = tokens.get(i);
						if(nToken.isSymbol()) {
							switch(nToken.getWord()) {
							case"<": semaphore++; break;
							case">": semaphore--; break;
							case"?": case",": 	  break;
							default :
								isContinue = false;
							}
						}
						if(semaphore==0) break;
					}
					if(semaphore > 0) i = start;
					else continue;
					break;
				case"++": case"--":
					if(name!=null) type = "USE&DEFINE";
					else type = "DEFINE&USE";
					break;
				case"+=": case"-=": case"*=": case"/=": case"%=": case"&=": case"|=": case"^=":
					type = "USE&DEFINE";
					break;
				case"=": 
					if(type==null) type = "DEFINE";
					break;
				}
				
				if(name!=null) {
					if(name.startsWith("this.")) name = name.replaceFirst("this.", "");		// this ¹«½Ã
					for(int index : list) {
						if(tokenInfo.containsKey(index)==false) {
							tokenInfo.put(index, new HashMap<Info, String>());
						}
						tokenInfo.get(index).put(Info.NAME, name);
						if(type!=null) {
							if("DECLARE".equals(type)) tokenInfo.get(index).put(Info.TYPE, "FD");
							else if("USE&DEFINE".equals(type)) tokenInfo.get(index).put(Info.TYPE, "UD");
							else if("DEFINE&USE".equals(type)) tokenInfo.get(index).put(Info.TYPE, "DU");
							else tokenInfo.get(index).put(Info.TYPE, "D");
						} else {
							tokenInfo.get(index).put(Info.TYPE, "U");
						}
						
						int pos = name.indexOf('.');
						if ( 0 < pos ) {
							String varRoot = name.substring(0, pos);
							tokenInfo.get(index).put(Info.NAME, varRoot);
							tokenInfo.get(index).put(Info.TYPE, "U");
						}
					}
				}
				type = null;
				name = null;
				chain = null;
				list = null;
			}
		}
		return tokenInfo;
	}
}
