package models;

import controllers.ChatController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Login frame
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("../views/loginView.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }
    @Override
    public void stop()  {
        System.out.println("STOP STO{P");
        ChatController.getInstance().saveMessage();
        try {
            ChatController.getInstance().readMeassage();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


       /* try {
            TodoData.getInstance().storeTodoItems();

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        */
    }

    @Override
    public void init() throws Exception {
        System.out.println("INIT");

       /* try {
            TodoData.getInstance().loadTodoItems();

        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        */
    }
}
