module dev.hgh.wordle {
	requires javafx.controls;
	requires javafx.fxml;


	opens dev.hgh.wordle to javafx.fxml;
	exports dev.hgh.wordle;
	exports dev.hgh.wordle.components;
	opens dev.hgh.wordle.components to javafx.fxml;
}