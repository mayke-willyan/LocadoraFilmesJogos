package br.com.locadora.controller;

import br.com.locadora.controller.model.*;
import br.com.locadora.controller.exception.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LocadoraController controller = new LocadoraController();
        Scanner scanner = new Scanner(System.in);

        Contato contato1 = new Contato("988269246", "mateuscandido470@email.com");
        Cliente cliente1 = new Cliente(1, "Mateus Candido", "14253808409", "mateuscandido470@email.com", contato1);
        controller.cadastrarCliente(cliente1);

        Contato contato2 = new Contato("900000000", "pedrohenrique@email.com");
        Cliente cliente2 = new Cliente(2, "Pedro Henrique", "12345678910", "pedrohenrique@email.com", contato2);
        controller.cadastrarCliente(cliente2);

        controller.cadastrarTitulo(new Filme(10, "Interestelar", 2014, 10.0));
        controller.cadastrarTitulo(new Serie(20, "Breaking Bad", 2008, 15.0));
        controller.cadastrarTitulo(new Jogo(30, "The Witcher 3", 2015, 20.0));
        controller.cadastrarTitulo(new Filme(40, "Avatar", 2009, 8.0));

        int opcao = -1;

        do {
            System.out.println("\n      SISTEMA DE LOCADORA       ");
            System.out.println("1. Cadastrar Novo Título");
            System.out.println("2. Consultar Clientes Ativos (Global)");
            System.out.println("3. Realizar Locação / Reserva");
            System.out.println("4. Registrar Devolução com Multa");
            System.out.println("5. Renovar Locação (Unitária por ID)");
            System.out.println("6. Renovar Locação (Em Massa por CPF)");
            System.out.println("7. Consultar Histórico do Cliente");
            System.out.println("8. Relatório de Popularidade");
            System.out.println("9. Sugerir Títulos (Recomendações)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        System.out.println("\n--- Cadastrar Título ---");
                        System.out.print("Tipo (1-Filme, 2-Série, 3-Jogo): ");
                        int tipo = scanner.nextInt();
                        System.out.print("ID do Título: "); int id = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Nome/Título: "); String titulo = scanner.nextLine();
                        System.out.print("Ano de Lançamento: "); int ano = scanner.nextInt();
                        System.out.print("Preço Base: R$ "); double preco = scanner.nextDouble();

                        if (tipo == 1) {
                            controller.cadastrarTitulo(new Filme(id, titulo, ano, preco));
                        } else if (tipo == 2) {
                            controller.cadastrarTitulo(new Serie(id, titulo, ano, preco));
                        } else if (tipo == 3) {
                            controller.cadastrarTitulo(new Jogo(id, titulo, ano, preco));
                        } else {
                            System.out.println("Tipo inválido!");
                            break;
                        }
                        System.out.println("Título '" + titulo + "' adicionado ao acervo!");
                        break;

                    case 2:
                        System.out.println("\n- Estatísticas do Sistema-");
                        System.out.println("Quantidade global de clientes ativos: " + Cliente.getTotalClientesAtivos());
                        break;

                    case 3:
                        System.out.println("\n Realizar Locação");
                        System.out.print("Digite o CPF do Cliente: "); String cpfLoc = scanner.nextLine();
                        System.out.print("Digite o ID do Título desejado: "); int idLoc = scanner.nextInt();

                        try {
                            controller.realizarLocacao(idLoc, cpfLoc);
                            System.out.println("Locação registada e efetuada com sucesso!");
                        } catch (TituloIndisponivelException e) {
                            System.out.println("[ALERTA]: " + e.getMessage());
                            System.out.print("Deseja colocar o cliente na Fila de Espera deste título? (S/N): ");
                            String resposta = scanner.nextLine();
                            if (resposta.equalsIgnoreCase("S")) {
                                Titulo t = controller.buscarTituloId(idLoc);
                                Cliente c = controller.buscarClienteCPF(cpfLoc);
                                t.getFilaEspera().add(c);
                                System.out.println(c.getNome() + " foi inserido na lista de reserva do título '" + t.getTitulo() + "'.");
                            }
                        }
                        break;

                    case 4:
                        System.out.println("\n Registrar Devolução ");
                        System.out.print("Digite o CPF do Cliente: "); String cpfDev = scanner.nextLine();
                        System.out.print("Digite o ID do Título a devolver: "); int idDev = scanner.nextInt();
                        System.out.print("Dias de atraso na entrega (0 se não houver): "); int dias = scanner.nextInt();

                        double valorMulta = controller.registrarDevolucao(cpfDev, idDev, dias);
                        System.out.println("Título retornado ao estado DISPONÍVEL.");
                        if (dias > 0) {
                            System.out.println("Atraso detetado! Taxa de multa aplicada: R$ " + String.format("%.2f", valorMulta));
                        } else {
                            System.out.println("Devolução sem atrasos. Nenhuma multa aplicada.");
                        }

                        Titulo tDev = controller.buscarTituloId(idDev);
                        if (!tDev.getFilaEspera().isEmpty()) {
                            Cliente proximo = tDev.getFilaEspera().remove(0);
                            System.out.println("[NOTIFICAÇÃO]: O título está livre! Próximo cliente prioritário: " + proximo.getNome());
                        }
                        break;

                    case 5:
                        System.out.println("\n Renovação Unitária (Sobrecarga por ID) ");
                        System.out.print("Digite o ID do Título a renovar: "); int idRen = scanner.nextInt();
                        controller.renovarLocacao(idRen);
                        break;

                    case 6:
                        System.out.println("\n Renovação em Massa (Sobrecarga por CPF) ");
                        System.out.print("Digite o CPF do Cliente: "); String cpfRen = scanner.nextLine();
                        controller.renovarLocacao(cpfRen);
                        break;

                    case 7:
                        System.out.print("Digite o CPF do Cliente para o Histórico: ");
                        String cpfHist = scanner.nextLine();
                        controller.consultarHistoricoCliente(cpfHist);
                        break;

                    case 8:
                        controller.exibirRelatorioPopularidade();
                        break;

                    case 9:
                        System.out.println("\n Modo de Sugestão ");
                        System.out.println("1. Baseado em um título anterior");
                        System.out.println("2. Geral (Top 3 mais populares)");
                        System.out.print("Escolha o modo: ");
                        int modo = scanner.nextInt();
                        if (modo == 1) {
                            System.out.print("Digite o ID do título base: ");
                            int idSug = scanner.nextInt();
                            controller.sugerirTitulos(idSug);
                        } else {
                            controller.sugerirTitulos();
                        }
                        break;

                    case 0:
                        System.out.println("A desligar o sistema da Locadora. Até à próxima!");
                        break;

                    default:
                        System.out.println("Opção inválida! Escolha um número do menu.");
                }

            } catch (TituloNaoEncontradoException | ClienteNaoEncontradoException | LocacaoNaoEncontradaException e) {
                System.out.println("\n[ERRO DE NEGÓCIO]: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("\n[ERRO INESPERADO]: Ocorreu uma falha na entrada de dados. " + e.getMessage());
                scanner.nextLine();
            }

        } while (opcao != 0);

        scanner.close();
    }
}