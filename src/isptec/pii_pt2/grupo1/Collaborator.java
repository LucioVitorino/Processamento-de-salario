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
    Scanner input_colab =  new Scanner(System.in);
    
    public  Collaborator create_collaborator ()
    {
        Collaborator novo = new Collaborator();
        System.out.println("------------Dados Pessoais-----------");
        System.out.print("Digite o nome: ");
        novo.name.append(input_colab.next());
        novo.birthday = create_birthday();
        String email_ = new String();
        do{
        System.out.print("Digite o seu email: ");
        //Validar email
        email_ = input_colab.next();
        }while(!validate_email(email_));
        
        novo.email.append(email_);
        
        System.out.println();
        novo.function = select_function();
        
        System.out.println();
        novo.household = create_new_address();
        start_data = LocalDate.now();
        return novo;
    }
    
    public boolean validate_email(String email)
    {
        //Validar....
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, email);
    }

    public static LocalDate create_birthday() {
         Scanner input_date = new Scanner(System.in);
         DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         String data = new String();
         LocalDate date = LocalDate.now();
         
        try{
            
        System.out.print("Data de nascimento (dd/MM/yyyy) : ");
        data = input_date.next();
        date = LocalDate.parse(data, formato);
        
        }catch(DateTimeParseException e){
            System.out.print("Erro de fomatação de data !");
        }
        return (date);
    }
}
