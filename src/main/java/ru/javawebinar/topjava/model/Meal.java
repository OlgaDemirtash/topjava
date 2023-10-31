package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQueries({
    @NamedQuery(name = Meal.GET, query = "SELECT m FROM Meal m WHERE m.id =?1 AND m.user.id=?2"),
    @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id AND m.user.id=:user_id"),
    @NamedQuery(name = Meal.GET_ALL_SORTED, query = "SELECT m FROM Meal m WHERE m.user.id=?1 ORDER BY m.dateTime DESC"),
    @NamedQuery(name = Meal.GET_ALL_BETWEEN_DATES, query = "SELECT m FROM Meal m WHERE m.user.id=?1 AND m.dateTime >=?2 "
        + " AND m.dateTime <?3 ORDER BY m.dateTime DESC")
})

@Entity
@Table(name = "meal", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "date_time"})})
public class Meal extends AbstractBaseEntity {
    public static final String GET = "Meal.getMealByIdForUserId";

    public static final String DELETE = "Meal.delete";
    public static final String GET_ALL_SORTED = "Meal.getAllSorted";

    public static final String GET_ALL_BETWEEN_DATES = "Meal.getAllBetweenHalfOpen";

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()", updatable = true)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotNull
    @Size(max = 255)
    private String description;

    @Column(name = "calories", nullable = false)
    @NotNull
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="USER_ID", nullable = false)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
            "id=" + id +
            ", dateTime=" + dateTime +
            ", description='" + description + '\'' +
            ", calories=" + calories +
            '}';
    }
}