package isptec.pii_pt2.grupo1;
import static isptec.pii_pt2.grupo1.Address.create_new_address;
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

/**
 *
 * @author lucio
 * @author jofre
 */
public class Collaborator {
    int number;
    StringBuilder name = new StringBuilder();
    LocalDate birthday;
    Address household = new Address();
    Function function = new Function();
    StringBuilder email = new StringBuilder();
    String Id = new String();
    LocalDate start_data;
    boolean is_active = true;

    public static void register_collaborator(ArrayList<Collaborator> list)
    {
        Collaborator novo = new Collaborator();
        System.out.println("------------Dados Pessoais-----------");
        System.out.print("Digite o nome: ");
        input.nextLine();
        novo.name.append(add_name());
        novo.birthday = create_date();
        novo.email.append(create_email(list));
        System.out.println();
        novo.function = select_function();
        System.out.println();
        novo.household = create_new_address();
        novo.start_data = LocalDate.now();
        novo.Id = gerador_id(novo.name.toString(), novo.birthday.getDayOfMonth(),
                novo.start_data.getYear(), novo.birthday.getMonthValue(),list.size());
        list.add(novo);
    }
    
    public static LocalDate create_date() 
    {
         DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         String data = new String();
         LocalDate date = LocalDate.now();
         
        try{
            
        System.out.print("Informe a data (dd/MM/yyyy) : ");
        data = input.next();
        date = LocalDate.parse(data, formato);
        
        }catch(DateTimeParseException e)
        {
            System.out.print("Erro de fomatação de data !");
        }
        return (date);
    }
    
    public static String create_email(ArrayList<Collaborator> list)
    {
        String email_ = new String();
        System.out.print("Digite o seu email: ");
        do{
            email_ = input.next();
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
                System.out.print("Funcionario desativado com exitos");
                return ;
            }
            else
                System.out.println("Nâo eexistem nenhum colaborador com este nome!");
    }
    
    public static void print_collaborator(Collaborator item)
    {
        System.out.println("=============="+item.Id+"================");
        System.out.println("1 - Nome: " + item.name);
        System.out.println("2 - Data de Aniversário: " + item.birthday.getDayOfMonth() + "/" 
                + item.birthday.getMonthValue() + "/" + item.birthday.getYear());
        System.out.println("3 - Morada: \n" + item.household.number_house + " " + item.household.street + ", " 
                + item.household.city + ", " + item.household.country);
        System.out.println("4 - Função: " + item.function.name);
        System.out.println("5 - Email: " + item.email);
        System.out.println("6 - Data de Início: " + item.start_data.getDayOfMonth() + "/" 
                + item.start_data.getMonthValue() + "/" + item.start_data.getYear());
        System.out.println("Ativo: " + (item.is_active ? "Sim" : "Não"));
    }
    public static void list_collaborators(ArrayList<Collaborator> list)
    {
        for(Collaborator item : list)
        {
            if(item.is_active)
                print_collaborator(item);
        }
    }
    public static int  search_collaborator(ArrayList<Collaborator> list, String Id)
    {
        list.sort(Comparator.comparing(item -> item.Id));
        int meio = list.size() / 2; 
        int inicio = 0;
        int fim = list.size();
        
        while(inicio <= fim)
        {
            if(list.get(meio).Id.toString().compareTo(Id) < 0)
                inicio = meio + 1;
            else
                fim = meio - 1;
            meio = (inicio + fim)/2;
        }
        
        if(list.get(meio).Id.toString().equals(Id))
            return (meio);
        else
            return (-1);
        
    }
    
    public static void  update_collaborator(ArrayList<Collaborator> list)
    {
       System.out.print("Informe o ID do colaborador : ");
       int index = search_collaborator(list, input.nextLine());
       if(index == -1)
       {
           System.out.println("Colaborador inexistente");
           return ;
       }
  
       int opc = 0;
       do{
       print_collaborator(list.get(index));
       System.out.println("0- Sair");
       System.out.println("Qual informação deseja actualizar ? : ");
       input.nextLine();
       opc = add_int();
       
       switch(opc){
           case 1:
               list.get(index).name.append(add_name());
               System.out.println("Nome actualizado com sucesso !");
               break;
           case 2:
               list.get(index).birthday = create_date();
               System.out.println("Data de aniversário actualizado com sucesso !");
               break;
           case 3:
               list.get(index).household = create_new_address();
               System.out.println("Morada actualizado com sucesso !");
               break;
           case 4:
               list.get(index).function = select_function();
               System.out.println("função actualizado com sucesso !");
               break;
           case 5:
               create_email(list);
               System.out.println("Email actualizado com sucesso !");
               break;
           case 6:
               list.get(index).start_data = create_date();
               System.out.println("Data de inicio actualizado com sucesso !");
               break;
           case 0:
               break;
           default:System.out.println("Digite uma Opção válida!");
        }     
               
       }while(opc != 0);
    }
}   