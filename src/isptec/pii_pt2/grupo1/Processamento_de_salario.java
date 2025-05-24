package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Collaborator.disable_collaborator;
import static isptec.pii_pt2.grupo1.Function.new_function;
import static isptec.pii_pt2.grupo1.Utils.input;

import java.util.ArrayList;
/**
 *
 * @author lucio
 * @author jofre
 * @author kialenguluka
 */
public class Processamento_de_salario {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
      ArrayList<Collaborator> colaboradores_list = new ArrayList<Collaborator>();
        Collaborator colab = new Collaborator();
        new_function();
        colab.register_collaborator(colaboradores_list);
        input.nextLine();
        disable_collaborator(input.nextLine(), colaboradores_list);
      
    }
    
}
