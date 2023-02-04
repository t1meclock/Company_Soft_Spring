-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Фев 04 2023 г., 03:25
-- Версия сервера: 5.6.51
-- Версия PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `spring_db`
--

-- --------------------------------------------------------

--
-- Структура таблицы `client_table`
--

CREATE TABLE `client_table` (
  `id` bigint(20) NOT NULL,
  `org_address` varchar(255) NOT NULL,
  `org_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `client_table`
--

INSERT INTO `client_table` (`id`, `org_address`, `org_name`) VALUES
(13, 'fwefwfwGGWE', 'Abobafwfw');

-- --------------------------------------------------------

--
-- Структура таблицы `contact_table`
--

CREATE TABLE `contact_table` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `phone_number` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `contact_table`
--

INSERT INTO `contact_table` (`id`, `email`, `phone_number`) VALUES
(6, 'petrovael@mail.ru', '79197662822'),
(9, 'serga01@mail.ru', '79153350918');

-- --------------------------------------------------------

--
-- Структура таблицы `department_post`
--

CREATE TABLE `department_post` (
  `post_id` bigint(20) NOT NULL,
  `department_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `department_post`
--

INSERT INTO `department_post` (`post_id`, `department_id`) VALUES
(4, 2),
(5, 3);

-- --------------------------------------------------------

--
-- Структура таблицы `department_table`
--

CREATE TABLE `department_table` (
  `id` bigint(20) NOT NULL,
  `department_name` varchar(255) NOT NULL,
  `staff_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `department_table`
--

INSERT INTO `department_table` (`id`, `department_name`, `staff_id`) VALUES
(2, 'Бухгалтерия', NULL),
(3, 'Отдел менеджмента', NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `hibernate_sequence`
--

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `hibernate_sequence`
--

INSERT INTO `hibernate_sequence` (`next_val`) VALUES
(14);

-- --------------------------------------------------------

--
-- Структура таблицы `inventory_table`
--

CREATE TABLE `inventory_table` (
  `id` bigint(20) NOT NULL,
  `inventory_name` varchar(255) NOT NULL,
  `inventory_number` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `order_table`
--

CREATE TABLE `order_table` (
  `id` bigint(20) NOT NULL,
  `deadline` varchar(255) DEFAULT NULL,
  `product_name` varchar(255) NOT NULL,
  `type_product_name` varchar(255) NOT NULL,
  `id_client` bigint(20) DEFAULT NULL,
  `type_product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `post_staff`
--

CREATE TABLE `post_staff` (
  `post_id` bigint(20) DEFAULT NULL,
  `staff_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `post_staff`
--

INSERT INTO `post_staff` (`post_id`, `staff_id`) VALUES
(4, 10),
(5, 7);

-- --------------------------------------------------------

--
-- Структура таблицы `post_table`
--

CREATE TABLE `post_table` (
  `id` bigint(20) NOT NULL,
  `post_name` varchar(255) DEFAULT NULL,
  `salary` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `post_table`
--

INSERT INTO `post_table` (`id`, `post_name`, `salary`) VALUES
(4, 'Бухгалтер', 45000),
(5, 'Менеджер', 30000);

-- --------------------------------------------------------

--
-- Структура таблицы `product_table`
--

CREATE TABLE `product_table` (
  `id` bigint(20) NOT NULL,
  `date_end` varchar(255) NOT NULL,
  `org_name` varchar(255) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `inventory_id` bigint(20) DEFAULT NULL,
  `type_product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `report_table`
--

CREATE TABLE `report_table` (
  `id` bigint(20) NOT NULL,
  `report_date` varchar(255) NOT NULL,
  `client_id` bigint(20) DEFAULT NULL,
  `product_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `staff_table`
--

CREATE TABLE `staff_table` (
  `id` bigint(20) NOT NULL,
  `department_id` bigint(20) NOT NULL,
  `post_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `staff_table`
--

INSERT INTO `staff_table` (`id`, `department_id`, `post_id`) VALUES
(7, 3, 5),
(10, 2, 4);

-- --------------------------------------------------------

--
-- Структура таблицы `typeproduct_table`
--

CREATE TABLE `typeproduct_table` (
  `id` bigint(20) NOT NULL,
  `type_product_name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `active` bit(1) NOT NULL,
  `date_of_birth` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `patronymic` varchar(255) DEFAULT NULL,
  `staff_id` bigint(20) NOT NULL,
  `surname` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `client_id` bigint(20) DEFAULT NULL,
  `contact_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user`
--

INSERT INTO `user` (`id`, `active`, `date_of_birth`, `name`, `password`, `patronymic`, `staff_id`, `surname`, `username`, `client_id`, `contact_id`) VALUES
(1, b'1', '2002-11-25', 'Admin', '$2a$08$RyTenJEZJwUw.V321vqLL.pUcMSBV.MNgZ0WUXk9T0YcPWu85Es2i', 'Admin', 0, 'Admin', 'Admin', NULL, NULL),
(8, b'1', '1995-02-15', 'Елена', '$2a$08$.QB7ppptl1caFSzTJrEyreyBVH9qnKzIOR/qxZZkA5NquzYhWaRnC', 'Дмитриевна', 7, 'Петрова', 'Meneg', NULL, 6),
(11, b'1', '1992-04-13', 'Сергей', '$2a$08$yg/iF7RYLm9xv1DM/7W7oeKdn4tk5AJJV3sFgsNPnZA5FnlC9FGrO', 'Петрович', 10, 'Сергеев', 'Buhgalter', NULL, 9),
(12, b'1', '2001-08-19', 'Никита', '$2a$08$z9xRpYKkIX461N3B5nA7gOTeIg00Yqi9MJOk.RcilDOD8HNz17hDK', 'Дмитриевич', 0, 'Абобов', 'Aboba', 13, NULL);

-- --------------------------------------------------------

--
-- Структура таблицы `user_role`
--

CREATE TABLE `user_role` (
  `user_id` bigint(20) NOT NULL,
  `roles` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `user_role`
--

INSERT INTO `user_role` (`user_id`, `roles`) VALUES
(1, 'ADMIN'),
(8, 'STAFF'),
(11, 'STAFF'),
(12, 'USER'),
(12, 'CLIENT');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `client_table`
--
ALTER TABLE `client_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_66ou3df5s7hlmwfp8p5s9chj` (`org_address`),
  ADD UNIQUE KEY `UK_mpwpbevq53rc14tfijshgjer2` (`org_name`);

--
-- Индексы таблицы `contact_table`
--
ALTER TABLE `contact_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_alas8jxfc3e68s19yiputfus8` (`email`),
  ADD UNIQUE KEY `UK_e6mheka5d0fptd53hwxl7x3li` (`phone_number`);

--
-- Индексы таблицы `department_post`
--
ALTER TABLE `department_post`
  ADD PRIMARY KEY (`department_id`,`post_id`),
  ADD KEY `FK4elnh89f23c7oxg6np3yb4j72` (`post_id`);

--
-- Индексы таблицы `department_table`
--
ALTER TABLE `department_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_nyot09rdvnfis2y1ghpsn62ca` (`department_name`),
  ADD KEY `FKiuc7gsic1pqfogt56wbpj0e7u` (`staff_id`);

--
-- Индексы таблицы `inventory_table`
--
ALTER TABLE `inventory_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_q8q0cu90l9vlt99xbjru68l0n` (`inventory_number`);

--
-- Индексы таблицы `order_table`
--
ALTER TABLE `order_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_ti0r17l3eybh876qhwtdp8sgm` (`product_name`),
  ADD UNIQUE KEY `UK_8g1g8w7mfrx46wjca9498x7f3` (`type_product_name`),
  ADD KEY `FKdd291sr6sm1feifcg38mlvico` (`id_client`),
  ADD KEY `FKhkpoodqhfpxllsyhen3pq25yg` (`type_product_id`);

--
-- Индексы таблицы `post_staff`
--
ALTER TABLE `post_staff`
  ADD PRIMARY KEY (`staff_id`),
  ADD KEY `FKdun3t8h8yimjgy1kxlykgkxs9` (`post_id`);

--
-- Индексы таблицы `post_table`
--
ALTER TABLE `post_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_akx2cfossyod9a0ebajj8sgds` (`post_name`);

--
-- Индексы таблицы `product_table`
--
ALTER TABLE `product_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_juwwomdo6oa88yvnt1iygbdon` (`date_end`),
  ADD UNIQUE KEY `UK_66m4is6bv10g56tncaqp4psq4` (`org_name`),
  ADD UNIQUE KEY `UK_ejxirxykhwu6o6rbx1a6b60wd` (`product_name`),
  ADD KEY `FKihwofx1euilct87ij0drch6q0` (`inventory_id`),
  ADD KEY `FK6fgr78md9r289i6ej3kdlkjb` (`type_product_id`);

--
-- Индексы таблицы `report_table`
--
ALTER TABLE `report_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_9wec9qgtyy11chisbhr0ri4ee` (`report_date`),
  ADD KEY `FKd0eue9v8gfn1dadcu9eowfg91` (`client_id`),
  ADD KEY `FKp5kbuvr7hj148oa2lxokjxnb9` (`product_id`);

--
-- Индексы таблицы `staff_table`
--
ALTER TABLE `staff_table`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `typeproduct_table`
--
ALTER TABLE `typeproduct_table`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_bnbufofnj07brog09f6k81wla` (`type_product_name`);

--
-- Индексы таблицы `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK_sb8bbouer5wak8vyiiy4pf2bx` (`username`),
  ADD KEY `FKonwl5pft525emaxk7tcxef1w0` (`client_id`),
  ADD KEY `FKe6yclxhijxsuqdw0endop35st` (`contact_id`);

--
-- Индексы таблицы `user_role`
--
ALTER TABLE `user_role`
  ADD KEY `FK859n2jvi8ivhui0rl0esws6o` (`user_id`);

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `department_post`
--
ALTER TABLE `department_post`
  ADD CONSTRAINT `FK4elnh89f23c7oxg6np3yb4j72` FOREIGN KEY (`post_id`) REFERENCES `post_table` (`id`),
  ADD CONSTRAINT `FKsm26cpqh9rwcmbm3g9rtubt2p` FOREIGN KEY (`department_id`) REFERENCES `department_table` (`id`);

--
-- Ограничения внешнего ключа таблицы `department_table`
--
ALTER TABLE `department_table`
  ADD CONSTRAINT `FKiuc7gsic1pqfogt56wbpj0e7u` FOREIGN KEY (`staff_id`) REFERENCES `staff_table` (`id`);

--
-- Ограничения внешнего ключа таблицы `order_table`
--
ALTER TABLE `order_table`
  ADD CONSTRAINT `FKdd291sr6sm1feifcg38mlvico` FOREIGN KEY (`id_client`) REFERENCES `client_table` (`id`),
  ADD CONSTRAINT `FKhkpoodqhfpxllsyhen3pq25yg` FOREIGN KEY (`type_product_id`) REFERENCES `typeproduct_table` (`id`);

--
-- Ограничения внешнего ключа таблицы `post_staff`
--
ALTER TABLE `post_staff`
  ADD CONSTRAINT `FKakdk81ibh1fcqgh150wanb4eu` FOREIGN KEY (`staff_id`) REFERENCES `staff_table` (`id`),
  ADD CONSTRAINT `FKdun3t8h8yimjgy1kxlykgkxs9` FOREIGN KEY (`post_id`) REFERENCES `post_table` (`id`);

--
-- Ограничения внешнего ключа таблицы `product_table`
--
ALTER TABLE `product_table`
  ADD CONSTRAINT `FK6fgr78md9r289i6ej3kdlkjb` FOREIGN KEY (`type_product_id`) REFERENCES `typeproduct_table` (`id`),
  ADD CONSTRAINT `FKihwofx1euilct87ij0drch6q0` FOREIGN KEY (`inventory_id`) REFERENCES `inventory_table` (`id`);

--
-- Ограничения внешнего ключа таблицы `report_table`
--
ALTER TABLE `report_table`
  ADD CONSTRAINT `FKd0eue9v8gfn1dadcu9eowfg91` FOREIGN KEY (`client_id`) REFERENCES `client_table` (`id`),
  ADD CONSTRAINT `FKp5kbuvr7hj148oa2lxokjxnb9` FOREIGN KEY (`product_id`) REFERENCES `product_table` (`id`);

--
-- Ограничения внешнего ключа таблицы `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FKe6yclxhijxsuqdw0endop35st` FOREIGN KEY (`contact_id`) REFERENCES `contact_table` (`id`),
  ADD CONSTRAINT `FKonwl5pft525emaxk7tcxef1w0` FOREIGN KEY (`client_id`) REFERENCES `client_table` (`id`);

--
-- Ограничения внешнего ключа таблицы `user_role`
--
ALTER TABLE `user_role`
  ADD CONSTRAINT `FK859n2jvi8ivhui0rl0esws6o` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
