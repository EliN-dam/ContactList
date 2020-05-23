package agenda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.InputMismatchException;
import utils.Console;
import utils.IO;

/**
 * Class for show the collection sorted by some criteria.
 * @author zelda
 */
public class Sort{
    
    CRUD<Contact> contacts;
    
    public Sort(CRUD<Contact> contacts){
        this.contacts = contacts;
        this.menu(IO.loadMenu("data\\mostrar.txt"));
    }
    
    /**
     * Agenda sorting menu.
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
                        this.contacts.sortElement(option);
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