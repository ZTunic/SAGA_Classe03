package com.saga.unipass.model.dao;

import com.saga.unipass.model.ConPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Implementa il DAO relativo alla prenotazione
 */
public class PrenotazioneDAO {

    /**
     * Implementa la funzionalità di salvataggio di una prenotazione nel database
     * @param idViaggio L'id del Viaggio
     * @param emailPasseggero L'email del Passeggero
     * @throws RuntimeException Se l'inserimento non è andato a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doSave(int idViaggio, String emailPasseggero){

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO partecipare VALUES (?,?);");


            ps.setString(1, emailPasseggero);
            ps.setInt(2, idViaggio);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> INSERT partecipare.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Implementa la funzionalità di rimozione di una prenotazione dal database
     * @param idViaggio L'id del Viaggio
     * @param emailPasseggero L'email del Passeggero
     * @throws RuntimeException Se la cancellazione non è andata a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doRemove(int idViaggio, String emailPasseggero){

        try(Connection connection = ConPool.getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement("DELETE FROM partecipare WHERE viaggio = ? AND passeggero = ?;");
            ps.setInt(1, idViaggio);
            ps.setString(1, emailPasseggero);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> DELETE partecipare.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Implementa la funzionalità di chiusura delle prenotazioni
     * @param idViaggio L'id del Viaggio
     * @param stato lo stato del Viaggio
     * @throws RuntimeException Se la modifica non è andata a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doUpdateStato(int idViaggio, boolean stato){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE viaggio " +
                            "SET prenotabile = ? " +
                            "WHERE idViaggio = ?;");
            ps.setBoolean(1, stato);
            ps.setInt(2, idViaggio);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> UPDATE viaggio.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
