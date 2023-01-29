package com.saga.unipass.model.dao;


import com.saga.unipass.model.ConPool;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ViaggioDAO {

    public void doSave(Viaggio viaggio){

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO viaggio VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, viaggio.getDestinazione());
            ps.setDate(2, (java.sql.Date) viaggio.getDataOraPartenza());
            ps.setInt(3, viaggio.getPosti());
            ps.setDouble(4, viaggio.getPrezzo());
            ps.setBoolean(5, true);
            ps.setString(6, viaggio.getGuidatore().getEmail());

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> INSERT viaggio.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void doRemove(int idViaggio){

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
    }


    public List<Viaggio> doSearch(String destinazione, Date dataOraPartenza, double prezzo){

        List<Viaggio> viaggi = null;

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                                                    "FROM viaggio " +
                                                    "WHERE destinazione = ?;" +
                                                    "AND dataOraPartenza = ?" +
                                                    "AND prezzo <= ?");

            ps.setString(1, destinazione);
            ps.setDate(2, (java.sql.Date) dataOraPartenza);
            ps.setDouble(3, prezzo);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Viaggio viaggio = new Viaggio();

                viaggio.setIdViaggio(rs.getInt("idViaggio"));
                viaggio.setDestinazione(rs.getString("destinazione"));
                viaggio.setDataOraPartenza(rs.getDate("dataOraPartenza"));
                viaggio.setPosti(rs.getInt("posti"));
                viaggio.setPrezzo(rs.getDouble("prezzo"));
                viaggio.setPrenotabile(rs.getBoolean("prenotabile"));

                AutenticazioneDAO autenticazioneDAO = new AutenticazioneDAO();
                viaggio.setGuidatore(autenticazioneDAO.doRetriveByEmail(rs.getString("guidatore")));

                viaggi.add(viaggio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return viaggi;
    }


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

    public ArrayList<Utente> doRetrivePasseggeriViaggio(int idViaggio){
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
                listaPasseggeri.add(autenticazioneDAO.doRetriveByEmail(rs.getString("passeggero")));
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return listaPasseggeri;
    }

    public Viaggio doRetrieveById(int idViaggio){
        Viaggio viaggio = null;

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
                viaggio.setDataOraPartenza(rs.getDate("dataOraPartenza"));
                viaggio.setPosti(rs.getInt("posti"));
                viaggio.setPrezzo(rs.getDouble("prezzo"));
                viaggio.setPrenotabile(rs.getBoolean("prenotabile"));

                AutenticazioneDAO autenticazioneDAO = new AutenticazioneDAO();
                viaggio.setGuidatore(autenticazioneDAO.doRetriveByEmail(rs.getString("guidatore")));
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return viaggio;
    }

}
