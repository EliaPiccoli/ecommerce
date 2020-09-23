package obj;

import java.sql.Date;

public class FidelityCard {

    Integer id;
    Date dataEmissione;
    Integer saldo;

    public FidelityCard(Integer id, Date dataEmissione, Integer saldo) {
        this.id=id;
        this.dataEmissione=dataEmissione;
        this.saldo=saldo;
    }

    public Integer getId() {
        return id;
    }

    public Date getDataEmissione() {
        return dataEmissione;
    }

    public Integer getSaldo() {
        return saldo;
    }
}