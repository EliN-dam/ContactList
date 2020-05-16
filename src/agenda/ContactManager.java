package agenda;

import java.util.InputMismatchException;
import utils.Console;
import utils.IO;

/**
 * Class for managing a contact list, allowing insert, update and delete new collection.
 * @author zelda
 */
public class ContactManager implements Manager<Contact> {

    Contact[] list;
    CRUD<Contact> contacts;
    
    public ContactManager(Object[] collection ){
        this.list = (Contact[])collection;
        contacts = new ContactController(this.list);
        this.menu(IO.loadMenu("data\\agenda.txt"));
    }
    
    /**
     * ContactManager menu.
     */
    @Override
    public void menu(String[] menuOptions){
        byte option = 0;
        do {
            Console.showMenu(menuOptions, true);
            try {
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opción: ", "byte");
                System.out.println();
                switch(option) {
                    case 1:
                        this.contacts.add(this.newContact());
                        Console.toContinue();
                        break;
                    case 2:
                        //if (this.collection.length > 0)
                            new Sort(this.contacts);
                        /*else
                            System.out.println("la agenda está vacía." + Console.EOF);*/
                        break;
                    case 3:
                        //if (this.collection.length > 0)
                            new Search(this.contacts);
                        /*else
                            System.out.println("la agenda está vacía." + Console.EOF);*/
                        break;
                    case 4:
                        //if (this.collection.length > 0)
                            new Update(this.contacts);
                        /*else
                            System.out.println("la agenda está vacía." + Console.EOF);*/
                        break;
                    case 5:
                        //if (this.collection.length > 0)
                            new Delete(this.contacts);
                        /*else
                            System.out.println("la agenda está vacía." + Console.EOF);*/
                        break;
                    case 6:
                        this.list = this.contacts.getContactList();
                        break;
                    default:
                        System.out.println("La opción seleccionada no "
                                + "existe, inténtelo de nuevo... " + Console.EOF);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(Console.EOF + "Opción no válida, intente lo de nuevo..." + Console.EOF);
            }
        } while (option != 6);
    }
    
    /**
     * Request the user the values of the new contact.
     * @return An array with the values introduced by the user.
     */
    @Override
    public Object[] newContact(){
        Object[] values = new Object[7];
        values[0] = Console.validDNI("Introduce el DNI del nuevo contacto: ");
        values[1] = Console.readLine("Escribe el nombre/s del contacto: ").trim();
        values[2] = Console.readLine("Escribe los apellido/s del contacto: ").trim();
        if (Console.makeSure("¿Desea añadir la fecha de nacimiento?"))
            values[3] = Console.validDate("Ecriba la fecha de nacimiento (dd-MM-aaaa): ");
        if (Console.makeSure("¿Desea añadir una puntuación al contacto?"))
            values[4] = Console.validInt("Introduce la puntuación: ", 1, 5);
        if (Console.makeSure("¿Desea añadir un correo electrónico?")){
            values[5] = new String[]{ 
                    Console.validEmail("Escribe el correo eléctronico a añadir: ")
            };
        }
        if (Console.makeSure("¿Dese añadir un teléfono de contacto?")){
            values[6] = new String[] {
                Console.validPhone("Escribe el teléfono a añadir: ")
                    
            };
        }
        return values;
    }
    
    /**
     * Return the collection of the agenda as an array.
     * @return An array with the collection of the agenda.
     */
    @Override
    public Object[] retrieveList(){
        return this.list;
    }
}