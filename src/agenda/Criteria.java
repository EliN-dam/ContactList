package agenda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Enum type class con containing all the getters method references of a Contact class
 * and the comparator method references, also labels for search and delete methods.
 * https://www.youtube.com/watch?v=DwriSApbm50&list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk&index=48
 * https://www.geeksforgeeks.org/enum-in-java/
 * @author zelda
 */
enum Criteria {
    
    ID(Contact::getDNI, null),
    NAME(Contact::getName, ContactCRUD::compareByName),
    LASTNAME(Contact::getLastNames, ContactCRUD::compareByLastName),
    BIRTHDATE(contact -> {
            if (contact.getBirthDate() != null)
                return contact.getBirthDate();
            else
                return "N/A";
    }, ContactCRUD::compareByBirthDate),
    RATING(Contact::getRating, ContactCRUD::compareByRating),
    EMAILS(contact -> { 
            if (contact.getEmails().length > 0)
                return String.join(", ", contact.getEmails()); // Comprobar el delimitador.
            else
                return "N/A";
    }, null),
    PHONENUMBERS(contact -> { 
            if (contact.getPhoneNumbers().length > 0)
                return String.join(", ", contact.getPhoneNumbers());
            else
                return "N/A";
    }, null);
    
    private final Function<Contact, Object> method;
    private final Comparator<Contact> comparator;
    
    private Criteria(Function<Contact, Object> function, Comparator<Contact> comparator){
        this.method = function;
        this.comparator = comparator;
    }
    
    public Function<Contact, Object> getMethod() {
        return this.method;
    }       

    public Comparator<Contact> getComparator() {
        return comparator;
    }  
    
    // Another way to work with stream().filter().
    /*public Predicate<Contact> getFilter(Object values){
        String value = values.toString();
        switch(this){
            case ID:
                return contact -> contact.getDNI().equalsIgnoreCase(value);
            case NAME:
                return contact -> contact.getName().toLowerCase().contains
                                          (value.toLowerCase());
            case LASTNAME:
                return  contact -> contact.getLastNames().toLowerCase()
                                          .contains(value.toLowerCase());
            case EMAILS:
                return contact -> Arrays.stream(contact.getEmails())
                        .anyMatch(email -> email.equalsIgnoreCase(value));
            case PHONENUMBERS:
                return contact -> Arrays.stream(contact.getPhoneNumbers())
                        .anyMatch(number -> number.equalsIgnoreCase(value));
            default:
                throw new IllegalStateException("Valor inesperado: " + this);
        }
    }*/
}