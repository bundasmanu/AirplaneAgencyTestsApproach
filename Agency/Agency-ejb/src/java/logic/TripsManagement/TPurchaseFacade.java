/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import logic.UsersManagement.TUser;

/**
 *
 * @author bruno
 */
@Stateless
public class TPurchaseFacade extends AbstractFacade<TPurchase> implements TPurchaseFacadeLocal {

    @PersistenceContext(unitName = "Agency-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TPurchaseFacade() {
        super(TPurchase.class);
    }

    @Override
    public List<TPurchase> findAllNotDonePurchasesOfUser(TUser user) {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        Root<TPurchase> purchase = cq.from(TPurchase.class);
        
        cq.where(cb.and(cb.equal(purchase.get(TPurchase_.done), false), cb.equal(purchase.get(TPurchase_.userid), user)));
        
        Query q = getEntityManager().createQuery(cq);
        return q.getResultList();
    }
    
}
