package agenda;

import java.io.Serializable;
import java.util.InputMismatchException;
import utils.Console;
import utils.IO;

/**
 * Class for modifying the items values of the collection.
 * @author zelda
 */
public class Update{
    
    CRUD<Contact> contacts;
    Contact currentContact;
    
    public Update(CRUD<Contact> contacts, String contactID){
        this.contacts = contacts;
        this.currentContact = this.contacts.getElementByID(contactID);
        if (this.currentContact != null)
            this.menu(IO.loadMenu("data\\actualizar.txt"), contactID);
        else {
            System.out.println("No se ha encontrado al contacto con el DNI " + 
                    contactID + " en la lista de contactos.");
            Console.toContinue();
        }
    }
    
    /**
     * Agenda update menu.
     */
    public void menu(String[] menuOptions, String contactID){
        byte option = 0;
        byte exit = (byte)(menuOptions.length - 1);
        menuOptions[0] = menuOptions[0] + " con DNI " + contactID;
        do {
            Console.showMenu(menuOptions, true);
            try {
                option = (byte)Console.readNumber(Console.EOF + "Escoge una opción: ", "byte");
                System.out.println();
                if (Console.inRange(option, 1, exit)){
                    if (option != exit){
                        Object value = this.getUpdatedValue(option);
                        if (this.contacts.updateElement(this.currentContact, option, value))
                                System.out.println("Contacto actualizado con éxito.");
                        else
                            System.out.println("No se ha podido actualizar el contacto.");
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
    
    /**
     * Request the user the new value to be updated.
     * @param option Criteria for the selection of the attribute to be updated.
     * @return The value to be update.
     */
    public Object getUpdatedValue(int option){
        Object value;
        switch(option){
            case 1:
                value = Console.readLine("Escribe un nuevo nombre para el "
                        + "contacto: ").trim();
                break;
            case 2:
                value = Console.readLine("Escribe nuevo/s apellido/s para el "
                        + "contacto: ").trim();
                break;
            case 3:
                value = Console.validDate("Ecriba una nueva fecha de nacimiento "
                        + "(dd-MM-aaaa): ");
                break;
            case 4:
                value = Console.validInt("Introduce la nueva puntuación: ", 1, 5);
                break;
            case 5:
                value = Console.validEmail("Escribe el correo eléctronico a añadir: ");
                break;
            case 6:
                value = Console.validPhone("Escribe el teléfono a añadir: ");
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + option);
        }
        System.out.println();
        return value;
    }
}