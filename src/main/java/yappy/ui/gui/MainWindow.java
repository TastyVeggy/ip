package yappy.ui.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import yappy.ui.Yappy;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Yappy yappy;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image yappyImage = new Image(this.getClass().getResourceAsStream("/images/DaDuke.png"));

    /** Initialise the controller for the main GUI */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren()
                .addAll(DialogBox.getYappyDialog(Yappy.getGreetings(), yappyImage, "greet"));
    }

    /** Injects the Duke instance */
    public void setYappy(Yappy yappy) {
        this.yappy = yappy;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and
     * then appends them to the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = yappy.interact(input);
        String commandName = yappy.getCommandName();
        // TODO: make this bye not hardcoded
        if (commandName.equals("bye")) {
            Platform.exit();
        }
        dialogContainer.getChildren().addAll(DialogBox.getUserDialog(input, userImage),
                DialogBox.getYappyDialog(response, yappyImage, commandName));
        userInput.clear();
    }
}
