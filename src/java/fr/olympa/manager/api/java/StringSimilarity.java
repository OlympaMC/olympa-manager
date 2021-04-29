package fr.olympa.manager.api.java;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class StringSimilarity {

	public static Set<String> getSimilarWords(String word, Iterable<String> list) {
		return getSimilarWords(word, list, 10);
	}

	public static Set<String> getSimilarWords(String word, Iterable<String> list, int max) {
		Set<String> similarWords = new HashSet<>();
		TreeMap<Integer, Set<String>> result = new TreeMap<>(Collections.reverseOrder());
		long nanoTime = System.nanoTime();
		for (String s : list) {
			StringSimilarity ss = new StringSimilarity(s, word);
			int score = ss.getScore();
			Set<String> l = result.get(score);
			if (l == null)
				l = new HashSet<>();
			l.add(s);
			result.put(score, l);
		}
		nanoTime = System.nanoTime() - nanoTime;
		System.out.println(String.format("End Time %d nano, %f milis,  %f sec. %d entries", nanoTime, nanoTime / 1000000d, nanoTime / 1000000000d, result.size()));
		while (!result.isEmpty() && similarWords.size() < max) {
			Entry<Integer, Set<String>> e = result.pollFirstEntry();
			if (e.getValue().size() > max && !similarWords.isEmpty())
				break;
			similarWords.addAll(e.getValue());
		}
		return similarWords;
	}

	String s1;
	String s2;

	int score = 0;

	public String getS1() {
		return s1;
	}

	public String getS2() {
		return s2;
	}

	public StringSimilarity(String s1, String s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	public int getScore() {
		char[] array1 = s1.toLowerCase().toCharArray();
		char[] array2 = s2.toLowerCase().toCharArray();
		//if (isEquals())
		//	return Integer.MAX_VALUE;
		int samePatternLastChars = 1;

		for (int i1 = 0; i1 < array1.length; i1++) {
			int tempScore = 0;
			Set<Character> l = new HashSet<>();
			char c1 = array1[i1];
			for (int i2 = 0; i2 < array2.length; i2++) {
				char c2 = array2[i2];
				if (c1 == c2) {
					l.add(c2);
					if (!l.contains(c2))
						tempScore += 2;
					int i3 = 1;
					while (array1.length > i1 + i3 && array2.length > i2 + i3 && array1[i1 + i3] == array2[i2 + i3]) {
						if (!l.contains(array2[i2 + i3])) {
							l.add(array2[i2 + i3]);
							tempScore += 2;
						}
						samePatternLastChars++;
						i3++;
					}
					if (samePatternLastChars == s2.length()) {
						if (samePatternLastChars == s1.length())
							return Integer.MAX_VALUE;
						else
							score += tempScore * samePatternLastChars * 10;
					} else
						score += tempScore * samePatternLastChars;

					break;
					/*} else if (Character.isUpperCase(c1) && Character.toLowerCase(c1) == c2 || Character.isLowerCase(c1) && Character.toUpperCase(c1) == c2) {
						score += 1;
						samePatternLastChars++;
						break;*/
				} else //score *= samePatternLastChars;
					samePatternLastChars = 1;
			}
		}
		//		if (s1.contains(s2))
		//			score *= 1000;
		return score;
	}

	@Deprecated
	public int getScoreOld() {
		//		ListCompareList<Character> l = new ListCompareList<Character>().compare((c1, c2) -> c2 == c1);
		//		l.getContains(s1.chars().iterator(), s2.chars().iterator());
		char[] array1 = s1.toCharArray();
		char[] array2 = s2.toCharArray();
		int samePatternLastChars = 1;
		if (isEquals())
			return Integer.MAX_VALUE;
		for (char c1 : array1)
			for (char c2 : array2)
				if (c1 == c2) {
					score += 2;
					samePatternLastChars++;
					break;
				} else if (Character.isUpperCase(c1) && Character.toLowerCase(c1) == c2 || Character.isLowerCase(c1) && Character.toUpperCase(c1) == c2) {
					score += 1;
					samePatternLastChars++;
					break;
				} else //score *= samePatternLastChars;
					samePatternLastChars = 1;

		if (s1.contains(s2))
			score *= 1000;
		return score;
	}

	public boolean isEquals() {
		boolean isNullS1 = s1 == null;
		return isNullS1 && s2 == null || !isNullS1 && s1.equals(s2);
	}
}
