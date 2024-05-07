insert into users (name, email, password)
values ('Admin', 'admin@mail.ru', '$2a$10$x9MPkI3Kg6QpWJHl19y/1.1FgETFg0U1568mCan9xpHsW2z/H5NrK'),
       ('Pavel', 'guest@mail.ru', '$2a$10$x9MPkI3Kg6QpWJHl19y/1.1FgETFg0U1568mCan9xpHsW2z/H5NrK');

insert into tasks (title, description, status, expiration_date)
values ('Дописать ТГ бота', 'Зачем там спринг?', 'IN_PROGRESS', '2024-05-25 00:00:00'),
       ('Подумать что можно сделать с этим приложением', 'Ну очевидно же', 'IN_PROGRESS', '2024-05-25 00:00:00'),
       ('Нужно еще реализовать микросервис', 'общение через кафку/рэббит', 'IN_PROGRESS', '2024-05-25 00:00:00');

insert into users_tasks (user_id, task_id)
values (1, 1),
       (1, 2),
       (2, 3)