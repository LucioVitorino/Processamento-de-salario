/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Collaborator.create_birthday;
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
        // TODO code application logic here
        Collaborator colab = new Collaborator();
        colab.create_collaborator();
        
        System.out.println("Nome: " + colab.name);
        System.out.println("Data de nascimento: " + colab.birthday);
        System.out.println("Email: " + colab.email);
        System.out.println("Função: " + colab.function.name);
        System.out.println("Endereço: " + colab.household.street);
    }
    
}
