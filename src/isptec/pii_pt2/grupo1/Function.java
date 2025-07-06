package isptec.pii_pt2.grupo1;
import java.util.ArrayList;
import static isptec.pii_pt2.grupo1.Utils.add_name;
import static isptec.pii_pt2.grupo1.Utils.validate_choose;
import static isptec.pii_pt2.grupo1.Utils.input;
import static isptec.pii_pt2.grupo1.Utils.ANSI_GREEN;
import static isptec.pii_pt2.grupo1.Utils.ANSI_RED;
import static isptec.pii_pt2.grupo1.Utils.ANSI_RESET;
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
    int expected_hours;
    int absent_discount;
    double bonus;
    int id;
    int colab_assigned;


    public static void create_function(String name, double salary, double bonus, double discount, int expectative)
    {
        Function function = new Function();
        function.name.append(name);
        function.salary = salary;
        function.bonus = bonus;
        function.id = functions_list.size() + 1;
        function.expected_hours = expectative;
        function.absent_discount = (int) discount;
        for(Function item : functions_list)
        {
         if(function.id == item.id)
             function.id = functions_list.size() + 2;  
        }
        function.colab_assigned = 0;
        functions_list.add(function);
        functions_list.sort(Comparator.comparing(item -> item.id));
        save_new_function_to_json_file(function, "files/functions.json");
        System.out.println(ANSI_GREEN + "Função criada com sucesso!" + ANSI_RESET);
    }
    public static void new_function()
    {
        System.out.println("-----Criando nova função-----");
        System.out.println("Digite o nome da função: ");
        String name = add_name();
        if (function_exists(name)) {
            System.out.println(ANSI_RED + "Essa função já existe." + ANSI_RESET);
            return;
        }
        System.out.println("Digite o salário: ");
        double salary = input.nextDouble();
        input.nextLine();
        System.out.println("Digite o bônus: ");
        double bonus = input.nextDouble();
        input.nextLine();
        System.out.println("Digite as horas esperadas por mês: ");
        int expectative = input.nextInt();
        System.out.println("Digite o valor de desconto por falta: ");
        double discount = input.nextInt();
        create_function(name, salary, bonus, discount, expectative );
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
            System.out.println(ANSI_RED + "Não existem funções cadastradas" + ANSI_RESET);
            return;
        }
        System.out.println("------Escolha a Funçao------");
        Integer choose = validate_choose(functions_list);
        if(choose == null)
        {
            System.out.println(ANSI_RED + "Função não encontrada." + ANSI_RESET);
            return;
        }
        if(functions_list.get(choose.intValue()).colab_assigned > 0)
        {
            System.out.println(ANSI_RED + "Essa função não pode ser removida, pois existem colaboradores associados a ela" + ANSI_RESET);
            return;
        }
        functions_list.remove(choose.intValue());
        System.out.println(ANSI_GREEN + "Função removida com sucesso!" + ANSI_RESET);
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
        if (functions_list.isEmpty()) {
            System.out.println(ANSI_RED + "Nenhuma função disponível." + ANSI_RESET);
            return;
        }
        
        System.out.println("\n┌─────────────── FUNÇÕES DISPONÍVEIS ───────────────┐");
        for (int i = 0; i < functions_list.size(); i++)
        {
            Function f = functions_list.get(i);
            System.out.printf("│ %s[%d]%s %-25s (%.2f Kzs) │%n", 
                            ANSI_GREEN, f.id, ANSI_RESET, 
                            truncateString(f.name.toString(), 25), 
                            f.salary);
        }
        System.out.println("└────────────────────────────────────────────────────┘");
    }
    public static void print_list_of_functions() {
        if (functions_list.isEmpty()) {
            System.out.println(ANSI_RED + "\nNão existem funções cadastradas.\n" + ANSI_RESET);
            return;
        }
        
        // Estatísticas rápidas
        int totalFuncoes = functions_list.size();
        int totalColaboradores = functions_list.stream().mapToInt(f -> f.colab_assigned).sum();
        double salarioMedio = functions_list.stream().mapToDouble(f -> f.salary).average().orElse(0);
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                              LISTA DE FUNÇÕES                                                ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ Total de Funções: %s%d%s │ Colaboradores Atribuídos: %s%d%s │ Salário Médio: %s%.2f Kzs%s                      ║%n",
                         ANSI_GREEN, totalFuncoes, ANSI_RESET,
                         ANSI_GREEN, totalColaboradores, ANSI_RESET,
                         ANSI_GREEN, salarioMedio, ANSI_RESET);
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        
        // Cabeçalho da tabela
        System.out.println("┌──────┬──────────────────────┬─────────────┬─────────────┬─────────────┬──────────┬─────────────┬──────────────┐");
        System.out.printf("│ %-4s │ %-20s │ %-11s │ %-11s │ %-11s │ %-8s │ %-11s │ %-12s │%n",
                         "ID", "Nome", "Salário", "Bônus", "Desconto", "Colabs", "Horas Esp.", "Custo Total");
        System.out.println("├──────┼──────────────────────┼─────────────┼─────────────┼─────────────┼──────────┼─────────────┼──────────────┤");
        
        // Dados das funções
        for (Function f : functions_list) {
            double custoTotal = (f.salary + f.bonus) * f.colab_assigned;
            String nomeFormatado = truncateString(f.name.toString(), 20);
            
            System.out.printf("│ %-4d │ %-20s │ %s%9.2f Kzs%s │ %s%9.2f Kzs%s │ %s%9d Kzs%s │ %s%6d%s   │ %s%9d h%s │ %s%10.2f Kzs%s │%n",
                f.id,
                nomeFormatado,
                ANSI_GREEN, f.salary, ANSI_RESET,
                ANSI_GREEN, f.bonus, ANSI_RESET,
                ANSI_RED, f.absent_discount, ANSI_RESET,
                f.colab_assigned > 0 ? ANSI_GREEN : ANSI_RED, f.colab_assigned, ANSI_RESET,
                ANSI_GREEN, f.expected_hours, ANSI_RESET,
                custoTotal > 0 ? ANSI_GREEN : "", custoTotal, custoTotal > 0 ? ANSI_RESET : ""
            );
        }
        
        System.out.println("└──────┴──────────────────────┴─────────────┴─────────────┴─────────────┴──────────┴─────────────┴──────────────┘");
        
        // Resumo financeiro
        double custoTotalEmpresa = functions_list.stream()
            .mapToDouble(f -> (f.salary + f.bonus) * f.colab_assigned)
            .sum();
        
        System.out.println("\n┌─────────────────── RESUMO FINANCEIRO ───────────────────┐");
        System.out.printf("│ Custo Total da Folha: %s%.2f Kzs%s                 │%n", 
                         ANSI_GREEN, custoTotalEmpresa, ANSI_RESET);
        System.out.printf("│ Custo Médio por Colaborador: %s%.2f Kzs%s          │%n", 
                         ANSI_GREEN, totalColaboradores > 0 ? custoTotalEmpresa / totalColaboradores : 0, ANSI_RESET);
        System.out.println("└──────────────────────────────────────────────────────────┘");
    }
    
    // Método auxiliar para truncar strings longas
    private static String truncateString(String str, int maxLength) {
        if (str == null) return "N/A";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
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
            if (jsonArray.length() == 0) {
                System.out.println(ANSI_RED + "Não existem funções cadastradas." + ANSI_RESET);
                return;
            }
            int before = functions_list.size();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                if (function_exists(name)) {
                    continue;
                }
                double salary = jsonObject.getDouble("salary");
                double bonus = jsonObject.getDouble("bonus");
                double discount = jsonObject.getDouble("discount");
                int expectativa = jsonObject.getInt("expected_hours");
                Function function = new Function();
                function.name.append(name);
                function.salary = salary;
                function.bonus = bonus;
                function.id = functions_list.size() + 1;
                function.expected_hours = expectativa;
                function.absent_discount = (int) discount;
                for(Function item : functions_list) {
                    if(function.id == item.id)
                        function.id = functions_list.size() + 2;
                }
                function.colab_assigned = 0;
                functions_list.add(function);
                functions_list.sort(Comparator.comparing(item -> item.id));
            }
            if (functions_list.size() > before) {
                System.out.println(ANSI_GREEN + "Funções lidas com sucesso!" + ANSI_RESET);
            }
        } catch (java.nio.file.NoSuchFileException e) {
            System.out.println(ANSI_RED + "Não existem funções cadastradas, precisa criar manualmente." + ANSI_RESET);
        } catch (Exception e) {
            System.out.println(ANSI_RED + "Erro ao ler o arquivo JSON: " + e.getMessage() + ANSI_RESET);
        }
    }
    // save new function to json file
    public static void save_new_function_to_json_file(Function function, String filePath) {
        // Create a new JSONArray if the file does not exist
        JSONArray jsonArray = new JSONArray();
        try {
            String content = new String(readAllBytes(Paths.get(filePath)));
            jsonArray = new JSONArray(content);
        } catch (java.io.IOException e) {
          System.out.println(ANSI_GREEN + "Criando novo arquivo JSON." + ANSI_RESET);
        }
        // Create a new JSONObject for the function
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", function.name.toString());
        jsonObject.put("salary", function.salary);
        jsonObject.put("bonus", function.bonus);
        jsonObject.put("discount", function.absent_discount);
        jsonObject.put("expected_hours", function.expected_hours);
        // verifica se este id já existe no JSONArray
        boolean exists = false;
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            if (obj.getString("name").equals(function.name.toString())) {
                exists = true;
                break;
            }
        }
        jsonArray.put(jsonObject);
        try {
            java.nio.file.Files.write(Paths.get(filePath), jsonArray.toString(4).getBytes());
        } catch (java.io.IOException e) {   
            System.out.println(ANSI_RED + "Erro ao salvar o arquivo JSON: " + e.getMessage() + ANSI_RESET);
        }
    }

    public static void save_functions_to_json_file(String filePath) {
        JSONArray jsonArray = new JSONArray();
        for (Function f : functions_list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", f.name.toString());
            jsonObject.put("salary", f.salary);
            jsonObject.put("bonus", f.bonus);
            jsonObject.put("discount", f.absent_discount);
            jsonObject.put("expected_hours", f.expected_hours);

            jsonArray.put(jsonObject);
        }
        try {
            java.nio.file.Files.write(Paths.get(filePath), jsonArray.toString(4).getBytes());
        } catch (java.io.IOException e) {
            System.out.println(ANSI_RED + "Erro ao salvar o arquivo JSON: " + e.getMessage() + ANSI_RESET);
        }
    }
}
