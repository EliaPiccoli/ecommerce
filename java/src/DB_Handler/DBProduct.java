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
            List<Product> prodList = new ArrayList<>();
            while(rs.next()) {
                prodList.add(new Product(rs.getInt("id"), rs.getString("tipo"), rs.getString("nome"), rs.getString("marca"), rs.getString("descrizione"), rs.getInt("quantita"), rs.getInt("quantita_conf"), rs.getBigDecimal("prezzo")));
            }
            return prodList;
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public Product updateProduct(Product prod){
        try(PreparedStatement st = con.prepareStatement("UPDATE prodotto SET tipo = ?, nome = ?, marca = ?, descrizione = ?, quantita = ?, quantita_conf = ?, prezzo = ? WHERE id = ?;")){
            st.setString(1, prod.getTipo());
            st.setString(2, prod.getNome());
            st.setString(3, prod.getMarca());
            st.setString(4, prod.getDescrizione());
            st.setInt(5, prod.getQuantita());
            st.setInt(6, prod.getQuantita_conf());
            st.setBigDecimal(7, prod.getPrezzo());
            st.setInt(8, prod.getId());

            int update = st.executeUpdate();
            if(update == 0) throw new SQLException("\nProduct update failed!\n");
            else return getProduct(prod.getId().toString());
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    public Product updateProduct(Product prod, Integer id){
        try(PreparedStatement st = con.prepareStatement("UPDATE prodotto SET tipo = ?, nome = ?, marca = ?, descrizione = ?, quantita = ?, quantita_conf = ?, prezzo = ? WHERE id = ?;")){
            st.setString(1, prod.getTipo());
            st.setString(2, prod.getNome());
            st.setString(3, prod.getMarca());
            st.setString(4, prod.getDescrizione());
            st.setInt(5, prod.getQuantita());
            st.setInt(6, prod.getQuantita_conf());
            st.setBigDecimal(7, prod.getPrezzo());
            st.setInt(8, id);

            int update = st.executeUpdate();
            if(update == 0) throw new SQLException("\nProduct update failed!\n");
            else return getProduct(prod.getId().toString());
        }
        catch(SQLException e){
            System.out.println(e);
            return null;
        }
    }

    private Boolean insertProduct(String tipo, String nome, String marca, String descrizione, Integer quantita, Integer quantita_conf, BigDecimal prezzo){
        try(PreparedStatement st = con.prepareStatement("INSERT INTO prodotto (tipo, nome, marca, descrizione, quantita, quantita_conf, prezzo) VALUES(?, ?, ?, ?, ?, ?, ?);")){
            st.setString(1, tipo);
            st.setString(2, nome);
            st.setString(3, marca);
            st.setString(4, descrizione);
            st.setInt(5, quantita);
            st.setInt(6, quantita_conf);
            st.setBigDecimal(7, prezzo);
            int update = st.executeUpdate();
            if(update == 0) throw new SQLException("\nProduct update failed!\n");
            else return true;
        }
        catch(SQLException e){
            System.out.println(e);
            return false;
        }
    }

    public void deleteProduct(Integer id){
        try(PreparedStatement st = con.prepareStatement("DELETE FROM prodotto WHERE id = ?;")){
            st.setInt(1, id);
            int delete = st.executeUpdate();
            if(delete == 0) throw new SQLException("\nProduct removal failed!\n");
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

    public List<Product> searchProducts(String column, String value) {
        try (PreparedStatement st = con.prepareStatement(String.format("SELECT * FROM prodotto WHERE LOWER(%s) = LOWER(?)", column))) {
            st.setString(1, value);
            ResultSet rs = st.executeQuery();
            List<Product> l = new ArrayList<>();
            while(rs.next())
                l.add(new Product(rs.getInt("id"), rs.getString("tipo"), rs.getString("nome"), rs.getString("marca"), rs.getString("descrizione"), rs.getInt("quantita"), rs.getInt("quantita_conf"), rs.getBigDecimal("prezzo")));
            return l;
        } catch (SQLException e) {
            System.out.println("Error fetching info from db");
            return null;
        }
    }
}
