package ru.javawebinar.topjava.repository.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User userReference = em.getReference(User.class, userId);
        if (meal.isNew()) {
            meal.setUser(userReference);
            em.persist(meal);
            return meal;
        } else if (get(meal.id(), userId) != null) {
            meal.setUser(userReference);
            return em.merge(meal);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE)
            .setParameter("id", id)
            .setParameter("user_id",userId)
            .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> meals = em.createNamedQuery(Meal.GET, Meal.class)
            .setParameter("id", id)
            .setParameter("user_id", userId)
            .getResultList();
        return DataAccessUtils.singleResult(meals);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL_SORTED, Meal.class)
            .setParameter("user_id", userId)
            .getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return em.createNamedQuery(Meal.GET_ALL_BETWEEN_DATES, Meal.class)
            .setParameter("user_id", userId)
            .setParameter("start_datetime", startDateTime)
            .setParameter("end_datetime", endDateTime)
            .getResultList();
    }
}