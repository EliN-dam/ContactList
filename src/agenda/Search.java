package agenda;

import java.util.InputMismatchException;
import utils.Console;
import utils.IO;

/**
 * Class for searching objects by some criteria in the collection.
 * @author zelda
 */
public class Search {
    
    CRUD<Contact> contacts;
    
    public Search(CRUD<Contact> contacts){
        this.contacts = contacts;
        this.menu(IO.loadMenu("data\\buscar.txt"));
    }
    
    /**
     * Agenda search menu.
     */
    public void menu(String[] menuOptions){
        byte option = 0;
        byte exit = (byte)(menuOptions.length - 1);
        do {
            Console.showMenu(menuOptions, true);
            try {
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opción: ", "byte");
                System.out.println();
                if (Console.inRange(option, 1, exit)){
                    if (option != exit){
                        String value = this.getSearchValue(option);
                        this.contacts.searchElement(option, value);
                        Console.toContinue();
                    }
                } else
                    System.out.println("La opción seleccionada no "
                                + "existe, inténtelo de nuevo... " + Console.EOF);
            } catch (InputMismatchException e) {
                System.out.println(Console.EOF + "Opción no válida, intente lo de nuevo..." + Console.EOF);
            }
        } while (option != exit);
    }
    
    /**
     * Request the user the value for the search criteria.
     * @param criteria Criteria for searching in the contact list.
     * @return The value for the search.
     */
    public String getSearchValue(int option){
        String value;
        switch(option){
            case 1:
                value = Console.validString("Escribe el DNI del contacto que desea "
                        + "buscar: ", 9, 9);
                break;
            case 2:
                value = Console.readLine("Escribe el nombre del contacto que "
                        + "desea buscar: ").trim();
                break;
            case 3:
                value = Console.readLine("Escribe los apellido/s del contacto que "
                        + "desea buscar: ").trim();
                break;
            case 4:
                value = Console.readLine("Escribe el correo eléctronico que desea "
                    + "buscar: ").trim();
                break;
            case 5:
                value = String.valueOf(Console.validInt("Escribe el teléfono que "
                        + "desea buscar: ", 100000000, 999999999)).trim();
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + option);
        }
        System.out.println();
        return value;
    }
}