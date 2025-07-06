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
    // Códigos ANSI para cores no terminal
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
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
      // Validar nome: permite letras (maiúsculas/minúsculas), espaços e acentos Unicode
      String regex = "^[\\p{L}\\s'-]+$";
      if (name == null || name.trim().isEmpty())
          return false;
      return Pattern.matches(regex, name.trim());
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
            input.nextLine();
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
              name = input.nextLine();
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
                  input.nextLine();
                  valido = true;
              } catch (Exception e) {
                  System.out.println("Digite um número válido");
                  input.next();
              }
          }
          return numero;
      }
      
      // Gera um ID ainda mais curto: 2 letras do nome + último dígito do ano + último dígito do mês + 3 do seq
      public static String gerador_id(String nome, int day_birthday, int year_start, int month_birthday, int cont)
      {
          String nomeLimpo = nome.replaceAll("\\s+", "").toUpperCase();
          String prefixo = nomeLimpo.length() >= 2 ? nomeLimpo.substring(0, 2) : nomeLimpo;
          String anoStr = String.valueOf(year_start % 10); // último dígito do ano
          String mesStr = String.valueOf(month_birthday % 10); // último dígito do mês
          String seqStr = String.format("%03d", (cont + 1) % 1000);
          return prefixo + anoStr + mesStr + seqStr;
      }
      
    // Limpa a tela no terminal (funciona na maioria dos terminais)
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Se não conseguir limpar, apenas segue
        }
    }

    // Pausa aguardando ENTER do usuário (equivalente ao getchar())
    public static void pause() {
        System.out.println("\nPressione ENTER para continuar...");
        try {
            System.in.read();
            if (System.in.available() > 0) System.in.skip(System.in.available());
        } catch (Exception e) {
            // ignora
        }
    }
}
