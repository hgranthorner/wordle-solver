package dev.hgh.wordle;

import dev.hgh.wordle.game.Outcome;
import dev.hgh.wordle.game.Wordle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableBooleanValue;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WordleViewModel extends Wordle {

	private final SimpleBooleanProperty dirtyProperty;

	public WordleViewModel(List<String> words) {
		super(words);
		dirtyProperty = new SimpleBooleanProperty();
	}

	public ObservableBooleanValue dirtyProperty() {
		return dirtyProperty;
	}

	@Override
	public void clear() {
		super.clear();
		dirtyProperty.set(isDirty());
	}

	@Override
	public String guess(@Nullable Outcome previousOutcome) {
		var outcome = super.guess(previousOutcome);
		dirtyProperty.set(isDirty());
		return outcome;
	}
}
