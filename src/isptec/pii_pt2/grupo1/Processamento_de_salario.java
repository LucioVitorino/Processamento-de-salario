/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Collaborator.create_birthday;
import static isptec.pii_pt2.grupo1.Function.new_function;

import java.time.LocalDate;

/**
 *
 * @author lucio
 * @author jofre
 */
public class Processamento_de_salario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Collaborator colab = new Collaborator();
        new_function();
        new_function();
        colab = colab.create_collaborator();
        System.out.println("------------Dados do Colaborador-----------");
        System.out.println("Nome: " + colab.name.toString());
        System.out.println("Data de nascimento: " + colab.birthday.getYear() + "-" + colab.birthday.getMonthValue() + "-" + colab.birthday.getDayOfMonth());
        System.out.println("Email: " + colab.email.toString());
        System.out.println("Função: " + colab.function.name.toString());
        System.out.println("Endereço: " + colab.household.number_house + ", " + colab.household.street.toString() + ", " + colab.household.city.toString() + ", " + colab.household.country.toString());
    }
    
}
