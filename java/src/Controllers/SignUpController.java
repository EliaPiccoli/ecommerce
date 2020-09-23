package Controllers;

import DB_Handler.DBUser;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import obj.User;

import java.awt.*;
import java.io.IOException;
import java.sql.*;

import static java.lang.System.exit;

public class SignUpController {
    @FXML private Button registerButton;
    @FXML private Checkbox termsOfService;
    @FXML private TextField name;
    @FXML private TextField surname;
    @FXML private TextField address;
    @FXML private TextField city;
    @FXML private TextField cap;
    @FXML private TextField telNum;
    @FXML private TextField email;
    @FXML private TextField password;
    @FXML private TextField cardCode;

    private boolean verifyTextField() {
        if (name.getText() == null || name.getText().trim().isEmpty())
            return false;
        if (surname.getText() == null || surname.getText().trim().isEmpty())
            return false;
        if (address.getText() == null || address.getText().trim().isEmpty())
            return false;
        if (city.getText() == null || city.getText().trim().isEmpty())
            return false;
        if (cap.getText() == null || cap.getText().trim().isEmpty())
            return false;
        if (telNum.getText() == null || telNum.getText().trim().isEmpty())
            return false;
        if (email.getText() == null || email.getText().trim().isEmpty())
            return false;
        if (password.getText() == null || password.getText().trim().isEmpty())
            return false;
        return true;
    }

    private boolean checkRightFormat() {
        if (cap.getText().trim().length() != 5) {//il cap deve essere esattamente di 5 cifre
            AlertBox.display("Error", "CAP must be numeric and composed by five numbers", false);
            return false;
        } else if (telNum.getText().trim().length() > 11 || telNum.getText().length() < 10) { //il numero telefonico deve essere di 10-11 caratteri
            AlertBox.display("Error", "Insert a valid telephone number", false);
            return false;
        } else if (email.getText().trim().length() < 6) {//non è possibile inserire una email con meno di 6 caratteri
            AlertBox.display("Error", "Email must be at least 6 characters long", false);
            return false;
        } else if (password.getText().trim().length() < 6) {//non è possibile inserire una password con meno di 6 caratteri
            AlertBox.display("Error", "Password must be at least 6 characters long", false);
            return false;
        } else {
            try {//testo se la stringa presa dal cap è effettivamente un numero o meno
                if (Integer.parseInt(cap.getText().trim()) < 0) {
                    AlertBox.display("Error", "Cap must be positive", false);
                    return false;
                }

            } catch (NumberFormatException e) {
                AlertBox.display("Error", "Insert a valid numeric CAP", false);
                return false;
            }

            try {//testo se la stringa presa dal numero di telefono è effettivamente un numero o meno
                if (Double.valueOf(telNum.getText().trim()) < 0) {
                    AlertBox.display("Error", "Telephone number must be positive", false);
                    return false;
                }

            } catch (NumberFormatException e) {
                AlertBox.display("Error", "Insert a valid numeric telephone number", false);
                return false;
            }
        }
        return true;
    }

    public void RegisterButtonPushed(ActionEvent event) throws IOException {

        if (!verifyTextField()) {
            AlertBox.display("Error", "All field must be filled", false);
            return;
        }
        if (!checkRightFormat()) return;

        try (Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ecommerce", "postgres", "postgres")) {
            DBUser dbUserConnector = new DBUser(con);
            if (!dbUserConnector.checkUser(email.getText(), password.getText())) {
                boolean insert = dbUserConnector.insertUser(new User(email.getText(), name.getText(), surname.getText(), address.getText(), city.getText(), cap.getText(), telNum.getText(), password.getText(), "Cliente"));
                if (insert) {
                    System.out.println("User added to db");
                    Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Home.fxml"));
                    Scene tableViewScene = new Scene(tableViewParent);
                    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    window.setScene(tableViewScene);
                    window.show();
                } else {
                    AlertBox.display("Error", "Couldn't add user to db", false);
                    return;
                }
            } else {
                AlertBox.display("Error", "Email already taken", false);
                return;
            }
        } catch (SQLException e) {
            System.out.println("Error connecting with db");
        }
    }
}
