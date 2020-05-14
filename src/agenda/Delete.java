package agenda;

import java.util.ArrayList;
import java.util.InputMismatchException;
import utils.Console;
import utils.IO;

/**
 * Class for erasing items from the collection.
 * @author zelda
 */
public class Delete<T> {
    
    ArrayList<T> contacts;
    
    public Delete(ArrayList<T> contacts){
        this.contacts = contacts;
        this.menu();
    }
    
    /**
     * Agenda delete menu.
     */
    public void menu(){
        byte option = 0;
        String[] agendaMenu = IO.loadMenu("data\\eliminar.txt");
        do {
            Console.showMenu(agendaMenu, true);
            try {
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opción: ", "byte");
                System.out.println();
                switch(option) {
                    case 1:

                        Console.toContinue();
                        break;
                    case 2:

                        Console.toContinue();
                        break;
                    case 3:
                        break;
                    default:
                        System.out.println("La opción seleccionada no "
                                + "existe, inténtelo de nuevo... " + Console.EOF);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(Console.EOF + "Opción no válida, intente lo de nuevo..." + Console.EOF);
            }
        } while (option != 3);
    }
}
