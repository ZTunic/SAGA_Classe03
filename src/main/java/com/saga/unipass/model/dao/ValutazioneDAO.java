package com.saga.unipass.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ValutazioneDAO {

    public void doValutaGuidatore(String emailGuidatore, int numValutazione, int sommaValutazioni){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE utente " +
                                                    "SET numeroValutazioniGuidatore = ? AND sommaValutazioniGuidatore = ? " +
                                                    "WHERE email = ?;");

            ps.setInt(1, numValutazione);
            ps.setInt(2, sommaValutazioni);
            ps.setString(3, emailGuidatore);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> UPDATE valutazione utente guidatore.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	public void doValutaPasseggero(String emailPasseggero, int numValutazione, int sommaValutazioni){
        try(Connection connection = ConPool.getConnection()){
            PreparedStatement ps =
                    connection.prepareStatement("UPDATE utente " +
                                                    "SET numeroValutazioniPasseggero = ? AND sommaValutazioniPasseggero = ? " +
                                                    "WHERE email = ?;");

            ps.setInt(1, numValutazione);
            ps.setInt(2, sommaValutazioni);
            ps.setString(3, emailPasseggero);

            if (ps.executeUpdate() != 1) {
                throw new RuntimeException("ERROR --> UPDATE valutazione utente passeggero.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
