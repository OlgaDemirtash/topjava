DELETE FROM meals;
DELETE FROM user_role;
DELETE FROM users;

ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id)
VALUES ('2020-01-30 10:00:00', 'Завтрак:User', 500, 100000),
       ('2020-01-30 13:00:00', 'Обед:User', 1000, 100000),
       ('2020-01-30 20:00:00', 'Ужин:User', 500, 100000),
       ('2020-01-31 00:00:00', 'Еда на граничное значение:User', 100, 100000),
       ('2020-01-31 10:00:00', 'Завтрак:User', 1000, 100000),
       ('2020-01-31 13:00:00', 'Обед:User', 500, 100000),
       ('2020-01-31 20:00:00', 'Ужин:User', 410, 100000),
       ('2020-01-28 08:00:00', 'Завтрак:Admin', 600, 100001),
       ('2020-01-28 12:00:00', 'Обед:Admin', 900, 100001),
       ('2020-01-28 19:00:00', 'Ужин:Admin', 511, 100001),
       ('2020-01-29 00:00:00', 'Еда на граничное значение:Admin', 99, 100001),
       ('2020-01-29 09:00:00', 'Завтрак:Admin', 998, 100001),
       ('2020-01-29 16:00:00', 'Обед:Admin', 502, 100001),
       ('2020-01-29 21:00:00', 'Ужин:Admin', 356, 100001);