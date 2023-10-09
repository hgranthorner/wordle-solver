package dev.hgh.wordle.game;

public class Strings {
	static boolean uniqueLetters(String w) {
		int[] characterMap = new int[200];
		var ref = new Object() {
			boolean isUnique = true;
		};
		w.chars().forEach(c -> {
			if (characterMap[c] != 0) {
				ref.isUnique = false;
			}
			characterMap[c] = c;
		});
		return ref.isUnique;
	}
}
