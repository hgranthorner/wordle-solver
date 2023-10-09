package dev.hgh.wordle.components;

import javafx.scene.control.TextField;

public class SingleCharacterField extends TextField {
	public SingleCharacterField() {
		setTextFormatter(new CharacterFormatter());
	}

	public SingleCharacterField(String s) {
		super(s);
		setTextFormatter(new CharacterFormatter());
	}
}
