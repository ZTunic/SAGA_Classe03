package com.saga.unipass.model.dao;

import com.saga.unipass.model.ConPool;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Implementa il DAO relativo all'autenticazione
 */
public class AutenticazioneDAO {

    /**
     * Implementa la funzionalità di salvare un Utente nel database
     * @param utente L'Utente da salvare
     * @throws RuntimeException Se l'inserimento non è andato a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doSave(Utente utente){
        try(Connection connection = ConPool.getConnection()){

            String query = "INSERT INTO utente(email,nome,cognome,passwordHash,telefono,tipo,numeroValutazioniPasseggero," +
                    "numeroValutazioniGuidatore,sommaValutazioniPasseggero,sommaValutazioniGuidatore) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?);";

            PreparedStatement ps =
                    connection.prepareStatement(query);
            ps.setString(1, utente.getEmail());
            ps.setString(2, utente.getNome());
            ps.setString(3, utente.getCognome());
            ps.setString(4, utente.getPassword());
            ps.setString(5, utente.getTelefono());
            ps.setString(6, "passeggero");  //tipo utente
            ps.setInt(7, 0);    //numeroValutazioniPasseggero
            ps.setInt(8, 0);    //numeroValutazioniGuidatore
            ps.setInt(9, 0);    //sommaValutazioniPasseggero
            ps.setInt(10, 0);   //sommaValutazioniGuidatore

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> INSERT utente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implementa la funzionalità di ricerca di un Utente in base alla sua email
     * @param email
     * @return utente Se l'Utente è stato trovato
     * null Se l'Utente non è stato trovato
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public Utente doRetrieveByEmail(String email){
        Utente utente = new Utente();

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                            "FROM utente " +
                            "WHERE email= ?;");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                utente.setEmail(rs.getString("email"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setPassword(rs.getString("passwordHash"));
                utente.setTelefono(rs.getString("telefono"));
                utente.setTipo(rs.getString("tipo"));
                utente.setNumeroValutazioniPasseggero(rs.getInt("numeroValutazioniPasseggero"));
                utente.setNumeroValutazioniGuidatore(rs.getInt("numeroValutazioniGuidatore"));
                utente.setSommaValutazioniPasseggero(rs.getInt("sommaValutazioniPasseggero"));
                utente.setSommaValutazioniGuidatore(rs.getInt("sommaValutazioniGuidatore"));
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utente;
    }

    /**
     * Implementa la funzionalità di ricerca di un Utente in base all'email e alla password
     * @param email L'email dell'Utente
     * @param password La password dell'Utente
     * @return utente Se l'Utente è stato trovato
     * null Se l'Utente non è stato trovato
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public Utente doRetrieveByCredentials(String email, String password){
        Utente utente = new Utente();

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                            "FROM utente " +
                            "WHERE email= ? AND passwordHash=SHA1(?);");

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                utente.setEmail(rs.getString("email"));
                utente.setNome(rs.getString("nome"));
                utente.setCognome(rs.getString("cognome"));
                utente.setPassword(rs.getString("passwordHash"));
                utente.setTelefono(rs.getString("telefono"));
                utente.setTipo(rs.getString("tipo"));

                if(rs.getString("tipo").equalsIgnoreCase("guidatore")){
                    VeicoloDAO veicoloDAO = new VeicoloDAO();
                    utente.setVeicolo(veicoloDAO.doRetrieveByGuidatore(rs.getString("email")));
                }

                utente.setNumeroValutazioniPasseggero(rs.getInt("numeroValutazioniPasseggero"));
                utente.setNumeroValutazioniGuidatore(rs.getInt("numeroValutazioniGuidatore"));
                utente.setSommaValutazioniPasseggero(rs.getInt("sommaValutazioniPasseggero"));
                utente.setSommaValutazioniGuidatore(rs.getInt("sommaValutazioniGuidatore"));

                ArrayList<Viaggio> viaggiCreati = doRetrieveViaggiCreati(rs.getString("email"));
                if(viaggiCreati != null)
                    utente.setListaViaggiCreati(viaggiCreati);

                ArrayList<Viaggio> viaggiPartecipati = doRetrieveViaggiPartecipati(rs.getString("email"));
                if(viaggiPartecipati != null)
                    utente.setListaViaggiPartecipati(viaggiPartecipati);
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utente;
    }

    /**
     * Implementa la funzionalità di ricercare tutti i viaggi creati da parte di un Guidatore
     * @param email l'email del Guidatore
     * @return doRetrieve La lista dei viaggi creati dal Guidatore
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public ArrayList<Viaggio> doRetrieveViaggiCreati(String email){

        ArrayList<Viaggio> doRetrieve = new ArrayList<>();
        ViaggioDAO viaggioDAO = new ViaggioDAO();

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                            "FROM viaggio " +
                            "WHERE guidatore = ?;");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Viaggio viaggio = new Viaggio();

                viaggio.setIdViaggio(rs.getInt("idViaggio"));
                viaggio.setDestinazione(rs.getString("destinazione"));

                String dataOraPartenza = rs.getString("dataOraPartenza");
                dataOraPartenza = dataOraPartenza.replace("-", "/");
                System.out.println(dataOraPartenza);
                viaggio.setDataOraPartenza(new Date(dataOraPartenza));

                viaggio.setPosti(rs.getInt("posti"));
                viaggio.setPrezzo(rs.getDouble("prezzo"));
                viaggio.setPrenotabile(rs.getBoolean("prenotabile"));
                viaggio.setGuidatore(doRetrieveByEmail(rs.getString("guidatore")));
                viaggio.setListaPasseggeri(viaggioDAO.doRetrievePasseggeriViaggio(rs.getInt("idViaggio")));

                doRetrieve.add(viaggio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doRetrieve;
    }

    /**
     * Implementa la funzionalità di ricerca di tutti i viaggi a cui un Passeggero ha partecipato
     * @param email L'email del Passeggero
     * @return viaggi La lista dei Viaggi a cui il Passeggero ha partecipato
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public ArrayList<Viaggio> doRetrieveViaggiPartecipati(String email){

        ArrayList<Viaggio> viaggi = new ArrayList<>();
        ViaggioDAO viaggioDAO = new ViaggioDAO();

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT v.* " +
                            "FROM partecipare p, viaggio v " +
                            "WHERE ? = p.passeggero AND p.viaggio = v.idViaggio;");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Viaggio viaggio = new Viaggio();

                viaggio.setIdViaggio(rs.getInt("idViaggio"));
                viaggio.setDestinazione(rs.getString("destinazione"));

                String dataOraPartenza = rs.getString("dataOraPartenza");
                dataOraPartenza = dataOraPartenza.replace("-", "/");
                System.out.println(dataOraPartenza);
                viaggio.setDataOraPartenza(new Date(dataOraPartenza));

                viaggio.setPosti(rs.getInt("posti"));
                viaggio.setPrezzo(rs.getDouble("prezzo"));
                viaggio.setPrenotabile(rs.getBoolean("prenotabile"));
                viaggio.setGuidatore(doRetrieveByEmail(rs.getString("guidatore")));
                viaggio.setListaPasseggeri(viaggioDAO.doRetrievePasseggeriViaggio(rs.getInt("idViaggio")));

                viaggi.add(viaggio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return viaggi;
    }

    /**
     * Implementa la funzionalità di modifica dei dati di un Utente
     * @param emailUtenteModifica L'email dell'Utente da modificare
     * @param utente L'Utente da modificare
     * @throws RuntimeException Se l'aggiornamento non è andato a buon fine
     * @throws SQLException Se la connessione al database non è andata a buon fine
     */
    public void doUpdate(String emailUtenteModifica, Utente utente){

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE utente " +
                            "SET email = ?, nome = ?, cognome = ?, " +
                            "passwordHash = ?, telefono = ? " +
                            "WHERE email = ?;");
            ps.setString(1, utente.getEmail());
            ps.setString(2, utente.getNome());
            ps.setString(3, utente.getCognome());
            ps.setString(4, utente.getPassword());
            ps.setString(5, utente.getTelefono());
            ps.setString(6, emailUtenteModifica);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> UPDATE utente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Implementa la funzionalità di modifica del tipo Utente
     * @param email L'email dell'utente del quale modificare il tipo
     * @param tipo Il tipo Utente (passeggero/guidatore)
     */
    public void doUpdateTipoUtente(String email, String tipo){

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE utente " +
                            "SET tipo = ? " +
                            "WHERE email = ?;");

            ps.setString(1, tipo);
            ps.setString(2, email);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> UPDATE utente.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
