package sample;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;


public class RegisterController {
    @FXML private Button registerButton;
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label errorLabel;

    /*___________________________________________________________________________________________
    public RegisterController() {
        try {
            this.socket = new Socket("localhost",5000);
            this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            this.objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
___________________________________________________________________________________________ */

    /**
     * Creates new user and
     * Closes the registerFrame once the register button is pressed
     */
    @FXML
    public void registerAction() {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();
        System.out.println(username);
        System.out.println(password);

        User user = new User(username, password, Status.OFFLINE);
        Message registerMessage = new Message(user, MessageType.REGISTER);
        //----------------
        System.out.println("new ClientThread(registerMessage)");
        new ClientThread(registerMessage, this).start();


/*___________________________________________________________________________________________
        //  checkDuplicateUsername method from ServerThread throws DuplicateUsernameException
        try{
            objectOutputStream.writeObject(newUserMessage);
            objectOutputStream.flush();
            System.out.println("newUser message has been send to the server...");
            Message serverResponse  = (Message)objectInputStream.readObject();
             // Close register window+
            if(serverResponse.getMessageType().equals(MessageType.USER_REGISTERED)){
                System.out.println("New user was registered!");
                for(String name: serverResponse.getUsersHashMap().keySet()){
                    System.out.println(" register controllerr : "+ name);
                }
                Stage stage = (Stage) registerButton.getScene().getWindow();
                stage.close();
            }else if(serverResponse.getMessageType().equals(MessageType.USER_NOT_REGISTERED)){
                    // wypisz komunikat w okienku rejestracji
                System.out.println("User not registered, check if entered data is correct");
            }

        }
//        catch(DuplicateUsernameException e){
//            //
//        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        // new NewFrame("sample/LoginFrame.fxml","Login Frame", 800, 600);
    }

  ___________________________________________________________________________________________ */
    }
    public void updateFrame(MessageType registrationResult){
        if(registrationResult.equals(MessageType.USER_REGISTERED)){
            System.out.println("New user was registered!");
//            for(String name: serverResponse.getUsersHashMap().keySet()){
//                System.out.println(" register controllerr : "+ name);
//            }
//            System.out.println("get register button");
//            Stage stage = (Stage) registerButton.getScene().getWindow();
//            usernameTextField.setText("333333333333333");
      //      passwordTextField.setEditable(false);
            //stage.close();
            Platform.runLater(() ->{
               usernameTextField.setEditable(false);
               passwordTextField.setEditable(false);
               errorLabel.setText("New user was registered! ");
               errorLabel.setTextFill(Color.FORESTGREEN);

            });
        }else if(registrationResult.equals(MessageType.USER_NOT_REGISTERED_DUPLICATE)){
            System.out.println("User not registered, check if entered data is correct");
            Platform.runLater(() ->{
                errorLabel.setText("User already exists! ");
                errorLabel.setVisible(true);
            });

        }else if(registrationResult.equals(MessageType.USER_NOT_REGISTERED_WRONG_FORMAT)){
            Platform.runLater(() ->{
                errorLabel.setVisible(true);
                errorLabel.setText("Username and password should have more than 3 characters which are letters or digits ");
            });

        }
    }
    public void updateFrame(){
        Platform.runLater(() ->{

        });
    }
}
