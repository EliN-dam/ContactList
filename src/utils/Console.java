package utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Some static methods to work with console.
 * @author zelda
 */
public class Console {
    
    /**
     * System end of file constant.
     */
    public static final String EOF = System.getProperty("line.separator");
    
    /**
     * Show a message and wait util the user presses Enter to continue.
     */
    public static void toContinue() {
        System.out.println(EOF + "Presiona ENTER para continuar...");
        new Scanner(System.in).nextLine();
    }
    
    /**
     * Catch a number introduced by console.
     * @param message(String) Message asking the user.
     * @param type(String) The numeric type ("byte", "short", "int", "long",
     * "float", "double").
     * @return The numeric value writed by the user. 
     * Return -1 in the case of error ocurred.
     */
    public static Number readNumber(String message, String type){
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        switch (type.toLowerCase().trim()){
            case "byte":
                return in.nextByte();
            case "short":
                return in.nextShort();
            case "int":
                return in.nextInt();
            case "long":
                return in.nextLong();
            case "float":
                return in.nextFloat();
            case "double":
                return in.nextDouble();
            default:
                return -1;        
        }
    }
    
    /**
     * Ask and catch the characters introduced by console.
     * @param message(String) Message asking the user.
     * @return The string writed by the user.
     */
    public static String readLine(String message){
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }
    
    /**
     * Catch a single character introduced by console.
     * @param message Message asking the user.
     * @return The first character writed by the user.
     */
    public static char readCharacter(String message){
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.next().charAt(0);
    }
    
    /**
     * Check if a integer number is in value.
     * @param value(int) The value to be checked.
     * @param min(int) The lowest value of the range.
     * @param max(int) The highest value of the range.
     * @return True or false depends if the value is between the min 
     * and max values.
     */
    public static boolean inRange(int value, int min, int max){
        if ((value >= min) && (value <= max))
            return true;
        return false;
    }     
    
    /**
     * Show a menu in console.
     * @param options(String[]) Array with the options of the menu.
     * @param showNumbers(Boolean) Allow to show numbers of each option.
     */
    public static void showMenu(String[] options, boolean showNumbers){
        header(options[0]);
        if (showNumbers){
            for (int i = 1; i < options.length; i++)
                System.out.printf("|%-1s%2.2s.- %-41.41s%-1s|" + EOF, "", i, options[i], "");
        } else {
            for (int i = 1; i < options.length; i++)
                System.out.printf("|%-1s%-46.46s%-1s|" + EOF, "", options[i], "");
        }        
        System.out.println("+================================================+");
    }
    
    /**
     * Show a box with a text in console.
     * @param title(String) Box text.
     */
    public static void header(String title){
        byte titlelength = (byte)title.length();
        System.out.println("+================================================+");
        System.out.printf("|%-" + ((48 - titlelength) / 2) + "s%" + titlelength + "s%-" + ((49 - titlelength) / 2) + 
                          "s|" + EOF, "", title.toUpperCase(), "");
        System.out.println("+================================================+");
    }
           
    /**
     * Ask the user for confirmation.
     * @param message The confirmation question.
     * @return <ul>
     *             <li>True - If the user type the character 'S'</li>
     *             <li>False - If the user type the character 'N'</li> 
     *         </ul>
     */
    public static boolean makeSure(String message){
        char answer;
        boolean validChar = false;
        do {
            answer = Console.readCharacter(message + " (S/N) ");
            if (Console.validateQuestion(answer))
                validChar = true;
            else
                System.out.println("Opción no válida... ");
        } while(!validChar);
        if (Character.toLowerCase(answer) == 's')
            return true;
        else
            return false;
    }
    
    
    /**
     * Validate a returned character match with the requested value.
     * @param character The character write by the user.
     * @return <ul>
     *             <li>True - If mached</li>
     *             <li>False - If doesn't mached</li> 
     *         </ul>
     */
    public static boolean validateQuestion(char character){
        switch (Character.toLowerCase(character)){
            case 's':
            case 'n':
                return true;
            default:
                return false;
        }
    }
    
