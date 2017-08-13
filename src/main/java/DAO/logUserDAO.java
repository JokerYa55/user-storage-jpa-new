/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import keycloak.bean.logUser;
import org.jboss.logging.Logger;

/**
 *
 * @author vasil
 */
public class logUserDAO {

    private static final Logger log = Logger.getLogger(logUserDAO.class);
    @PersistenceContext
    protected EntityManager em;

    public logUser getItemByUserId(String userId) {
        log.info("getItemByUserId => " + userId);
        logUser res = null;
        try {
            Query q = em.createQuery(
                    "SELECT e FROM logUser e WHERE user_id = :id");
            log.info("q = " + q.toString());
            q.setParameter("id", userId);
            return (logUser) q.getSingleResult();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return res;
    }
}
