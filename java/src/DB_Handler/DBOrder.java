package DB_Handler;

import obj.*;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
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
        try(PreparedStatement st = con.prepareStatement("SELECT dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, po.quantita, id_ordine, id_prodotto, tipo, nome, marca, descrizione, quantita_conf, prezzo FROM ordine o JOIN prodotto_in_ordine po ON o.id=po.id_ordine JOIN prodotto p ON p.id=po.id_prodotto ORDER BY id_ordine;")){

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

    public List<Order> getOrdersOfUser(String loggedUser){
        try(PreparedStatement st = con.prepareStatement("SELECT dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, po.quantita, id_ordine, id_prodotto, tipo, nome, marca, descrizione, quantita_conf, prezzo FROM ordine o JOIN prodotto_in_ordine po ON o.id=po.id_ordine JOIN prodotto p ON p.id=po.id_prodotto WHERE LOWER(emailCliente) = LOWER(?) ORDER BY id_ordine;")){
            st.setString(1, loggedUser);

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

        Integer id_ordine;
        if (order.getProdottiOrdine().isEmpty()){
            System.out.println("\nNon ci sono prodotti nel carrello, quindi non si aggiunge nessun ordine!\n");
            return false;
        }
        else {
            //insert user
            try (PreparedStatement st = con.prepareStatement("INSERT INTO ordine (DataConsegna, OraConsegna, EmailCliente, Totale, SaldoPunti) VALUES (?, ?, ?, ?, ?) RETURNING id;")) { //se non va mettiamo anche id e DEFAULT
                st.setDate(1, order.getDataConsegna());
                st.setTime(2, order.getOraConsegna());
                st.setString(3, order.getEmailCliente());
                st.setBigDecimal(4, order.getTotale());
                st.setInt(5, order.getSaldoPunti());
                ResultSet rs = st.executeQuery();
                if (rs.next() == false) throw new SQLException("\nOrder insertion failed!\n");
                id_ordine=rs.getInt("id");
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }

            //insert products in order
            for (HashMap.Entry<Product, Integer> entry : order.getProdottiOrdine().entrySet()) {
                Product product = entry.getKey();
                Integer quantita = entry.getValue();

                try (PreparedStatement st = con.prepareStatement("INSERT INTO prodotto_in_ordine(id_ordine, id_prodotto, quantita) VALUES(?,?,?)")) {
                    st.setInt(1, id_ordine);
                    st.setInt(2, product.getId());
                    st.setInt(3, quantita);
                    int update = st.executeUpdate();
                    if (update == 0) throw new SQLException("\nProduct_in_order insertion failed!\n");
                } catch (SQLException e) {
                    System.out.println(e);
                    return false;
                }
            }
            return true;
        }
    }
}
