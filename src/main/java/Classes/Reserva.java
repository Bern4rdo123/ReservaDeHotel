/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Classes;

import ConnectionFactory.DatabasePersistence;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 *
 * @author berna
 */
public class Reserva {

    private int id;
    private int idCliente;
    private int idQuarto;
    private Date dataEntrada;
    private Date dataSaida;
    private String status;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idCliente
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * @param idCliente the idCliente to set
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * @return the idQuarto
     */
    public int getIdQuarto() {
        return idQuarto;
    }

    /**
     * @param idQuarto the idQuarto to set
     */
    public void setIdQuarto(int idQuarto) {
        this.idQuarto = idQuarto;
    }

    /**
     * @return the dataEntrada
     */
    public Date getDataEntrada() {
        return dataEntrada;
    }

    /**
     * @param dataEntrada the dataEntrada to set
     */
    public void setDataEntrada(Date dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

    /**
     * @return the dataSaida
     */
    public Date getDataSaida() {
        return dataSaida;
    }

    /**
     * @param dataSaida the dataSaida to set
     */
    public void setDataSaida(Date dataSaida) {
        this.dataSaida = dataSaida;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
     public void adicionarReserva(Reserva reserva) {
        String sql = "INSERT INTO Reserva (id_quarto, data_entrada, data_saida, status) VALUES ( ?, ?, ?, ?)";

        try (Connection conn = DatabasePersistence.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, reserva.getIdQuarto());
            pstmt.setDate(2, new java.sql.Date(reserva.getDataEntrada().getTime()));
            pstmt.setDate(3, new java.sql.Date(reserva.getDataSaida().getTime()));
            pstmt.setString(4, reserva.getStatus());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean verificarDisponibilidadeQuarto(int idQuarto, java.util.Date dataEntrada, java.util.Date dataSaida) {
        String sql = "SELECT * FROM Reserva WHERE id_quarto = ? AND ((data_entrada <= ? AND data_saida >= ?) OR (data_entrada <= ? AND data_saida >= ?))";
        
        try (Connection conn = DatabasePersistence.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idQuarto);
            pstmt.setDate(2, new java.sql.Date(dataSaida.getTime()));
            pstmt.setDate(3, new java.sql.Date(dataEntrada.getTime()));
            pstmt.setDate(4, new java.sql.Date(dataEntrada.getTime()));
            pstmt.setDate(5, new java.sql.Date(dataSaida.getTime()));
            ResultSet rs = pstmt.executeQuery();

            return !rs.next(); // Retorna true se não houver conflito, ou seja, se estiver disponível

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
