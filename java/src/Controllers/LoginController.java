package Controllers;

import DB_Handler.DBUser;
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
import obj.User;

import java.io.IOException;
import java.sql.*;

public class LoginController {
    @FXML private TextField emailField;
    @FXML private PasswordField pwField;
    @FXML private Button loginButton;
    @FXML private Button signUpButton;

    public void SignUpButtonPushed(ActionEvent event) throws IOException {
        Parent tableViewParent = FXMLLoader.load(getClass().getResource("/SignUp.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

    public void LoginButtonPushed(ActionEvent event) throws IOException, ClassNotFoundException {
        String email = emailField.getText();
        String pw = pwField.getText();

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBUser dbUserConnector = new DBUser(con);
            if (dbUserConnector.checkUser(email, pw)) {
                User usr = dbUserConnector.getUser(email);
                Parent tableViewParent = FXMLLoader.load(getClass().getResource((usr.getRuolo().equals("Cliente")) ? "/Home.fxml" : "/HomeAdminOrders.fxml"));
                Scene tableViewScene = new Scene(tableViewParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            }
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }
}
