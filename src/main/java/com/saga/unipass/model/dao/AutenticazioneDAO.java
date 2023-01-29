package com.saga.unipass.model.dao;

import com.saga.unipass.model.ConPool;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AutenticazioneDAO {

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

    public Utente doRetriveByEmail(String email){
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

    public Utente doRetriveByCredentials(String email, String password){
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
                    utente.setVeicolo(veicoloDAO.doRetriveByGuidatore(rs.getString("email")));
                }

                utente.setNumeroValutazioniPasseggero(rs.getInt("numeroValutazioniPasseggero"));
                utente.setNumeroValutazioniGuidatore(rs.getInt("numeroValutazioniGuidatore"));
                utente.setSommaValutazioniPasseggero(rs.getInt("sommaValutazioniPasseggero"));
                utente.setSommaValutazioniGuidatore(rs.getInt("sommaValutazioniGuidatore"));

                ArrayList<Viaggio> viaggiCreati = doRetriveViaggiCreati(rs.getString("email"));
                if(viaggiCreati != null)
                    utente.setListaViaggiCreati(viaggiCreati);

                ArrayList<Viaggio> viaggiPartecipati = doRetriveViaggiPartecipati(rs.getString("email"));
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

    public ArrayList<Viaggio> doRetriveViaggiCreati(String email){

        ArrayList<Viaggio> doRetrive = new ArrayList<>();

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
                viaggio.setDataOraPartenza(rs.getDate("dataOraPartenza"));
                viaggio.setPosti(rs.getInt("posti"));
                viaggio.setPrezzo(rs.getDouble("prezzo"));
                viaggio.setPrenotabile(rs.getBoolean("prenotabile"));
                viaggio.setGuidatore(doRetriveByEmail(rs.getString("guidatore")));

                doRetrive.add(viaggio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doRetrive;
    }

    public ArrayList<Viaggio> doRetriveViaggiPartecipati(String email){

        ArrayList<Viaggio> viaggi = new ArrayList<>();

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
                viaggio.setDataOraPartenza(rs.getDate("dataOraPartenza"));
                viaggio.setPosti(rs.getInt("posti"));
                viaggio.setPrezzo(rs.getDouble("prezzo"));
                viaggio.setPrenotabile(rs.getBoolean("prenotabile"));
                viaggio.setGuidatore(doRetriveByEmail(rs.getString("guidatore")));

                viaggi.add(viaggio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return viaggi;
    }

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
}
