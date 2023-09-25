package org.example.services;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.example.entity.*;


import java.util.List;

public class OSBBMemberCriteria {
    private EntityManagerFactory entityManagerFactory;

    public OSBBMemberCriteria() {
        this.entityManagerFactory = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public void close() {
        entityManagerFactory.close();
    }

    public List<Member> getMembersWithAutoNotAllowed() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Member> criteriaQuery = criteriaBuilder.createQuery(Member.class);
            Root<OSBBMember> root = criteriaQuery.from(OSBBMember.class);

            Join< OSBBMember,OwnershipRight> ownershipRightJoin = root.join("osbbMember", JoinType.INNER);
            Join<OwnershipRight, Apartment> apartmentJoin = ownershipRightJoin.join("apartment", JoinType.INNER);
            Join<Apartment, Building> buildingJoin = apartmentJoin.join("building", JoinType.INNER);
            Join<Apartment, Resident> residentJoin = apartmentJoin.join("residents", JoinType.LEFT);

            Predicate carAccessIsNull = criteriaBuilder.isNull(residentJoin.get("hasCarAccess"));
            Subquery<Long> ownershipCountSubquery = criteriaQuery.subquery(Long.class);
            Root<OwnershipRight> ownershipRightRoot = ownershipCountSubquery.from(OwnershipRight.class);
            ownershipCountSubquery.select(criteriaBuilder.count(ownershipRightRoot));
            ownershipCountSubquery.where(criteriaBuilder.equal(ownershipRightRoot.get("member"), root));

            Predicate ownershipCountLessThan2 = criteriaBuilder.lessThan(ownershipCountSubquery, 2L);

            Predicate finalPredicate = criteriaBuilder.and(carAccessIsNull, ownershipCountLessThan2);

            criteriaQuery.select(
                    criteriaBuilder.construct(
                            Member.class,
                            root.get("id"),
                            root.get("firstName"),
                            root.get("lastName"),
                            root.get("email"),
                            apartmentJoin.get("apartmentNumber"),
                            buildingJoin.get("address")
                    )
            ).distinct(true).where(finalPredicate);

            TypedQuery<Member> query = entityManager.createQuery(criteriaQuery);
            return query.getResultList();
        } finally {
            entityManager.close();
        }
    }
}

