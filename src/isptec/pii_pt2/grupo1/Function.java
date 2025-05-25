package isptec.pii_pt2.grupo1;
import java.util.ArrayList;
import static isptec.pii_pt2.grupo1.Utils.add_name;
import static isptec.pii_pt2.grupo1.Utils.validate_choose;
import static isptec.pii_pt2.grupo1.Utils.input; 
/**
 *
 * @author lucio
 */
public class Function {
    
   static ArrayList<Function> functions_list = new ArrayList<Function>();
    StringBuilder name = new StringBuilder();
    double salary;
    double bonus;
    int id;
    int colab_assigned;


    public static void create_function(String name, double salary, double bonus)
    {
        Function function = new Function();
        function.name.append(name);
        function.salary = salary;
        function.bonus = bonus;
        function.id = functions_list.size() + 1;
        for(Function item : functions_list)
        {
         if(function.id == item.id)
             function.id = functions_list.size() + 2;  
        }
        function.colab_assigned = 0;
        functions_list.add(function);
    }
    public static void new_function()
    {
        System.out.println("-----Criando nova função-----");
        System.out.println("Digite o nome da função: ");
        String name = add_name();
        if (function_exists(name)) {
            System.out.println("Essa função já existe.");
            return;
        }
        System.out.println("Digite o salário: ");
        double salary = input.nextDouble();
        System.out.println("Digite o bônus: ");
        double bonus = input.nextDouble();
        create_function(name, salary, bonus);
        System.out.println("Função criada com sucesso!");
    }
    public static Function select_function()
        {
            System.out.println("------Escolha a Funçao------");
           Integer choose = validate_choose(functions_list);
           if (choose == null)
               select_function();
           functions_list.get(choose.intValue()).colab_assigned ++;
            return functions_list.get(choose.intValue());
        }

    public static void delete_function()
    {
        System.out.println("------Removendo Função------");
        if (functions_list.isEmpty())
        {
            System.out.println("Não existem funções cadastradas");
            return;
        }
        System.out.println("------Escolha a Funçao------");
        Integer choose = validate_choose(functions_list);
        if(choose == null)
        {
            System.out.println("Função não encontrada.");
            return;
        }
        if(functions_list.get(choose.intValue()).colab_assigned > 0)
        {
            System.out.println("Essa função não pode ser removida, pois existem colaboradores associados a ela");
            return;
        }
        functions_list.remove(choose.intValue());
        System.out.println("Função removida com sucesso!");
    }

    public static void list_functions()
    {
        System.out.println("Lista de Funções");
        for (int i = 0; i < functions_list.size(); i++)
        {
            System.out.println(functions_list.get(i).id + " - " + functions_list.get(i).name);
        }
    }
    public static void print_list_of_functions()
    {
        if(functions_list.isEmpty()) {
            System.out.println("Não existem funções cadastradas.");
            return;
        }
        System.out.println("Lista de Funções:");
        System.out.println("-----------------------------");
        for (Function f : functions_list) {
            System.out.println("ID: " + f.id);
            System.out.println("Nome: " + f.name);
            System.out.println("Salário: " + f.salary);
            System.out.println("Bônus: " + f.bonus);
            System.out.println("Colaboradores associados: " + f.colab_assigned);
            System.out.println("-----------------------------");
        }
    }
    public static boolean function_exists(String name) {
        for (Function f : functions_list) {
            if (f.name.toString().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}
