/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic.TripsManagement;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author bruno
 */
@Stateless
public class TPlaneFacade extends AbstractFacade<TPlane> implements TPlaneFacadeLocal {

    @PersistenceContext(unitName = "Agency-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TPlaneFacade() {
        super(TPlane.class);
    }
    
    @Override
    public boolean deleteAll(){
        
        try {
            Query qu = this.em.createNamedQuery("delete from TPlane");
            qu.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
        
    }
    
}
