package shu.dbdealership;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by Andrew Shubin on 11/4/16.
 */

@Repository
@Transactional
public class VehicleDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void create(Vehicle vehicle) {
        entityManager.persist(vehicle);
        entityManager.refresh(vehicle);
        return;
    }

    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    public Vehicle update(Vehicle vehicle) {
        return entityManager.merge(vehicle);
    }

    public void deleteById(int id) {
        Vehicle vehicle = getById(id);
        if (vehicle != null) {
            entityManager.remove(vehicle);
        }
        return;
    }
}
