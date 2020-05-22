package agenda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

/**
 * Class for the implementation of the methods to add, list, update and delete for 
 * contacts.
 * @author zelda
 */
public class ContactController implements CRUD<Contact> {
    
    ArrayList<Contact> contactList;
    private static ArrayList<Function<Contact, String>> getters;
    
    public ContactController(Contact[] collection){
        if (collection != null)
            this.contactList = new ArrayList<Contact>(Arrays.asList(collection));
        else
            this.contactList = new ArrayList<Contact>();
        getters = loadFunctions();
    }
    
    /**
     * Load an ArrayList with the getters of the class methods allowing to iterate 
     * with them in a loop.
     * @return An ArrayList with the selected functions.
     */
    public static ArrayList<Function<Contact, String>> loadFunctions(){
        ArrayList<Function<Contact, String>> temp = new ArrayList(7);
        temp.add(Contact::getDNI);
        temp.add(Contact::getName);
        temp.add(Contact::getLastNames);
        temp.add(contact -> {
            if (contact.getBirthDate() != null)
                return contact.getBirthDate().toString();
            else
                return "N/A";
        });
        temp.add(contact -> {
                return String.valueOf(contact.getRating());
        });
        temp.add(contact -> { 
            if (contact.getEmails().length > 0)
                return String.join("\n", contact.getEmails()); // Comprobar el delimitador.
            else
                return "N/A";
        });
        temp.add(contact -> { 
            if (contact.getPhoneNumbers().length > 0)
                return String.join("\n", contact.getPhoneNumbers());
            else
                return "N/A";
        });
        return temp;
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
    public boolean add(Contact newContact){
        boolean success = false;
        if (newContact != null) {
            this.contactList.add(newContact);
            success = true;
        }
        return success;
    }
           
    /**
     * Show the contact list in console.
     * @param list An ArrayList with the contact list.
     * @param title A sort of title.
     * @param criteria Criteria for sorting the contact list.
     */
    @Override 
    public void list(ArrayList<Contact> list, String title){
        String[] labels = {
            "",
            "DNI",
            "Nombre",
            "Apellidos",
            "Fecha de nacimiento",
            "Puntuaci�n",
            "Emails",
            "Tel�fonos"
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
            for (int i = 0; i < data.size(); i++){
                current = data.get(i);
                System.out.print("|");
                for (int j = 1; j < sizes.length; j++){
                    value = getters.get(j - 1).apply(current);
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
        for (int i = 0; i < list.size(); i++){
            current = list.get(i);
            for (int j = 0; j < getters.size(); j++){
                value = getters.get(j).apply(current).length();
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
     * Sort the contact lists according to the selected criteria and print them 
     * on the console.
     * @param criteria Criteria for sorting the contact list.
     */
    @Override
    public void sort(int criteria){
        
        /**
         * Inner class with the comparison methods.
         */
        class Comparison {
            public int compareByName(Contact c1, Contact c2){
                if (c1.getName().equalsIgnoreCase(c2.getName()))
                    return c1.getLastNames().compareTo(c2.getLastNames());
                else
                    return c1.getName().compareTo(c2.getName());
            }
            
            public int compareByLastName(Contact c1, Contact c2){
                if (c1.getLastNames().equalsIgnoreCase(c2.getLastNames()))
                    return c1.getName().compareTo(c2.getName());
                else
                    return c1.getLastNames().compareTo(c2.getLastNames());
            }
            
            public int compareByBirthDate(Contact c1, Contact c2){
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
            
            public int compareByRating(Contact c1, Contact c2){
                if (c1.getRating() == c2.getRating())
                    return c1.getName().compareTo(c2.getName());
                else
                    return c2.getRating() - c1.getRating();
            }
        }
        
        Comparison compare = new Comparison();
        String title = "Contactos ordenados por ";
        switch (criteria){
            case 1:
                Collections.sort(this.contactList, compare::compareByName);
                title += "Nombre";
                break;
            case 2:
                Collections.sort(this.contactList, compare::compareByLastName);
                title += "Apellidos";
                break;
            case 3:
                Collections.sort(this.contactList, compare::compareByBirthDate);
                title += "Fecha de nacimiento";
                break;
            case 4:
                Collections.sort(this.contactList, compare::compareByRating);
                title += "Puntuaci�n";
                break;
        }
        this.list(this.contactList, title);
    }
    
    /**
     * Select the contacts in the contact list that meets the search criteria and 
     * print them on the console.
     * @param criteria Criteria for searching in the contact list.
     * @param value The search value.
     */
    @Override
    public void search(int criteria, String value){
        
        /**
         * Inner class for search methods by criteria.
         */
        class searchEngine {
            
            private ArrayList<Contact> found = new ArrayList();
            
            public ArrayList<Contact> searchByID(ArrayList<Contact> list, String value){
                for (Contact current : list){
                    if (current.getDNI().equalsIgnoreCase(value))
                        this.found.add(current);
                }
                return found;
            }
            
            public ArrayList<Contact> searchByName(ArrayList<Contact> list, String value){
                for (Contact current : list){
                    if (current.getName().toLowerCase().contains(value.toLowerCase()))
                        this.found.add(current);
                }
                return found;
            }
            
            public ArrayList<Contact> searchByLastName(ArrayList<Contact> list, String value){
                for (Contact current : list){
                    if (current.getLastNames().toLowerCase().contains(value.toLowerCase()))
                        this.found.add(current);
                }
                return found;
            }
            
            public ArrayList<Contact> searchByEmail(ArrayList<Contact> list, String value){
                for (Contact current : list){
                    for (String email: current.getEmails()){
                        if (email.equalsIgnoreCase(value))
                            this.found.add(current);
                    }
                }
                return found;
            }
            
            public ArrayList<Contact> searchByPhone(ArrayList<Contact> list, String value){
                for (Contact current : list){
                    for (String phoneNumber : current.getPhoneNumbers()){
                        if (phoneNumber.equalsIgnoreCase(value))
                            this.found.add(current);
                    }
                }
                return found;
            }            
        }
        
        searchEngine lookup = new searchEngine();
        ArrayList<Contact> results = new ArrayList();
        String message, type = "";
        switch(criteria){
            case 1:
                results = lookup.searchByID(this.contactList, value);
                type = "el DNI ";
                break;
            case 2:
                results = lookup.searchByName(this.contactList, value);
                type = "el nombre ";
                break;
            case 3:
                results = lookup.searchByLastName(this.contactList, value);
                type = "los apellidos ";
                break;
            case 4:
                results = lookup.searchByEmail(this.contactList, value);
                type = "el correo ";
                break;
            case 5:
                results = lookup.searchByPhone(this.contactList, value);
                type = "el tel�fono ";
                break;
        }        
        if (results.size() > 0){
            message = "Resultados para " + type + value;
            this.list(results, message);
        } else {
            message = "No se han encontrado resultados para " + type + value + ".";
            System.out.println(message);
        }
    }
    
    @Override
    public void update(int criteria){
        
    }
    
    @Override
    public void delete (int criteria){
        
    }
    
    /**
     * Return the list of contacts as an array.
     * @return An array with the contacts.
     */
    @Override
    public Contact[] getContactList(){ // COMRPOBAR SI ES MEJOR ASI O HACER UN CAST
        return this.contactList.toArray(new Contact[this.contactList.size()]);
    }
}