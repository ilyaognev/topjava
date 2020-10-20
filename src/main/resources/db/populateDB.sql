DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories)
VALUES (100000, '2020-04-12 04:05:06', 'Users night dozor', 1000),
       (100000, '2020-04-12 12:05:06', 'Users lunch', 990),
       (100000, '2020-04-12 16:05:06', 'Users second lunch dozor', 100),
       (100001, '2020-04-12 10:05:06', 'Admin breakfast', 1000),
       (100001, '2020-04-12 22:05:06', 'Admin dinner', 900);
