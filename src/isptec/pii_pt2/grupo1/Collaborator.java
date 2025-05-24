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
        StringBuilder email_ = new StringBuilder();
        do{
        System.out.print("Digite o seu email: ");
        email_.append(input.next());
        }while(!validate_email(email_, list));
        
        novo.email.append(email_);
        
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
            
        System.out.print("Data de nascimento (dd/MM/yyyy) : ");
        data = input.next();
        date = LocalDate.parse(data, formato);
        
        }catch(DateTimeParseException e)
        {
            System.out.print("Erro de fomatação de data !");
        }
        return (date);
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
    public static int  search_collaborator(ArrayList<Collaborator> list, String name)
    {
        int meio = list.size() / 2; 
        int inicio = 0;
        int fim = list.size();
        
        while(inicio <= fim)
        {
            if(list.get(meio).name.toString().compareTo(name) < 0)
                inicio = meio + 1;
            else
                fim = meio - 1;
            meio = (inicio + fim)/2;
        }
        
        if(list.get(meio).name.toString().equals(name))
            return (meio);
        else
            return (-1);
        
    }
}   