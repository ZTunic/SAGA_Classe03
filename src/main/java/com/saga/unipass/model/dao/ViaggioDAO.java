package com.saga.unipass.model.dao;


import com.saga.unipass.model.beans.Viaggio;

import java.sql.*;
import java.util.Date;
import java.util.List;

public class ViaggioDAO {

    public void doSave(Viaggio viaggio, String emailGuidatore){

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO viaggio VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, viaggio.getDestinazione());
            ps.setString(2, viaggio.getDataOraPartenza());
            ps.setString(3, viaggio.getPosti());
            ps.setString(4, viaggio.getPrezzo());
            ps.setString(5, true);
            ps.setString(6, emailGuidatore);

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
            ps.setString(1, idViaggio);

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
                            "AND prezzo < ?");

            ps.setString(1, destinazione);
            ps.setString(2, dataOraPartenza);
            ps.setString(3, prezzo);

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

                viaggi.add(viaggio);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return viaggi;
    }


    public void doRemovePasseggero(int idViaggio, String emailPasseggero){
        try(Connection connection = ConPool.getConnection()) {
            PreparedStatement ps =
                    connection.prepareStatement("DELETE FROM partecipare WHERE viaggio = ? AND passeggero = ?;");
            ps.setString(1, idViaggio);
            ps.setString(1, emailPasseggero);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> DELETE partecipare.");
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Viaggio doRetrieveById(int idViaggio){
        Viaggio daRestituire = null;

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("SELECT * " +
                            "FROM viaggio " +
                            "WHERE idViaggio = ?");

            ps.setString(1, idViaggio);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                daRestituire.setId(rs.getInt("idViaggio"));
                daRestituire.setDestinazione(rs.getString("destinazione"));
                daRestituire.setDataOraPartenza(rs.getDate("dataOraPartenza"));
                daRestituire.setPosti(rs.getInt("posti"));
                daRestituire.setPrezzo(rs.getDouble("prezzo"));
                daRestituire.setPrenotabile(rs.getBoolean("prenotabile"));
                daRestituire.setGuidatore(rs.getString("guidatore"));
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return daRestituire;
    }

}
