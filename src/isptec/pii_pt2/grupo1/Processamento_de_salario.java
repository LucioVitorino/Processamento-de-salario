/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Collaborator.create_birthday;
import static isptec.pii_pt2.grupo1.Collaborator.print_collaborator;
import static isptec.pii_pt2.grupo1.Function.new_function;

import java.time.LocalDate;
import java.util.ArrayList;

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
      ArrayList<Collaborator> colaboradores_list = new ArrayList<Collaborator>();
        Collaborator colab = new Collaborator();
        new_function();
        new_function();
        colab.register_collaborator(colaboradores_list);
        colab.register_collaborator(colaboradores_list);
        print_collaborator(colaboradores_list);
    }
    
}
