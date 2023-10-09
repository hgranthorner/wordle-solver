package dev.hgh.wordle.game;

import dev.hgh.wordle.OutcomeColors;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Wordle {
	private final List<String> words;
	private final HashSet<Character> notInWord = new HashSet<>();
	private final HashMap<Character, List<Position>> inWord = new HashMap<>();
	private final HashMap<Character, Position> found = new HashMap<>();
	private final Random random = new Random();;

	public Wordle(List<String> words) {
		this.words = words;
	}

	public String guess(Optional<Outcome> previousOutcome) {
		previousOutcome.ifPresent(this::updateState);
		Stream<String> wordStream = words.stream();
		for (var entry : found.entrySet()) {
			Character c = entry.getKey();
			Position position = entry.getValue();
			wordStream = wordStream.filter(w -> w.charAt(position.ordinal()) == c);
		}

		for (var c : notInWord) {
			wordStream = wordStream.filter(w -> w.indexOf(c) == -1);
		}

		for (var entry : inWord.entrySet()) {
			Character c = entry.getKey();
			List<Position> positions = entry.getValue();
			for (var p : positions) {
				wordStream = wordStream.filter(w -> w.charAt(p.ordinal()) != c);
			}
			wordStream = wordStream.filter(w -> w.indexOf(c) != -1);
		}

		List<String> remainingWords = wordStream.toList();
		if (remainingWords.isEmpty()) {
			throw new RuntimeException("Could not find any words!");
		}
		return remainingWords.get(random.nextInt(remainingWords.size()));
	}

	private void updateState(Outcome previousOutcome) {
		BiConsumer<CharacterOutcome, Position> update = (CharacterOutcome c, Position p) -> {
			switch (c.outcome()) {
				case None -> notInWord.add(c.character());
				case Green -> found.put(c.character(), p);
				case Yellow -> {
					List<Position> positions = inWord.computeIfAbsent(c.character(), k -> new ArrayList<>());
					positions.add(p);
				}
			}
		};

		update.accept(previousOutcome.first(), Position.First);
		update.accept(previousOutcome.second(), Position.Second);
		update.accept(previousOutcome.third(), Position.Third);
		update.accept(previousOutcome.fourth(), Position.Fourth);
		update.accept(previousOutcome.fifth(), Position.Fifth);
	}

	public void clear() {
		notInWord.clear();
		inWord.clear();
		found.clear();
	}

	public boolean isDirty() {
		return !notInWord.isEmpty() || !inWord.isEmpty() || !found.isEmpty();
	}
}
