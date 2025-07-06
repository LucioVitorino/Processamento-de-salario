package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Collaborator.disable_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.generate_salary;
import static isptec.pii_pt2.grupo1.Collaborator.list_collaborators;
import static isptec.pii_pt2.grupo1.Collaborator.print_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.register_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.search_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.update_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.read_collaborators_from_json_file;
import static isptec.pii_pt2.grupo1.Function.delete_function;
import static isptec.pii_pt2.grupo1.Function.new_function;
import static isptec.pii_pt2.grupo1.Utils.add_int;
import static isptec.pii_pt2.grupo1.Utils.input;
import static isptec.pii_pt2.grupo1.Report.report_collaborator;
import static isptec.pii_pt2.grupo1.Function.print_list_of_functions;
import static isptec.pii_pt2.grupo1.Utils.clearScreen;
import static isptec.pii_pt2.grupo1.Utils.ANSI_GREEN;
import static isptec.pii_pt2.grupo1.Utils.ANSI_RED;
import static isptec.pii_pt2.grupo1.Utils.ANSI_RESET;
import static isptec.pii_pt2.grupo1.Utils.pause;
import static isptec.pii_pt2.grupo1.Dashboard.showDashboard;
import java.util.ArrayList;

public class Menu {


    public static void menu_collaborator(ArrayList<Collaborator> list, ArrayList<Function> functions_list) {
        int option1;
        do{
            clearScreen();
            System.out.println("========= MENU COLABORADORES =========");
            System.out.println("1. Cadastrar Colaborador");
            System.out.println("2. Ler Automaticamente Colaborador");
            System.out.println("3. Actualizar Colaborador");
            System.out.println("4. Desativar Colaborador");
            System.out.println("5. Pesquisar Colaborador");
            System.out.println("6. Imprimir Colaboradores");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("=====================================");
            System.out.print("Escolha uma opção: ");
            option1 = add_int();
            switch (option1) {
                case 1:
                    if (functions_list == null || functions_list.isEmpty()) {
                        System.out.println("\n" + ANSI_RED + "A lista de funções encontra-se vazia! Por favor, crie uma função antes de adicionar colaboradores." + ANSI_RESET + "\n");
                        pause();
                        return;
                    }
                    register_collaborator(list);
                    pause();
                    break;
                case 2:
                    if (functions_list == null || functions_list.isEmpty()) {
                        System.out.println("\n" + ANSI_RED + "A lista de funções encontra-se vazia! Por favor, crie uma função antes de adicionar colaboradores." + ANSI_RESET + "\n");
                        pause();
                        return;
                    }
                    System.out.println("\nLendo colaboradores automaticamente...");
                    read_collaborators_from_json_file(list, "files/collaborators.json");
                    pause();
                    break;
                case 3:
                    update_collaborator(list);
                    pause();
                    break;
                case 4:
                    System.out.print("Digite o ID do colaborador a desativar: ");
                    String id = input.next();
                    input.nextLine();
                    disable_collaborator(id, list);
                    System.out.println("\n--------------------------------------\n");
                    pause();
                    break;
                case 5:
                    System.out.print("Digite o ID do colaborador a pesquisar: ");
                    id = input.next();
                    input.nextLine();
                    int index = search_collaborator(list, id);
                    if (index == -1) {
                        System.out.println("\nColaborador não encontrado.\n");
                        pause();
                        break;
                    }
                    print_collaborator(list.get(index));
                    System.out.println();
                    pause();
                    break;
                case 6:
                    if(list.isEmpty()) {
                        System.out.println("\n" + ANSI_RED + "Nenhum colaborador cadastrado." + ANSI_RESET + "\n");
                    } else {
                        System.out.println("\nLista de Colaboradores:");
                        list_collaborators(list);
                        System.out.println();
                    }
                    pause();
                    break;
                case 0:
                    System.out.println("\nVoltando ao Menu Principal...\n");
                    pause();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Opção inválida. Tente novamente." + ANSI_RESET + "\n");
                    pause();
                    break;
            }
        } while (option1 != 0);
    }

    public static void menu_function() {
        int option2;
        do{
            clearScreen();
            System.out.println("========= MENU FUNÇÕES =========");
            System.out.println("1. Criar Função");
            System.out.println("2. Ler Funções Automaticamente");
            System.out.println("3. Remover Função");
            System.out.println("4. Listar Funções");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("================================");
            System.out.print("Escolha uma opção: ");
            option2 = add_int();
            switch (option2) {
                case 1:
                    new_function();
                    pause();
                    break;
                case 2:
                    System.out.println("\nLendo funções automaticamente...");
                    Function.read_functions_from_json_file("files/functions.json");
                    pause();
                    break;
                case 3:
                    delete_function();
                    pause();
                    break;
                case 4:
                    print_list_of_functions();
                    pause();
                    break;
                case 0:
                    System.out.println("\n" + ANSI_GREEN + "Voltando ao Menu Principal..." + ANSI_RESET + "\n");
                    pause();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Opção inválida. Tente novamente." + ANSI_RESET + "\n");
                    pause();
                    break;
            }
        } while (option2 != 0);
    }

    public static void menu_report(ArrayList<Collaborator> list) {
        int option3;
        do{
            clearScreen();
            System.out.println("========= MENU RELATÓRIOS =========");
            System.out.println("1. Gerar Relatório(PDF)");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("===================================");
            System.out.print("Escolha uma opção: ");
            option3 = add_int();
            switch (option3) {
                case 1:
                    report_collaborator(list);
                    pause();
                    break;
                case 0:
                    System.out.println("\n" + ANSI_GREEN + "Voltando ao Menu Principal..." + ANSI_RESET + "\n");
                    pause();
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Opção inválida. Tente novamente." + ANSI_RESET + "\n");
                    pause();
                    break;
            }
        } while (option3 != 0);
    }

    public static void print_welcome(ArrayList<Collaborator> list, ArrayList<Function> functions_list) {
        int option;
        do{
            clearScreen();
            System.out.println("========== BEM VINDO ===========");
            System.out.println("PROCESSAMENTO DE SALARIO");
            System.out.println("========= MENU ================");
            System.out.println("1. Colaboradores");
            System.out.println("2. Funções");
            System.out.println("3. Gerar Salário");
            System.out.println("4. Gerar Relatório");
            System.out.println("5. Dashboard Executivo");
            System.out.println("0. Sair");
            System.out.println("================================");
            System.out.print("Escolha uma opção: ");
            option = add_int();
            switch (option) {
                case 1:
                    menu_collaborator(list, functions_list);
                    break;
                case 2:
                    menu_function();
                    break;
                case 4:
                    menu_report(list);
                    break;
                case 5:
                    showDashboard(list);
                    break;
                case 3:
                    generate_salary(list);
                    pause();
                    break;
                case 0:
                    System.out.println("\n" + ANSI_GREEN + "Saindo..." + ANSI_RESET + "\n");
                    System.exit(0);
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Opção inválida. Tente novamente." + ANSI_RESET + "\n");
            }
            System.out.println();
        } while (option != 0);
    }

}
