package agenda;

import utils.Console;
import utils.IO;
import java.util.InputMismatchException;

/**
 * Main class of the project, where the project is started in the main method.
 * @author zelda
 */
public class Main {
   
    /**
     * Main menu of the exercise.
     */
    public static void main(String[] args) {
        byte option = 0;
        String[] mainMenu = IO.loadMenu("data\\menu.txt");
        Contact[] contacts = null;
        do {
            Console.showMenu(mainMenu, true);
            try {
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opción: ", 
                        "byte");
                System.out.println();
                switch(option) {
                    case 1:
                        
                        Console.toContinue();
                        break;
                    case 2:
                        
                        Console.toContinue();
                        break;
                    case 3:
                        new Agenda<Contact>(contacts);
                        break;
                    case 4:
                        String[] files = IO.getFiles("data");
                        files[0] = "Archivos del ejercicio";
                        Console.showMenu(files, false);
                        Console.toContinue();
                        break;
                    case 5:
                        System.out.println("Gracias por utilizar nuestra "
                                + "aplicación ¡Que tengas un buen día! ");
                        break;
                    default:
                        System.out.println("La opción seleccionada no "
                                + "existe, inténtelo de nuevo... " + Console.EOF);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(Console.EOF + "Opción no válida, inténtelo de "
                        + "nuevo..." + Console.EOF);
            }
        } while (option != 5);
    }
}