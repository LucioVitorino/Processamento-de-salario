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
    ArrayList<Function> functions_list = new ArrayList<Function>();
    Scanner input = new Scanner(System.in);
   public Function select_function()
    {
        System.out.println("------Escolha a Funçao------");
      
        int choose = input.nextInt();
        return chosen_Funtion(choose);
    }

    private Function chosen_Funtion(int choose)
    {
            
    }
}
