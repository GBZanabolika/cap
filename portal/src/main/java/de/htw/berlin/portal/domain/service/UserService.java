/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.htw.berlin.portal.domain.service;

import de.htw.berlin.portal.domain.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * 
 */

@Repository
@Transactional
public class UserService {
    
    @PersistenceContext(unitName="portalPU")
    EntityManager em;
    
    public boolean doesUserAlreadyExists(String name){
        return !em.createNamedQuery(User.FIND_USER_BY_NAME, User.class)
                .setParameter("name", name)
                .getResultList()
                .isEmpty();
    }
    
    public void saveUser(User u){
        em.persist(u);
    }
    
    public User authenticateUser(String name, String password) throws AuthenticationException{
        List<User> result = em.createNamedQuery(User.FIND_USER_BY_NAME, User.class)
                            .setParameter("name", name)
                            .getResultList();
        if(result.isEmpty()){
            throw new AuthenticationException();
        }
        User user = result.get(0);
        if(!user.getPassword().equals(password)){
            throw new AuthenticationException();
        }
        return user;
    }
}
