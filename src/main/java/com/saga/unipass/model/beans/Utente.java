package com.saga.unipass.model.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    private List<Viaggio> listaViaggiCreati;
    private List<Viaggio> listaViaggiPartecipati;
    private Veicolo veicolo;

    //COSTRUTTORE

    public Utente(){ }

    public Utente(String email, String password, String nome, String cognome, String telefono) {
        this.email = email;
        this.password = password;
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


    //INIZIO GETTER E SETTER
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
        this.password = password;
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

    public List<Viaggio> getListaViaggiCreati() {
        return listaViaggiCreati;
    }

    public void setListaViaggiCreati(List<Viaggio> listaViaggiCreati) {
        this.listaViaggiCreati = listaViaggiCreati;
    }

    public List<Viaggio> getListaViaggiPartecipati() {
        return listaViaggiPartecipati;
    }

    public void setListaViaggiPartecipati(List<Viaggio> listaViaggiPartecipati) {
        this.listaViaggiPartecipati = listaViaggiPartecipati;
    }

    public Veicolo getVeicolo() {
        return veicolo;
    }

    public void setVeicolo(Veicolo veicolo) {
        this.veicolo = veicolo;
    }


    //STORICO VIAGGI DI UN UTENTE
    public List<Viaggio> getListaViaggi(){
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

    //MEDIA VALUTAZIONI DI UN UTENTE NEL RUOLO DI PASSEGGERO
    public double mediaValutazioni(){
        double media = 0;

        if(numeroValutazioniPasseggero != 0)
            media = sommaValutazioniPasseggero / numeroValutazioniPasseggero;

        return media;
    }

    //MEDIA VALUTAZIONI DI UN UTENTE NEL RUOLO DI GUIDATORE
    public double mediaValutazioniGuida(){
        double media = 0;

        if(numeroValutazioniGuidatore != 0)
            media = sommaValutazioniGuidatore / numeroValutazioniGuidatore;

        return media;
    }


}
