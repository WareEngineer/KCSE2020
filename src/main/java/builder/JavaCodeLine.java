package builder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavaCodeLine {
	private List<Object> line = new ArrayList<Object>();
	private Set<String> declaredVarList = new HashSet<String>();
	private Set<String> definedVarList = new HashSet<String>();
	private Set<String> usedVarList = new HashSet<String>();
	private boolean isInitMethodCall = false;	// call a this methods or a super method
	
	public Set<String> getDeclareList() { return declaredVarList; }
	public Set<String> getDefinedVarList() { return definedVarList; }
	public Set<String> getUsedVarList() { return usedVarList; }
	public boolean isCall() { return isInitMethodCall; }

	public void append(String word) {
		line.add(word);
	}

	public void append(JavaCodeBlock block) {
		line.add(block);
	}
	
	public void scan() {
		for(Object obj : line) {
			if(obj instanceof JavaCodeBlock) {
				JavaCodeBlock block = (JavaCodeBlock) obj;
				block.scan();
				definedVarList.addAll(block.getDefinedVarList());
				usedVarList.addAll(block.getUsedVarList());
			}
		}
	}
	
	public void putVar(String name, String type) {
		if(type == null) return;
		if(type.contains("D")) {
			definedVarList.add(name);
		}
		if(type.contains("U")) {
			usedVarList.add(name);
		}
		if(type.contains("F")) {
			declaredVarList.add(name);
		}
		if(type.contains("MC")) {
			isInitMethodCall = true;
		}
	}

	public void rearrange() {
		for(Object obj : line) {
			if(obj instanceof JavaCodeBlock) {
				JavaCodeBlock block = (JavaCodeBlock) obj;
				block.rearrange();
			}
		}
	}
	
	public String toString() {
		StringBuffer buffer = new StringBuffer();
		for(Object obj : line) {
			buffer.append(obj.toString());
			buffer.append(" ");
		}
		buffer.append("\n");
		return buffer.toString();
	}
	
	public void print() {
		System.out.println("D : " + definedVarList.toString());
		System.out.println("U : " + usedVarList.toString());
		System.out.println(this.toString());
		System.out.println("\n\n");
	}

	public boolean isReturnStatement() {
		if("return".equals(line.get(0))) {
			return true;
		}
		return false;
	}
}
