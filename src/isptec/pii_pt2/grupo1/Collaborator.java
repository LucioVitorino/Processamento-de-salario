
package isptec.pii_pt2.grupo1;
import static isptec.pii_pt2.grupo1.Function.functions_list;
import static isptec.pii_pt2.grupo1.Function.get_function_by_id;
import static isptec.pii_pt2.grupo1.Function.select_function;
import static isptec.pii_pt2.grupo1.Utils.add_int;
import static isptec.pii_pt2.grupo1.Utils.add_name;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import static isptec.pii_pt2.grupo1.Utils.gerador_id;
import static isptec.pii_pt2.grupo1.Utils.input;
import static isptec.pii_pt2.grupo1.Utils.validate_email;
import java.util.Comparator;
import java.nio.file.Paths;
import static java.nio.file.Files.readAllBytes;
import static isptec.pii_pt2.grupo1.Utils.ANSI_RED;
import static isptec.pii_pt2.grupo1.Utils.ANSI_GREEN;
import static isptec.pii_pt2.grupo1.Utils.ANSI_RESET;
import org.json.JSONObject;
import org.json.JSONArray;
/**
 *
 * @author lucio
 * @author jofre
 */
public class Collaborator {

    int number;
    StringBuilder name = new StringBuilder();
    LocalDate birthday;
    StringBuilder household = new StringBuilder();
    Function function = new Function();
    StringBuilder email = new StringBuilder();
    String Id = new String();
    LocalDate start_data;
    int worked_hours;
    int fouls;
    int extra_hours;
    StringBuilder license;
    boolean is_active = true;
    double net_salary;
    
    public static void register_collaborator(ArrayList<Collaborator> list)
    {
        Collaborator novo = new Collaborator();
        System.out.println("============ Cadastro de Colaborador ============");
        System.out.print("Digite o nome: ");
        novo.name.append(add_name());
        novo.birthday = create_date();
        novo.email.append(create_email(list));
        System.out.println();
        do {
            novo.function = select_function();
        } while (novo.function == null);
        System.out.println();
        System.out.print("Informa a sua morada :");
        novo.household.append(input.nextLine());
        novo.start_data = LocalDate.now();
        System.out.print("Informe a quantidade de horas trabalhadas: ");
        novo.worked_hours = input.nextInt();
        System.out.print("Informe a quantidade de faltas: ");
        novo.fouls = input.nextInt();
        novo.Id = gerador_id(novo.name.toString(), novo.birthday.getDayOfMonth(), novo.start_data.getYear(), novo.birthday.getMonthValue(), list.size());
        System.out.println("\nID gerado para o colaborador: [" + novo.Id + "]");
        System.out.println("\nResumo do cadastro:");
        System.out.println("----------------------------------------");
        System.out.println("Nome:         " + novo.name);
        System.out.println("Nascimento:   " + (novo.birthday != null ? novo.birthday : ""));
        System.out.println("Email:        " + novo.email);
        System.out.println("Função:       " + (novo.function != null ? novo.function.name : ""));
        System.out.println("Morada:       " + novo.household);
        System.out.println("Início:       " + (novo.start_data != null ? novo.start_data : ""));
        System.out.println("Horas trab.:  " + novo.worked_hours);
        System.out.println("Faltas:       " + novo.fouls);
        System.out.println("----------------------------------------");
        list.add(novo);
        System.out.println(ANSI_GREEN + "Colaborador cadastrado com sucesso!" + ANSI_RESET);
        // Persist the entire list to JSON after adding a new collaborator
        save_all_collaborators_to_json_file(list, "files/collaborators.json");
        System.out.println("\nID gerado para o colaborador: [" + novo.Id + "]");
    }

