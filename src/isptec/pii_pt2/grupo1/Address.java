package isptec.pii_pt2.grupo1;
import static isptec.pii_pt2.grupo1.Utils.add_int;
import static isptec.pii_pt2.grupo1.Utils.add_name;
import static isptec.pii_pt2.grupo1.Utils.input;
/**
 *
 * @author lucio
 * @author jofre
 */
public class Address {
    static StringBuilder street = new StringBuilder();
    static StringBuilder country = new StringBuilder();
    static StringBuilder city = new StringBuilder();
    static int number_house;

    public static Address create_address(String name, int number, String city, String country)
    {
            Address address = new Address();
            address.street.setLength(0);
            address.street.append(name);
            address.number_house = number;
            address.city.setLength(0);
            address.city.append(city);
            address.country.setLength(0);
            address.country.append(country);
            return address;
    }
    public static Address create_new_address() {
        
        System.out.println("------------Endereço-----------");
        System.out.print("Digite o nome da rua: ");
        String name = input.nextLine();
        System.out.print("Digite o número da casa: ");
        int number = add_int();
        System.out.print("Digite a cidade: ");
        String city = add_name();
        System.out.print("Digite o país: ");
        String country = add_name();
        System.out.println("Adicionado !");
        return create_address(name, number, city, country);
    }   
}