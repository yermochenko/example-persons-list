/* полное удаление прежней базы данных */
DROP DATABASE IF EXISTS `persons_db`;

/* создание новой базы данных */
CREATE DATABASE `persons_db` DEFAULT CHARACTER SET utf8;

/* использование в качестве текущей только что созданной базы данных */
USE `persons_db`;

/* создание в базе данных новой таблицы */
CREATE TABLE `person` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `first_name` VARCHAR(255) NOT NULL,
    `middle_name` VARCHAR(255) NOT NULL,
    `last_name` VARCHAR(255) NOT NULL,
    `height` DOUBLE NOT NULL,
    `weight` DOUBLE NOT NULL,
    `is_citizen` BOOLEAN NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `type` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

CREATE TABLE `contact` (
    `id` INTEGER NOT NULL AUTO_INCREMENT,
    `person_id` INTEGER NOT NULL,
    `type_id` INTEGER NOT NULL,
    `value` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`person_id`) REFERENCES `person` (`id`) ON UPDATE RESTRICT ON DELETE CASCADE,
    FOREIGN KEY (`type_id`) REFERENCES `type` (`id`) ON UPDATE RESTRICT ON DELETE RESTRICT
) ENGINE=INNODB DEFAULT CHARACTER SET utf8;

/* вставка нескольких значений в таблицу */
INSERT INTO `person`
(`id`, `first_name`, `middle_name`, `last_name`, `height`, `weight`, `is_citizen`)
VALUES
(1,    "Иван",       "Иванович",    "Иванов",    173.5,    85.4,     TRUE),
(2,    "Пётр",       "Петрович",    "Петров",    189.0,    79.5,     FALSE),
(3,    "Сидор",      "Сидорович",   "Сидоров",   168.0,    97.8,     TRUE),
(4,    "Василий",    "Васильевич",  "Васильев",  195.5,    103.2,    TRUE),
(5,    "Вера",       "Петровна",    "Иванова",   185.0,    88.4,     TRUE),
(6,    "Надежда",    "Ивановна",    "Петрова",   177.5,    76.9,     FALSE),
(7,    "Любовь",     "Петровна",    "Сидорова",  164.0,    79.9,     TRUE),
(8,    "Любовь",     "Сидоровна",   "Иванова",   157.0,    67.2,     TRUE),
(9,    "Надежда",    "Сидоровна",   "Петрова",   171.5,    73.5,     FALSE),
(10,   "Вера",       "Ивановна",    "Сидорова",  162.0,    56.1,     TRUE);

INSERT INTO `type`
(`id`, `name`)
VALUES
(1, "телефон"),
(2, "e-mail");

INSERT INTO `contact`
(`id`, `person_id`, `type_id`, `value`)
VALUES
(1, 1, 1, "+375-29-123-45-67"),
(2, 1, 2, "ivanov@company.by"),
(3, 2, 1, "+375-33-987-65-43");