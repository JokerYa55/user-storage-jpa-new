/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package keycloak.interfaces;

import java.util.List;
import javax.persistence.EntityManager;
import org.jboss.logging.Logger;

/**
 *
 * @author vasil
 */
public interface daoInterface<T, V> {

    public EntityManager getEM();
    
    public Logger getLogger();

    public T getItem(V id);

    public List<T> getList();

    public List<T> getList(V startIdx, V stopIdx);

    default public T addItem(T Item) {
        T res = null;
        try {
            EntityManager em = getEM();
            res = em.merge(Item);
        } catch (Exception e) {
            getLogger().debug("Error => " + e.getMessage());
        }
        return res;
    }

    default public boolean deleteItem(T Item) {
        boolean res = true;
        try {
            EntityManager em = getEM();
            em.detach(Item);
        } catch (Exception e) {
            res = false;
            getLogger().debug("Error => " + e.getMessage());
        }
        return res;
    }

    default public boolean updateItem(T Item) {
        boolean res = true;
        try {

        } catch (Exception e) {
            res = false;
            getLogger().debug("Error => " + e.getMessage());
        }
        return res;
    }
}
