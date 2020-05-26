package agenda;

import java.io.Serializable;
import utils.Console;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

/**
 * Class for the contacts of the agenda.
 * @author zelda
 */
public class Contact implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String DNI;
    private String name;
    private String lastNames;
    private LocalDate birthDate;
    private int rating;
    private String[] emails;
    private String[] phoneNumbers;
    
    public Contact(){
        this.emails = new String[0];
        this.phoneNumbers = new String[0];        
    }
       
    public Contact(String dni, String name, String lastNames, LocalDate birthDate, 
            int rating, String[] emails, String[] phoneList){
        if (Console.checkDNI(dni))
            this.DNI = dni;
        this.name = name;
        this.lastNames = lastNames;
        if (birthDate.isBefore(LocalDate.now())) // Check if the date is older.
            this.birthDate = birthDate;
        if (Console.inRange(rating, 1, 5))
            this.rating = rating;
        this.emails = emails;
        this.phoneNumbers = phoneList;
    }

    public String getDNI() {
        return DNI;
    }

    public void setDNI(String DNI) {
        if (Console.checkDNI(DNI))
            this.DNI = DNI;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastNames() {
        return lastNames;
    }

    public void setLastNames(String lastNames) {
        this.lastNames = lastNames;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        if (birthDate.isBefore(LocalDate.now()))
            this.birthDate = birthDate;
    }

    // Overload for Criteria setter variable.
    public void setBirthDate(String birthDate){
        LocalDate value = Console.checkDate(birthDate, "yyyy-MM-dd");
        if (value != null) {
            if (value.isBefore(LocalDate.now()))
                this.birthDate = value;
        }
    }
    
    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (Console.inRange(rating, 1, 5))
            this.rating = rating;
    }
    
    // Overload for Criteria setter variable.
    public void setRating(String rating){
        int value = Integer.valueOf(rating);
        if (Console.inRange(value, 1, 5))
            this.rating = value;
    }

    public String[] getEmails() {
        return emails;
    }

    public void addEmail(String newEmail) {
        if (Console.checkEmail(newEmail)){
            int lastIndex = this.emails.length;
            this.emails = Arrays.copyOf(this.emails, lastIndex + 1);
            this.emails[lastIndex] = newEmail;
        }
    }

    public String[] getPhoneNumbers() {
        return phoneNumbers;
    }

    public void addPhoneNumber(String newPhone) {
        if (Console.checkPhoneNumber(newPhone)){
            int lastIndex = this.phoneNumbers.length;
            this.phoneNumbers = Arrays.copyOf(this.phoneNumbers, lastIndex + 1);
            this.phoneNumbers[lastIndex] = newPhone;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.DNI);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Contact other = (Contact) obj;
        return Objects.equals(this.DNI, other.DNI);
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
}