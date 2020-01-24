package filemanager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class FileManager {
	public static List<String> getFilePaths(String root, String fileExtension) {
		List<String> javaSourceFiles = new ArrayList<String>();
		
		try {
			javaSourceFiles = Files.walk(Paths.get(root))
							   	   .filter(Files::isRegularFile)
							   	   .map(file -> file.toAbsolutePath().toString())
							   	   .filter(name -> name.endsWith(fileExtension))
							   	   .collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return javaSourceFiles;
	}

	public static String readAll(String path) {
		StringBuffer buffer = new StringBuffer();
		
		File file = new File(path);
		try {
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
			                      new FileInputStream(file), "UTF8"));
			
			String line;
			while ((line = in.readLine()) != null) {
			    buffer.append(line);
			    buffer.append("\n");
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buffer.toString();
	}
	
	public static void writeAll(String path, String text) {
		File file = new File(path);
		file.getParentFile().mkdirs();
	    try {
	      FileWriter fileWriter = new FileWriter(file);
	      fileWriter.write(text);
	      fileWriter.close();
	    } catch (IOException e) {
	      e.printStackTrace();
	    }
	}

	public static String copy(String root, String copyRoot) {
		String command;
		
		if(new File(copyRoot).exists()) {
			System.out.println("Remove the existing copied files");
			command = String.format("rd /S /Q %s", copyRoot);
			Cmd.execCommand(command);
		}

		System.out.println("Copy the files");
		command = String.format("Xcopy /E /I %s %s", root, copyRoot);
		Cmd.execCommand(command);
		
		return copyRoot;
	}

	public static Map<String, Set<String>> getClonePathMap(String root, String fileExtension) {
		List<String> paths = getFilePaths(root, fileExtension);
		Map<String, Set<String>> pathMap = new HashMap<String, Set<String>>();
		for(String path : paths) {
			String subPath = path.replace(root+"\\", "");
			String category = subPath.substring(subPath.indexOf('\\'));
			if(pathMap.containsKey(category) == false) {
				pathMap.put(category, new HashSet<String>());
			}
			pathMap.get(category).add(path);
		}
		
		Map<String, Set<String>> clonePathMap = new HashMap<String, Set<String>>();
		for(String key : pathMap.keySet()) {
			if(pathMap.get(key).size() >= 2) {
				clonePathMap.put(key, pathMap.get(key));
			}
		}
		
		return clonePathMap;
	}
}
