package obj;

import java.math.BigDecimal;

public class ProductInOrder {
    Integer id;
    String nome;
    String marca;
    Integer quantita;
    BigDecimal totale;

    public ProductInOrder(Integer id, String nome, String marca, String descrizione, Integer quantita, Integer quantita_conf, BigDecimal prezzo) {
        this.id = id;
        this.nome = nome;
        this.marca = marca;
        this.quantita = quantita;
        this.totale = totale;
    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public BigDecimal getTotale() {
        return totale;
    }

    public void setQuantita(Integer quantita) {
        this.quantita = quantita;
    }
}