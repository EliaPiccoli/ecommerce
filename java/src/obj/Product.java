package obj;

import java.math.BigDecimal;

public class Product {

    Integer id;
    String tipo;
    String nome;
    String marca;
    String descrizione;
    Integer quantita;
    Integer quantita_conf;
    BigDecimal prezzo;

    public Product(Integer id, String tipo, String nome, String marca, String descrizione, Integer quantita, Integer quantita_conf, BigDecimal prezzo){
        this.id=id;
        this.tipo=tipo;
        this.nome=nome;
        this.marca=marca;
        this.descrizione=descrizione;
        this.quantita=quantita;
        this.quantita_conf=quantita_conf;
        this.prezzo=prezzo;
    }

    public void setProduct(String tipo, String nome, String marca, String descrizione, Integer quantita, Integer quantita_conf, BigDecimal prezzo){
        this.tipo=tipo;
        this.nome=nome;
        this.marca=marca;
        this.descrizione=descrizione;
        this.quantita=quantita;
        this.quantita_conf=quantita_conf;
        this.prezzo=prezzo;
    }

    public Integer getId(){
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public String getMarca() {
        return marca;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public Integer getQuantita() {
        return quantita;
    }

    public Integer getQuantita_conf() {
        return quantita_conf;
    }

    public BigDecimal getPrezzo() {
        return prezzo;
    }

}
