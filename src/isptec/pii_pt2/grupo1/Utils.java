package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Function.list_functions;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
/**
 * 
 * @author jofre
 */
public class Utils {
 static Scanner input = new Scanner(System.in);

  public static boolean validate_email(String email, ArrayList<Collaborator> list)
    {
        for(Collaborator item: list)
        {
            if(email.equals(item.email.toString()))
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

  public static Integer validate_choose(ArrayList<Function> functions_list)
  {
            list_functions();
             System.out.println("Escolha uma funçao: ");
             do{
                if (input.hasNextInt() == false)
                {
                    System.out.println("Digite um número válido");
                    //input.nextInt();
                }
            }while(input.hasNextInt() == false );
            int id = input.nextInt();
            for (int i = 0; i < functions_list.size(); i++) 
                if (functions_list.get(i).id == id) 
                    return i;
            System.out.println("Função não encontrada, tente novamente.");
            return null;
        }
        public static String capitalize(String str) {
            String[] words = str.split(" ");
            StringBuilder capitalized = new StringBuilder();
            for (String word : words) {
                if (word.length() > 0) {
                    capitalized.append(Character.toUpperCase(word.charAt(0)))
                               .append(word.substring(1).toLowerCase())
                               .append(" ");
                }
            }
            return capitalized.toString().trim();
        }
      public static String add_name()
      {
          String name = new String();
          do{
              input.nextLine();
              name = input.next();
              //input.nextLine();
              if(!validate_name(name))
              {
                  System.out.println("Digite um nome válido");
              }
          }while(!validate_name(name));
          return capitalize(name);
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
      
      public static String gerador_id(String nome, int day_birthday, int year_start, int month_birthday, int cont)
      {
          String words[] = nome.toString().split(" ");
         
          String id  = "" + year_start + words[0].charAt(0) + words[words.length - 1].charAt(0) + day_birthday + month_birthday +"_"+cont ;
   
          return(id);
      }
}
