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
        contacts = new ContactCRUD(this.list);
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
                        if (this.contacts.addElement(this.newElement())){
                            System.out.println("Contacto añadido con éxito a la "
                                    + "agenda.");
                            /* In the case the first contact add, allow to access 
                             * to the rest of submenus */
                            this.list = this.updateList(); 
                        } else
                             System.out.println("No se ha podido añadir el "
                                     + "contacto a la agenda.");
                        Console.toContinue();
                        break;
                    case 2:
                        if (this.list != null)
                            new Sort(this.contacts);
                        else
                            System.out.println("la agenda está vacía." + Console.EOF);
                        break;
                    case 3:
                        if (this.list != null)
                            new Search(this.contacts);
                        else
                            System.out.println("la agenda está vacía." + Console.EOF);
                        break;
                    case 4:
                        if (this.list != null)
                            new Update(this.contacts, this.getID());
                        else
                            System.out.println("la agenda está vacía." + Console.EOF);
                        break;
                    case 5:
                        if (this.list != null)
                            new Delete(this.contacts);
                        else
                            System.out.println("la agenda está vacía." + Console.EOF);
                        break;
                    case 6:
                        this.list = this.updateList();
                        break;
                    default:
                        System.out.println("La opción seleccionada no "
                                + "existe, inténtelo de nuevo... " + Console.EOF);
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println(Console.EOF + "Opción no válida, intente lo de nuevo..." + Console.EOF);
            }
        } while (option != menuOptions.length - 1);
    }
    
    /**
     * Request the user the values of the new contact.
     * @return An array with the values introduced by the user.
     */
    @Override
    public Contact newElement(){
        Contact current = new Contact();
        current.setDNI(Console.validDNI("Introduce el DNI del nuevo contacto: "));
        current.setName(Console.readLine("Escribe el nombre/s del contacto: ").trim());
        current.setLastNames(Console.readLine("Escribe los apellido/s del contacto: ")
                .trim());
        if (Console.makeSure("¿Desea añadir la fecha de nacimiento?"))
            current.setBirthDate(Console.validDate("Escriba la fecha de nacimiento "
                    + "(dd-MM-aaaa): "));
        if (Console.makeSure("¿Desea añadir una puntuación al contacto?"))
            current.setRating(Console.validInt("Introduce la puntuación: ", 1, 5));
        if (Console.makeSure("¿Desea añadir un correo electrónico?"))
            current.addEmail(Console.validEmail("Escribe el correo eléctronico a "
                    + "añadir: "));
        if (Console.makeSure("¿Desea añadir un teléfono de contacto?"))
            current.addPhoneNumber(Console.validPhone("Escribe el teléfono a "
                    + "añadir: "));
        System.out.println();
        return current;
    }
    
    /**
     * Request the user the ID of the contact.
     * @return A string with the ID value.
     */
    public String getID() {
        String value = Console.validString("Escribe el DNI del contacto que desea "
                        + "actualizar: ", 9, 9);
        System.out.println();
        return value;
    }
    
    /**
     * Update the collection from the controller.
     * @return The updated contact list.
     */
    @Override
    public Contact[] updateList(){
        return this.contacts.getElementList();
    }
    
    /**
     * Return the list of contacts as an objects array.
     * @return An objects array with the contacts.
     */
    @Override
    public Object[] retrieveList(){
        return this.list;
    }
}