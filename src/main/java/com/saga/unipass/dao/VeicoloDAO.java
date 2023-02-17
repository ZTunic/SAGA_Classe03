package com.saga.unipass.dao;

import com.saga.unipass.dao.AutenticazioneDAO;
import com.saga.unipass.model.ConPool;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Veicolo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Implementa il DAO relativo al veicolo
 */
public class VeicoloDAO {

    /**
     * Implementa la funzionalità di salvataggio di un Veicolo nel database
     * @param veicolo Il Veicolo da salvare
     * @throws RuntimeException Se l'inserimento non è andato a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doSave(Veicolo veicolo){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO veicolo VALUES (?,?,?,?,?,?)");
            ps.setString(1, veicolo.getTarga());
            ps.setString(2, veicolo.getMarca());
            ps.setString(3, veicolo.getModello());
            ps.setString(4, veicolo.getColore());
            ps.setInt(5, veicolo.getPostiDisponibili());
            ps.setString(6, veicolo.getProprietario().getEmail()); //proprietario veicolo


            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> INSERT veicolo.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implementa la funzionalità di ricerca di un Veicolo in base alla targa
     * @param targa La targa da ricercare
     * @return veicolo Se il veicolo è stato trovato
     * null Se il veicolo non è stato trovato
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public Veicolo doRetrieveByTarga(String targa){
        Veicolo veicolo = new Veicolo();

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                            "FROM veicolo " +
                            "WHERE targa= ?;");

            ps.setString(1, targa);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                veicolo.setTarga(rs.getString("targa"));
                veicolo.setMarca(rs.getString("marca"));
                veicolo.setModello(rs.getString("modello"));
                veicolo.setColore(rs.getString("colore"));
                veicolo.setPostiDisponibili(rs.getInt("postiDisponibili"));

                AutenticazioneDAO autenticazioneDAO = new AutenticazioneDAO();
                Utente utente = autenticazioneDAO.doRetrieveByEmail(rs.getString("proprietario"));
                veicolo.setProprietario(utente);
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veicolo;
    }

    /**
     * Implementa la funzionalità di ricercare un Veicolo in base al suo Guidatore
     * @param email L'email del Guidatore
     * @return veicolo Se il Veicolo è stato trovato
     * null Se il veicolo non è stato trovato
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public Veicolo doRetrieveByGuidatore(String email){
        Veicolo veicolo = new Veicolo();

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                            "FROM veicolo " +
                            "WHERE proprietario = ?;");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                veicolo.setTarga(rs.getString("targa"));
                veicolo.setMarca(rs.getString("marca"));
                veicolo.setModello(rs.getString("modello"));
                veicolo.setColore(rs.getString("colore"));
                veicolo.setPostiDisponibili(rs.getInt("postiDisponibili"));

                AutenticazioneDAO autenticazioneDAO = new AutenticazioneDAO();
                Utente utente = autenticazioneDAO.doRetrieveByEmail(rs.getString("proprietario"));
                veicolo.setProprietario(utente);
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veicolo;
    }

    /**
     * Implementa la funzionalità di modifica di un Veicolo nel database
     * @param targaVeicoloModifica La targa del Veicolo da modificare
     * @param veicolo Il veicolo da modificare
     * @throws RuntimeException Se l'aggiornamento non è andato a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doUpdate(String targaVeicoloModifica, Veicolo veicolo){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE veicolo " +
                            "SET targa = ?, marca = ?, modello = ?, " +
                            "colore = ?, postiDisponibili = ? " +
                            "WHERE targa = ?;");
            ps.setString(1, veicolo.getTarga());
            ps.setString(2, veicolo.getMarca());
            ps.setString(3, veicolo.getModello());
            ps.setString(4, veicolo.getColore());
            ps.setInt(5, veicolo.getPostiDisponibili());
            ps.setString(6, targaVeicoloModifica);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> UPDATE veicolo.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implementa la funzionalità di rimozione di un Veicolo dal database
     * @param targa La targa del Veicolo da rimuovere
     * @throws RuntimeException Se la cancellazione non è andata a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doRemove(String targa){
        try(Connection connection = ConPool.getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement("DELETE FROM veicolo WHERE targa = ?;");
            ps.setString(1, targa);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> DELETE veicolo.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }
}
