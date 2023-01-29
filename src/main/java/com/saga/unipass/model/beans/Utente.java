package com.saga.unipass.model.beans;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SAGA
 *
 * La classe "Utente" mantiene le informazione di un utente.
 * Inoltre, la medesima classe offre dei metodi utili ad un utente.
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
     * Costruttore della classe.
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getNumeroValutazioniPasseggero() {
        return numeroValutazioniPasseggero;
    }

    public void setNumeroValutazioniPasseggero(int numeroValutazioniPasseggero) {
        this.numeroValutazioniPasseggero = numeroValutazioniPasseggero;
    }

    public int getNumeroValutazioniGuidatore() {
        return numeroValutazioniGuidatore;
    }

    public void setNumeroValutazioniGuidatore(int numeroValutazioniGuidatore) {
        this.numeroValutazioniGuidatore = numeroValutazioniGuidatore;
    }

    public int getSommaValutazioniPasseggero() {
        return sommaValutazioniPasseggero;
    }

    public void setSommaValutazioniPasseggero(int sommaValutazioniPasseggero) {
        this.sommaValutazioniPasseggero = sommaValutazioniPasseggero;
    }

    public int getSommaValutazioniGuidatore() {
        return sommaValutazioniGuidatore;
    }

    public void setSommaValutazioniGuidatore(int sommaValutazioniGuidatore) {
        this.sommaValutazioniGuidatore = sommaValutazioniGuidatore;
    }

    public ArrayList<Viaggio> getListaViaggiCreati() {
        return listaViaggiCreati;
    }

    public void setListaViaggiCreati(ArrayList<Viaggio> listaViaggiCreati) {
        this.listaViaggiCreati = listaViaggiCreati;
    }

    public ArrayList<Viaggio> getListaViaggiPartecipati() {
        return listaViaggiPartecipati;
    }

    public void setListaViaggiPartecipati(ArrayList<Viaggio> listaViaggiPartecipati) {
        this.listaViaggiPartecipati = listaViaggiPartecipati;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }


    /**
     * si effettua una fusione della <i>lista dei viaggi a cui un utente ha partecipato e la lista
     * dei viaggi che ha creato</i>.
     * @return  la lista che rappresenta lo "Storico viaggi" di un utente.
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
     * Si calcola la medie delle valutazioni di un utente nel ruolo di <strong>passeggero</strong> di un viaggio.
     * @return la media delle valutazioni.
     */
    public double mediaValutazioni(){
        double media = 0;

        if(numeroValutazioniPasseggero != 0)
            media = sommaValutazioniPasseggero / numeroValutazioniPasseggero;

        return media;
    }

    /**
     * Si calcola la medie delle valutazioni di un utente nel ruolo di <strong>guidatore</strong> di un viaggio.
     * @return la media delle valutazioni.
     */
    public double mediaValutazioniGuida(){
        double media = 0;

        if(numeroValutazioniGuidatore != 0)
            media = sommaValutazioniGuidatore / numeroValutazioniGuidatore;

        return media;
    }

    /**
     * Rimuove un veicolo associato ad un guidatore.
     */
    public void rimuoviVeicolo(){
        veicolo = null;
    }


}
