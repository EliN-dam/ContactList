package agenda;

import java.util.Comparator;
import java.util.function.Function;
import java.util.function.BiConsumer;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * Enum type class con containing all the getters method references of a Contact class
 * and the comparator method references, also labels for search and delete methods.
 * https://www.youtube.com/watch?v=DwriSApbm50&list=PLU8oAlHdN5BktAXdEVCLUYzvDyqRQJ2lk&index=48
 * https://www.geeksforgeeks.org/enum-in-java/
 * @author zelda
 */
enum Criteria {
    
    ID(Contact::getDNI, Contact::setDNI, null),
    NAME(Contact::getName, Contact::setName ,Contact::compareByName),
    LASTNAME(Contact::getLastNames, Contact::setLastNames, Contact::compareByLastName),
    BIRTHDATE(contact -> {
            if (contact.getBirthDate() != null)
                return contact.getBirthDate();
            else
                return "N/A";
    }, Contact::setBirthDate, Contact::compareByBirthDate),
    RATING(Contact::getRating, Contact::setRating, Contact::compareByRating),
    EMAILS(contact -> { 
            if (contact.getEmails().length > 0)
                return String.join(", ", contact.getEmails());
            else
                return "N/A";
    }, Contact::addEmail, null),
    PHONENUMBERS(contact -> { 
            if (contact.getPhoneNumbers().length > 0)
                return String.join(", ", contact.getPhoneNumbers());
            else
                return "N/A";
    }, Contact::addPhoneNumber, null),
    FULLNAME(null, null, null); // This constant is only for delete function.
    
    private final Function<Contact, Object> getter;
    private final BiConsumer<Contact, String> setter;
    private final Comparator<Contact> comparator;
    
    private Criteria(Function<Contact, Object> getter, BiConsumer<Contact, String> setter, 
                     Comparator<Contact> comparator){
        this.getter = getter;
        this.setter = setter;
        this.comparator = comparator;
    }
    
    public Function<Contact, Object> getGetter() {
        return this.getter;
    }       

    public BiConsumer<Contact, String> getSetter() {
        return this.setter;
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