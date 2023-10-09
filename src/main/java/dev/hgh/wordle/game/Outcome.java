package dev.hgh.wordle.game;

import dev.hgh.wordle.OutcomeColors;

import java.util.List;

public record Outcome(
		CharacterOutcome first,
		CharacterOutcome second,
		CharacterOutcome third,
		CharacterOutcome fourth,
		CharacterOutcome fifth) {
	public List<CharacterOutcome> characters() {
		return List.of(first, second, third, fourth, fifth);
	}

	public CharacterOutcome inPosition(Position position) {
		return switch (position) {
			case First -> first;
			case Second -> second;
			case Third -> third;
			case Fourth -> fourth;
			case Fifth -> fifth;
		};
	}
}
