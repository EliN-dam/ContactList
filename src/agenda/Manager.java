package agenda;

import java.io.Serializable;

/**
 * Interface with methods needed to implements a Manager.
 * @author zelda
 */
public interface Manager<T extends Serializable> {
    public void menu(String[] menuOptions);
    public T newContact();
    public Object[] retrieveList();
}
