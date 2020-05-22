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
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opción: ", "byte");
                System.out.println();
                if (Console.inRange(option, 1, exit)){
                    if (option != exit){
                        this.contacts.delete(option);
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
}