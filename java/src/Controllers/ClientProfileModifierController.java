package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientProfileModifierController {
    @FXML private Button CancelButton;
    @FXML private Button SaveButton;

    @FXML private TextField Name;
    @FXML private TextField Surname;
    @FXML private TextField Address;
    @FXML private TextField City;
    @FXML private TextField Cap;
    @FXML private TextField Phone;
    @FXML private TextField TelNum;
    @FXML private TextField Email;
    @FXML private TextField Password;
    @FXML private TextField CardCode;

    private boolean vefifyTextField() {
        if(Name.getText() == null || Name.getText().trim().isEmpty())
            return false;
        if(Surname.getText() == null || Surname.getText().trim().isEmpty())
            return false;
        if(Address.getText() == null || Address.getText().trim().isEmpty())
            return false;
        if(City.getText() == null || City.getText().trim().isEmpty())
            return false;
        if(Cap.getText() == null || Cap.getText().trim().isEmpty())
            return false;
        if(Phone.getText() == null || Phone.getText().trim().isEmpty())
            return false;
        if(Email.getText() == null || Email.getText().trim().isEmpty())
            return false;
        if(Password.getText() == null || Password.getText().trim().isEmpty())
            return false;
        return true;
    }

    private boolean checkRightFormat() {
        if(Cap.getText().trim().length() != 5) {//il cap deve essere esattamente di 5 cifre
            AlertBox.display("Error", "CAP must be numeric and composed by five numbers");
            return false;
        }
        else if(TelNum.getText().trim().length() > 11 || TelNum.getText().length() < 10) { //il numero telefonico deve essere di 10-11 caratteri
            AlertBox.display("Error", "Insert a valid telephone number");
            return false;
        }
        else if(Email.getText().trim().length() < 6) {//non è possibile inserire una email con meno di 6 caratteri
            AlertBox.display("Error", "Email must be at least 6 characters long");
            return false;
        }
        else if(Password.getText().trim().length() < 6) {//non è possibile inserire una password con meno di 6 caratteri
            AlertBox.display("Error", "Password must be at least 6 characters long");
            return false;
        }
        else {
            try {//testo se la stringa presa dal cap è effettivamente un numero o meno
                if( Integer.parseInt(Cap.getText().trim())<0) {
                    AlertBox.display("Error", "Cap must be positive");
                    return false;
                }

            }
            catch(NumberFormatException e){
                AlertBox.display("Error", "Insert a valid numeric CAP");
                return false;
            }

            try {//testo se la stringa presa dal numero di telefono è effettivamente un numero o meno
                if(Double.valueOf(Phone.getText().trim())<0) {
                    AlertBox.display("Error", "Telephone number must be positive");
                    return false;
                }

            }
            catch(NumberFormatException e){
                AlertBox.display("Error", "Insert a valid numeric telephone number");
                return false;
            }
        }
        return true;
    }

    public void  SaveButtonPushed(ActionEvent event) throws IOException {

        if(!vefifyTextField()) {
            AlertBox.display("Error","All field must be filled");
            return;
        }
        if(!checkRightFormat()) return;

        //update user tuple
    }

    public void CancelButtonPushed(ActionEvent event) throws IOException{
        Parent tableViewParent =  FXMLLoader.load(getClass().getResource("Home.fxml"));
        Scene tableViewScene = new Scene(tableViewParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(tableViewScene);
        window.show();
    }

}
