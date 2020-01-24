
package builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import parser.Info;
import tokenizer.Token;

public class CodeBuilder {
	private List<JavaCodeLine> codeList = new ArrayList<JavaCodeLine>();
	
	public CodeBuilder(List<Token> tokens, Map<Integer, Map<Info, String>> tokenInfo) {
		Stack<Object> stack = new Stack<Object>();
		stack.add(codeList);
		JavaCodeBlock block = null;
		JavaCodeLine line = null;
		for(int i=0; i<tokens.size(); i++) {
			if(line == null) {
				line = new JavaCodeLine();
				if(stack.peek() instanceof JavaCodeBlock) {
					block = (JavaCodeBlock) stack.peek();
					block.add(line);
				} else {
					codeList.add(line);
				}
			}
				
			Token token = tokens.get(i);
			String word = token.getWord();
			line.append(word);
			if(tokenInfo.containsKey(i)) {
				line.putVar(tokenInfo.get(i).get(Info.NAME), tokenInfo.get(i).get(Info.TYPE));
			}
			switch(word) {
			case"if": case"do": case"try": case"catch":
				stack.push(line);
				stack.push(word);
				break;
			case";":
				boolean isInParentheses = stack.peek() instanceof JavaCodeLine;
				if(isInParentheses == false) {
					if("if".equals(stack.peek())) {
						if(i+1<tokens.size() && "else".equals(tokens.get(i+1).getWord())) {
							stack.pop();
							line = (JavaCodeLine) stack.pop();
						} else {
							stack.pop();
							stack.pop();
							line = null;
						}
					} else {
						line = null;
					}
				}
				break;
			case"(": 
				stack.push(word); 
				stack.push(line);
				break;
			case")": 
				stack.pop(); 
				stack.pop();
				break;
			case"{":
				block = new JavaCodeBlock();
				stack.push(word);
				stack.push(block);
				line.append(block);
				line = null;
				break;
			case"}":
				stack.pop();
				stack.pop();
				if(i+1<tokens.size() && ";".equals(tokens.get(i+1).getWord())) {
					line.append(";");
					i++;
				}
				
				if(stack.peek() instanceof String) {
					String str = (String) stack.peek();
					switch(str) {
					case"if":
						if(i+1<tokens.size() && "else".equals(tokens.get(i+1).getWord())) {
							stack.pop();
							line = (JavaCodeLine) stack.pop();
						} else {
							stack.pop();
							stack.pop();
							line = null;
						}
						break;
					case"do":
						if(i+1<tokens.size() && "while".equals(tokens.get(i+1).getWord())) {
							stack.pop();
							line = (JavaCodeLine) stack.pop();
						} else {
							stack.pop();
							stack.pop();
							line = null;
						}
						break;
					case"try": case"catch":
						if(i+1<tokens.size() && "catch".equals(tokens.get(i+1).getWord())) {
							stack.pop();
							line = (JavaCodeLine) stack.pop();
						} else {
							stack.pop();
							stack.pop();
							line = null;
						}
						break;
					}
				} else if(stack.peek() instanceof JavaCodeLine) {
					line = (JavaCodeLine) stack.peek();
				} else {
					line = null;
				}
				break;
			}
		}
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(JavaCodeLine line : codeList) {
			buffer.append(line.toString());
		}
		return buffer.toString();
	}
	
	public void rearrange() {
		for(JavaCodeLine line : codeList) {
			line.scan();
			line.rearrange();
		}
	}
}
