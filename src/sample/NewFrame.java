package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewFrame {

    private String relativePath;
    private String title;
    private int width;
    private int height;

    public NewFrame(String relativePath, String title, int width, int height) {
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

    public NewFrame(String title) {
        switchFrame(title);
    }

    public void switchFrame(String title) {
        switch (title) {
            case "LOGIN":
                this.relativePath="sample/loginFrame.fxml";
                this.title=title;
                this.width=800;
                this.height=600;
                this.createFrame(relativePath,title,width,height);
                break;
            case "REGISTER":
                this.relativePath="sample/registerFrame.fxml";
                this.title=title;
                this.width=600;
                this.height=400;
                this.createFrame(relativePath,title,width,height);
                break;
        }

    }
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