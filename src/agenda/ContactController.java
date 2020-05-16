package agenda;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Class for the implementation of the methods to add, list, update and delete for 
 * contacts.
 * @author zelda
 */
public class ContactController implements CRUD<Contact> {
    
    ArrayList<Contact> contactList;
    
    public ContactController(Contact[] collection){
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
    public boolean add(Contact newContact){
        boolean success = false;
        if (newContact != null) {
            this.contactList.add(newContact);
            success = true;
        }
        return success;
    }
    
    @Override 
    public void list(ArrayList<Contact> list){
        
    }
    
    @Override
    public void sort(ArrayList<Contact> list, int criteria){
        
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
                if (c1.getBirthDate().equals(c2.getBirthDate()))
                    return c1.getName().compareTo(c2.getName());
                else
                    return c1.getBirthDate().compareTo(c2.getBirthDate());
            }
            
            public int compareByRating(Contact c1, Contact c2){
                if (c1.getRating() == c2.getRating())
                    return c1.getName().compareTo(c2.getName());
                else
                    return c2.getRating() - c1.getRating();
            }
        }
        
        Comparison compare = new Comparison();
        
        switch (criteria){
            case 1:
                Collections.sort(list, compare::compareByName);
                break;
            case 2:
                Collections.sort(list, compare::compareByLastName);
                break;
            case 3:
                Collections.sort(list, compare::compareByBirthDate);
                break;
            case 4:
                Collections.sort(list, compare::compareByRating);
                break;
        }
    }
    
    @Override
    public void search(ArrayList<Contact> list, int criteria){
        
        class searchEngine {
            
            private ArrayList<Contact> found = new ArrayList();
            
            public void searchByID(ArrayList<Contact> list){
                //code...
            }
            
            public void searchByName(ArrayList<Contact> list){
                //code...
            }
            
            public void searchByLastName(ArrayList<Contact> list){
                
            }
            
            public void searchByEmail(ArrayList<Contact> list){
                //code...
            }
            
            public void searchByPhone(ArrayList<Contact> list){
                
            }            
        }
        
        searchEngine lookup = new searchEngine();
        
        switch(criteria){
            case 1:
                lookup.searchByID(list);
                break;
            case 2:
                lookup.searchByName(list);
                break;
            case 3:
                lookup.searchByLastName(list);
                break;
            case 4:
                lookup.searchByEmail(list);
                break;
            case 5:
                lookup.searchByPhone(list);
                break;
        }
    }
    
    @Override
    public void update(int criteria){
        
    }
    
    @Override
    public void delete (int criteria){
        
    }
    
    /**
     * Return the collection of the agenda as an array.
     * @return An array with the collection of the agenda.
     */
    @Override
    public Contact[] getContactList(){ // COMRPOBAR SI ES MEJOR ASI O HACER UN CAST
        return this.contactList.toArray(new Contact[this.contactList.size()]);
    }
}