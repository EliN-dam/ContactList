package agenda;

import java.io.Serializable;

/**
 * Interface with methods needed to implements a Manager.
 * @author zelda
 */
public interface Manager<T extends Serializable> {
    void menu(String[] menuOptions);
    T newContact();
    T[] updateList();
    Object[] retrieveList();
}