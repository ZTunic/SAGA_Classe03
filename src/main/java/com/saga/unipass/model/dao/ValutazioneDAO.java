package com.saga.unipass.model.dao;

import com.saga.unipass.model.ConPool;
import com.saga.unipass.model.beans.Utente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implementa il DAO relativo alla valutazione
 */
public class ValutazioneDAO {

    /**
     * Implementa la funzionalità di salvataggio ne database di informazioni sulla valutazione di un Guidatore
     * @param guidatore Il Guidatore cui è stata data una valutazione
     * @param numValutazione Il numero totale di valutazioni date al Guidatore
     * @param sommaValutazioni La somma delle valutazioni date al Guidatore
     * @throws RuntimeException Se l'aggiornamento non è andato a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doValutaGuidatore(Utente guidatore, int numValutazione, int sommaValutazioni){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE utente " +
                            "SET numeroValutazioniGuidatore = ?, sommaValutazioniGuidatore = ? " +
                            "WHERE email = ?;");

            ps.setInt(1, numValutazione);
            ps.setInt(2, sommaValutazioni);
            ps.setString(3, guidatore.getEmail());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> UPDATE valutazione utente guidatore.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implementa la funzionalità di salvataggio ne database di informazioni sulla valutazione di un Passeggero
     * @param passeggero Il Passeggero cui è stata data una valutazione
     * @param numValutazione Il numero totale di valutazioni date al Passeggero
     * @param sommaValutazioni La somma delle valutazioni date al Passeggero
     * @throws RuntimeException Se l'aggiornamento non è andato a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doValutaPasseggero(Utente passeggero, int numValutazione, int sommaValutazioni){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE utente " +
                            "SET numeroValutazioniPasseggero = ?, sommaValutazioniPasseggero = ? " +
                            "WHERE email = ?;");

            ps.setInt(1, numValutazione);
            ps.setInt(2, sommaValutazioni);
            ps.setString(3, passeggero.getEmail());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> UPDATE valutazione utente passeggero.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
