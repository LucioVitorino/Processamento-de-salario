package isptec.pii_pt2.grupo1;
import java.util.ArrayList;
import static isptec.pii_pt2.grupo1.Utils.add_name;
import static isptec.pii_pt2.grupo1.Utils.validate_choose;
import static isptec.pii_pt2.grupo1.Utils.input;
import java.util.Comparator;
import java.nio.file.Paths;
import static java.nio.file.Files.readAllBytes;
import org.json.JSONObject;
import org.json.JSONArray;
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
        input.nextLine();
        System.out.println("Digite o bônus: ");
        double bonus = input.nextDouble();
        input.nextLine();
        create_function(name, salary, bonus);
        System.out.println("Função criada com sucesso!");
    }
    public static Function select_function()
        {
            System.out.println("------Escolha a Funçao------");
           Integer choose = validate_choose(functions_list);
           if (choose == null)
                 return null;
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
        functions_list.sort(Comparator.comparing(item -> item.id));
    }
    // get functions by id
    public static Function get_function_by_id(int id)
    {
        for (Function f : functions_list) {
            if (f.id == id) {
                return f;
            }
        }
        return null;
    }
    public static void list_functions()
    {
        System.out.println("Lista de Funções");
        for (int i = 0; i < functions_list.size(); i++)
        {
            System.out.println(i + 1 +": "+functions_list.get(i).id + " - " + functions_list.get(i).name);
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
    // read functions from json file and add to functions_list
    public static void read_functions_from_json_file(String filePath) {
        try {
            String content = new String(readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                if (function_exists(name)) {
                    System.out.println("Essa função já existe.");
                    continue;
                }
                double salary = jsonObject.getDouble("salary");
                double bonus = jsonObject.getDouble("bonus");
                create_function(name, salary, bonus);
            }
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
    }
    

}
