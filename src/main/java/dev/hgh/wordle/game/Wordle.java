package dev.hgh.wordle.game;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class Wordle {
	private final HashSet<Character> notInWord = new HashSet<>();
	private final HashMap<Character, List<Position>> inWord = new HashMap<>();
	private final HashMap<Character, Position> found = new HashMap<>();
	private final Random random = new Random();
	private final List<String> uniqueLetterWords;
	private final List<String> repeatedLetterWords;

	public Wordle(List<String> words) {
		this.uniqueLetterWords = words.stream().filter(Strings::uniqueLetters).toList();
		this.repeatedLetterWords = words.stream().filter(w -> !Strings.uniqueLetters(w)).toList();
	}

	public String guess(@Nullable Outcome previousOutcome) {
		if (previousOutcome != null) {
			updateState(previousOutcome);
		}

		var uniqueLetterGuess = getCandidate(uniqueLetterWords);
		if (uniqueLetterGuess.isPresent()) {
			return uniqueLetterGuess.get();
		}
		var repeatedLetterGuess = getCandidate(repeatedLetterWords);
		if (repeatedLetterGuess.isPresent()) {
			return repeatedLetterGuess.get();
		}
		throw new RuntimeException("Could not get any valid candidates!");
	}

	private void updateState(@NotNull Outcome previousOutcome) {
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

	private Optional<String> getCandidate(List<String> words) {
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
			return Optional.empty();
		}
		return Optional.of(remainingWords.get(random.nextInt(remainingWords.size())));
	}
}
