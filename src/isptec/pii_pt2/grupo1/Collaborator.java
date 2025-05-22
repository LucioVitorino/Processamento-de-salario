/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Address.create_new_address;
import static isptec.pii_pt2.grupo1.Function.select_function;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 *
 * @author lucio
 */
public class Collaborator {
    int number;
    StringBuilder name = new StringBuilder();
    LocalDate birthday;
    Address household = new Address();
    Function function = new Function();
    StringBuilder email = new StringBuilder();
    LocalDate start_data;
    boolean is_active = true;
    Scanner input_colab =  new Scanner(System.in);
    
    public  Collaborator register_collaborator (ArrayList<Collaborator> list)
    {
        Collaborator novo = new Collaborator();
        System.out.println("------------Dados Pessoais-----------");
        System.out.print("Digite o nome: ");
        novo.name.append(input_colab.next());
        novo.birthday = create_birthday();
        StringBuilder email_ = new StringBuilder();
        do{
        System.out.print("Digite o seu email: ");
        //Validar email
        email_.append(input_colab.next());
        }while(!validate_email(email_, list));
        
        novo.email.append(email_);
        
        System.out.println();
        novo.function = select_function();
        
        System.out.println();
        novo.household = create_new_address();
        start_data = LocalDate.now();
        list.add(novo);
        return novo;
    }
    
    public boolean validate_email(StringBuilder email, ArrayList<Collaborator> list)
    {
        //Validar....
        for(Collaborator item: list)
        {
            if(!email.toString().equals(item.toString()))
                return (false);
        }
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, email);
    }

    public static LocalDate create_birthday() 
    {
         Scanner input_date = new Scanner(System.in);
         DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         String data = new String();
         LocalDate date = LocalDate.now();
         
        try{
            
        System.out.print("Data de nascimento (dd/MM/yyyy) : ");
        data = input_date.next();
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
        System.out.println("Nome\t\t\tEmail");
        for(Collaborator item : list){
            System.out.print(item.name);
            System.out.print("\t\t\t");
            System.out.println(item.email);
        }
    }
}
