package com.saga.unipass.model.dao;

import com.saga.unipass.model.ConPool;
import com.saga.unipass.model.beans.Utente;
import com.saga.unipass.model.beans.Veicolo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VeicoloDAO {

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

    public Veicolo doRetriveByTarga(String targa){
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
                Utente utente = autenticazioneDAO.doRetriveByEmail(rs.getString("proprietario"));
                veicolo.setProprietario(utente);
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veicolo;
    }

    public Veicolo doRetriveByGuidatore(String email){
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
                Utente utente = autenticazioneDAO.doRetriveByEmail(rs.getString("proprietario"));
                veicolo.setProprietario(utente);
            }
            else
                return null;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return veicolo;
    }

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
