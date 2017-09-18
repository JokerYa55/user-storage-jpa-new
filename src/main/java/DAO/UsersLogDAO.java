/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import keycloak.bean.UsersLog;
import keycloak.interfaces.daoInterface;
import org.jboss.logging.Logger;

/**
 *
 * @author vasil
 */
public class UsersLogDAO implements daoInterface<UsersLog, Long>{

    private static final Logger log = Logger.getLogger(UsersLogDAO.class);

    /**
     *
     */
    @PersistenceContext
    protected EntityManager em;

    /**
     * Конструктор
     * @param em 
     */
    
    public UsersLogDAO(EntityManager em) {
        this.em = em;
    }

    /**
     * 
     * @param userId
     * @return 
     */
    public UsersLog getItemByUserId(String userId) {
        log.info("getItemByUserId => " + userId);
        UsersLog res = null;
        try {
            Query q = em.createQuery(
                    "SELECT e FROM logUser e WHERE user_id = :id");
            log.info("q = " + q.toString());
            q.setParameter("id", userId);
            return (UsersLog) q.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }

    /**
     * 
     * @return 
     */
    @Override
    public EntityManager getEM() {
        return this.em;
    }

    /**
     * 
     * @return 
     */
    @Override
    public Logger getLogger() {
        return this.log;
    }
    /**
     * 
     * @param id
     * @return 
     */
    @Override
    public UsersLog getItem(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * 
     * @return 
     */    
    @Override
    public List<UsersLog> getList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * 
     * @param startIdx
     * @param stopIdx
     * @return 
     */
    @Override
    public List<UsersLog> getList(Long startIdx, Long stopIdx) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
