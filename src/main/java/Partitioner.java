import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.AbstractDelta;
import com.github.difflib.patch.Patch;

public class Partitioner {
	
	public static List<String> partition(List<String> list) {
		List<String> integration = new LinkedList<String>( Arrays.asList(list.get(0).split("\n")) );
		
		for(int i=1; i<list.size(); i++) {
			List<String> target = Arrays.asList(list.get(i).split("\n"));
			try {
				Patch<String> patch = DiffUtils.diff(integration, target);
				List<AbstractDelta<String>> deltaList = patch.getDeltas();
				Collections.reverse(deltaList);
				for(AbstractDelta<String> delta : deltaList) {
					List<String> block = new LinkedList<String>();
					int index = delta.getSource().getPosition();
					int start = 0;
					int end = 0;
					switch(delta.getType()) {
					case DELETE:
						start = index;
						end = index + delta.getSource().getLines().size()-1;
						block.add("#@#Bodundary#@#");
						block.addAll(delta.getSource().getLines());
						block.add("#@#Bodundary#@#");
						break;
					case INSERT:
						if(integration.size()<=index) {
							index = integration.size()-1;
						}
						block.add("#@#Bodundary#@#");
						block.addAll(delta.getTarget().getLines());
						block.add("#@#Bodundary#@#");
						break;
					case CHANGE:
						start = index;
						end = index + delta.getSource().getLines().size()-1;
						block.add("#@#Bodundary#@#");
						block.addAll(delta.getSource().getLines());
						block.add("#@#Bodundary#@#");
						block.addAll(delta.getTarget().getLines());
						block.add("#@#Bodundary#@#");
						break;
					default: 
						continue;
					}
					for(int k=end; k>=start; k--) {
						integration.remove(k);
					}
					integration.addAll(index, block);
				}
			} catch (DiffException e) {
				e.printStackTrace();
			}
			
		}
		
		List<String> partitionOfSet = new ArrayList<String>();
		StringBuffer buffer = new StringBuffer();
		for(String line : integration) {
			if("#@#Bodundary#@#".equals(line)) {
				if("".equals(buffer.toString().trim()) == false) {
					partitionOfSet.add(buffer.toString());
				}
				buffer = new StringBuffer();
			} else {
				buffer.append(line);
			}
		}
		
		return partitionOfSet;
	}
}
