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
                        if (this.contacts.add(this.newContact()))
                            System.out.println(Console.EOF + "Contacto añadido con "
                                    + "éxito a la agenda.");
                        else
                             System.out.println(Console.EOF + "Ha ocurrido un error, "
                                     + "no se ha podido añadir el contacto a la agenda.");
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
    public Contact newContact(){
        Contact current = new Contact();
        current.setDNI(Console.validDNI("Introduce el DNI del nuevo contacto: "));
        current.setName(Console.readLine("Escribe el nombre/s del contacto: ").trim());
        current.setLastNames(Console.readLine("Escribe los apellido/s del contacto: ")
                .trim());
        if (Console.makeSure("¿Desea añadir la fecha de nacimiento?"))
            current.setBirthDate(Console.validDate("Ecriba la fecha de nacimiento "
                    + "(dd-MM-aaaa): "));
        if (Console.makeSure("¿Desea añadir una puntuación al contacto?"))
            current.setRating(Console.validInt("Introduce la puntuación: ", 1, 5));
        if (Console.makeSure("¿Desea añadir un correo electrónico?"))
            current.addEmail(Console.validEmail("Escribe el correo eléctronico a "
                    + "añadir: "));
        if (Console.makeSure("¿Dese añadir un teléfono de contacto?"))
            current.addPhoneNumber(Console.validPhone("Escribe el teléfono a "
                    + "añadir: "));
        return current;
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