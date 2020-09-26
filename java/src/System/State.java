package System;

import obj.Order;
import obj.Product;
import obj.User;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class State {
    private static State s;
    private User currentUser;
    private Order currentOrder;

    private State () {
        this.currentUser = null;
        this.currentOrder = null;
    }

    public static State getInstance() {
        if(s == null)
            s = new State();
        return s;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
        this.currentOrder = new Order(null, null, null, currentUser.getEmail(), BigDecimal.ZERO, currentUser.getCartaFed().getSaldo(), new ArrayList<>(), null, null);
    }

    public Order getCurrentOrder() {
        return currentOrder;
    }

    public void setCurrentOrder(Order currentOrder) {
        this.currentOrder = currentOrder;
    }

    public void addProduct(Product prod) {
        this.currentOrder.addProduct(prod);
    }

    public void reset() {
        this.currentUser = null;
        this.currentOrder = null;
    }

    public void resetOrder() {
        this.currentOrder = new Order(null, null, null, currentUser.getEmail(), BigDecimal.ZERO, currentUser.getCartaFed().getSaldo(), new ArrayList<>(), null, null);
    }
}
