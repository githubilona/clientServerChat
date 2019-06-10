package models;

import controllers.ChatController;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;

public class NewFrame {

    private String relativePath;
    private String title;
    private int width;
    private int height;
    private Parent root;
    private Stage stage;

    public NewFrame(String relativePath, String title, int width, int height) {

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource(relativePath));
            stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Closing the chat frame and saving a conversation to a file
     */

    public void createFrame(String relativePath, String title, int width, int height) {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource(relativePath));
            Stage stage = new Stage();
            stage.setTitle(title);
            stage.setScene(new Scene(root, width, height));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}