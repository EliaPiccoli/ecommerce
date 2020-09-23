package DB_Handler;

import obj.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBOrder {
    Connection con = null;

    public DBOrder(Connection con){
        this.con = con;
    }

    public Order getOrder(Integer id){
        try(PreparedStatement st = con.prepareStatement("SELECT p. FROM ordine o JOIN prodotto_in_ordine po ON o.id=po.id_ordine JOIN prodotto p ON p.id=po.id_prodotto WHERE id = ?;")){
            st.setInt(1, id);

            ResultSet rs = st.executeQuery();
            if(rs.next() != false){
                FidelityCard fidelityCard = new FidelityCard(rs.getInt("id"), rs.getDate("dataemissione"), rs.getInt("saldo"));
                User user = new User(rs.getString("email"), rs.getString("matricola"), rs.getString("nome"), rs.getString("cognome"), rs.getString("indirizzo"), rs.getString("citta"), rs.getString("cap"), rs.getString("telefono"), rs.getString("password"), fidelityCard,rs.getString("ruolo"));
                return null;
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
            if(update == 0) throw new SQLException("update was unsuccesful");
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public boolean insertUser(User user){
        Integer fidelityCard_id = null;
        //insert fidelity card
        try(PreparedStatement st = con.prepareStatement("INSERT INTO cartaFed (dataEmissione, saldo) VALUES (?, ?) RETURNING id;")){ //se non va mettiamo anche id e DEFAULT           st.setDate(1, new Date(System.currentTimeMillis()));
            st.setDate(1, new Date(System.currentTimeMillis()));
            st.setInt(2, 0);
            ResultSet rs = st.executeQuery();
            if(rs.next() == false) throw new SQLException("Something went TERRIBLY wrong");
            else fidelityCard_id = rs.getInt("id");
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }

        //insert user
        try(PreparedStatement st = con.prepareStatement("INSERT INTO utente(nome, cognome, indirizzo, citta, cap, email, telefono, password, cartafed, ruolo) VALUES(?,?,?,?,?,?,?,?,?,?)")){
            st.setString(1, user.getNome());
            st.setString(2, user.getCognome());
            st.setString(3, user.getIndirizzo());
            st.setString(4, user.getCitta());
            st.setString(5, user.getCap());
            st.setString(6, user.getEmail());
            st.setString(7, user.getTelefono());
            st.setString(8, user.getPassword());
            st.setInt(9, fidelityCard_id);
            st.setString(10, user.getRuolo());

            int update = st.executeUpdate();
            if(update == 0) throw new SQLException("update was unsuccesful");
            else return true;
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }
}
