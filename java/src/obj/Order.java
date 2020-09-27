package obj;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

public class Order {

    private Integer id;
    private Date dataConsegna;
    private Time oraConsegna;
    private String emailCliente;
    private BigDecimal totale;
    private Integer saldoPuntiBase;
    private Integer saldoPunti;
    private List<ProductInOrder> prodottiOrdine;
    private String pagamento;
    private String stato;


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
        boolean newProduct = true;
        for(ProductInOrder p : prodottiOrdine) {
            p.updateTotal();
            if(p.getId().equals(prodotto.getId())) {
                p.setQuantita(p.getQuantita()+1);
                newProduct = false;
            }
            p.updateTotal();
        }
        if(newProduct)
            prodottiOrdine.add(new ProductInOrder(prodotto.getId(), prodotto.getNome(), prodotto.getMarca(), prodotto.getDescrizione(), 1, prodotto.getQuantita_conf(), prodotto.getPrezzo()));
    }

    public void removeOneProduct(Product prodotto) {
        for(ProductInOrder p : prodottiOrdine) {
            p.updateTotal();
            if(p.getId().equals(prodotto.getId())) {
                totale=totale.subtract(prodotto.getPrezzo());
                saldoPunti=saldoPuntiBase+totale.intValue();
                int q = p.getQuantita();
                if(q>1)
                    p.setQuantita(q-1);
                else
                    prodottiOrdine.remove(p);
            }
            p.updateTotal();
        }
        System.out.println("\n!!!ERROR!!! You are trying to remove a product that is not in your cart!\n");
    }

    public void removeProductFromOrder(Product prodotto) {
        for(ProductInOrder p : prodottiOrdine) {
            if(p.getId() == prodotto.getId()) {
                totale = totale.subtract(prodotto.getPrezzo().multiply(BigDecimal.valueOf(prodotto.getQuantita())));
                saldoPunti = saldoPuntiBase + totale.intValue();
                prodottiOrdine.remove(p);
                break;
            }
        }
    }

    public BigDecimal getTotalOfOrder(){
        BigDecimal total = BigDecimal.ZERO;
        for(ProductInOrder p : prodottiOrdine) {
            total = total.add(p.getTotale());
        }
        return total;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                '}';
    }
}
