package DB_Handler;

import obj.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;

public class DBOrder {
    Connection con = null;

    public DBOrder(Connection con){
        this.con = con;
    }

    public Order getOrder(Integer id){
        try(PreparedStatement st = con.prepareStatement("SELECT dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, po.quantita, id_prodotto, tipo, nome, marca, descrizione, quantita_conf, prezzo FROM ordine o JOIN prodotto_in_ordine po ON o.id=po.id_ordine JOIN prodotto p ON p.id=po.id_prodotto WHERE o.id = ?;")){
            st.setInt(1, id);
            //utility variables
            HashMap<Product, Integer> prodottiOrdine=null;
            Date dataConsegna;
            Time oraConsegna;
            String emailCliente;
            BigDecimal totale;
            Integer saldoPunti;

            ResultSet rs = st.executeQuery();

            if(rs.next() == false) return null;

            dataConsegna=rs.getDate("dataConsegna");
            oraConsegna=rs.getTime("oraConsegna");
            emailCliente=rs.getString("emailCliente");
            totale=rs.getBigDecimal("totale");
            saldoPunti=rs.getInt("saldoPunti");
            //avendo fatto rs.next() nell'if precedente vado di do-while.
            do{
                prodottiOrdine.put(new Product(rs.getInt("id_prodotto"), rs.getString("tipo"), rs.getString("nome"), rs.getString("marca"), rs.getString("descrizione"), null, rs.getInt("quantita_conf"), rs.getBigDecimal("prezzo")), rs.getInt("quantita"));
            }while(rs.next());

            return new Order(id, dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, prodottiOrdine);

        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public List<Order> getOrders(){
        try(PreparedStatement st = con.prepareStatement("SELECT dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, po.quantita, id_ordine, id_prodotto, tipo, nome, marca, descrizione, quantita_conf, prezzo FROM ordine o JOIN prodotto_in_ordine po ON o.id=po.id_ordine JOIN prodotto p ON p.id=po.id_prodotto WHERE o.id = ? ORDER BY id_ordine;")){

            HashMap<Product, Integer> prodottiOrdine=null;

            Integer id_ordine;
            Date dataConsegna;
            Time oraConsegna;
            String emailCliente;
            BigDecimal totale;
            Integer saldoPunti;

            ResultSet rs = st.executeQuery();

            List<Order> orderList = new ArrayList<Order>();
            if(rs.next() == false) return null;

            do{
                id_ordine=rs.getInt("id_ordine");
                dataConsegna=rs.getDate("dataConsegna");
                oraConsegna=rs.getTime("oraConsegna");
                emailCliente=rs.getString("emailCliente");
                totale=rs.getBigDecimal("totale");
                saldoPunti=rs.getInt("saldoPunti");
                //la query ordina per id_ordine, quindi vado avanti finché ho prodotti per lo stesso ordine
                do{
                    prodottiOrdine.put(new Product(rs.getInt("id_prodotto"), rs.getString("tipo"), rs.getString("nome"), rs.getString("marca"), rs.getString("descrizione"), null, rs.getInt("quantita_conf"), rs.getBigDecimal("prezzo")), rs.getInt("quantita"));
                }while(rs.next()&&rs.getInt("id_ordine")==id_ordine);
                orderList.add(new Order(id_ordine, dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, prodottiOrdine));
            }while(rs.next());

            return orderList;
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }


    public boolean insertOrder(Order order) {
        if (order.getProdottiOrdine().isEmpty()){
            System.out.println("\nNon ci sono prodotti nel carrello, quindi non si aggiunge nessun ordine!\n");
            return false;
        }
        else {
            try (PreparedStatement st = con.prepareStatement("INSERT INTO ordine (DataConsegna, OraConsegna, EmailCliente, Totale, SaldoPunti) VALUES (?, ?, ?, ?, ?) RETURNING id;")) { //se non va mettiamo anche id e DEFAULT
                st.setDate(1, new Date(System.currentTimeMillis()));
                st.setInt(2, 0);
                ResultSet rs = st.executeQuery();
                if (rs.next() == false) throw new SQLException("Something went TERRIBLY wrong");
                else fidelityCard_id = rs.getInt("id");
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }

            //insert user
            try (PreparedStatement st = con.prepareStatement("INSERT INTO utente(nome, cognome, indirizzo, citta, cap, email, telefono, password, cartafed, ruolo) VALUES(?,?,?,?,?,?,?,?,?,?)")) {
                st.setString(1, order.getNome());
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
                if (update == 0) throw new SQLException("update was unsuccesful");
                else return true;
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }
        }
    }
}
