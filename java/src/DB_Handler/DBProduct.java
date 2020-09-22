package DB_Handler;

import obj.Product;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBProduct {
    Connection con = null;

    public DBProduct(Connection con){
        this.con = con;
    }

    public Product getProduct(String id){
        try(PreparedStatement st = con.prepareStatement("SELECT * FROM Prodotto WHERE id = ?;")){
            st.setString(1, id);

            ResultSet rs = st.executeQuery();
            if(rs.next() != false){
                Product prod = new Product(rs.getInt("id"), rs.getString("tipo"), rs.getString("nome"), rs.getString("marca"), rs.getString("descrizioni"), rs.getInt("quantita"), rs.getInt("quantita_conf"), rs.getBigDecimal("prezzo"));
                return prod;
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

    public List<Product> getProducts(){
        try(PreparedStatement st = con.prepareStatement("SELECT * FROM Prodotto;")){
            ResultSet rs = st.executeQuery();
            List<Product> prodList = new ArrayList<Product>();
            if(rs.next() == false) return null;

            do{
                prodList.add(new Product(rs.getInt("id"), rs.getString("tipo"), rs.getString("nome"), rs.getString("marca"), rs.getString("descrizioni"), rs.getInt("quantita"), rs.getInt("quantita_conf"), rs.getBigDecimal("prezzo")));
            }while(rs.next());

            return prodList;
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public void updateProduct(Integer id, String tipo, String nome, String marca, String descrizione, Integer quantita, Integer quantita_conf, BigDecimal prezzo){
        try(PreparedStatement st = con.prepareStatement("UPDATE prodotto SET tipo = ?, nome = ?, marca = ?, descrizione = ?, quantita = ?, quantita_conf = ?, prezzo = ? WHERE id = ?;")){
            st.setString(1, tipo);
            st.setString(1, nome);
            st.setString(1, marca);
            st.setString(1, descrizione);
            st.setInt(1, quantita);
            st.setInt(1, quantita_conf);
            st.setBigDecimal(1, prezzo);
            st.setInt(1, id);
            int update = st.executeUpdate();
            if(update == 0) new SQLException("update was unsuccesful");//TODO impara l'inglese
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public void insertProduct(String tipo, String nome, String marca, String descrizione, Integer quantita, Integer quantita_conf, BigDecimal prezzo){
        try(PreparedStatement st = con.prepareStatement("INSERT INTO prodotto (tipo, nome, marca, descrizione, quantita, quantita_conf, prezzo) VALUES(?, ?, ?, ?, ?, ?, ?);")){
            st.setString(1, tipo);
            st.setString(1, nome);
            st.setString(1, marca);
            st.setString(1, descrizione);
            st.setInt(1, quantita);
            st.setInt(1, quantita_conf);
            st.setBigDecimal(1, prezzo);
            int insert = st.executeUpdate();
            if(insert == 0) new SQLException("insert was unsuccesful");//TODO impara l'esperanto
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public void deleteProduct(Integer id){
        try(PreparedStatement st = con.prepareStatement("DELETE FROM prodotto WHERE id = ?;")){
            st.setInt(1, id);
            int delete = st.executeUpdate();
            if(delete == 0) new SQLException("delete was unsuccesful");//TODO impara l'esperanto
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
}
