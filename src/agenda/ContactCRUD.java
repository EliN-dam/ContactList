package agenda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public boolean searchElement(int option, Object value){
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
        if (!results.isEmpty()){
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
     * @param value The new value for the update.
     * @return <ul>
     *              <li>True - If the contact was updated</li>
     *              <li>False - If the contact could not be updated</li>
     *         </ul>
     */
    @Override
    public boolean updateElement(Contact contact, int option, Object value){
        boolean success = false;
        Criteria criteria;
        if (value != null){
            switch(option){
                case 1:
                    criteria = Criteria.NAME;
                    break;
                case 2:
                    criteria = Criteria.LASTNAME;
                    break;
                case 3:
                    criteria = Criteria.BIRTHDATE;
                    break;
                case 4:
                    criteria = Criteria.RATING;
                    break;
                case 5:
                    criteria = Criteria.EMAILS;
                    break;
                case 6:
                    criteria = Criteria.PHONENUMBERS;
                    break;
                default:
                    throw new IllegalStateException("Valor inesperado: " + option);
            }
            this.update(contact, criteria, value.toString());
            success = true;
        }
        return success;
    }
    
    /**
     * Removing a contact if exists in the contact list.
     * @param option Criteria for identify a contact.
     * @param value The value to identify a contact in the contact list.
     * @return <ul>
     *              <li>True - If the contact was deleted</li>
     *              <li>False - If the contact could not be deleted</li>
     *         </ul>
     */
    @Override
    public boolean deleteElement(int option, Object value){
        Criteria criteria;
        switch(option){
            case 1:
                criteria = Criteria.ID;
                break;
            case 2:
                criteria = Criteria.FULLNAME;
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + option);
        }
        return this.delete(this.contactList, criteria, value);
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
                    value = getters[j - 1].getGetter().apply(current).toString();
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
            for (int j = 1; j < sizes.length; j++){
                value = getters[j - 1].getGetter().apply(current).toString().length();
                if (value > sizes[j])
                    sizes[j] = value;
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
    public ArrayList<Contact> search(ArrayList<Contact> list, Criteria criteria, Object value){
        Predicate<Contact> searchEngine;
        switch(criteria){
            case ID:
                searchEngine = contact -> contact.getDNI().equalsIgnoreCase(value.toString());
                break;
            case NAME:
                searchEngine = contact -> contact.getName().toLowerCase().contains
                                          (value.toString().toLowerCase());
                break;
            case LASTNAME:
                searchEngine = contact -> contact.getLastNames().toLowerCase()
                                          .contains(value.toString().toLowerCase());
                break;
            case EMAILS:
                searchEngine = contact -> Arrays.stream(contact.getEmails())
                        .anyMatch(email -> email.equalsIgnoreCase(value.toString()));
                break;
            case PHONENUMBERS:
                searchEngine = contact -> Arrays.stream(contact.getPhoneNumbers())
                        .anyMatch(number -> number.equals(value.toString()));
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + criteria);
        }
        ArrayList<Contact> results = this.contactList.stream().filter(searchEngine)
                .collect(Collectors.toCollection(ArrayList::new));
        return results;
    }  
    
    /**
     * Update an element values by setting the selected attributes.
     * @param contact The element to be updated.
     * @param criteria Criteria for the selection of the attribute to be updated.
     * @param value The new value for the update.
     */
    @Override
    public void update(Contact contact, Criteria criteria, String value){
        /*switch(criteria){
            case NAME:
                contact.setName((String)value);
                break;
            case LASTNAME:
                contact.setLastNames((String)value);
                break;
            case BIRTHDATE:
                contact.setBirthDate((LocalDate)value);
                break;
            case RATING:
                contact.setRating((int)value);
                break;
            case EMAILS:
                contact.addEmail((String)value);
                break;
            case PHONENUMBERS:
                contact.addPhoneNumber((String)value);
                break;
            default:
                throw new IllegalStateException("Valor inesperado: " + criteria);
        }*/
        criteria.getSetter().accept(contact, value);
    }    
    
    /**
     * Removing a element if exists in the ArrayList.
     * @param list An ArrayList with the elements.
     * @param criteria Criteria for identify an element in the ArrayList.
     * @param value The value to identify an element.
     */
    @Override
    public boolean delete(ArrayList<Contact> list, Criteria criteria, Object value){
        Predicate<Contact> removeCondition;
        switch(criteria) {
            case ID:
                removeCondition = contact -> contact.getDNI().equalsIgnoreCase(value.toString());
                return list.removeIf(removeCondition);
            case FULLNAME:
                removeCondition = contact -> (contact.getName() + " " + 
                        contact.getLastNames()).equalsIgnoreCase(value.toString());
                return list.removeIf(removeCondition);
            default:
                throw new IllegalStateException("Valor inesperado: " + criteria);
        }
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
        if (!found.isEmpty())
            return found.get(0);
        else
            return null;
    }
}