package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @Test
    public void getWithMeals() {
        User user = super.service.getWithMeals(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
    }

    @Test
    public void getWithEmptyMeals() {
        User user = super.service.getWithMeals(GUEST_ID);
        USER_MATCHER.assertMatch(user, UserTestData.guest);
    }
}