package obj;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.Dictionary;
import java.util.List;

public class Order {

    Integer id;
    Date dataConsegna;
    Time oraConsegna;
    String emailCliente;
    BigDecimal totale;
    Integer saldoPunti;
    Dictionary<Product, Integer> prodottiOrdine;

    public Order(Integer id, Date dataConsegna, Time oraConsegna, String emailCliente, BigDecimal totale, Integer saldoPunti, Dictionary<Product, Integer> prodottiOrdine){
        this.id=id;
        this.dataConsegna=dataConsegna;
        this.oraConsegna=oraConsegna;
        this.emailCliente=emailCliente;
        this.totale=totale;
        this.saldoPunti=saldoPunti;
        this.prodottiOrdine=prodottiOrdine;
    }

    public Integer getId() {
        return id;
    }

    public Date getDataConsegna() {
        return dataConsegna;
    }

    public Time getOraConsegna() {
        return oraConsegna;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public Integer getPuntiGenerati() {
        return saldoPunti;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public Dictionary<Product, Integer> getProdottiOrdine() {
        return prodottiOrdine;
    }

    public void setDataConsegna(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public void setOraConsegna(Time oraConsegna) {
        this.oraConsegna = oraConsegna;
    }

    public void addProduct(Product prodotto) {
        totale=totale.add(prodotto.getPrezzo());


    }

    public void removeProduct(Product prodotto) {
        if(prodottiOrdine.get(prodotto)!=null){
            totale=totale.subtract(prodotto.getPrezzo());
        }


    }

    public void removeAllProducts(Product prodotto) {
        totale=totale.add(prodotto.getPrezzo());


    }
}
