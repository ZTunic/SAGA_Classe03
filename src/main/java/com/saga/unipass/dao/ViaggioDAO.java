package com.saga.unipass.dao;


import com.saga.unipass.dao.AutenticazioneDAO;
import com.saga.unipass.model.ConPool;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementa il DAO relativo al viaggio
 */
public class ViaggioDAO {

    /**
     * Implementa la funzionalità di salvataggio di un Viaggio nel database
     * @param viaggio Il Viaggio da salvare
     * @param dataSave La data e l'ora di partenza del Viaggio
     * @throws RuntimeException Se l'inserimento non è andato a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doSave(Viaggio viaggio, String dataSave){

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO viaggio(destinazione, dataOraPartenza, posti, prezzo, prenotabile, guidatore) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, viaggio.getDestinazione());
            ps.setString(2, dataSave);
            ps.setInt(3, viaggio.getPosti());
            ps.setDouble(4, viaggio.getPrezzo());
            ps.setBoolean(5, true);
            ps.setString(6, viaggio.getGuidatore().getEmail());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> INSERT viaggio.");
            }

            ResultSet rs = ps.getGeneratedKeys();
            if(rs.next()) {
                int id = rs.getInt(1);
                viaggio.setIdViaggio(id);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * Implementa la funzionalità di ricerca di un Viaggio in base ai vari parametri
     * @param destinazione Il luogo di destinazione del Viaggio
     * @param dataOraPartenza La data e l'ora di partenza del Viaggio
     * @param prezzo Il prezzo massimo del Viaggio
     * @param emailPrenotante l'email del Passeggero che vuole prenotarsi
     * @return viaggi La lista di Viaggi disponibili
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public ArrayList<Viaggio> doSearch(String destinazione, String dataOraPartenza, double prezzo, String emailPrenotante){

        ArrayList<Viaggio> viaggi = new ArrayList<>();

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                            "FROM viaggio " +
                            "WHERE destinazione = ? " +
                            "AND dataOraPartenza >= ? " +
                            "AND prezzo <= ? " +
                            "AND guidatore != ? " +
                            "AND prenotabile = true;");

            ps.setString(1, destinazione);
            ps.setString(2, dataOraPartenza);
            ps.setDouble(3, prezzo);
            ps.setString(4, emailPrenotante);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Viaggio viaggio = new Viaggio();

                viaggio.setIdViaggio(rs.getInt("idViaggio"));
                viaggio.setDestinazione(rs.getString("destinazione"));

                String dataOra = rs.getString("dataOraPartenza");
                dataOra = dataOra.replace("-", "/");
                System.out.println(dataOra);
                viaggio.setDataOraPartenza(new Date(dataOra));

                viaggio.setPosti(rs.getInt("posti"));
                viaggio.setPrezzo(rs.getDouble("prezzo"));
                viaggio.setPrenotabile(rs.getBoolean("prenotabile"));

                AutenticazioneDAO autenticazioneDAO = new AutenticazioneDAO();
                viaggio.setGuidatore(autenticazioneDAO.doRetrieveByEmail(rs.getString("guidatore")));

                viaggi.add(viaggio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return viaggi;
    }

    /**
     * Implementa la funzionalità di rimuovere un passeggero da un Viaggio nel database
     * @param idViaggio L'id del Viaggio
     * @param passeggero Il Passeggero da rimuovere
     * @throws RuntimeException Se la cancellazione non è andata a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doRemovePasseggero(int idViaggio, Utente passeggero){
        try(Connection connection = ConPool.getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement("DELETE FROM partecipare WHERE viaggio = ? AND passeggero = ?;");
            ps.setInt(1, idViaggio);
            ps.setString(2, passeggero.getEmail());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> DELETE partecipare.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Implementa la funzionalità di ricerca di tutti i passeggeri di un Viaggio
     * @param idViaggio L'id del Viaggio
     * @return listaPasseggeri La lista dei passeggeri di un Viaggio
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public ArrayList<Utente> doRetrievePasseggeriViaggio(int idViaggio){
        ArrayList<Utente> listaPasseggeri = new ArrayList<>();

        AutenticazioneDAO autenticazioneDAO = new AutenticazioneDAO();

        try(Connection connection = ConPool.getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                            "FROM partecipare " +
                            "WHERE viaggio = ?;");
            ps.setInt(1, idViaggio);

            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                listaPasseggeri.add(autenticazioneDAO.doRetrieveByEmail(rs.getString("passeggero")));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return listaPasseggeri;
    }

    /**
     * Implementa la funzionalità di ricerca di un Viaggio in base al suo id
     * @param idViaggio L'id del Viaggio
     * @return viaggio Se il Viaggio è stato trovato
     * null Se il Viaggio non è stato trovato
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public Viaggio doRetrieveById(int idViaggio){
        Viaggio viaggio = new Viaggio();

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                            "FROM viaggio " +
                            "WHERE idViaggio = ?");

            ps.setInt(1, idViaggio);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                viaggio.setIdViaggio(rs.getInt("idViaggio"));
                viaggio.setDestinazione(rs.getString("destinazione"));

                String dataOraPartenza = rs.getString("dataOraPartenza");
                dataOraPartenza = dataOraPartenza.replace("-", "/");
                System.out.println(dataOraPartenza);
                viaggio.setDataOraPartenza(new Date(dataOraPartenza));

                viaggio.setPosti(rs.getInt("posti"));
                viaggio.setPrezzo(rs.getDouble("prezzo"));
                viaggio.setPrenotabile(rs.getBoolean("prenotabile"));

                AutenticazioneDAO autenticazioneDAO = new AutenticazioneDAO();
                viaggio.setGuidatore(autenticazioneDAO.doRetrieveByEmail(rs.getString("guidatore")));

                viaggio.setListaPasseggeri(doRetrievePasseggeriViaggio(rs.getInt("idViaggio")));
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return viaggio;
    }

    /**
     * Implementa la funzionalità di rimuovere un Viaggio dal database
     * @param idViaggio L'id del Viaggio
     * @return doRetrieve Il Viaggio rimosso
     * @throws RuntimeException Se la cancellazione non è andata a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public Viaggio doRemove(int idViaggio){

        Viaggio doRetrieve = doRetrieveById(idViaggio);

        try(Connection connection = ConPool.getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement("DELETE FROM viaggio WHERE idViaggio = ?;");
            ps.setInt(1, idViaggio);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> DELETE viaggio.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return doRetrieve;
    }

}
