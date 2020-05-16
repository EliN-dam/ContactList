package utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    
    /**
     * Load data from a file using deserialization.
     * @param path Path to file with the information. 
     * @return An array of objects with the deserialized data.
     */
    public static Object[] loadData(String path){
        String file = System.getProperty("user.dir") + "\\" + path;
        Object[] recoveredData = new Object[]{};
        try (ObjectInputStream read = new ObjectInputStream(new FileInputStream(file))) {
            recoveredData = (Object[]) read.readObject();
            System.out.println("Los datos se han cargado con éxito.");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("No se ha podido leer el archivo " + path + ", "
                    + "compruebe si tiene permisos de acceso.");
            //e.printStackTrace();
        }
        return recoveredData;
    }
    
    /**
     * Save data in a file using serialization.
     * @param data An array of objects with the data to storage.
     * @param path Path to file that storage the data.
     */
    public static void saveData(Object[] data, String path){
        String file = System.getProperty("user.dir") + "\\" + path;
        try (ObjectOutputStream write = new ObjectOutputStream(new FileOutputStream(file))){
            write.writeObject(data);
            System.out.println("Los datos se han guardado con éxito.");
        } catch (IOException e){
            System.out.println("No se ha podido leer el archivo " + path + ", "
                    + "compruebe si tiene permisos de acceso.");
            //e.printStackTrace();
        }
    }
}