    /**
     * Request user for a value until enter a valid integer value.
     * @param message(String) Message asking the user.
     * @return A valid integer value introduced by the user. 
     */
    public static int validInt(String message){
        boolean success = false;
        int value = -1;
        do {
            try {
                value = (int)readNumber(message, "int");
                success = true;
            } catch (InputMismatchException e){
                System.out.println("Debe introducir un valor numérico... ");
            }
        } while(!success);
        return value;
    }
    
    /**
     * Request user for a value until enter a valid integer value.
     * @param message(String) Message asking the user.
     * @param minValue(int) The minimum value allowed.
     * @param maxValue(int) The maximum value allowed.
     * @return A valid integer value introduced by the user. 
     */
    public static int validInt(String message, int minValue, int maxValue){
        boolean success = false;
        int value = -1;
        do {
            try {
                value = (int)readNumber(message, "int");
                if (inRange(value, minValue, maxValue))
                    success = true;
                else
                    System.out.println("debe introducir un numero entre " + 
                            minValue + " y " + maxValue + "... ");
            } catch (InputMismatchException e){
                System.out.println("Debe introducir un valor numérico... ");
            }
        } while(!success);
        return value;
    }
    
    /**
     * Request user for a value until enter a valid decimal value.
     * @param message(String) Message asking the user.
     * @return A valid double value introduced by the user. 
     */
    public static double validDouble(String message){
        boolean success = false;
        double value = -1;
        do {
            try {
                value = (double)readNumber(message, "double");
                success = true;
            } catch (InputMismatchException e){
                System.out.println("Debe introducir un valor numérico válido... ");
            }
        } while(!success);
        return value;
    }
    
    /**
     * Request user for a String until enter valid String format base of its length.
     * @param message(String) Message asking the user.
     * @param maxLength Max number of characters.
     * @return A valid formated String introduced by the user. 
     */
    public static String validString(String message, int maxLength){
        boolean success = false;
        String line = "";
        do {
            line = readLine(message).trim();
            if (inRange(line.length(), 1, maxLength))
                success = true;
            else
                System.out.println("El número máximo de caracteres es " + 
                        maxLength + "... ");
        } while(!success);
        return line;
    }
    
    /**
     * Ask the user for an email and check if it's valid.
     * https://howtodoinjava.com/regex/java-regex-validate-email-address/
     * @param message(String) Message asking the user.
     * @return A valid email. 
     */
    public static String validEmail(String message){
        boolean success = false;
        String line = "";
        do {
            line = readLine(message).trim();
            success = checkEmail(line);
            if (!success)
                System.out.println("Debes introducir una dirección de correo "
                        + "válida... ");
        } while(!success);
        return line;
    }
    
    /**
     * Check if the current String is a valid email address.
     * @param email(String) An email address.
     * @return <ul>
     *              <li>True - If it's valid</li>
     *              <li>False - If it's invalid</li>
     *         </ul>
     */
    public static boolean checkEmail(String email){
        String regex = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?"
                    + "`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        if (email.matches(regex))
            return true;
        else
            return false;
    }
    
    /**
     * Ask the user for a phone number and check if it's valid.
     * @param message(String) Message asking the user.
     * @return A valid phone number. 
     */
    public static String validPhone(String message){
        boolean success = false;
        String line = "";
        do {
            line = readLine(message).trim();
            success = checkPhoneNumber(line);
            if (!success)
                System.out.println("Debes introducir un número de teléfono válido... ");
        } while(!success);
        return line;
    }
    
    /**
     * Check if a String is a valid phone number.
     * @param phone(String) A phone number.
     * @return <ul>
     *              <li>True - If it's valid</li>
     *              <li>False - If it's invalid</li>
     *         </ul>
     */
    public static boolean checkPhoneNumber(String phone){
        String regex = "\\d{9}"; 
        if (phone.matches(regex))
            return true;
        else
            return false;
    }    
        
