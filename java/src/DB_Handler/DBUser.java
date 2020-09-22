package DB_Handler;

import obj.FidelityCard;
import obj.User;

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
        try(PreparedStatement st = con.prepareStatement("SELECT 1 FROM utente WHERE email = ? AND password = ?;")){
            st.setString(1, email);
            st.setString(2, password);

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
        try(PreparedStatement st = con.prepareStatement("SELECT * FROM utente u LEFT JOIN cartaFed cf ON cf.id=u.cartafed WHERE email = ?;")){
            st.setString(1, email);

            ResultSet rs = st.executeQuery();
            if(rs.next() != false){
                FidelityCard fidelityCard = new FidelityCard(rs.getInt("id"), rs.getDate("dataemissione"), rs.getInt("saldo"));
                User user = new User(rs.getString("email"), rs.getString("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("indirizzo"), rs.getString("citta"), rs.getString("cap"), rs.getString("telefono"), rs.getString("password"), fidelityCard,rs.getString("ruolo"));
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

    public List<User> getUsers(){
        try(PreparedStatement st = con.prepareStatement("SELECT * FROM utente u LEFT JOIN cartaFed cf;")){
            ResultSet rs = st.executeQuery();
            List<User> userList = new ArrayList<User>();
            if(rs.next() == false) return null;

            do{
                FidelityCard fidelityCard = new FidelityCard(rs.getInt("id"),rs.getDate("dataEmissione"),rs.getInt("saldo"));
                userList.add(new User(rs.getString("email"), rs.getString("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("indirizzo"), rs.getString("citta"), rs.getString("cap"), rs.getString("telefono"), rs.getString("password"), fidelityCard,rs.getString("ruolo")));
            }while(rs.next());

            return userList;
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public void updateUser(User user){
        try(PreparedStatement st = con.prepareStatement("UPDATE utente SET nome = ?, cognome = ?, indirizzo = ?, citta = ?, cap = ?, telefono = ?, password = ? WHERE email = ?;")){
            st.setString(1, user.getNome());
            st.setString(2, user.getCognome());
            st.setString(3, user.getIndirizzo());
            st.setString(4, user.getCitta());
            st.setString(5, user.getCap());
            st.setString(6, user.getTelefono());
            st.setString(7, user.getPassword());
            st.setString(8, user.getEmail());

            int update = st.executeUpdate();
            if(update == 0) new SQLException("update was unsuccesful");
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public void insertUser(User user, FidelityCard fidelityCard){

        Integer fidelityCard_id = null;

        //insert fidelity card
        try(PreparedStatement st = con.prepareStatement("INSERT INTO cartaFed (dataEmissione, saldo) VALUES (?, ?);")){
            st.setDate(1, fidelityCard.getDataEmissione());
            st.setInt(2, fidelityCard.getSaldo());
            int update = st.executeUpdate();
            if(update == 0) new SQLException("update was unsuccesful");
        }
        catch(SQLException e){
            System.out.println(e);
            return;
        }
        //TODO se poffà mejo: referenziata al contrario (chiedi a mario)
        //get fidelity card id to insert user
        try(PreparedStatement st = con.prepareStatement("SELECT id FROM cartaFed ORDER BY id DESC LIMIT 1;")){
            ResultSet rs = st.executeQuery();
            if(rs.next() == false) new SQLException("Something went TERRIBLY wrong");
            else fidelityCard_id = rs.getInt("id");
        }
        catch(SQLException e){
            System.out.println(e);
            return;
        }

        //insert user
        try(PreparedStatement st = con.prepareStatement("INSERT INTO utente  nome = ?, cognome = ?, indirizzo = ?, citta = ?, cap = ?, telefono = ?, password = ?, cartaFed = ? WHERE email = ?;")){
            st.setString(1, user.getNome());
            st.setString(2, user.getCognome());
            st.setString(3, user.getIndirizzo());
            st.setString(4, user.getCitta());
            st.setString(5, user.getCap());
            st.setString(6, user.getTelefono());
            st.setString(7, user.getPassword());
            st.setInt(8, fidelityCard_id);
            st.setString(9, user.getEmail());

            int update = st.executeUpdate();
            if(update == 0) new SQLException("update was unsuccesful");
        }
        catch(SQLException e){
            System.out.println(e);
            return;
        }
    }
}

