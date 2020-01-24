package builder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JavaCodeBlock {
	private List<JavaCodeLine> list = new ArrayList<JavaCodeLine>();
	private Map<String, Set<Integer>> declaredVarLocationMap;
	private Map<String, Set<Integer>> definedVarLocationMap;
	private Map<String, Set<Integer>> usedVarLocationMap;
	private Set<String> definedVarList;
	private Set<String> usedVarList;
	
	public Set<String> getDefinedVarList() { return new HashSet<String>(); }
	
	public Set<String> getUsedVarList() { return usedVarList; }
	
	public void add(JavaCodeLine line) {
		list.add(line);
	}
	
	public void scan() {
		declaredVarLocationMap = new HashMap<String, Set<Integer>>();
		definedVarLocationMap = new HashMap<String, Set<Integer>>();
		usedVarLocationMap = new HashMap<String, Set<Integer>>();
		definedVarList = new HashSet<String>();
		usedVarList = new HashSet<String>();
		
		for(int i=0; i<list.size(); i++) {
			JavaCodeLine line = list.get(i);
			line.scan();
			
			for(String name : line.getDeclareList()) {
				if(declaredVarLocationMap.containsKey(name)==false) {
					declaredVarLocationMap.put(name, new HashSet<Integer>());
				}
				declaredVarLocationMap.get(name).add(i);
			}
			
			definedVarList.addAll(line.getDefinedVarList());
			for(String name : line.getDefinedVarList()) {
				if(definedVarLocationMap.containsKey(name)==false) {
					definedVarLocationMap.put(name, new HashSet<Integer>());
				}
				definedVarLocationMap.get(name).add(i);
			}
			
			usedVarList.addAll(line.getUsedVarList());
			for(String name : line.getUsedVarList()) {
				if(usedVarLocationMap.containsKey(name)==false) {
					usedVarLocationMap.put(name, new HashSet<Integer>());
				}
				usedVarLocationMap.get(name).add(i);
			}
		}
	}
	
	public void rearrange() {
		for(JavaCodeLine line : list) {
			line.rearrange();
		}
		
		List<JavaCodeLine> result = new ArrayList<JavaCodeLine>();
		boolean[] visit = new boolean[list.size()];
		
		List<Integer> initMethodCallLineList = new ArrayList<Integer>();
		Integer returnLineNum = null;
		for(int i=0; i<list.size(); i++) {
			JavaCodeLine line = list.get(i);
			if(line.isCall()) {
				initMethodCallLineList.add(i);
			}
			if(line.isReturnStatement()) {
				if(returnLineNum != null) {
					try {
						throw new Exception();
					} catch(Exception e) {
						System.out.println("Return Statement Error");
					}
				}
				returnLineNum = i;
				visit[i] = true;
			}
		}
		
		for(int i=0; i<list.size()-1; i++) {
			if(visit[i]) continue;
			List<Integer> group = new ArrayList<Integer>();
			Deque<Integer> queue = new ArrayDeque<Integer>();
			queue.add(i);
			while(!queue.isEmpty()) {
				int pos = queue.pop();
				if(visit[pos]) continue;
				group.add(pos);
				visit[pos] = true;
				JavaCodeLine line = list.get(pos);
				
				// 현재 라인 이전에 호출된 초기화 메소드 라인을 스택에 추가
				for(int lineNum : initMethodCallLineList) {
					if(lineNum < pos) queue.add(lineNum);
				}
				
				// 현재 라인에서 정의된 변수가 있다면, 다음 정의까지 사용된 라인을 스택에 추가
				for(String name : line.getDefinedVarList()) {
					int nextDefinePos = this.getNextPos(definedVarLocationMap, name, pos);
					if(declaredVarLocationMap.containsKey(name)) {
						if( declaredVarLocationMap.get(name).contains(pos) == false) {
							int declarePos = this.getPreviousPos(declaredVarLocationMap, name, pos);
							if(declarePos < pos) {
								queue.push(declarePos);
							}
						}
					}
					if(usedVarLocationMap.containsKey(name)) {
						List<Integer> uPosList = new ArrayList<Integer>(usedVarLocationMap.get(name));
						Collections.sort(uPosList);
						for(int uPos : uPosList) {
							if(pos<uPos && uPos<=nextDefinePos) {
								queue.push(uPos);
							}
						}
					}
				}
				
				// 현재 라인에서 사용된 변수가 있다면, 이전에 해당 변수가 정의된 라인을 스택에 추가
				for(String name : line.getUsedVarList()) {
					int previousDefinePos = this.getPreviousPos(definedVarLocationMap, name, pos);
					if(previousDefinePos < pos) {
						queue.push(previousDefinePos);
					}
				}
			}
			Collections.sort(group);
			for(int index : group) {
				result.add(list.get(index));
			}
		}
		if(returnLineNum != null) {
			result.add(list.get(returnLineNum));
		}
		result.add(list.get(list.size()-1));
		list = result;
	}
	
	private int getPreviousPos(Map<String, Set<Integer>> map, String name, int pos) {
		int previousPos = pos;
		if(map.containsKey(name)) {
			List<Integer> indexes = new ArrayList<Integer>(map.get(name));
			Collections.sort(indexes);
			Collections.reverse(indexes);
			for(int index : indexes) {
				if(index < pos) {
					previousPos = index;
					break;
				}
			}
		}
		return previousPos;
	}

	private int getNextPos(Map<String, Set<Integer>> map, String name, int pos) {
		int nextPos = list.size()-1;
		if(map.containsKey(name)) {
			List<Integer> indexes = new ArrayList<Integer>(map.get(name));
			Collections.sort(indexes);
			for(int index : indexes) {
				if(pos < index) {
					nextPos = index;
					break;
				}
			}
		}
		return nextPos;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("\n");
		for(JavaCodeLine line : list) {
			buffer.append(line.toString());
		}
		return buffer.toString();
	}
}
