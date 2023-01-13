package com.saga.unipass.model.dao;

import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.GregorianCalendar;
import java.util.List;

public class AutenticazioneDAO {

    public void doSave(Utente utente){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO utente VALUES (?,?,?,SHAI(?),?,?,?,?,?,?)");
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

    public Utente doRetriveByCredentials(String email, String password){
        Utente doRetrive = new Utente();

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                                                    "FROM utente " +
                                                    "WHERE email= ? AND passwordHash=SHA1(?);");

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                doRetrive.setEmail(rs.getString("email"));
                doRetrive.setNome(rs.getString("nome"));
                doRetrive.setCognome(rs.getString("cognome"));
                doRetrive.setPassword(rs.getString("passwordHash"));
                doRetrive.setTelefono(rs.getString("telefono"));
                doRetrive.setTipo(rs.getString("tipo"));
                doRetrive.setNumeroValutazioniPasseggero(rs.getInt("numeroValutazioniPasseggero"));
                doRetrive.setNumeroValutazioniGuidatore(rs.getInt("numeroValutazioniGuidatore"));
                doRetrive.setSommaValutazioniPasseggero(rs.getInt("sommaValutazioniPasseggero"));
                doRetrive.setSommaValutazioniGuidatore(rs.getInt("sommaValutazioniGuidatore"));
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doRetrive;
    }

    public List<Viaggio> doRetriveViaggiCreati(String email){

        List<Viaggio> doRetrive = null;

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                                                    "FROM viaggio " +
                                                    "WHERE guidatore = ?;");

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Viaggio viaggio = new Viaggio();

                viaggio.setId(rs.getInt("idViaggio"));
                viaggio.setDestinazione(rs.getString("destinazione"));
                viaggio.setDataOraPartenza(rs.getDate("dataOraPartenza"));
                viaggio.setPosti(rs.getInt("posti"));
                viaggio.setPrezzo(rs.getDouble("prezzo"));
                viaggio.setPrenotabile(rs.getBoolean("prenotabile"));
                viaggio.setGuidatore(rs.getString("guidatore"));

                doRetrive.add(viaggio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doRetrive;
    }

	public List<Viaggio> doRetriveViaggiPartecipati(String email){

        List<Viaggio> doRetrive = null;

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
                viaggio.setGuidatore(rs.getString("guidatore"));

                doRetrive.add(viaggio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return doRetrive;
    }

	public void doUpdate(String emailUtenteModifica, Utente utente){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE utente " +
                                                    "SET email = ? AND nome = ? AND cognome = ? AND " +
                                                    "passwordHash = SHAI(?) AND telefono = ? " +
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
