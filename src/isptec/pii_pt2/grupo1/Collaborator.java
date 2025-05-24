package isptec.pii_pt2.grupo1;
import static isptec.pii_pt2.grupo1.Address.create_new_address;
import static isptec.pii_pt2.grupo1.Function.select_function;
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

    public  void register_collaborator(ArrayList<Collaborator> list)
    {
        Collaborator novo = new Collaborator();
        System.out.println("------------Dados Pessoais-----------");
        System.out.print("Digite o nome: ");
        input.nextLine();
        novo.name.append(input.nextLine());
        novo.birthday = create_birthday();
        novo.email.append(creat_email(list));
        System.out.println();
        novo.function = select_function();
        System.out.println();
        novo.household = create_new_address();
        novo.start_data = LocalDate.now();
        novo.Id = gerador_id(novo.name.toString(), novo.birthday.getDayOfMonth(),novo.start_data.getYear(), novo.birthday.getMonthValue());
        list.sort(Comparator.comparing(item -> item.name));
        list.add(novo);
    }
    
    public static LocalDate create_birthday() 
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
    
    public static String creat_email(ArrayList<Collaborator> list)
    {
        String email_ = new String();
        System.out.print("Digite o seu email: ");
        do{
           if(!validate_email(email_, list))
               System.out.print("Digite um email válido: ");
                email_ = input.next();
        }while(!validate_email(email_, list));
        return (email_);
    }
    
    public static void disable_collaborator(String nome, ArrayList<Collaborator> list)
    {
            int index = search_collaborator(list, nome);
            if(index != -1){
                list.get(index).is_active = false;
                System.out.print("Funcionario desativado com exitos");
                return ;
            }
            else
                System.out.println("Nâo eexistem nenhum colaborador com este nome!");
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
       input.nextLine();
       int opc = input.nextInt();
       
       switch(opc){
           case 1:
               update_name(list, index);
               System.out.println("Nome actualizado com sucesso !");
               break;
           case 2:
               list.get(index).birthday = create_birthday();
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
               creat_email(list);
               System.out.println("Email actualizado com sucesso !");
               break;
           case 6:
               
       }
    }
            
}   