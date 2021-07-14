-- Adminer 4.3.1 MySQL dump

SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `orders`;
CREATE TABLE `orders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `address` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `created` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `total_price` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `zip` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `order_items`;
CREATE TABLE `order_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `price` varchar(255) DEFAULT NULL,
  `product_variant_id` int(11) DEFAULT NULL,
  `order_id` int(11) DEFAULT NULL,
  `product_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_items_order` (`order_id`),
  CONSTRAINT `FK_order_items_order` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `access_token` varchar(255) DEFAULT NULL,
  `created` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_user_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `users` (`id`, `access_token`, `created`, `email`, `name`, `password`) VALUES
(1,	NULL,	NULL,	'b',	'Boris Trivic',	'$2a$04$WdK723i7N7Gql8VeziMjvOudXQzSg43ahCIVK83aB1sZ91X0zkFqq'),
(2,	NULL,	NULL,	'user',	'Test User',	'$2a$04$6awb3tKYlsw6uyay5.Rv1.nAiw/Tp3N5GjVtckRhqaUI64.Gp43gO');

ALTER TABLE `order_items`
CHANGE `order_id` `order_id` int(11) NOT NULL AFTER `product_variant_id`,
CHANGE `product_id` `product_id` int(11) NOT NULL AFTER `order_id`;

------

update `orders` set created = "2017-06-03 00:00:00";
ALTER TABLE `orders`
CHANGE `created` `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `comment`,
CHANGE `name` `name` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `created`;

-----


-----
ALTER TABLE `users`
CHANGE `email` `email` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `created`,
CHANGE `name` `name` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `email`;