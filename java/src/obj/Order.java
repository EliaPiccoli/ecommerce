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
    BigDecimal totale;
    Integer saldoPunti;
    String emailCliente;
    Dictionary<Product, Integer> prodottiOrdine;

    public Order(Integer id, Date dataConsegna, Time oraConsegna, BigDecimal totale, Integer saldoPunti, String emailCliente, Dictionary<Product, Integer> prodottiOrdine){
        this.id=id;
        this.dataConsegna=dataConsegna;
        this.oraConsegna=oraConsegna;
        this.totale=totale;
        this.saldoPunti=saldoPunti;
        this.emailCliente=emailCliente;
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
}