    public static Collaborator read_collaborator_from_json(JSONObject json) {
        Collaborator colaborador = new Collaborator();
        colaborador.Id = json.getString("Id");
        colaborador.name.append(json.getString("name"));
        colaborador.household.append(json.getString("household"));
        int funcId = json.getInt("function_id");
        colaborador.function = get_function_by_id(funcId);
        // Não incrementa colab_assigned aqui, só ao associar novo colaborador de fato
        if (colaborador.function == null) {
            System.out.println("Atenção: função com id " + funcId + " não encontrada para o colaborador " + colaborador.name + ".\nPor favor, selecione uma função válida para este colaborador.");
            do {
                colaborador.function = select_function();
            } while (colaborador.function == null);
        }
        colaborador.email.append(json.getString("email"));

        try {
            colaborador.birthday = LocalDate.parse(json.getString("birthday"));
        } catch (Exception e) {
            colaborador.birthday = null;
        }
        try {
            colaborador.start_data = LocalDate.parse(json.getString("start_data"));
        } catch (Exception e) {
            colaborador.start_data = LocalDate.now();
        }
        colaborador.is_active = json.getBoolean("is_active");
        return colaborador;
    }
    
    public static void read_collaborators_from_json_file(ArrayList<Collaborator> list, String filePath) {
        boolean erro = false;
        boolean atualizado = false;
        // Zera a contagem de colab_assigned de todas as funções antes de ler
        ArrayList<Function> allFunctions = functions_list;
        if (allFunctions != null) {
            for (Function f : allFunctions) {
                f.colab_assigned = 0;
            }
        }
        try {
            String content = new String(readAllBytes(Paths.get(filePath)));
            JSONArray jsonArray = new JSONArray(content);
            if (jsonArray.length() == 0) {
                System.out.println("Não existem colaboradores cadastrados.");
                return;
            }
            int before = list.size();
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Collaborator colaborador = read_collaborator_from_json(jsonObject);
                    // Atualiza ou adiciona colaborador na lista
                    boolean exists = false;
                    for (Collaborator c : list) {
                        if (c.Id.equals(colaborador.Id)) {
                            // Se a função mudou, decrementa da antiga e incrementa na nova
                            if (c.function != null && colaborador.function != null && c.function.id != colaborador.function.id) {
                                c.function.colab_assigned--;
                                colaborador.function.colab_assigned++;
                            }
                            // Atualiza todos os campos relevantes do colaborador existente
                            c.name.setLength(0);
                            c.name.append(colaborador.name);
                            c.household.setLength(0);
                            c.household.append(colaborador.household);
                            c.birthday = colaborador.birthday;
                            c.function = colaborador.function;
                            c.email.setLength(0);
                            c.email.append(colaborador.email);
                            c.start_data = colaborador.start_data;
                            c.is_active = colaborador.is_active;
                            exists = true;
                            atualizado = true;
                            break;
                        }
                    }
                    if (!exists) {
                        list.add(colaborador);
                        // Só incrementa colab_assigned ao adicionar novo colaborador
                        if (colaborador.function != null) {
                            colaborador.function.colab_assigned++;
                        }
                        atualizado = true;
                    }
                } catch (Exception ex) {
                    erro = true;
                    System.out.println("Erro ao processar colaborador do JSON: " + ex.getMessage());
                }
            }
            if (atualizado) {
                // Atualiza o arquivo JSON com a lista atualizada
                JSONArray novoArray = new JSONArray();
                for (Collaborator c : list) {
                    JSONObject obj = new JSONObject();
                    obj.put("Id", c.Id);
                    obj.put("name", c.name.toString());
                    obj.put("birthday", c.birthday != null ? c.birthday.toString() : "");
                    obj.put("household", c.household.toString());
                    obj.put("function_id", c.function != null ? c.function.id : 0);
                    obj.put("email", c.email.toString());
                    obj.put("start_data", c.start_data != null ? c.start_data.toString() : "");
                    obj.put("is_active", c.is_active);
                    novoArray.put(obj);
                }
                try {
                    java.nio.file.Files.write(Paths.get(filePath), novoArray.toString(4).getBytes());
                } catch (Exception e) {
                    System.out.println("Erro ao atualizar o arquivo JSON de colaboradores: " + e.getMessage());
                }
            }
            if (!erro && list.size() > before) {
                System.out.println(ANSI_GREEN + "Colaboradores lidos com sucesso!" + ANSI_RESET);
            }
        } catch (java.nio.file.NoSuchFileException e) {
            System.out.println("Não existem colaboradores cadastrados, precisa criar manualmente.");
        } catch (Exception e) {
            System.out.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        }
    }

  public static LocalDate create_date() 
    {
         DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         String data = new String();
         LocalDate date = LocalDate.now();
         
        try{
            
        System.out.print("Informe a data (dd/MM/yyyy) : ");
        data = input.next();
        input.nextLine();
        date = LocalDate.parse(data, formato);
            
        }catch(DateTimeParseException e)
        {
            System.out.print("Erro de fomatação de data !");
            return (create_date());
        }
        return (date);
    }
    
    public static String create_email(ArrayList<Collaborator> list)
    {
        String email_ = new String();
        System.out.print("Digite o seu email: ");
        do{
            email_ = input.next();
            input.nextLine();
           if(!validate_email(email_, list))
               System.out.print("Digite um email válido: ");
        }while(!validate_email(email_, list));
        return (email_);
    }
    
    public static void disable_collaborator(String Id, ArrayList<Collaborator> list)
    {
            int index = search_collaborator(list, Id);
            if(index != -1){
                list.get(index).is_active = false;
                System.out.print(ANSI_GREEN + list.get(index).name + " desativado com Sucesso!" + ANSI_RESET);
                return ;
            }
            else
                System.out.println(ANSI_RED + "Não existe nenhum colaborador com este id!" +ANSI_RESET);
    }
    
    public static void print_collaborator(Collaborator item) {
        String status = item.is_active ? ANSI_GREEN + "Ativo" + ANSI_RESET : ANSI_RED + "Inativo" + ANSI_RESET;
        String salario = item.net_salary > 0 ? String.format("%.2f Kzs", item.net_salary) : "N/A";
        
        System.out.printf("│ %-8s │ %-18s │ %-10s │ %-12s │ %-15s │ %-9s │ %-6s │ %-10s │%n",
            item.Id,
            truncateString(item.name.toString(), 18),
            item.birthday != null ? item.birthday.toString() : "N/A",
            truncateString(item.function != null ? item.function.name.toString() : "N/A", 12),
            truncateString(item.email.toString(), 15),
            item.start_data != null ? item.start_data.toString() : "N/A",
            item.worked_hours + "h",
            salario
        );
    }

    public static void list_collaborators(ArrayList<Collaborator> list) {
        if (list.isEmpty()) {
            System.out.println(ANSI_RED + "\nNenhum colaborador cadastrado.\n" + ANSI_RESET);
            return;
        }
        
        // Estatísticas rápidas
        long ativos = list.stream().filter(c -> c.is_active).count();
        long inativos = list.size() - ativos;
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                                           LISTA DE COLABORADORES                                                ║");
        System.out.println("╠══════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
        System.out.printf("║ Total: %s%d%s colaboradores │ Ativos: %s%d%s │ Inativos: %s%d%s                                                    ║%n",
                         ANSI_GREEN, list.size(), ANSI_RESET,
                         ANSI_GREEN, ativos, ANSI_RESET,
                         ANSI_RED, inativos, ANSI_RESET);
        System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
        
        // Cabeçalho da tabela
        System.out.println("┌──────────┬────────────────────┬────────────┬──────────────┬─────────────────┬───────────┬────────┬────────────┐");
        System.out.printf("│ %-8s │ %-18s │ %-10s │ %-12s │ %-15s │ %-9s │ %-6s │ %-10s │%n",
                         "ID", "Nome", "Nascimento", "Função", "Email", "Início", "Horas", "Salário");
        System.out.println("├──────────┼────────────────────┼────────────┼──────────────┼─────────────────┼───────────┼────────┼────────────┤");
        
        // Dados dos colaboradores
        for (Collaborator item : list) {
            if (item.is_active) {
                print_collaborator(item);
            }
        }
        
        System.out.println("└──────────┴────────────────────┴────────────┴──────────────┴─────────────────┴───────────┴────────┴────────────┘");
        
        // Mostrar inativos se existirem
        if (inativos > 0) {
            System.out.println("\n" + ANSI_RED + "COLABORADORES INATIVOS:" + ANSI_RESET);
            System.out.println("┌──────────┬────────────────────┬────────────┬──────────────┬─────────────────┬───────────┬────────┬────────────┐");
            for (Collaborator item : list) {
                if (!item.is_active) {
                    print_collaborator(item);
                }
            }
            System.out.println("└──────────┴────────────────────┴────────────┴──────────────┴─────────────────┴───────────┴────────┴────────────┘");
        }
    }
    public static int search_collaborator(ArrayList<Collaborator> list, String Id) {
        if (list == null || list.isEmpty())
            return -1;
    
        list.sort(Comparator.comparing(item -> item.Id));
        int inicio = 0;
        int fim = list.size() - 1;
    
        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int comparacao = list.get(meio).Id.compareTo(Id);
    
            if (comparacao == 0)
                return meio;
            else if (comparacao < 0)
                inicio = meio + 1;
            else
                fim = meio - 1;
        }
        return -1;
    }
    public static void update_collaborator(ArrayList<Collaborator> list) {
        System.out.print("Informe o ID do colaborador : ");
        String id = input.next();
        input.nextLine();
        int index = search_collaborator(list, id);
        if (index == -1) {
            System.out.println(ANSI_RED + "Colaborador inexistente" +ANSI_RESET);
            return;
        }

        int opc = 0;
        boolean updated = false;
        do {
            System.out.println("\n╔══════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println("║                              ACTUALIZAR COLABORADOR                                ║");
            System.out.println("╚══════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
            
            // Mostrar dados atuais
            System.out.println("\nDADOS ATUAIS:");
            System.out.println("┌──────────┬────────────────────┬────────────┬──────────────┬─────────────────┬───────────┬────────┬────────────┐");
            print_collaborator(list.get(index));
            System.out.println("└──────────┴────────────────────┴────────────┴──────────────┴─────────────────┴───────────┴────────┴────────────┘");
            
            System.out.println("\nOPÇÕES DE ACTUALIZAÇÃO:");
            System.out.println("┌──────────────────────────────────────────────────────────────────────────────────────────────────────────────┐");
            System.out.println("│ [1] Nome          │ [2] Data Nascimento │ [3] Morada        │ [4] Função        │");
            System.out.println("├───────────────────┼─────────────────────┼───────────────────┼───────────────────┤");
            System.out.println("│ [5] Email         │ [6] Data Início     │ [7] Status        │ [0] Sair          │");
            System.out.println("└───────────────────┴─────────────────────┴───────────────────┴───────────────────┘");
            System.out.print("\nEscolha uma opção: ");
            opc = add_int();

            switch (opc) {
                case 1:
                    System.out.println("Digite o novo nome: ");
                    list.get(index).name.setLength(0);
                    list.get(index).name.append(add_name());
                    System.out.println(ANSI_GREEN + "Nome actualizado com sucesso !" + ANSI_RESET);
                    updated = true;
                    break;
                case 2:
                    System.out.println("Data de aniversário (dd/MM/yyyy): ");
                    list.get(index).birthday = create_date();
                    System.out.println(ANSI_GREEN + "Data de aniversário actualizado com sucesso !" + ANSI_RESET);
                    updated = true;
                    break;
                case 3:
                    System.out.print("Digite a nova morada: ");
                    list.get(index).household.setLength(0);
                    list.get(index).household.append(input.nextLine());
                    System.out.println(ANSI_GREEN + "Morada actualizado com sucesso !" + ANSI_RESET);
                    updated = true;
                    break;
                case 4:
                    list.get(index).function.colab_assigned--;
                    list.get(index).function = select_function();
                    System.out.println(ANSI_GREEN + "função actualizado com sucesso !" + ANSI_RESET);
                    updated = true;
                    break;
                case 5:
                    System.out.print("Digite o novo email: ");
                    list.get(index).email.setLength(0);
                    list.get(index).email.append(create_email(list));
                    System.out.println(ANSI_GREEN + "Email actualizado com sucesso !" + ANSI_RESET);
                    updated = true;
                    break;
                case 6:
                    System.out.println("Data de início (dd/MM/yyyy): ");
                    list.get(index).start_data = create_date();
                    System.out.println(ANSI_GREEN + "Data de inicio actualizado com sucesso !" + ANSI_RESET);
                    updated = true;
                    break;
                case 7:
                    String statusAtual = list.get(index).is_active ? "Ativo" : "Inativo";
                    String novoStatus = !list.get(index).is_active ? "Ativo" : "Inativo";
                    list.get(index).is_active = !list.get(index).is_active;
                    System.out.println(ANSI_GREEN + "Status alterado de " + statusAtual + " para " + novoStatus + " com sucesso!" + ANSI_RESET);
                    updated = true;
                    break;
                case 0:
                    System.out.println("\n" + ANSI_GREEN + "Saindo da actualização..." + ANSI_RESET);
                    break;
                default:
                    System.out.println("\n" + ANSI_RED + "Opção inválida! Escolha entre 0-7." + ANSI_RESET);
            }
        } while (opc != 0);

        if (updated) {
            save_all_collaborators_to_json_file(list, "files/collaborators.json");
            System.out.println("\n" + ANSI_GREEN + "Todas as alterações foram salvas com sucesso!" + ANSI_RESET);
        }
    }

    // Salva toda a lista de colaboradores no arquivo JSON (atualização robusta)
    public static void save_all_collaborators_to_json_file(ArrayList<Collaborator> list, String filePath) {
        JSONArray jsonArray = new JSONArray();
        for (Collaborator c : list) {
            JSONObject obj = new JSONObject();
            obj.put("Id", c.Id);
            obj.put("name", c.name.toString());
            obj.put("birthday", c.birthday != null ? c.birthday.toString() : "");
            obj.put("household", c.household.toString());
            obj.put("function_id", c.function != null ? c.function.id : 0);
            obj.put("email", c.email.toString());
            obj.put("start_data", c.start_data != null ? c.start_data.toString() : "");
            obj.put("is_active", c.is_active);
            jsonArray.put(obj);
        }
        try {
            java.nio.file.Files.write(Paths.get(filePath), jsonArray.toString(4).getBytes());
        } catch (java.io.IOException e) {
            System.out.println(ANSI_RED + "Erro ao salvar o arquivo JSON de colaboradores: " + e.getMessage() + ANSI_RESET);
        }
    }
    public static void generate_salary(ArrayList<Collaborator> list)
    {
        if (list.isEmpty()) {
            System.out.println(ANSI_RED + "Não existem colaboradores cadastrados." + ANSI_RESET);
            return;
        }
        double desconto_hora = 0;
        for(Collaborator item : list)
        {
            int horas_extras = item.worked_hours - item.function.expected_hours;
            if (horas_extras < 0)
               desconto_hora = (horas_extras * item.function.salary*0.02); 
            else
               desconto_hora = (horas_extras * item.function.salary*0.002);
            item.net_salary = item.function.salary + desconto_hora
                    - (item.function.absent_discount * item.fouls) + item.function.bonus; 
        }
        System.out.println(ANSI_GREEN + "Salários Gerados com sucesso!" + ANSI_RESET);
    }
    // Método auxiliar para truncar strings longas
    private static String truncateString(String str, int maxLength) {
        if (str == null) return "N/A";
        if (str.length() <= maxLength) return str;
        return str.substring(0, maxLength - 3) + "...";
    }
    
    // Remove método duplicado e problemático save_new_collaborator_to_json_file
    
}   