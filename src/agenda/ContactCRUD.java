package agenda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Class for the implementation of the methods to add, list, update and delete for 
 * contacts.
 * @author zelda
 */
public class ContactCRUD implements CRUD<Contact> {
    
    ArrayList<Contact> contactList;
    
    public ContactCRUD(Contact[] collection){
        if (collection != null)
            this.contactList = new ArrayList<Contact>(Arrays.asList(collection));
        else
            this.contactList = new ArrayList<Contact>();
    }
    
    /**
     * Add a new contact to the current contact list.
     * @param newContact The contact to want to add to the list.
     * @return <ul>
     *              <li>True - If the contact was added</li>
     *              <li>False - If the contact couldn't be added</li>
     *         </ul>
     */
    @Override
    public boolean addElement(Contact newContact){
        boolean success = false;
        if (newContact != null) {
            if (this.checkIfExists(newContact))
                System.out.println("El contacto que intenta añadir ya se encuentra"
                        + " en la lista de contactos");
            else {
                this.contactList.add(newContact);
                success = true;
            }
        } else 
            System.out.println("Debe rellenar todos los datos necesarios para "
                    + "añadir el nuevo contacto.");
        return success;
    }
    
    /**
     * Sort the contact lists according to the selected criteria and print them 
     * on the console.
     * @param option Criteria for sorting the contact list.
     */
    @Override
    public void sortElement(int option){
        String title = "Contactos ordenados por ";
        Criteria criteria;
        switch (option){
            case 1:
                title += "Nombre";
                criteria = Criteria.NAME;
                break;
            case 2:
                title += "Apellidos";
                criteria = Criteria.LASTNAME;
                break;
            case 3:
                title += "Fecha de nacimiento";
                criteria = Criteria.BIRTHDATE;
                break;
            case 4:
                title += "Puntuación";
                criteria = Criteria.RATING;
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + option);
        }
        this.sort(this.contactList, criteria);
        this.list(this.contactList, title);
    }
     
    /**
     * Select the contacts in the contact list that meets the search criteria and 
     * print them on the console.
     * @param option Criteria for searching in the contact list.
     * @param value The search value.
     * @return <ul>
     *              <li>True - If the search criteria have found any match</li>
     *              <li>False - If the search criteria did not find any match</li>
     *         </ul>
     */
    @Override
    public boolean searchElement(int option, String value){
        String message, type = "";
        Criteria criteria;
        switch(option){
            case 1:
                type = "el DNI ";
                criteria = Criteria.ID;
                break;
            case 2:
                type = "el nombre ";
                criteria = Criteria.NAME;
                break;
            case 3:
                type = "los apellidos ";
                criteria = Criteria.LASTNAME;
                break;
            case 4:
                type = "el correo ";
                criteria = Criteria.EMAILS;
                break;
            case 5:
                type = "el teléfono ";
                criteria = Criteria.PHONENUMBERS;
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + option);
        }
        ArrayList<Contact> results = this.search(this.contactList, criteria, value);
        if (results.size() > 0){
            message = "Resultados para " + type + value;
            this.list(results, message);
            return true;
        } else {
            message = "No se han encontrado resultados para " + type + value + ".";
            System.out.println(message);
            return false;
        }
    }
    
    /**
     * Update a contact's information using the chosen attributes.
     * @param contact The contact to be updated.
     * @param option Criteria for the selection of the attribute to be updated.
     * @param value The new value to be update.
     * @return <ul>
     *              <li>True - If the contact was updated</li>
     *              <li>False - If the contact could not be updated</li>
     *         </ul>
     */
    @Override
    public boolean updateElement(Contact contact, int option, Object value){
        boolean success = false;
        if (value != null){
            switch(option){
                case 1:
                    contact.setName((String)value);
                    break;
                case 2:
                    contact.setLastNames((String)value);
                    break;
                case 3:
                    contact.setBirthDate((LocalDate)value);
                    break;
                case 4:
                    contact.setRating((int)value);
                    break;
                case 5:
                    contact.addEmail((String)value);
                    break;
                case 6:
                    contact.addPhoneNumber((String)value);
                    break;
                default:
                    throw new IllegalStateException("Valor inesperado: " + option);
            }
            success = true;
        }
        return success;
    }
    
    /**
     * Show the contact list in console.
     * @param list An ArrayList with the contact list.
     * @param title A sort of title.
     */
    @Override 
    public void list(ArrayList<Contact> list, String title){
        String[] labels = {
            "",
            "DNI",
            "Nombre",
            "Apellidos",
            "Fecha de nacimiento",
            "Puntuación",
            "Emails",
            "Teléfonos"
        };
        labels[0] = title;
        printTable(list, labels, getTableSizes(list, labels));
    }
    
    /**
     * Print a table with the contact list data in the console.
     * @param data An ArrayList with the data.
     * @param labels An array with the columns labels.
     * @param sizes An array with the sizes needed to draw the table.
     */
    public static void printTable(ArrayList<Contact> data, String[] labels, int[] sizes){
        if (data != null) {
            int titleLength = labels[0].length();
            int nColumns = sizes.length - 1;
            int totalSize = sizes[0] + (nColumns * 2) + (nColumns - 1);
            String interline = "+";
            for (int i = 1; i < sizes.length; i++)
                interline += new String(new char[sizes[i]]).replace('\0', '=') + "==" + "+";
            // Print table title
            System.out.println("+" + new String(new char[totalSize]).replace('\0', '=') + "+");
            System.out.printf("|%" + (totalSize - titleLength) / 2 +
                              "s%" + titleLength +
                              "s%" + ((totalSize - titleLength) + 1) / 2 + "s|" +
                              System.lineSeparator(), 
                              "", labels[0].toUpperCase(), "");
            System.out.println("+" + new String(new char[totalSize]).replace('\0', '=') + "+");
            // Print table head
            int length;
            System.out.print("|");
            for (int i = 1; i < sizes.length; i++){
                length = labels[i].length();
                System.out.printf("%" + (sizes[i] - length + 2) / 2 +
                                  "s%-" + length +
                                  "s%" + (sizes[i] - length + 3)  / 2 + "s|",
                                  "", labels[i] , "");
            }
            System.out.println("\n" + interline);
            // Print table rows
            Contact current;
            String value; 
            Criteria[] getters = Criteria.values();
            for (int i = 0; i < data.size(); i++){
                current = data.get(i);
                System.out.print("|");
                for (int j = 1; j < sizes.length; j++){
                    value = getters[j - 1].getMethod().apply(current).toString();
                    System.out.printf("%" + (sizes[j] - value.length() + 2) / 2 + "s%-" + 
                        value.length() + "s%" + (sizes[j] - value.length() + 3) / 2 + 
                        "s|","", value, "");
                }
                System.out.println();
                if (i != data.size() - 1)
                    System.out.println(interline);
            }
            System.out.println("+" + new String(new char[totalSize]).replace('\0', '=') + "+");
        }
    }
    
    /**
     * Get the max sizes of the data to print a table in Console.
     * @param list An ArrayList with the data.
     * @param labels An array with the columns labels.
     * @return An array with the max sizes of each column and the length of the table.
     */
    public static int[] getTableSizes(ArrayList<Contact> list, String[] labels){
        int[] sizes = new int[8];
        int value;
        // Get the max sizes of the header.
        for (int i = 1; i < sizes.length; i++)
            sizes[i] = labels[i].length();
        // Get the max sizes of the contacts.
        Contact current;
        Criteria[] getters = Criteria.values();
        for (int i = 0; i < list.size(); i++){
            current = list.get(i);
            for (int j = 0; j < getters.length; j++){
                value = getters[j].getMethod().apply(current).toString().length();
                if (value > sizes[j + 1])
                    sizes[j + 1] = value;
            } 
        }
        // Get max total size.
        for (int i = 1; i < sizes.length; i++)
            sizes[0] += sizes[i];
        return sizes;
    }
       
    /**
     * Sort and ArrayList of elements by the selected criteria.
     * @param list An ArrayList with the elements to be sorted.
     * @param criteria Criteria for sorting the contact list.
     */
    @Override
    public void sort(ArrayList<Contact> list, Criteria criteria){
        Collections.sort(list, criteria.getComparator());
    }  

    /**
     * Search in an ArrayList the elements that meets the search value.
     * @param list An ArrayList with the elements to search.
     * @param criteria Criteria for searching in the Arraylist.
     * @param value The search value.
     * @return An ArrayList with the elements who meets the search criteria.
     */
    @Override
    public ArrayList<Contact> search(ArrayList<Contact> list, Criteria criteria, String value){
        Predicate<Contact> searchEngine;
        switch(criteria){
            case ID:
                searchEngine = contact -> contact.getDNI().equalsIgnoreCase(value);
                break;
            case NAME:
                searchEngine = contact -> contact.getName().toLowerCase().contains
                                          (value.toLowerCase());
                break;
            case LASTNAME:
                searchEngine = contact -> contact.getLastNames().toLowerCase()
                                          .contains(value.toLowerCase());
                break;
            case EMAILS:
                searchEngine = contact -> Arrays.stream(contact.getEmails())
                        .anyMatch(email -> email.equalsIgnoreCase(value));
                break;
            case PHONENUMBERS:
                searchEngine = contact -> Arrays.stream(contact.getPhoneNumbers())
                        .anyMatch(number -> number.equalsIgnoreCase(value));
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + criteria);
        }
        ArrayList<Contact> results = this.contactList.stream().filter(searchEngine)
                .collect(Collectors.toCollection(ArrayList::new));
        return results;
    }  
    
    @Override
    public void delete (int option){
        
    }
    
    /**
     * Returns the list of contacts as an array.
     * @return An array with the contacts.
     */
    @Override
    public Contact[] getElementList(){
        return this.contactList.toArray(new Contact[this.contactList.size()]);
    }
    
    /**
     * Returns the contact with the selected ID.
     * @param ID The ID of the contact.
     * @return A contact identified.
     */
    @Override
    public Contact getElementByID(String ID){
        ArrayList<Contact> found = this.search(this.contactList, Criteria.ID, ID);
        if (found.size() > 0)
            return found.get(0);
        else
            return null;
    }
    
    /**
     * Compare the name of two contacts.
     * @param c1 A first contact.
     * @param c2 A second contact to compare to.
     * @return <ul>
     *              <li>A negative integer - If the first contact name is less 
     *                  than the second one</li>
     *              <li>Zero - If both names are equals</li>
     *              <li>A positive integer - If the first contact name is greater
     *                  than the second one</li>
     *         </ul>
     */
    public static int compareByName(Contact c1, Contact c2){
        if (c1.getName().equalsIgnoreCase(c2.getName()))
            return c1.getLastNames().compareTo(c2.getLastNames());
        else
            return c1.getName().compareTo(c2.getName());
    }

    /**
     * Compare the last name of two contacts.
     * @param c1 A first contact.
     * @param c2 A second contact to compare to.
     * @return <ul>
     *              <li>A negative integer - If the first contact last name is 
     *                  less than the second one</li>
     *              <li>Zero - If both last names are equals</li>
     *              <li>A positive integer - If the first contact last name is 
     *                  greater than the second one</li>
     *         </ul>
     */
    public static int compareByLastName(Contact c1, Contact c2){
        if (c1.getLastNames().equalsIgnoreCase(c2.getLastNames()))
            return c1.getName().compareTo(c2.getName());
        else
            return c1.getLastNames().compareTo(c2.getLastNames());
    }

    /**
     * Compare the birthdate of two contacts.
     * @param c1 A first contact.
     * @param c2 A second contact to compare to.
     * @return <ul>
     *              <li>A negative integer - If the first contact birthdate is 
     *                  less than the second one or is null</li>
     *              <li>Zero - If both birthdates are equals</li>
     *              <li>A positive integer - If the first contact birthdate is 
     *                  greater than the second one or is null</li>
     *         </ul>
     */
    public static int compareByBirthDate(Contact c1, Contact c2){
        if (c1.getBirthDate() != null){ 
            if (c2.getBirthDate() != null){
                if (c1.getBirthDate().equals(c2.getBirthDate()))
                    return c1.getName().compareTo(c2.getName());
                else
                    return c1.getBirthDate().compareTo(c2.getBirthDate());
            } else {
               return -1;
            }
        } else { // Manage null values
            if (c2 == null)
                return 0;
            return 1;
        }
    }

    /**
     * Compare the rating of two contacts.
     * @param c1 A first contact.
     * @param c2 A second contact to compare to.
     * @return <ul>
     *              <li>A negative integer - If the first contact rating is 
     *                  lower than the second one</li>
     *              <li>Zero - If both ratings are equals</li>
     *              <li>A positive integer - If the first contact rating is 
     *                  higher than the second one</li>
     *         </ul>
     */
    public static int compareByRating(Contact c1, Contact c2){
        if (c1.getRating() == c2.getRating())
            return c1.getName().compareTo(c2.getName());
        else
            return c2.getRating() - c1.getRating();
    }
    
    /**
     * Check if the contact already exists in the contact list.
     * @param newContact
     * @return 
     */
    @Override
    public boolean checkIfExists(Contact newContact){
        for (Contact current : this.contactList){
            if (current.equals(newContact))
                return true;
        }
        return false;
    }
}