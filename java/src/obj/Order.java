package obj;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

public class Order {

    Integer id;
    Date dataConsegna;
    Time oraConsegna;
    BigDecimal totale;
    Integer puntiGenerati;
    String emailCliente;

    public Order(Integer id, Date dataConsegna, Time oraConsegna, BigDecimal totale, Integer puntiGenerati, String emailCliente){
        this.id=id;
        this.dataConsegna=dataConsegna;
        this.oraConsegna=oraConsegna;
        this.totale=totale;
        this.puntiGenerati=puntiGenerati;
        this.emailCliente=emailCliente;
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
        return puntiGenerati;
    }

    public String getEmailCliente() {
        return emailCliente;
    }
}
