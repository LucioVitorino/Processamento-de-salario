/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isptec.pii_pt2.grupo1;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author lucio
 */
public class Function {
    
   static ArrayList<Function> functions_list = new ArrayList<Function>();
    static Scanner input = new Scanner(System.in);
    StringBuilder name = new StringBuilder();
    double salary;
    double bonus;
    int id;
    int colab_assigned;

    public static void create_function(String name, double salary, double bonus)
    {
        Function function = new Function();
        function.name.append(name);
        function.salary = salary;
        function.bonus = bonus;
        function.id = functions_list.size() + 1;
        function.colab_assigned = 0;
        functions_list.add(function);
    }
    public static void new_function()
    {
        System.out.println("-----Criando nova função-----");
        System.out.println("Digite o nome da função: ");
        String name = input.next();
        System.out.println("Digite o salário: ");
        double salary = input.nextDouble();
        System.out.println("Digite o bônus: ");
        double bonus = input.nextDouble();
        create_function(name, salary, bonus);
        System.out.println("Função criada com sucesso!");
    }
    public static Function select_function()
        {
            System.out.println("------Escolha a Funçao------");
            for (int i = 0; i < functions_list.size(); i++)
            {
                System.out.println(i + " - " + functions_list.get(i).name);
            }
            System.out.println("Escolha uma funçao: ");
            //Validar se o número digitado é válido
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
            functions_list.get(choose).colab_assigned++;
            return functions_list.get(choose);
        }

    public static void delete_function()
    {
        System.out.println("------Removendo Função------");
        if (functions_list.isEmpty())
        {
            System.out.println("Não existem funções cadastradas");
            return;
        }
        System.out.println("------Escolha a Funçao------");
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
            do{
                if (input.hasNextInt() == false)
                {
                    System.out.println("Digite um número válido");
                    input.next();
                }
            }while(input.hasNextInt() == false );
            choose = input.nextInt();
        }
        if(functions_list.get(choose).colab_assigned > 0)
        {
            System.out.println("Essa função não pode ser removida, pois existem colaboradores associados a ela");
            return;
        }
        functions_list.remove(choose);
        System.out.println("Função removida com sucesso!");
    }
    public static void list_functions()
    {
        System.out.println("------Lista de Funções------");
        for (int i = 0; i < functions_list.size(); i++)
        {
            System.out.println(i + " - " + functions_list.get(i).name);
        }
    }
}
