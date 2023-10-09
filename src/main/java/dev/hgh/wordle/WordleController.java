package dev.hgh.wordle;

import dev.hgh.wordle.game.CharacterOutcome;
import dev.hgh.wordle.game.Outcome;
import dev.hgh.wordle.game.Wordle;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class WordleController {
	@FXML
	private TextField firstLetter;
	@FXML
	private TextField secondLetter;
	@FXML
	private TextField thirdLetter;
	@FXML
	private TextField fourthLetter;
	@FXML
	private TextField fifthLetter;

	@FXML
	private ComboBox<OutcomeColors> firstComboBox;
	@FXML
	private ComboBox<OutcomeColors> secondComboBox;
	@FXML
	private ComboBox<OutcomeColors> thirdComboBox;
	@FXML
	private ComboBox<OutcomeColors> fourthComboBox;
	@FXML
	private ComboBox<OutcomeColors> fifthComboBox;

	@FXML
	private Label dirtyLabel;

	private final Wordle game;
	private boolean hasGuessed = false;

	public WordleController() throws IOException {
		URL wordUrl = Objects.requireNonNull(WordleController.class.getResource("wordlist.txt"));
		List<String> words = Files.readAllLines(Path.of(wordUrl.getPath()));
		game = new Wordle(words);
	}

	@FXML
	protected void onGuessButtonClick() {
		Optional<Outcome> input;
		if (!hasGuessed) {
			hasGuessed = true;
			dirtyLabel.setText("Dirty!");
			input = Optional.empty();
		} else {
			CharacterOutcome first = new CharacterOutcome(firstLetter.getText().charAt(0), firstComboBox.getValue());
			CharacterOutcome second = new CharacterOutcome(secondLetter.getText().charAt(0), secondComboBox.getValue());
			CharacterOutcome third = new CharacterOutcome(thirdLetter.getText().charAt(0), thirdComboBox.getValue());
			CharacterOutcome fourth = new CharacterOutcome(fourthLetter.getText().charAt(0), fourthComboBox.getValue());
			CharacterOutcome fifth = new CharacterOutcome(fifthLetter.getText().charAt(0), fifthComboBox.getValue());
			input = Optional.of(new Outcome(first, second, third, fourth, fifth));
		}
		var guess = game.guess(input);

		firstLetter.setText(guess.substring(0, 1));
		secondLetter.setText(guess.substring(1, 2));
		thirdLetter.setText(guess.substring(2, 3));
		fourthLetter.setText(guess.substring(3, 4));
		fifthLetter.setText(guess.substring(4, 5));
		for (var cb : List.of(firstComboBox, secondComboBox, thirdComboBox, fourthComboBox, fifthComboBox)) {
			if (cb.getValue() != OutcomeColors.Green) {
				cb.setValue(OutcomeColors.None);
			}
		}
	}

	public void onClearButtonClick() {
		game.clear();
		hasGuessed = false;
		dirtyLabel.setText("");
		for (var t : List.of(firstLetter, secondLetter, thirdLetter, fourthLetter, fifthLetter)) {
			t.setText("");
		}
		for (var cb : List.of(firstComboBox, secondComboBox, thirdComboBox, fourthComboBox, fifthComboBox)) {
			cb.setValue(OutcomeColors.None);
		}
	}
}