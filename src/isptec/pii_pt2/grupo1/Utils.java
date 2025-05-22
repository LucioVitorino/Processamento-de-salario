package isptec.pii_pt2.grupo1;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
/**
 * 
 * @author jofre
 */
public class Utils {
 static Scanner input = new Scanner(System.in);

  public static boolean validate_email(StringBuilder email, ArrayList<Collaborator> list)
    {
        for(Collaborator item: list)
        {
            if(email.toString().equals(item.email.toString()))
                return (false);
        }
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return Pattern.matches(regex, email);
    }
    
  public static boolean validate_name(String name)
  {
      //Validar nome
      String regex = "^[a-zA-ZçÇáéíóúàèìòùÁÉÍÓÚÀÈÌÒÙãõÃÕâêîôûÂÊÎÔÛ\\s]+$";
      if (name.isEmpty())
          return false;
      return Pattern.matches(regex, name);
  }

  public static int validate_choose(ArrayList<Function> functions_list)
  {
      System.out.println("Lista Funçãoes----\n");
      //Listar as funções
      for (int i = 0; i < functions_list.size(); i++)
            {
                System.out.println(i + " - " + functions_list.get(i).name);
            }
            System.out.println("Escolha uma funçao: ");
             do{
                if (input.hasNextInt() == false)
                {
                    System.out.println("Digite um número válido");
                    input.next();
                }
            }while(input.hasNextInt() == false );
            int choose = input.nextInt();
            while (choose < 0 || choose >= functions_list.size())
            {
                System.out.println("Digite um número válido e que existe na lista");
                //Validar se o número digitado é válido
                do{
                    if (input.hasNextInt() == false)
                    {
                        System.out.println("Digite um número válido");
                        input.next();
                    }
                }while(input.hasNextInt() == false );
                choose = input.nextInt();
            }
            System.out.println("Você escolheu a função: " + functions_list.get(choose).name);
            return choose;
   
      }
      public static String add_name()
      {
          String name = new String();
          do{
              name = input.next();
              if(!validate_name(name))
              {
                  System.out.println("Digite um nome válido");
              }
          }while(!validate_name(name));
          return name;
      }
      public static int add_int() {
          int numero = 0;
          boolean valido = false;
          while (!valido) {
              try {
                  numero = input.nextInt();
                  valido = true;
              } catch (Exception e) {
                  System.out.println("Digite um número válido");
                  input.next();
              }
          }
          return numero;
      }
}
