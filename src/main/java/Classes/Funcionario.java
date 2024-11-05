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
import java.util.concurrent.ExecutionException;

/**
 *
 * @author berna
 */
public class Funcionario extends Pessoa{
    private double salario;
    private String cargo;

    /**
     * @return the salario
     */
    public double getSalario() {
        return salario;
    }

    /**
     * @param salario the salario to set
     */
    public void setSalario(double salario) {
        this.salario = salario;
    }

    /**
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * @param cargo the cargo to set
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    
        
    // Método para adicionar cliente, disponível apenas para Funcionario
    public void adicionarCliente(Cliente cliente) {
    String sqlPessoa = "INSERT INTO Pessoa (id, nome, email, telefone, cpf) VALUES (?, ?, ?, ?, ?)";
    String sqlCliente = "INSERT INTO Cliente (id) VALUES (?)";

    try (Connection conn = DatabasePersistence.getConnection()) {
        conn.setAutoCommit(false); // Desativar o commit automático para controle de transação

        // Primeiro, insere na tabela Pessoa
        try (PreparedStatement pstmtPessoa = conn.prepareStatement(sqlPessoa)) {
            pstmtPessoa.setInt(1, cliente.getId());
            pstmtPessoa.setString(2, cliente.getNome());
            pstmtPessoa.setString(3, cliente.getEmail());
            pstmtPessoa.setString(4, cliente.getTelefone());
            pstmtPessoa.setString(5, cliente.getCpf());
            pstmtPessoa.executeUpdate();
        }

        // Depois, insere na tabela Cliente
        try (PreparedStatement pstmtCliente = conn.prepareStatement(sqlCliente)) {
            pstmtCliente.setInt(1, cliente.getId());
            pstmtCliente.executeUpdate();
        }

        conn.commit(); // Confirma as transações
        System.out.println("Cliente adicionado com sucesso!");

    } catch (SQLException e) {
        e.printStackTrace(); // Desfaz a transação em caso de erro
    }
    }

    // Método para buscar cliente por ID, disponível apenas para Funcionario
    public Cliente buscarClientePorId(int id) {
        String sql = "SELECT * FROM Cliente WHERE id = ?";
        Cliente cliente = null;

        try (Connection conn = DatabasePersistence.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));
                cliente.setCpf(rs.getString("cpf"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cliente;
    }
}
