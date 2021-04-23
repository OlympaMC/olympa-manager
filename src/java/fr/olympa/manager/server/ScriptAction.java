package fr.olympa.manager.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.function.Consumer;

public class ScriptAction {

	public static void execute(String command) throws IOException, InterruptedException {
		action(command, s -> System.out.println(s));
	}

	public static void action(String command, Consumer<String> functionForAllLines) throws IOException, InterruptedException {
		Process p = Runtime.getRuntime().exec(command);
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = br.readLine()) != null) {
			if (line.isEmpty())
				continue;
			if (functionForAllLines != null)
				functionForAllLines.accept(line);
		}
		br.close();
		p.waitFor();
	}
}
