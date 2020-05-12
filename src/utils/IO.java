package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;


/**
 * Functions to work with files.
 * @author zelda
 */
public class IO {
    
    /**
     * Read lines from a file and save into an array.
     * @param path Path to the file.
     * @return An array of the strings with the lines in the file.
     */
    public static String[] loadMenu(String path){
        String file = System.getProperty("user.dir") + "\\" + path;
        String data = "";
        try (BufferedReader read = new BufferedReader(
                new FileReader(file, StandardCharsets.UTF_8))){
            String line;
            while((line = read.readLine()) != null)
                data += line + "\n";
            return data.split("\\n");
        } catch (FileNotFoundException e){
            System.out.println("Archivo \"" + path + "\" no encontrado.");
            //e.printStackTrace();
        } catch (IOException e){
            System.out.println("No se ha podido leer el archivo " + path + ", "
                    + "compruebe si tiene permisos de acceso.");
            //e.printStackTrace();
        }
        return null;
    }   
    
    /**
     * Map a folder and return all files path.
     * @param path Path to the folder.
     * @return An array of the strings with the file paths.
     */
    public static String[] getFiles(String path){
        String file = System.getProperty("user.dir") + "\\" + path;
        try {
            Path src = Paths.get(path);
            String files = "";
            Iterator it = Files.walk(src).iterator();
            while (it.hasNext()){
                files += it.next() + "\n";
            }
            return files.split("\\n");
        } catch (IOException e){
            System.out.println("No se ha podido acceder a la ruta especificada.");
            //e.printStackTrace();
            return null;
        }
    }
}