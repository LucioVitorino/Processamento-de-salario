package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Address.create_new_address;
import static isptec.pii_pt2.grupo1.Function.select_function;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import static isptec.pii_pt2.grupo1.Utils.add_name;
import static isptec.pii_pt2.grupo1.Utils.gerador_id;
import static isptec.pii_pt2.grupo1.Utils.input;
import static isptec.pii_pt2.grupo1.Utils.validate_email;
import java.util.Scanner;

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
        novo.name.append(add_name());
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
        list.add(novo);
    }
    
    public static LocalDate create_birthday() 
    {
         Scanner input = new Scanner(System.in);
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
        for(Collaborator item : list)
        {
            if(nome.equals(item.name.toString())){
                item.is_active = false;
                return ;
            }
        }
    }
    
    public static void print_collaborator(ArrayList<Collaborator> list)
    {
        System.out.println("Nome\t\t\tEmail\t\t\tID");
        for(Collaborator item : list){
            System.out.print(item.name);
            System.out.print("\t\t\t");
            System.out.println(item.email);
            System.out.print("\t\t\t");
            System.out.print(item.Id);
        }
    }
}
