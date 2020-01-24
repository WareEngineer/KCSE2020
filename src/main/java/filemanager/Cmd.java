package filemanager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Cmd {
	private static Process process;
	private static BufferedReader bufferedReader;
	private static StringBuffer readBuffer;
	
	public static String execCommand(String cmd) {
		String command = String.format("cmd.exe /c %s", cmd);
		
		try {
			process = Runtime.getRuntime().exec(command);
			bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line = null;
			readBuffer = new StringBuffer();
			
			while((line = bufferedReader.readLine()) != null) {
				readBuffer.append(line);
				readBuffer.append("\n");
			}
			
			return readBuffer.toString();
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
}

