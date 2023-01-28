package com.saga.unipass.model.dao;

import com.saga.unipass.model.ConPool;
import com.saga.unipass.model.beans.Viaggio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrenotazioneDAO {

    public void doSave(int idViaggio, String emailPasseggero){

        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("INSERT INTO partecipare VALUES (?,?)");
            ps.setInt(1, idViaggio);
            ps.setString(2, emailPasseggero);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> INSERT partecipare.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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

    public void doClose(int idViaggio){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE viaggio " +
                            "SET prenotabile = ?" +
                            "WHERE idViaggio = ?;");
            ps.setBoolean(1, false);
            ps.setInt(2, idViaggio);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> UPDATE viaggio.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
