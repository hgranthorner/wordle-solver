module dev.hgh.wordle {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.jetbrains.annotations;


	opens dev.hgh.wordle to javafx.fxml;
	opens dev.hgh.wordle.game to javafx.fxml;
	opens dev.hgh.wordle.components to javafx.fxml;

	exports dev.hgh.wordle;
	exports dev.hgh.wordle.components;
	exports dev.hgh.wordle.game;
}