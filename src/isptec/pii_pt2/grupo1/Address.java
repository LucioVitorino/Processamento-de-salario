package isptec.pii_pt2.grupo1;
import static isptec.pii_pt2.grupo1.Utils.add_int;
import static isptec.pii_pt2.grupo1.Utils.add_name;
import static isptec.pii_pt2.grupo1.Utils.input;


/**
 *
 * @author lucio
 */
public class Address {
    static StringBuilder street = new StringBuilder();
    static StringBuilder country = new StringBuilder();
    static StringBuilder city = new StringBuilder();
    static int number_house;
    public static Address create_new_address( )
    {
       Address new_address = new Address();
        System.out.println("-----PREENCHENDO O ENDEREÇO----");
        System.out.print("País: ");
        new_address.country.append(add_name());
        System.out.print("Cidade: ");
         new_address.city.append(add_name());
        System.out.print("Rua: ");
         new_address.street.append(input.next());
        System.out.print("Numero da casa: ");
         new_address.number_house = add_int();
        return new_address;
    }
}