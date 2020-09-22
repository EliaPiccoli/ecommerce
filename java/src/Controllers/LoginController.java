package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField pwField;
    @FXML private Button loginButton;
    @FXML private Button signUpButton;

    public void SignUpButtonPushed(ActionEvent event) throws IOException
    {
        Parent tableViewParent =  FXMLLoader.load(getClass().getResource("/SignUp.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void LoginButtonPushed(ActionEvent event) throws IOException, ClassNotFoundException {
        System.out.println("Login");

        String email=emailField.getText();
        String pw=pwField.getText();

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            try (PreparedStatement st = con.prepareStatement("SELECT * FROM utente WHERE email = ? AND password = ?")) {
                st.setString(1, email);
                st.setString(2, pw);

                ResultSet userResult = st.executeQuery();
                if(userResult!=null){
                    userResult.next();
                    System.out.println("Login effettuato con -> " + userResult.getString("email"));

                    Parent tableViewParent;
                    Scene tableViewScene;
                    Stage window;

                    if(userResult.getString("ruolo").equals("Cliente")){
                        System.out.println("AAAAAAAAAA");
                        tableViewParent =  FXMLLoader.load(getClass().getResource("/Home.fxml"));
                        tableViewScene = new Scene(tableViewParent);
                        window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    }
                    else {
                        System.out.println("BBBBBBBBB");
                        tableViewParent =  FXMLLoader.load(getClass().getResource("/HomeAdminOrders.fxml"));
                        tableViewScene = new Scene(tableViewParent);
                        window = (Stage)((Node)event.getSource()).getScene().getWindow();
                    }

                    window.setScene(tableViewScene);
                    window.show();
                }
                else {
                    AlertBox.display("Error", "Email and/or password are not correct");
                }
            } catch (SQLException e) {
                System.out.println("Error in fetching users from db");
            }
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }


}
