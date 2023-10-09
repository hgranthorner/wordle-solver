package dev.hgh.wordle.components;

import dev.hgh.wordle.game.OutcomeColors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class OutcomeComboBox extends ComboBox<OutcomeColors> {

	private final ObservableList<OutcomeColors> outcomeColors =
			FXCollections.observableArrayList(OutcomeColors.values());

	public OutcomeComboBox() {
		super();
		setItems(outcomeColors);
		getSelectionModel().selectFirst();
	}
}
