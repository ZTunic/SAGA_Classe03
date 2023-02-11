package com.saga.unipass.model.dao;


import com.saga.unipass.model.ConPool;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Viaggio;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

public class ViaggioDAO {

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
                viaggio.setDataOraPartenza(rs.getDate("dataOraPartenza"));
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
                viaggio.setDataOraPartenza(rs.getDate("dataOraPartenza"));
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
