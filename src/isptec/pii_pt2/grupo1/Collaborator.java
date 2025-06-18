package isptec.pii_pt2.grupo1;
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
    int worked_days;
    int extra_hours;
    int fouls;
    StringBuilder license;
    boolean is_active = true;
    double net_salary;
    
    public static void register_collaborator(ArrayList<Collaborator> list)
    {
        Collaborator novo = new Collaborator();
        System.out.println("------------Dados Pessoais-----------");
        System.out.print("Digite o nome: ");
        novo.name.append(add_name());
        novo.birthday = create_date();
        novo.email.append(create_email(list));
        System.out.println();
        do{
        novo.function = select_function();
        }while(novo.function == null);
        System.out.println();
        novo.household.append(input.nextLine());
        novo.start_data = LocalDate.now();
        novo.Id = gerador_id(novo.name.toString(), novo.birthday.getDayOfMonth(),
                novo.start_data.getYear(), novo.birthday.getMonthValue(),list.size());
        list.add(novo);
    }

    public static Collaborator read_collaborator_from_json(JSONObject json) {
        Collaborator colaborador = new Collaborator();
        colaborador.Id = json.getString("Id");
        colaborador.name.append(json.getString("name"));
        colaborador.birthday = LocalDate.parse(json.getString("birthday"));
        colaborador.household.append(json.getString("household"));
        colaborador.function = get_function_by_id(json.getInt("function_id"));
        colaborador.email.append(json.getString("email"));
        colaborador.start_data = LocalDate.parse(json.getString("start_data"));
        colaborador.is_active = json.getBoolean("is_active");
        return colaborador;
    }
    
    public static void read_collaborators_from_json_file(ArrayList<Collaborator> list, String filePath){
        try {
            String content = new String(readAllBytes(Paths.get(filePath)));
           JSONArray jsonArray = new JSONArray(content);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Collaborator colaborador = read_collaborator_from_json(jsonObject);
                list.add(colaborador);
            }
        } catch (java.io.IOException e) {
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
                System.out.print(list.get(index).name + " desativado com Sucesso!");
                return ;
            }
            else
                System.out.println("Não existe nenhum colaborador com este id!");
    }
    
    public static void print_collaborator(Collaborator item)
    {
        System.out.println("-----"+item.Id+"-----");
        System.out.println("1 - Nome: " + item.name);
        System.out.println("2 - Data de Aniversário: " + item.birthday.getDayOfMonth() + "/" 
                + item.birthday.getMonthValue() + "/" + item.birthday.getYear());
        System.out.println("3 - Morada: " + item.household);
        System.out.println("4 - Função: " + item.function.name);
        System.out.println("5 - Email: " + item.email);
        System.out.println("6 - Data de Início: " + item.start_data.getDayOfMonth() + "/" 
                + item.start_data.getMonthValue() + "/" + item.start_data.getYear());
        System.out.println("7 - Status: " + (item.is_active ? "Activo" : "Inactivo"));
    }
    public static void list_collaborators(ArrayList<Collaborator> list)
    {
        for(Collaborator item : list)
        {
            if(item.is_active)
                print_collaborator(item);
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
    
    public static void  update_collaborator(ArrayList<Collaborator> list)
    {
       System.out.print("Informe o ID do colaborador : ");
       int index = search_collaborator(list, input.next());
       input.nextLine();
       if(index == -1)
       {
           System.out.println("Colaborador inexistente");
              return;
       }
  
       int opc = 0;
       do{
       print_collaborator(list.get(index));
       System.out.println("0 - Sair");
       System.out.println("Qual informação deseja actualizar ? : ");
       opc = add_int();
       
       switch(opc){
           case 1:
               list.get(index).name.setLength(0);
               list.get(index).name.append(add_name());
               System.out.println("Nome actualizado com sucesso !");
               break;
           case 2:
               list.get(index).birthday = create_date();
               System.out.println("Data de aniversário actualizado com sucesso !");
               break;
           case 3:
                list.get(index).household.setLength(0);
               list.get(index).household.append(input.nextLine());
               System.out.println("Morada actualizado com sucesso !");
               break;
           case 4:
               list.get(index).function = select_function();
               System.out.println("função actualizado com sucesso !");
               break;
           case 5:
               list.get(index).email.setLength(0);
               list.get(index).email.append(create_email(list));
               System.out.println("Email actualizado com sucesso !");
               break;
           case 6:
               list.get(index).start_data = create_date();
               System.out.println("Data de inicio actualizado com sucesso !");
               break;
           case 7:
               if(list.get(index).is_active)
                   list.get(index).is_active = false;
               else
                   list.get(index).is_active = true;
                System.out.println("Status actualizado com sucesso !");
           case 0:
               break;
           default:System.out.println("Digite uma Opção válida!");
        } 
       }while(opc != 0);
    }
}   