package models;

import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class FrameThread {
    final CountDownLatch latch = new CountDownLatch(1);
    public FrameThread(){

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JFXPanel(); // initializes JavaFX environment
                latch.countDown();
                Parent root;
                try {
                    String relativePath= "views/loginFrame.fxml";
                    String title="title";
                    int width=800;
                    int height=600;
                    root = FXMLLoader.load(getClass().getClassLoader().getResource(relativePath));
                    Stage stage = new Stage();
                    stage.setTitle(title);
                    stage.setScene(new Scene(root, width, height));
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}