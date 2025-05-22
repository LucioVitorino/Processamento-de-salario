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

    public static Function select_function()
        {
            System.out.println("------Escolha a Funçao------");
            for (int i = 0; i < functions_list.size(); i++)
            {
                System.out.println(i + " - " + functions_list.get(i).name);
            }
            System.out.println("Escolha uma funçao: ");
            //Validar se o número digitado é válido
             
            int choose = input.nextInt();
            return functions_list.get(choose);
        }
}
