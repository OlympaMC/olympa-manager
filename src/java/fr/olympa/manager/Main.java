package fr.olympa.manager;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.security.auth.login.LoginException;

import fr.olympa.manager.api.java.StringSimilarity;
import fr.olympa.manager.discord.DiscordManager;

public class Main {
	public static void main(String[] args) throws IOException, URISyntaxException {
		//test();
		discord();
	}

	public static void discord() {

		try {
			new DiscordManager().connect();
		} catch (LoginException e) {
			e.printStackTrace();
		}

	}

	public static void test2() {
		StringSimilarity.getSimilarWords("orange", List.of("or, orange"));

	}

	public static void test() throws IOException, URISyntaxException {
		File file;
		URL resource = Main.class.getClassLoader().getResource("motsfr.txt");
		if (resource == null)
			throw new IllegalArgumentException("file not found!");
		else
			file = new File(resource.toURI());
		List<String> list = Files.readAllLines(file.toPath(), Charset.defaultCharset());
		TreeMap<Integer, Set<String>> result = new TreeMap<>(Collections.reverseOrder());
		//		Map<Integer, Set<String>> result = new HashMap<>();

		long nanoTime = System.nanoTime();

		//		list.forEach(s -> {
		for (String s : list) {
			String s1 = s;
			String s2 = "orange";
			StringSimilarity ss = new StringSimilarity(s1, s2);
			int score = ss.getScore();

			Set<String> l = result.get(score);
			if (l == null)
				l = new HashSet<>();
			l.add(s);
			result.put(score, l);
			//System.out.println(String.format("StringSimilarity score of '%s' & '%s' : %d", s1, s2, score));
		}
		//		});
		nanoTime = System.nanoTime() - nanoTime;
		System.out.println(String.format("End Time %d nano, %f milis,  %f sec. %d entries", nanoTime, nanoTime / 1000000d, nanoTime / 1000000000d, result.size()));

		//		Set<Entry<Integer, Set<String>>> set = result.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder())).limit(10).collect(Collectors.toSet());
		//		for (Entry<Integer, Set<String>> e : set)
		//			if (e.getValue().size() < 100)
		//				System.out.println(String.format("Similar words, %d values, score %d , '%s'", e.getValue().size(), e.getKey(), String.join(",", e.getValue())));
		//			else
		//				System.out.println(String.format("Similar words, %d values, score %d ", e.getValue().size(), e.getKey()));

		int iMax = 10;
		while (iMax-- >= 0) {
			Entry<Integer, Set<String>> e = result.pollFirstEntry();
			if (e.getValue().size() < 100)
				System.out.println(String.format("Similar words n°%d, %d values, score %d , '%s'", iMax, e.getValue().size(), e.getKey(), String.join(",", e.getValue())));
			else
				System.out.println(String.format("Similar words n°%d, %d values, score %d ", iMax, e.getValue().size(), e.getKey()));
		}
		//System.out.println(String.format("Similar word : %s.", result.entrySet().stream().limit(1).map(e -> String.join("|", e.getValue()) + "(" + e.getKey() + ")").collect(Collectors.joining(", "))));
	}

}
