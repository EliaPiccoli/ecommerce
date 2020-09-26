package obj;

public class User {
    private String email;
    private String matricola;
    private String nome;
    private String cognome;
    private String indirizzo;
    private String citta;
    private String cap;
    private String telefono;
    private String password;
    private FidelityCard cartaFedelta;
    private String ruolo;

    public User(String email, String nome, String cognome, String indirizzo, String citta, String cap, String telefono, String password, String ruolo) {
        this.email = email;
        this.nome = nome;
        this.cognome = cognome;
        this.indirizzo = indirizzo;
        this.citta = citta;
        this.cap = cap;
        this.telefono = telefono;
        this.password = password;
        this.cartaFedelta = null;
        this.ruolo = ruolo;
    }

    public User(String email, String matricola, String nome, String cognome, String indirizzo, String citta, String cap, String telefono, String password, FidelityCard cartaFed, String ruolo){
        this.email=email;
        this.matricola=matricola;
        this.nome=nome;
        this.cognome=cognome;
        this.indirizzo=indirizzo;
        this.citta=citta;
        this.cap=cap;
        this.telefono=telefono;
        this.password=password;
        this.cartaFedelta=cartaFed;
        this.ruolo=ruolo;
    }

    public void setUser(String nome, String cognome, String indirizzo, String citta, String cap, String telefono, String password, FidelityCard cartaFed, String ruolo){
        this.nome=nome;
        this.cognome=cognome;
        this.indirizzo=indirizzo;
        this.citta=citta;
        this.cap=cap;
        this.telefono=telefono;
        this.password=password;
        this.cartaFedelta=cartaFed;
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

    public FidelityCard getCartaFed() {
        return cartaFedelta;
    }

    public String getRuolo() {
        return ruolo;
    }
}
