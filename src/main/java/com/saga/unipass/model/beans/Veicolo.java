package com.saga.unipass.model.beans;


/**
 * @author SAGA
 *
 * La classe "Veicolo" mantiene le informazione di un veicolo.
 * Inoltre, la medesima classe offre dei metodi utili per la gestione dei veicoli.
 *
 */

public class Veicolo {

    private String targa;
    private String marca;
    private String modello;
    private String colore;
    private int postiDisponibili;
    private Utente proprietario;

    public Veicolo(){
        this.proprietario = null;
    }

    public Veicolo(String targa, String marca, String modello, String colore, int postiDisponibili, Utente proprietario) {
        this.targa = targa;
        this.marca = marca;
        this.modello = modello;
        this.colore = colore;
        this.postiDisponibili = postiDisponibili;
        this.proprietario = proprietario;
    }

    //GETTER E SETTER


    public String getTarga() {
        return targa;
    }

    public void setTarga(String targa) {
        this.targa = targa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModello() {
        return modello;
    }

    public void setModello(String modello) {
        this.modello = modello;
    }

    public String getColore() {
        return colore;
    }

    public void setColore(String colore) {
        this.colore = colore;
    }

    public int getPostiDisponibili() {
        return postiDisponibili;
    }

    public void setPostiDisponibili(int postiDisponibili) {
        this.postiDisponibili = postiDisponibili;
    }

    public Utente getProprietario() {
        return proprietario;
    }

    public void setProprietario(Utente proprietario) {
        this.proprietario = proprietario;
    }
}
