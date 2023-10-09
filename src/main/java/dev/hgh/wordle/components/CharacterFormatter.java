package dev.hgh.wordle.components;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.function.UnaryOperator;

public class CharacterFormatter extends TextFormatter<String> {
	public CharacterFormatter() {
		super(change -> {
			var s = change.getControlNewText();
			if (s.length() > 1) {
				return null;
			}

			return change;
		});
	}

	public CharacterFormatter(UnaryOperator<Change> unaryOperator) {
		super(unaryOperator);
	}

	public CharacterFormatter(StringConverter<String> stringConverter, String s, UnaryOperator<Change> unaryOperator) {
		super(stringConverter, s, unaryOperator);
	}

	public CharacterFormatter(StringConverter<String> stringConverter, String s) {
		super(stringConverter, s);
	}

	public CharacterFormatter(StringConverter<String> stringConverter) {
		super(stringConverter);
	}
}
