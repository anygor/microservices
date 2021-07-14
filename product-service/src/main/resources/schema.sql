SET NAMES utf8;
SET time_zone = '+00:00';
SET foreign_key_checks = 0;
SET sql_mode = 'NO_AUTO_VALUE_ON_ZERO';

DROP TABLE IF EXISTS `group_variants`;
CREATE TABLE `group_variants` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `variant_name` varchar(255) DEFAULT NULL,
  `group_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_group_variant_product_group` (`group_id`),
  CONSTRAINT `FK_group_variant_product_group` FOREIGN KEY (`group_id`) REFERENCES `product_groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `group_variants` (`id`, `variant_name`, `group_id`) VALUES
(1,	'Blue',	1),
(2,	'White',	1);

DROP TABLE IF EXISTS `products`;
CREATE TABLE `products` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `price` varchar(5) DEFAULT NULL,
  `description` text,
  `created` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `group_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_product_product_group` (`group_id`),
  CONSTRAINT `FK_product_product_group` FOREIGN KEY (`group_id`) REFERENCES `product_groups` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `products` (`id`, `name`, `price`, `description`, `created`, `group_id`, `user_id`) VALUES
(6,	'Shirt',	'24',	NULL,	'2017-05-02 15:58:11',	13,	1),
(7,	'Sweat Shirt',	'60',	NULL,	'2017-05-02 15:58:18',	13,	1),
(8,	'Flag',	'24',	NULL,	'2017-05-02 16:05:46',	1,	1),
(9,	'Golf V',	'20000',	NULL,	'2017-04-19 15:53:40',	1,	1);

DROP TABLE IF EXISTS `product_groups`;
CREATE TABLE `product_groups` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created` varchar(255) DEFAULT NULL,
  `group_name` varchar(255) DEFAULT NULL,
  `price` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `product_groups` (`id`, `created`, `group_name`, `price`) VALUES
(1,	'2017-04-19 12:47:33',	'Vehicles',	''),
(13,	'2017-05-02 10:30:22',	'Clothes',	NULL);

-- 2017-05-02 14:08:31
DROP TABLE IF EXISTS `product_images`;
CREATE TABLE `product_images` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `product_id` int not NULL,
  `path` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB;

ALTER TABLE `group_variants`
CHANGE `variant_name` `variant_name` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `id`,
CHANGE `group_id` `group_id` int(11) NOT NULL AFTER `variant_name`;


update product_groups set created = '2017-06-03 00:00:00';
ALTER TABLE `product_groups`
CHANGE `created` `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP AFTER `id`,
CHANGE `group_name` `group_name` varchar(255) COLLATE 'utf8_general_ci' NOT NULL AFTER `created`;