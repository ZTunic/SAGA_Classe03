package com.saga.unipass.model.beans;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

/**
 * @author SAGA
 *
 * La classe "Utente" mantiene le informazione di un Utente.
 * Inoltre, la medesima classe offre dei metodi utili ad un Utente.
 *
 */

public class Utente {
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private String telefono;
    private String tipo;
    private int numeroValutazioniPasseggero;
    private int numeroValutazioniGuidatore;
    private int sommaValutazioniPasseggero;
    private int sommaValutazioniGuidatore;
    private ArrayList<Viaggio> listaViaggiCreati;
    private ArrayList<Viaggio> listaViaggiPartecipati;
    private Veicolo veicolo;

    /**
     * Costruttore senza parametri.
     */
    public Utente(){
        this.tipo = "passeggero";
        this.numeroValutazioniPasseggero = 0;
        this.numeroValutazioniGuidatore = 0;
        this.sommaValutazioniPasseggero = 0;
        this.sommaValutazioniGuidatore = 0;
        this.listaViaggiCreati = new ArrayList<>();
        this.listaViaggiPartecipati = new ArrayList<>();
        this.veicolo = null;
    }

    /**
     * Costruttore con parametri
     * @param email L'email dell'Utente
     * @param password La password dell'Utente
     * @param nome Il nome dell'Utente
     * @param cognome Il cognome dell'Utente
     * @param telefono Il numero di telefono dell'Utente
     */
    public Utente(String email, String password, String nome, String cognome, String telefono) {
        this.email = email;
        this.setPassword(password);
        this.nome = nome;
        this.cognome = cognome;
        this.telefono = telefono;
        this.tipo = "passeggero";
        this.numeroValutazioniPasseggero = 0;
        this.numeroValutazioniGuidatore = 0;
        this.sommaValutazioniPasseggero = 0;
        this.sommaValutazioniGuidatore = 0;
        this.listaViaggiCreati = new ArrayList<>();
        this.listaViaggiPartecipati = new ArrayList<>();
        this.veicolo = null;
    }

    /**
     * Getter per l'email dell'Utente
     * @return email L'email dell'Utente
     */
    public String getEmail() {
        return email;
    }

