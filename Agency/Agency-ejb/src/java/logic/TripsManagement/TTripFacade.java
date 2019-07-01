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

/**
 *
 * @author bruno
 */
@Stateless
public class TTripFacade extends AbstractFacade<TTrip> implements TTripFacadeLocal {

    @PersistenceContext(unitName = "Agency-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TTripFacade() {
        super(TTrip.class);
    }

    @Override
    public List<TTrip> findAllNotDoneAndNotCanceled() {
        CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        Root<TTrip> trip = cq.from(TTrip.class);
        
        
        /* is showing all seats which pertence that trip or all seats that purchase is done... and need to be a AND and not  OR
        cq.where(cb.equal(seat.get(TSeat_.tripid).get(TTrip_.id), trip.getId()));      
        cq.where(cb.equal(seat.get(TSeat_.purchaseid).get(TPurchase_.done), true));
        */
        
        cq.where(cb.and(cb.equal(trip.get(TTrip_.canceled), false), cb.equal(trip.get(TTrip_.done), false)));

        Query q = getEntityManager().createQuery(cq);
        
        //q.setParameter("done", true);
        
        return q.getResultList();
    }

    @Override
    public List<TTrip> findLast(int maxResults, String column) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        
        CriteriaQuery cq = cb.createQuery();
        Root<TTrip> root = cq.from(TTrip.class);
        cq.select(root);
        
        cq.where(cb.and(cb.equal(root.get(TTrip_.canceled), false), cb.equal(root.get(TTrip_.done), false)));

        cq.orderBy(cb.desc(root.get(column)));    
        
        Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(maxResults);
        return q.getResultList();
    }
    
    
}
