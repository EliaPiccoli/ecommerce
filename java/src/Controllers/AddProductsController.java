package Controllers;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import obj.Product;
import obj.ProductInOrder;

import System.State;

import java.io.IOException;

public class AddProductsController {

    @FXML private Label name;
    @FXML private Label quantita;

    private final State state = State.getInstance();

    ProductInOrder myProduct;

    public void initialize() {
    }

    public void changeProduct(ProductInOrder product) {
        this.myProduct = product;
        name.setText(this.myProduct.getNome());
        quantita.setText(this.myProduct.getQuantita().toString());
    }

    public void addOne(ActionEvent e) {
        state.getCurrentOrder().addProduct(new Product(myProduct.getId(), myProduct.getPrezzo(), myProduct.getQuantita()));
        updateText();
    }

    public void removeOne(ActionEvent e) {
        if (quantita.getText().equals("1")) {
            deleteFromCart(e);
        } else {
            state.getCurrentOrder().removeOneProduct(new Product(myProduct.getId(), myProduct.getPrezzo(), myProduct.getQuantita()+Integer.parseInt(quantita.getText())));
            updateText();
        }
    }

    public void deleteFromCart(ActionEvent e) {
        state.getCurrentOrder().removeProductFromOrder(new Product(myProduct.getId(), myProduct.getPrezzo(), myProduct.getQuantita()+Integer.parseInt(quantita.getText())));
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.close();
    }

    private void updateText() {
        name.setText(this.myProduct.getNome());
        quantita.setText(this.myProduct.getQuantita().toString());
    }

    public void close(ActionEvent event) {
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.close();
    }


}
