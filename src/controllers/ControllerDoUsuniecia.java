package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class ControllerDoUsuniecia {
    @FXML private Button button;
    @FXML private TextArea textArea;

    @FXML
    public void sendMessageAction(){
        System.out.println(textArea.getText().trim());
    }
}