    /**
     * Request user for a date until enter a valid date format.
     * @param message(String) Message asking the user.
     * @return A valid date.
     */
    public static LocalDate validDate(String message){
        boolean success = false;
        String line;
        LocalDate date = null;
        do {
            line = readLine(message).trim();
            line = formatDate(line);
            try {
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                date = LocalDate.parse(line, dateFormat);
                success = true;
            } catch (DateTimeParseException e){
                System.out.println("Debe introducir una fecha válida (dd-MM-aaaa)... ");
            }
        } while(!success);
        return date;
    }
    
    /**
     * Format a string to a valid format date.
     * @param date(String) A date.
     * @return A well formated date.
     */
    public static String formatDate(String date){
        String formatedDate = date;
        if (!date.matches("[0-9]{2}-[0-9]{2}-[0-9]{4}")){
            if (date.matches("[0-9]{1,2}/[0-9]{1,2}/([0-9]{2}|[0-9]{4})"))
                formatedDate = date.replace('/', '-');
            if (date.matches("[0-9]{1,2}.[0-9]{1,2}.([0-9]{2}|[0-9]{4})")){
                if (formatedDate.matches("[0-9]{1}.[0-9]{1,2}.([0-9]{2}|[0-9]{4})"))
                    formatedDate = "0" + formatedDate;
                if (formatedDate.matches("[0-9]{2}.[0-9]{1}.([0-9]{2}|[0-9]{4})"))
                    formatedDate = formatedDate.substring(0, 3) + "0" + 
                                   formatedDate.substring(3);
                if (formatedDate.matches("[0-9]{2}.[0-9]{2}.[0-9]{2}")){
                    String thisYear = String.valueOf(LocalDate.now().getYear());
                    String century = thisYear.substring(0,2);
                    /* If the last two digits of the date are larger than the two last digits of 
                     * the current date, then we can suppose that the year corresponds to the last 
                     * century.
                     */ 
                    if (Integer.valueOf(formatedDate.substring(6)) > Integer.valueOf(thisYear.substring(2)))
                        century = String.valueOf(Integer.valueOf(century) - 1);
                    formatedDate = formatedDate.substring(0, 6) + century +
                               formatedDate.substring(6);            
                }
            }
        }
        return formatedDate;
    }
    
    /**
     * Ask the user for a identification number and check if it's valid.
     * @param message(String) Message asking the user.
     * @return A valid identification number. 
     */
    public static String validDNI(String message){
        boolean success = false;
        String line = "";
        do {
            line = readLine(message).trim();
            success = checkDNI(line);
            if (!success)
                System.out.println("Debes introducir un DNI válido... ");
        } while(!success);
        return line;
    }    
    
    /**
     * Check if a String is a valid NIF or NIE (Spanish identification number).
     * http://www.interior.gob.es/web/servicios-al-ciudadano/dni/calculo-del-digito-de-control-del-nif-nie
     * @param dni(String) An identification number.
     * @return <ul>
     *              <li>True - If it's valid</li>
     *              <li>False - If it's invalid</li>
     *         </ul>
     */
    public static boolean checkDNI(String dni){
        final String letters = "TRWAGMYFPDXBNJZSQVHLCKE";
        dni = dni.trim().toUpperCase();
        if (dni.length() == 9){
            if (Character.isDigit(dni.charAt(0))){
                int numbers = Integer.parseInt(dni.substring(0, 8));                
                return String.valueOf(letters.charAt(numbers % 23)).equals(dni.substring(8, 9));
            } else {
                int numbers = Integer.parseInt(dni.substring(1, 8));
                String checkDigit = "";
                switch(dni.charAt(0)){
                    case 'X':
                        checkDigit = String.valueOf(letters.charAt(numbers % 23));
                        break;
                    case 'Y':
                        checkDigit = String.valueOf(letters.charAt((10000000 + numbers) % 23));
                        break;
                    case 'Z':
                        checkDigit = String.valueOf(letters.charAt((20000000 + numbers) % 23));
                        break;
                }
                return checkDigit.equals(dni.substring(8, 9));
            }
        }  
        return false;
    }
}