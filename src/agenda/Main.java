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
        Object[] items = null;
        do {
            Console.showMenu(mainMenu, true);
            try {
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opci�n: ", 
                        "byte");
                System.out.println();
                switch(option) {
                    case 1:
                        items = IO.loadData("data\\agenda.dat");
                        System.out.println();
                        break;
                    case 2:
                        if (Console.makeSure("Se van a sobreescribir los datos �Est� seguro?"))
                            IO.saveData(items, "data\\agenda.dat");
                        else
                            System.out.println("No se han guardado los datos.");
                        System.out.println();
                        //Console.toContinue();
                        break;
                    case 3:
                        Manager<Contact> agenda = new ContactManager(items);
                        items = agenda.retrieveList();
                        break;
                    case 4: // Show the external config files.
                        String[] files = IO.getFiles("data");
                        files[0] = "Archivos del ejercicio";
                        Console.showMenu(files, false);
                        Console.toContinue();
                        break;
                    case 5:
                        System.out.println("Gracias por utilizar nuestra "
                                + "aplicaci�n �Que tengas un buen d�a! ");
                        break;
                    default:
                        System.out.println("La opci�n seleccionada no "
                                + "existe, int�ntelo de nuevo... " + Console.EOF);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(Console.EOF + "Opci�n no v�lida, int�ntelo de "
                        + "nuevo..." + Console.EOF);
            }
        } while (option != 5);
    }
}