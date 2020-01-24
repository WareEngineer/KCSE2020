import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.difflib.DiffUtils;
import com.github.difflib.algorithm.DiffException;
import com.github.difflib.patch.Patch;

import builder.CodeBuilder;
import filemanager.FileManager;
import parser.Info;
import parser.TokenParser;
import tokenizer.Token;
import tokenizer.Tokenizer;

public class Main {
	
	public static void main(String[] args) {
		String fileExtension = ".java";
		String root = "C:\\Users\\user\\Downloads\\ApoGames\\Java";
		String copiedRoot = "C:\\Users\\user\\Downloads\\ApoGames\\JavaCopy";
		String sortedRoot = "C:\\Users\\user\\Downloads\\ApoGames\\JavaSort";

		System.out.println("Preparing...");
		List<String> paths = FileManager.getFilePaths(root, fileExtension);

		System.out.println("Sorting...");
		sortCode(paths, root, copiedRoot, sortedRoot);

		System.out.println("Anlyzing...");
		Map<String, Integer> origin = analyze(copiedRoot, fileExtension);
		Map<String, Integer> outcome = analyze(sortedRoot, fileExtension);
		String title = String.format("정렬 전        정렬 후        파일명");
		System.out.println("================================================================");
		System.out.println(title);
		for(String key : origin.keySet()) {
			int originCount = origin.get(key);
			int outcomeCount = outcome.get(key);
			if(originCount==0 && outcomeCount==0) continue;
			String line = String.format("%4d %7d    %s", originCount, outcomeCount, key);
			System.out.println(line);
		}
		System.out.println("================================================================");
	}
	
	private static void sortCode(List<String> paths, String root, String copiedRoot, String sortedRoot) {
		int count = 0;
		int sum = 0;
		for(String path : paths) {
			String source = FileManager.readAll(path);
			List<Token> tokens = Tokenizer.tokenize(source);
			Map<Integer, Map<Info, String>> tokenInfo = TokenParser.getTokenInfo(tokens);
			
			CodeBuilder editor = new CodeBuilder(tokens, tokenInfo);
			String original = editor.toString();
			String copiedPath = path.replace(root, copiedRoot);
			FileManager.writeAll(copiedPath, original);
			
			editor.rearrange();
			String revised = editor.toString();
			String sortedPath = path.replace(root, sortedRoot);
			FileManager.writeAll(sortedPath, revised);
			
			try {
				List<String> originalList = Arrays.asList(original.split("\n"));
				List<String> revisedList = Arrays.asList(revised.split("\n"));
				Patch<String> patch = DiffUtils.diff(originalList, revisedList);
//				System.out.println(String.format("%3d : %s", patch.getDeltas().size(), path));
				count++;
				sum += patch.getDeltas().size();
			} catch (DiffException e) {
				e.printStackTrace();
			}
		}
		float mean = (float)sum/count;
//		System.out.println("==> Mean : " + mean);
	}
	
	private static Map<String, Integer> analyze(String filePath, String fileExtension) {
		Map<String, Integer> result = new HashMap<String, Integer>();
		Map<String, Set<String>> map = FileManager.getClonePathMap(filePath, fileExtension);
		for(String key : map.keySet()) {
			List<String> list = new ArrayList<String>();
			for(String path : map.get(key)) {
				String source = FileManager.readAll(path);
				list.add(source);
			}
			result.put(key, Partitioner.partition(list).size());
		}
		return result;
	}
}

