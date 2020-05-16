package agenda;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Interface with methods needed to work with a manager.
 * @author zelda
 */
public interface CRUD<T extends Serializable> {
    boolean add(Object[] values);
    void list(ArrayList<T> list);
    void sort(ArrayList<T> list, int criteria);
    void search(ArrayList<T> list, int criteria);
    void update(int criteria);
    void delete(int criteria);
    T[] getContactList();
}