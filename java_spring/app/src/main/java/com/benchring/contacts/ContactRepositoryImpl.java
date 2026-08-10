package com.benchring.contacts;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ContactRepositoryImpl implements ContactRepositoryCustom {

    private final EntityManager entityManager;

    public ContactRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<Contact> findByFilters(Integer externalId, String phoneNumber, int limit, int offset) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contact> query = cb.createQuery(Contact.class);
        Root<Contact> contact = query.from(Contact.class);

        List<Predicate> predicates = new ArrayList<>();
        if (externalId != null) {
            predicates.add(cb.equal(contact.get("externalId"), externalId));
        }
        if (phoneNumber != null) {
            predicates.add(cb.equal(contact.get("phoneNumber"), phoneNumber));
        }
        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        return entityManager.createQuery(query)
                .setFirstResult(Math.max(offset, 0))
                .setMaxResults(Math.max(limit, 0))
                .getResultList();
    }
}
