package agenda;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Interface with methods needed to work with a manager.
 * @author zelda
 */
public interface CRUD<T extends Serializable> {
    boolean addElement(T newItem);
    void sortElement(int option);
    boolean searchElement(int option, String value);
    boolean updateElement(T item, int option, Object value);
    void list(ArrayList<T> list, String title);
    void sort(ArrayList<T> list, Criteria criteria);
    ArrayList<T> search(ArrayList<T> list, Criteria criteria, String value);
    void delete(int option);
    T[] getElementList();
    T getElementByID(String ID);
    boolean checkIfExists(T item);
}