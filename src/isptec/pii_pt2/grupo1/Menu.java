package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Collaborator.disable_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.list_collaborators;
import static isptec.pii_pt2.grupo1.Collaborator.print_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.register_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.search_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.update_collaborator;
import static isptec.pii_pt2.grupo1.Function.delete_function;
import static isptec.pii_pt2.grupo1.Function.new_function;
import static isptec.pii_pt2.grupo1.Utils.add_int;
import static isptec.pii_pt2.grupo1.Utils.input;
import static isptec.pii_pt2.grupo1.Report.report_collaborator;
import static isptec.pii_pt2.grupo1.Function.print_list_of_functions;
import java.util.ArrayList;

public class Menu {

    public static void menu_collaborator(ArrayList<Collaborator> list, ArrayList<Function> functions_list) {
        int option1;
        do{
            System.out.println("========= MENU COLABORADORES =========");
            System.out.println("1. Cadastrar Colaborador");
            System.out.println("2. Actualizar Colaborador");
            System.out.println("3. Desativar Colaborador");
            System.out.println("4. Pesquisar Colaborador");
            System.out.println("5. Imprimir Colaboradores");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("=====================================");
            System.out.print("Escolha uma opção: ");
            option1 = add_int();
            switch (option1) {
                case 1:
                if (functions_list == null || functions_list.isEmpty()) {
                    System.out.println("A lista de funções encontra-se vazia! Por favor, crie uma função antes de adicionar colaboradores.");
                    return;
                }
                    register_collaborator(list);
                    break;
                case 2:
                    update_collaborator(list);
                    break;
                case 3:
                    System.out.print("Digite o ID do colaborador a desativar: ");
                    String id = input.next();
                    input.nextLine();
                    disable_collaborator(id, list);
                    System.out.println();
                    break;
                case 4:
                    System.out.print("Digite o ID do colaborador a pesquisar: ");
                    id = input.next();
                    input.nextLine();
                    int index = search_collaborator(list, id);
                    if (index == -1) {
                        System.out.println("Colaborador não encontrado.");
                        break;
                    } 
                    print_collaborator(list.get(index));
                    System.out.println();
                    break;
                case 5:
                    if(list.isEmpty()) {
                        System.out.println("Nenhum colaborador cadastrado.");
                    } else {
                        System.out.println("Lista de Colaboradores:");
                        list_collaborators(list);
                    }
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
            } while (option1 != 0); 
    }

    public static void menu_function() {
        int option2;
        do{
            System.out.println("========= MENU FUNÇÕES =========");
            System.out.println("1. Criar Função");
            System.out.println("2. Remover Função");
            System.out.println("3. Listar Funções");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("================================");
            System.out.print("Escolha uma opção: ");
            option2 = add_int();
                switch (option2) {
                case 1:
                    new_function();
                    break;
                case 2:
                    delete_function();
                    break;
                case 3:
                print_list_of_functions();
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
                }
            } while (option2 != 0);
    }

    public static void menu_report(ArrayList<Collaborator> list) {
        int option3;
        do{
            System.out.println("========= MENU RELATÓRIOS =========");
            System.out.println("1. Gerar Relatório");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.println("===================================");
            System.out.print("Escolha uma opção: ");
            option3 = add_int();
                switch (option3) {
                case 1:
                    report_collaborator(list);
                    break;
                case 0:
                    System.out.println("Voltando ao Menu Principal...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
                }
            } while (option3 != 0);
    }
  public static void print_welcome(ArrayList<Collaborator> list, ArrayList<Function> functions_list) {
   

    int option;
     do{
        System.out.println("========== BEM VINDO ===========");
        System.out.println("PROCESSAMENTO DE SALARIO");
        System.out.println("========= MENU ================");
        System.out.println("1. Colaboradores");
        System.out.println("2. Funções");
        System.out.println("3. Gerir Relatório");
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
            case 3:
                menu_report(list);
                break;
            case 0:
                System.out.println("Saindo...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
            System.out.println();
    } while (option != 0);

}

}
