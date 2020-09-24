package obj;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;

public class Order {

    Integer id;
    Date dataConsegna;
    Time oraConsegna;
    String emailCliente;
    BigDecimal totale;
    Integer saldoPuntiBase;
    Integer saldoPunti;
    HashMap<Product, Integer> prodottiOrdine;
    //TODO add pagamento - stato fields

    public Order(Integer id, Date dataConsegna, Time oraConsegna, String emailCliente, BigDecimal totale, Integer saldoPunti, HashMap<Product, Integer> prodottiOrdine){
        this.id=id;
        this.dataConsegna=dataConsegna;
        this.oraConsegna=oraConsegna;
        this.emailCliente=emailCliente;
        this.totale=totale;
        this.saldoPuntiBase=saldoPunti;
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

    public Integer getSaldoPunti() {
        return saldoPunti;
    }

    public String getEmailCliente() {
        return emailCliente;
    }

    public HashMap<Product, Integer> getProdottiOrdine() {
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
        saldoPunti=saldoPuntiBase+totale.intValue();
        if(prodottiOrdine.get(prodotto)==null)
            prodottiOrdine.put(prodotto, 1);
        else
            prodottiOrdine.put(prodotto, prodottiOrdine.get(prodotto)+1);
    }

    public void removeProduct(Product prodotto) {
        if(prodottiOrdine.get(prodotto)!=null){
            totale=totale.subtract(prodotto.getPrezzo());
            saldoPunti=saldoPuntiBase+totale.intValue();
            if(prodottiOrdine.get(prodotto)>1)
                prodottiOrdine.put(prodotto, prodottiOrdine.get(prodotto)-1);
            else
                prodottiOrdine.remove(prodotto);
        }
        else
            System.out.println("\n!!!ERROR!!! You are trying to remove a product that is not in your cart!\n");
    }

    public void removeAllProducts(Product prodotto) {
        if(prodottiOrdine.get(prodotto)!=null){
            totale=totale.subtract(prodotto.getPrezzo().multiply(BigDecimal.valueOf(prodotto.getQuantita())));
            saldoPunti=saldoPuntiBase+totale.intValue();
            prodottiOrdine.remove(prodotto);
        }
        else
            System.out.println("\n!!!ERROR!!! You are trying to remove a product that is not in your cart!\n");
    }
}
