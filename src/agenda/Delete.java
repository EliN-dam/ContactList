package agenda;

import java.io.Serializable;
import java.util.InputMismatchException;
import utils.Console;
import utils.IO;

/**
 * Class for erasing items from the collection.
 * @author zelda
 */
public class Delete {
    
    CRUD<Contact> contacts;
    
    public Delete(CRUD<Contact> contacts){
        this.contacts = contacts;
        this.menu(IO.loadMenu("data\\eliminar.txt"));
    }
    
    /**
     * Agenda delete menu.
     */
    public void menu(String[] menuOptions){
        byte option = 0;
        byte exit = (byte)(menuOptions.length - 1);
        do {
            Console.showMenu(menuOptions, true);
            try {
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opci�n: ", "byte");
                System.out.println();
                if (Console.inRange(option, 1, exit)){
                    if (option != exit){
                        Object value = this.getIdentifyValue(option);
                        if (this.contacts.deleteElement(option, value))
                            System.out.println("El cont�cto se ha eliminado con �xito.");
                        else
                            System.out.println("Contacto no eliminado.");
                        Console.toContinue();
                        option = exit;
                    }
                } else
                    System.out.println("La opci�n seleccionada no "
                                + "existe, int�ntelo de nuevo... " + Console.EOF);
            } catch (InputMismatchException e) {
                System.out.println(Console.EOF + "Opci�n no v�lida, intente lo de nuevo..." + Console.EOF);
            }
        } while (option != exit);
    }
    
    /**
     * Request the user for a value to identify a contact.
     * @param option Criteria for identify a contact.
     * @return The value to identify a contact in the contact list.
     */
    public Object getIdentifyValue(int option){
        Object value;
        switch(option){
            case 1:
                value = Console.validString("Escribe el DNI del contacto que desea "
                        + "eliminar: ", 9, 9);
                break;
            case 2:
                value = Console.readLine("Escribe los nombre y apellidos del "
                        + "contacto que desea eliminar: ").trim();
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + option);
        }
        return value;
    }
}