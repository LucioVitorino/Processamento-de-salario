/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package isptec.pii_pt2.grupo1;

import static isptec.pii_pt2.grupo1.Function.validate_name;

import java.util.Scanner;

/**
 *
 * @author lucio
 */
public class Address {
    static StringBuilder street = new StringBuilder();
    static StringBuilder country = new StringBuilder();
    static StringBuilder city = new StringBuilder();
    static int number_house;
    static Scanner input = new Scanner(System.in);
    public static Address create_new_address( )
    {
       Address new_address = new Address();
        System.out.println("-----PREENCHENDO O ENDEREÇO----");
        System.out.print("País: ");
        new_address.country.append(add_name());
        System.out.print("Cidade: ");
         new_address.city.append(add_name());
        System.out.print("Rua: ");
         new_address.street.append(add_name());
        System.out.print("Numero da casa: ");
         new_address.number_house = input.nextInt();
        return new_address;
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
