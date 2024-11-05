/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.reservadehotel;

/**
 *
 * @author berna
 */
import Classes.Cliente;
import Classes.Funcionario;
import Classes.Reserva;
import java.util.Date;
import java.util.Scanner;

public class ReservaDeHotel {
    public static void main(String[] args) {
        // Criando os DAOs necessários
        Funcionario funcionario = new Funcionario();
        Reserva reserva = new Reserva();

        // Exemplo de criação de cliente (precisa ser feita por um funcionário)
        Cliente cliente = new Cliente();
        cliente.setNome("Maria Souza");
        cliente.setEmail("maria@email.com");
        cliente.setTelefone("987654321");
        cliente.setCpf("98765432109");
        funcionario.adicionarCliente(cliente);

        // Chamando a função para adicionar uma reserva
        adicionarReserva(reserva, cliente.getId(), 101); // Exemplo de ID de quarto 101
    }

    public static void adicionarReserva(Reserva reservaDAO, int idCliente, int idQuarto) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Data de entrada (dd-MM-yyyy): ");
        String dataEntradaStr = scanner.nextLine();

        System.out.println("Data de saída (dd-MM-yyyy): ");
        String dataSaidaStr = scanner.nextLine();

        try {
            // Converter as datas de entrada e saída para o formato Date
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd-MM-yyyy");
            Date dataEntrada = sdf.parse(dataEntradaStr);
            Date dataSaida = sdf.parse(dataSaidaStr);

            // Verificar a disponibilidade do quarto
            boolean disponivel = reservaDAO.verificarDisponibilidadeQuarto(idQuarto, dataEntrada, dataSaida);

            if (!disponivel) {
                System.out.println("O quarto já está reservado para as datas selecionadas.");
                return;
            }

            // Criando a reserva se o quarto estiver disponível
            Reserva reserva = new Reserva();
            reserva.setDataEntrada(dataEntrada);
            reserva.setStatus("Confirmada");
            reserva.setDataSaida(dataSaida);
            reserva.setIdQuarto(idQuarto);
            reserva.setIdCliente(idCliente);
            
            reservaDAO.adicionarReserva(reserva);
            System.out.println("Reserva adicionada com sucesso para o Cliente ID: " + idCliente + ", Quarto ID: " + idQuarto);

        } catch (Exception e) {
            System.out.println("Erro ao criar a reserva: " + e.getMessage());
        }
    }
}

