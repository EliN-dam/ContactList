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
        boolean saved = false; // if the user save the data.
        boolean manager = false; //if the user enter in the contact manager.
        do {
            Console.showMenu(mainMenu, true);
            try {
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opción: ", 
                        "byte");
                System.out.println();
                switch(option) {
                    case 1:
                        items = IO.loadData("data\\agenda.dat");
                        System.out.println();
                        break;
                    case 2:
                        if (Console.makeSure("Se van a sobreescribir los datos ¿Está seguro?")){
                            IO.saveData(items, "data\\agenda.dat");
                            saved = true;
                        } else
                            System.out.println("No se han guardado los datos.");
                        System.out.println();
                        //Console.toContinue();
                        break;
                    case 3:
                        Manager<Contact> agenda = new ContactManager(items);
                        items = agenda.retrieveList();
                        manager = true;
                        break;
                    case 4: // Show the external config files.
                        String[] files = IO.getFiles("data");
                        files[0] = "Archivos del ejercicio";
                        Console.showMenu(files, false);
                        Console.toContinue();
                        break;
                    case 5:
                        // Just to make sure the user don't loose any changes.
                        if (!saved && manager){ 
                            if (!Console.makeSure("No ha guardado los datos de "
                                    + "contacto ¿Está seguro que desea salir?")){
                                option = 0;
                                
                            }
                        } 
                        System.out.println();
                        if (option != 0)
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