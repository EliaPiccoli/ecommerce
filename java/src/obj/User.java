package obj;

public class User {

    String email;
    String matricola;
    String nome;
    String cognome;
    String indirizzo;
    String citta;
    String cap;
    String telefono;
    String password;
    Integer cartaFed;
    String ruolo;

    public User(String email, String matricola, String nome, String cognome, String indirizzo, String citta, String cap, String telefono, String password, Integer cartaFed, String ruolo){
        this.email=email;
        this.matricola=matricola;
        this.nome=nome;
        this.cognome=cognome;
        this.indirizzo=indirizzo;
        this.citta=citta;
        this.cap=cap;
        this.telefono=telefono;
        this.password=password;
        this.cartaFed=cartaFed;
        this.ruolo=ruolo;
    }

    public void setUser(String matricola, String nome, String cognome, String indirizzo, String citta, String cap, String telefono, String password, Integer cartaFed, String ruolo){
        this.matricola=matricola;
        this.nome=nome;
        this.cognome=cognome;
        this.indirizzo=indirizzo;
        this.citta=citta;
        this.cap=cap;
        this.telefono=telefono;
        this.password=password;
        this.cartaFed=cartaFed;
        this.ruolo=ruolo;
    }

    public String getEmail() {
        return email;
    }

    public String getMatricola() {
        return matricola;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getIndirizzo() {
        return indirizzo;
    }

    public String getCitta() {
        return citta;
    }

    public String getCap() {
        return cap;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getPassword() {
        return password;
    }

    public Integer getCartaFed() {
        return cartaFed;
    }

    public String getRuolo() {
        return ruolo;
    }
}
