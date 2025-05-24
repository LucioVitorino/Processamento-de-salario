package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Collaborator.disable_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.list_collaborators;
import static isptec.pii_pt2.grupo1.Collaborator.print_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.register_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.search_collaborator;
import static isptec.pii_pt2.grupo1.Collaborator.update_collaborator;
import static isptec.pii_pt2.grupo1.Function.delete_function;
import static isptec.pii_pt2.grupo1.Function.list_functions;
import static isptec.pii_pt2.grupo1.Function.new_function;
import static isptec.pii_pt2.grupo1.Utils.add_int;
import static isptec.pii_pt2.grupo1.Utils.input;

import java.util.ArrayList;

public class Menu {

    public static void menu_collaborator(ArrayList<Collaborator> list) {
        System.out.println("========= MENU COLABORADORES =========");
        System.out.println("1. Cadastrar Colaborador");
        System.out.println("2. Actualizar Colaborador");
        System.out.println("3. Desativar Colaborador");
        System.out.println("4. Pesquisar Colaborador");
        System.out.println("5. Imprimir Colaboradores");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.println("=====================================");
        System.out.print("Escolha uma opção: ");
         int option;
        do{
         option = add_int();
          switch (option) {
            case 1:
                register_collaborator(list);
                break;
            case 2:
                update_collaborator(list);
                break;
            case 3:
                System.out.print("Digite o ID do colaborador a desativar: ");
                String id = input.nextLine();
                disable_collaborator(id, list);
                System.out.println();
                break;
            case 4:
                System.out.print("Digite o ID do colaborador a pesquisar: ");
                id = input.nextLine();
                System.out.println();
                int index = search_collaborator(list, id);
                if (index == -1) {
                    System.out.println("Colaborador não encontrado.");
                    break;
                } 
                print_collaborator(list.get(index));
                System.out.println();
                break;
            case 5:
                list_collaborators(list);
                break;
            case 0:
                System.out.println("Voltando ao Menu Principal...");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                break;
          }
        } while (option != 0); 
    }

    public static void menu_function() {
        System.out.println("========= MENU FUNÇÕES =========");
        System.out.println("1. Criar Função");
        System.out.println("2. Remover Função");
        System.out.println("3. Listar Funções");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.println("================================");
        System.out.print("Escolha uma opção: ");
         int option;
        do{
          option = add_int();
            switch (option) {
              case 1:
                  new_function();
                  break;
              case 2:
                  delete_function();
                  break;
              case 3:
                  list_functions();
                  break;
              case 0:
                  System.out.println("Voltando ao Menu Principal...");
                  break;
              default:
                  System.out.println("Opção inválida. Tente novamente.");
                  break;
            }
          } while (option != 0);
    }

    public static void menu_report() {
        System.out.println("========= MENU RELATÓRIOS =========");
        System.out.println("1. Gerar Relatório");
        System.out.println("0. Voltar ao Menu Principal");
        System.out.println("===================================");
        System.out.print("Escolha uma opção: ");
         int option;
        do{
          option = add_int();
            switch (option) {
              case 1:
                //  gennerate_report();
                  break;
              case 0:
                  System.out.println("Voltando ao Menu Principal...");
                  break;
              default:
                  System.out.println("Opção inválida. Tente novamente.");
                  break;
            }
          } while (option != 0);
        
    }
  public static void print_welcome(ArrayList<Collaborator> list) {
      System.out.println("========= BEM VINDO ==========");
      System.out.println("Sistema de Gestão de Colaboradores");
      System.out.println("========= MENU =========");
      System.out.println("1. Colaboradores");
      System.out.println("2. Funções");
      System.out.println("3. Gerir Relatório");
      System.out.println("0. Sair");
      System.out.println("================================");
      System.out.print("Escolha uma opção: ");
      int option = add_int();
      switch (option) {
          case 1:
              menu_collaborator(list);
              break;
          case 2:
              menu_function();
              break;
          case 3:
              menu_report();
              break;
          case 0:
              System.out.println("Saindo...");
              System.exit(0);
              break;
          default:
              System.out.println("Opção inválida. Tente novamente.");
      }
}

}
