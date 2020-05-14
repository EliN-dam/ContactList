package agenda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import utils.Console;
import utils.IO;

/**
 * Class for managing a contact agenda, allowing insert, update and delete new contacts.
 * @author zelda
 */
public class Agenda<T> {
    
    ArrayList<T> contacts;
    
    public Agenda(T[] contacts){
        if (contacts != null)
            this.contacts = new ArrayList<T>(Arrays.asList(contacts));
        else
            this.contacts = new ArrayList<T>();
        this.menu();
    }
    
    /**
     * Agenda menu.
     */
    public void menu(){
        byte option = 0;
        String[] agendaMenu = IO.loadMenu("data\\agenda.txt");
        do {
            Console.showMenu(agendaMenu, true);
            try {
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opci�n: ", "byte");
                System.out.println();
                switch(option) {
                    case 1:

                        Console.toContinue();
                        break;
                    case 2:
                        //if (this.contacts.size() > 0)
                            new Sort(this.contacts);
                        /*else
                            System.out.println("la agenda est� vac�a." + Console.EOF);*/
                        break;
                    case 3:
                        //if (this.contacts.size() > 0)
                            new Search(this.contacts);
                        /*else
                            System.out.println("la agenda est� vac�a." + Console.EOF);*/
                        break;
                    case 4:
                        //if (this.contacts.size() > 0)
                            new Update(this.contacts);
                        /*else
                            System.out.println("la agenda est� vac�a." + Console.EOF);*/
                        break;
                    case 5:
                        //if (this.contacts.size() > 0)
                            new Delete(this.contacts);
                        /*else
                            System.out.println("la agenda est� vac�a." + Console.EOF);*/
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("La opci�n seleccionada no "
                                + "existe, int�ntelo de nuevo... " + Console.EOF);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(Console.EOF + "Opci�n no v�lida, intente lo de nuevo..." + Console.EOF);
            }
        } while (option != 6);
    }
}