    /**
     * Setter per l'email dell'Utente
     * @param email L'email dell'Utente
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Getter per la password dell'Utente
     * @return password La password dell'Utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter per la password dell'Utente
     * @param password La password dell'Utente
     */
    public void setPassword(String password) {
        try {
            MessageDigest digest =
                    MessageDigest.getInstance("SHA-1");
            digest.reset();
            digest.update(password.getBytes(StandardCharsets.UTF_8));
            this.password = String.format("%040x", new BigInteger(1, digest.digest()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Getter per il nome dell'Utente
     * @return nome Il nome dell'Utente
     */
    public String getNome() {
        return nome;
    }

    /**
     * Setter per il nome dell'Utente
     * @param nome Il nome dell'Utente
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Getter per il cognome dell'Utente
     * @return cognome Il cognome dell'Utente
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Setter per il cognome dell'Utente
     * @param cognome Il cognome dell'Utente
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Getter per il numero di telefono dell'Utente
     * @return telefono Il numero di telefono dell'Utente
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Setter per il numero di telefono dell'Utente
     * @param telefono Il numero di telefono dell'Utente
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Getter per il tipo di Utente
     * @return tipo Il tipo di Utente
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Setter per il tipo di Utente
     * @param tipo Il tipo di Utente
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Getter per il numero di valutazioni del Passeggero
     * @return numeroValutazioniPasseggero Il numero di valutazioni del Passeggero
     */
    public int getNumeroValutazioniPasseggero() {
        return numeroValutazioniPasseggero;
    }

    /**
     * Setter per il numero di valutazioni del Passeggero
     * @param numeroValutazioniPasseggero Il numero di valutazioni del Passeggero
     */
    public void setNumeroValutazioniPasseggero(int numeroValutazioniPasseggero) {
        this.numeroValutazioniPasseggero = numeroValutazioniPasseggero;
    }

    /**
     * Getter per il numero di valutazioni del Guidatore
     * @return numeroValutazioniGuidatore Il numero di valutazioni del Guidatore
     */
    public int getNumeroValutazioniGuidatore() {
        return numeroValutazioniGuidatore;
    }

    /**
     * Setter per il numero di valutazioni del Guidatore
     * @param numeroValutazioniGuidatore Il numero di valutazioni del Guidatore
     */
    public void setNumeroValutazioniGuidatore(int numeroValutazioniGuidatore) {
        this.numeroValutazioniGuidatore = numeroValutazioniGuidatore;
    }

    /**
     * Getter per la somma delle valutazioni del Passeggero
     * @return sommaValutazioniPasseggero La somma delle valutazioni del Passeggero
     */
    public int getSommaValutazioniPasseggero() {
        return sommaValutazioniPasseggero;
    }

    /**
     * Setter per la somma delle valutazioni del Passeggero
     * @param sommaValutazioniPasseggero La somma delle valutazioni del Passeggero
     */
    public void setSommaValutazioniPasseggero(int sommaValutazioniPasseggero) {
        this.sommaValutazioniPasseggero = sommaValutazioniPasseggero;
    }

    /**
     * Getter per la somma delle valutazioni del Guidatore
     * @return sommaValutazioniGuidatore La somma delle valutazioni del Guidatore
     */
    public int getSommaValutazioniGuidatore() {
        return sommaValutazioniGuidatore;
    }

    /**
     * Setter per la somma delle valutazioni del Guidatore
     * @param sommaValutazioniGuidatore La somma delle valutazioni del Guidatore
     */
    public void setSommaValutazioniGuidatore(int sommaValutazioniGuidatore) {
        this.sommaValutazioniGuidatore = sommaValutazioniGuidatore;
    }

    /**
     * Getter per la lista dei viaggi creati da un Utente
     * @return listaViaggiCreati La lista dei viaggi creati
     */
    public ArrayList<Viaggio> getListaViaggiCreati() {
        return listaViaggiCreati;
    }

    /**
     * Setter per la lista dei viaggi creati da un Utente
     * @param listaViaggiCreati La lista dei viaggi creati
     */
    public void setListaViaggiCreati(ArrayList<Viaggio> listaViaggiCreati) {
        this.listaViaggiCreati = listaViaggiCreati;
    }

    /**
     * Getter per la lista dei viaggi a cui un Utente ha partecipato
     * @return listaViaggiPartecipati La lista dei viaggi a cui un Utente ha partecipato
     */
    public ArrayList<Viaggio> getListaViaggiPartecipati() {
        return listaViaggiPartecipati;
    }

    /**
     * Setter per la lista dei viaggi a cui un Utente ha partecipato
     * @param listaViaggiPartecipati La lista dei viaggi a cui un Utente ha partecipato
     */
    public void setListaViaggiPartecipati(ArrayList<Viaggio> listaViaggiPartecipati) {
        this.listaViaggiPartecipati = listaViaggiPartecipati;
    }

    /**
     * Getter per il Veicolo dell'Utente
     * @return veicolo Il Veicolo associato all'Utente
     */
    public Veicolo getVeicolo() {
        return veicolo;
    }

    /**
     * Setter per il Veicolo dell'Utente
     * @param veicolo Il Veicolo associato all'Utente
     */
    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }


    /**
     * Getter per i viaggi che un Utente ha creato e a cui ha partecipato
     * @return listaViaggi La lista dei viaggi che un Utente ha creato e a cui ha partecipato
     */
    public ArrayList<Viaggio> getListaViaggi(){
        ArrayList<Viaggio> listaViaggi = new ArrayList<>();

        if(listaViaggiCreati != null){
            for(int i=0; i<listaViaggiCreati.size(); i++){
                Viaggio viaggio = listaViaggiCreati.get(i);
                listaViaggi.add(viaggio);
            }
        }

        if(listaViaggiPartecipati != null){
            for(int i=0; i<listaViaggiPartecipati.size(); i++){
                Viaggio viaggio = listaViaggiPartecipati.get(i);
                listaViaggi.add(viaggio);
            }
        }

        return listaViaggi;
    }

    /**
     * Implementa la funzionalità di calcolo della media delle valutazioni di un Passeggero
     * @return media La media delle valutazioni di un Passeggero
     */
    public double mediaValutazioni(){
        double media = 0;

        if(numeroValutazioniPasseggero != 0)
            media = sommaValutazioniPasseggero / numeroValutazioniPasseggero;

        return media;
    }

    /**
     * Implementa la funzionalità di calcolo della media delle valutazioni di un Guidatore
     * @return media La media delle valutazioni di un Guidatore
     */
    public double mediaValutazioniGuida(){
        double media = 0;

        if(numeroValutazioniGuidatore != 0)
            media = sommaValutazioniGuidatore / numeroValutazioniGuidatore;

        return media;
    }

    /**
     * Implementa la funzionalità di rimozione del Veicolo associato ad un Guidatore
     */
    public void rimuoviVeicolo(){
        veicolo = null;
    }


}
