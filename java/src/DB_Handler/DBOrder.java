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

    private void ordersStatusUpdate(){
        List<Order> ordini= new ArrayList<>();
        try(PreparedStatement st = con.prepareStatement("SELECT * FROM ordine;")){

            ResultSet rs = st.executeQuery();

            while(rs.next()){
                ordini.add(new Order(rs.getInt("id"), rs.getDate("dataConsegna"), rs.getTime("oraConsegna"), null, null, null, null, null, null));
            }
        }
        catch(SQLException e){
            System.out.println(e);
        }

        for(Order ordine: ordini) {
            try(PreparedStatement st = con.prepareStatement("UPDATE ordine SET stato=? WHERE id=?")){
                String status=null;
                Date data = ordine.getDataConsegna();
                long millis = data.getTime();
                long now = System.currentTimeMillis();
                long timeDiff = millis - now;

                if(timeDiff < 0)
                    status = "Consegnato";
                else if(timeDiff < 24*60*60*1000L)
                    status = "In preparazione";
                else status = "Confermato";

                st.setString(1, status);
                st.setInt(2, ordine.getId());

                int update = st.executeUpdate();
            }
            catch(SQLException e){
                System.out.println(e);
            }
        }
    }

    public Order getOrder(Integer id){
        ordersStatusUpdate(); //aggiorno gli status
        try(PreparedStatement st = con.prepareStatement("SELECT dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, pagamento, stato, po.quantita, id_prodotto, tipo, nome, marca, descrizione, quantita_conf, prezzo FROM ordine o JOIN prodotto_in_ordine po ON o.id=po.id_ordine JOIN prodotto p ON p.id=po.id_prodotto WHERE o.id = ?;")){
            st.setInt(1, id);
            //utility variables
            Date dataConsegna;
            Time oraConsegna;
            String emailCliente;
            BigDecimal totale;
            Integer saldoPunti;
            List<ProductInOrder> prodottiOrdine = new ArrayList<>();
            String pagamento;
            String stato;

            ResultSet rs = st.executeQuery();

            if(rs.next() == false) return null;

            dataConsegna=rs.getDate("dataConsegna");
            oraConsegna=rs.getTime("oraConsegna");
            emailCliente=rs.getString("emailCliente");
            totale=rs.getBigDecimal("totale");
            saldoPunti=rs.getInt("saldoPunti");
            pagamento=rs.getString("pagamento");
            stato=rs.getString("stato");
            //avendo fatto rs.next() nell'if precedente vado di do-while.
            do{
                prodottiOrdine.add(new ProductInOrder(rs.getInt("id_prodotto"), rs.getString("nome"), rs.getString("marca"), rs.getString("descrizione"), rs.getInt("quantita"), rs.getInt("quantita_conf"), rs.getBigDecimal("prezzo")));
            }while(rs.next());

            return new Order(id, dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, prodottiOrdine, pagamento, stato);

        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public List<Order> getOrders(){
        Integer id_ordine;
        Date dataConsegna;
        Time oraConsegna;
        String emailCliente;
        BigDecimal totale;
        Integer saldoPunti;
        List<ProductInOrder> prodottiOrdine;
        String pagamento;
        String stato;
        List<Order> orderList = new ArrayList<>();

        ordersStatusUpdate(); //aggiorno gli status
        try(PreparedStatement orderSt = con.prepareStatement("SELECT * FROM ordine");
            PreparedStatement prodSt = con.prepareStatement("SELECT po.quantita, id_prodotto, tipo, nome, marca, descrizione, quantita_conf, prezzo " +
                    "FROM prodotto p JOIN prodotto_in_ordine po ON po.id_prodotto = p.id " +
                    "WHERE po.id_ordine = ?")) {
            ResultSet rs = orderSt.executeQuery();

            while(rs.next()) {
                id_ordine=rs.getInt("id");
                dataConsegna=rs.getDate("dataConsegna");
                oraConsegna=rs.getTime("oraConsegna");
                emailCliente=rs.getString("emailCliente");
                totale=rs.getBigDecimal("totale");
                saldoPunti=rs.getInt("saldoPunti");
                pagamento=rs.getString("pagamento");
                stato=rs.getString("stato");
                prodottiOrdine = new ArrayList<>();
                prodSt.clearParameters();
                prodSt.setInt(1, id_ordine);
                ResultSet rsp = prodSt.executeQuery();

                while(rsp.next()) {
                    prodottiOrdine.add(new ProductInOrder(rsp.getInt("id_prodotto"), rsp.getString("nome"), rsp.getString("marca"), rsp.getString("descrizione"), rsp.getInt("quantita"), rsp.getInt("quantita_conf"), rsp.getBigDecimal("prezzo")));
                }
                orderList.add(new Order(id_ordine, dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, prodottiOrdine, pagamento, stato));
            }

            System.out.println(orderList);

            return orderList;
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public List<Order> getOrdersOfUser(String loggedUser){
        Integer id_ordine;
        Date dataConsegna;
        Time oraConsegna;
        String emailCliente;
        BigDecimal totale;
        Integer saldoPunti;
        List<ProductInOrder> prodottiOrdine;
        String pagamento;
        String stato;
        List<Order> orderList = new ArrayList<>();

        ordersStatusUpdate(); //aggiorno gli status
        try(PreparedStatement orderSt = con.prepareStatement("SELECT * FROM ordine WHERE LOWER(emailCliente) = LOWER(?)");
            PreparedStatement prodSt = con.prepareStatement("SELECT po.quantita, id_prodotto, tipo, nome, marca, descrizione, quantita_conf, prezzo " +
                                                                "FROM prodotto p JOIN prodotto_in_ordine po ON po.id_prodotto = p.id " +
                                                                "WHERE po.id_ordine = ?")) {
            orderSt.clearParameters();
            orderSt.setString(1, loggedUser);
            ResultSet rs = orderSt.executeQuery();

            while(rs.next()) {
                id_ordine=rs.getInt("id");
                dataConsegna=rs.getDate("dataConsegna");
                oraConsegna=rs.getTime("oraConsegna");
                emailCliente=rs.getString("emailCliente");
                totale=rs.getBigDecimal("totale");
                saldoPunti=rs.getInt("saldoPunti");
                pagamento=rs.getString("pagamento");
                stato=rs.getString("stato");
                prodottiOrdine = new ArrayList<>();
                prodSt.clearParameters();
                prodSt.setInt(1, id_ordine);
                ResultSet rsp = prodSt.executeQuery();

                while(rsp.next()) {
                    prodottiOrdine.add(new ProductInOrder(rsp.getInt("id_prodotto"), rsp.getString("nome"), rsp.getString("marca"), rsp.getString("descrizione"), rsp.getInt("quantita"), rsp.getInt("quantita_conf"), rsp.getBigDecimal("prezzo")));
                }
                orderList.add(new Order(id_ordine, dataConsegna, oraConsegna, emailCliente, totale, saldoPunti, prodottiOrdine, pagamento, stato));
            }

            System.out.println(orderList);

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
            try (PreparedStatement st = con.prepareStatement("INSERT INTO ordine (DataConsegna, OraConsegna, EmailCliente, Totale, SaldoPunti, Pagamento, Stato) VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id;")) { //se non va mettiamo anche id e DEFAULT
                st.setDate(1, order.getDataConsegna());
                st.setTime(2, order.getOraConsegna());
                st.setString(3, order.getEmailCliente());
                st.setBigDecimal(4, order.getTotale());
                st.setInt(5, order.getSaldoPunti());
                st.setString(6, order.getPagamento());
                st.setString(7, "Confermato");
                ResultSet rs = st.executeQuery();
                if (rs.next() == false) throw new SQLException("\nOrder insertion failed!\n");
                id_ordine=rs.getInt("id");
            } catch (SQLException e) {
                System.out.println(e);
                return false;
            }

            //insert products in order
            for (ProductInOrder entry : order.getProdottiOrdine()) {
                Integer productId = entry.getId();
                Integer quantita = entry.getQuantita();

                try (PreparedStatement st = con.prepareStatement("INSERT INTO prodotto_in_ordine(id_ordine, id_prodotto, quantita) VALUES(?,?,?)")) {
                    st.setInt(1, id_ordine);
                    st.setInt(2, productId);
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
