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
        if (birthDate.compareTo(LocalDate.now()) < 0) // Check if the date is older.
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
        if (birthDate.compareTo(LocalDate.now()) < 0)
            this.birthDate = birthDate;
    }

    // Overload for Criteria setter variable.
    public void setBirthDate(String birthDate){
        LocalDate value = Console.checkDate(birthDate, "yyyy-MM-dd");
        if (value != null) {
            if (value.compareTo(LocalDate.now()) < 0)
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
        if (!Objects.equals(this.DNI, other.DNI)) {
            return false;
        }
        return true;
    }
}