/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Address.create_new_address;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author lucio
 */
public class Collaborator {
    int number;
    StringBuilder name = new StringBuilder();
    localDate birthday = new Date();
    Address household = new Address();
    Function function = new Function();
    StringBuilder email = new StringBuilder();
    Date start_data = new Date();
    Scanner input_colab =  new Scanner(System.in);
    
    public  Collaborator create_collaborator ()
    {
        Collaborator novo = new Collaborator();
        System.out.println("------------Dados Pessoais-----------");
        System.out.print("Digite o nome: ");
        novo.name.append(input_colab.next());
        novo.birthday = create_birthday();
        start_data.getTime();
        
        System.out.print("Digite o seu email: ");
        String email_ = validate_email(input_colab.next());
        novo.email.append(email_);
        
        System.out.println();
        novo.function = select_function();
        
        System.out.println();
        novo.household = create_new_address();
        return novo;
    }
    
    public String validate_email(String email)
    {
        //Validar....
        return email;
    }

    private Date create_birthday() {
        Date new_birth =  new Date();
        System.out.println("Digite a da de nascimento:  ");
        System.out.print("Dia: ");
        new_birth.setDate(input_colab.nextInt());
        System.out.print("Mes: ");
        new_birth.setMonth(input_colab.nextInt());
        System.out.print("Ano: ");
        new_birth.setYear(input_colab.nextInt());
        System.out.println();
        return new_birth;
    }
}
