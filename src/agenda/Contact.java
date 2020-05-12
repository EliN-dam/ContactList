package agenda;

import utils.Console;
import java.time.LocalDate;
import java.util.Arrays;

/**
 * Class for the contacts of the agenda.
 * @author zelda
 */
public class Contact {
    
    private String DNI;
    private String name;
    private String lastNames;
    private LocalDate birthDate;
    private int rating;
    private String[] emails;
    private String[] phoneNumbers;
    
    public Contact(String dni, String name, String lastNames, LocalDate birthDate){
        if (Console.checkDNI(dni))
            this.DNI = dni;
        this.name = name;
        this.lastNames = lastNames;
        if (birthDate.compareTo(LocalDate.now()) < 0) // Check if the date is older.
            this.birthDate = birthDate;
    }
    
    public Contact(String dni, String name, String lastNames, LocalDate birthDate, 
            int rating, String[] emails, String[] phoneList){
        if (Console.checkDNI(dni))
            this.DNI = dni;
        this.name = name;
        this.lastNames = lastNames;
        if (birthDate.compareTo(LocalDate.now()) < 0)
            this.birthDate = birthDate;
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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
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
}