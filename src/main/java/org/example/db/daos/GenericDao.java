package org.example.db.daos;


import org.example.api.geocode.Coordinates;
import org.example.db.models.Location;
import org.example.db.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class GenericDao <T, ID extends Serializable> {
    private final Class<T> entityType;

    public GenericDao(Class<T> entityType) {
        this.entityType = entityType;
    }

    public void save(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            transaction.commit();
        }
    }

    public T getById(ID id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(entityType, id);
        }
    }

    public List<T> getAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityType);
            Root<T> genericRoot = criteriaQuery.from(entityType);

            criteriaQuery.select(genericRoot);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }

    public T getByCoords(Coordinates coordinates) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityType);
            Root<T> genericRoot = criteriaQuery.from(entityType);

            Predicate latPredicate = criteriaBuilder.equal(genericRoot.get("latitude"), coordinates.getLatitude());
            Predicate lonPredicate = criteriaBuilder.equal(genericRoot.get("longitude"), coordinates.getLongitude());


            criteriaQuery.select(genericRoot).where(latPredicate, lonPredicate);

            List<T> results = session.createQuery(criteriaQuery).getResultList();
            return results.isEmpty() ? null : results.getFirst();
        }
    }

    public void update(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();
        }
    }

    public void delete(T entity) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }

    public List<T> getByLocation(Location location) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(entityType);
            Root<T> genericRoot = criteriaQuery.from(entityType);

            Predicate locationPredicate = criteriaBuilder.equal(genericRoot.get("location"), location.getId());

            criteriaQuery.select(genericRoot).where(locationPredicate);

            return session.createQuery(criteriaQuery).getResultList();
        }
    }
}
