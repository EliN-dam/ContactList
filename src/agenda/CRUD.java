package agenda;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Interface with methods needed to work with a manager.
 * @author zelda
 */
public interface CRUD<T extends Serializable> {
    boolean add(T newItem);
    void list(ArrayList<T> list, String title, int criteria);
    void sort(int criteria);
    void search(int criteria);
    void update(int criteria);
    void delete(int criteria);
    T[] getContactList();
}