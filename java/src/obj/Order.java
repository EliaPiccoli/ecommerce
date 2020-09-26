package obj;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Order {

    Integer id;
    Date dataConsegna;
    Time oraConsegna;
    String emailCliente;
    BigDecimal totale;
    Integer saldoPuntiBase;
    Integer saldoPunti;
    List<ProductInOrder> prodottiOrdine;
    String pagamento;
    String stato;


    public Order(Integer id, Date dataConsegna, Time oraConsegna, String emailCliente, BigDecimal totale, Integer saldoPunti, List<ProductInOrder> prodottiOrdine, String pagamento, String stato){
        this.id=id;
        this.dataConsegna=dataConsegna;
        this.oraConsegna=oraConsegna;
        this.emailCliente=emailCliente;
        this.totale=totale;
        this.saldoPuntiBase=saldoPunti;
        this.saldoPunti=saldoPunti;
        this.prodottiOrdine=prodottiOrdine;
        this.pagamento=pagamento;
        this.stato=stato;
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

    public List<ProductInOrder> getProdottiOrdine() {
        return prodottiOrdine;
    }

    public String getPagamento() {
        return pagamento;
    }

    public String getStato() {
        return stato;
    }

    public void setDataConsegna(Date dataConsegna) {
        this.dataConsegna = dataConsegna;
    }

    public void setOraConsegna(Time oraConsegna) {
        this.oraConsegna = oraConsegna;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public void addProduct(Product prodotto) {
        totale=totale.add(prodotto.getPrezzo());
        saldoPunti=saldoPuntiBase+totale.intValue();
        for(ProductInOrder p : prodottiOrdine) {
            if(p.getId().equals(prodotto.getId())) {
                p.setQuantita(p.getQuantita()+1);
                return;
            }
        }
        prodottiOrdine.add(new ProductInOrder(prodotto.getId(), prodotto.getNome(), prodotto.getMarca(), prodotto.getDescrizione(), 1, prodotto.getQuantita_conf(), prodotto.getPrezzo()));
        System.out.print(prodottiOrdine);
    }

    public void removeProduct(Product prodotto) {
        for(ProductInOrder p : prodottiOrdine) {
            if(p.getId().equals(prodotto.getId())) {
                totale=totale.subtract(prodotto.getPrezzo());
                saldoPunti=saldoPuntiBase+totale.intValue();
                int q = p.getQuantita();
                if(q>1)
                    p.setQuantita(q-1);
                else
                    prodottiOrdine.remove(p);
                return;
            }
        }
        System.out.println("\n!!!ERROR!!! You are trying to remove a product that is not in your cart!\n");
    }

    // ?????? should get a list or products ??????
    public void removeAllProducts(Product prodotto) {
        for(ProductInOrder p : prodottiOrdine) {
            totale=totale.subtract(prodotto.getPrezzo().multiply(BigDecimal.valueOf(prodotto.getQuantita())));
            saldoPunti=saldoPuntiBase+totale.intValue();
            prodottiOrdine.remove(p);
        }
    }

    public BigDecimal getTotalOfOrder(){
        BigDecimal total = BigDecimal.ZERO;
        for(ProductInOrder p : prodottiOrdine)
            total.add(p.getTotale());
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                '}';
    }
}
