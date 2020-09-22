package DB_Handler;

import obj.User;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBUser {
    Connection con = null;

    public DBUser(Connection con){
        this.con = con;
    }

    public Boolean checkUser(String email, String password){
        try(PreparedStatement st = con.prepareStatement("SELECT 1 FROM User WHERE email = ? AND password = ?;")){
            st.setString(1, email);
            st.setString(1, password);

            ResultSet rs = st.executeQuery();
            if(rs.next() != false){
                return true;
            }
            else{
                return false;
            }
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }

    public User getUser(String email){
        try(PreparedStatement st = con.prepareStatement("SELECT * FROM User WHERE email = ?;")){
            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            if(rs.next() != false){
                User user = new User(rs.getString("email"), rs.getString("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("indirizzo"), rs.getString("citta"), rs.getString("cap"), rs.getString("telefono"), rs.getString("password"), rs.getInt("cartaFed"),rs.getString("ruolo"));
                return user;
            }
            else{
                return null;
            }
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }
}

