INSERT INTO `category` (`id`, `name`, `description`)
    VALUES ('1', 'fishes', 'All related about seafood') ,
           ('2', 'salad','All related about salad'),
           ('3', 'soup', 'All related soup'),
           ('4', 'spaguetti', 'All related spaguetti');

INSERT INTO `restaurant` (`id`, `name`, `nit`, `address`, `phone`, `url_logo`, `owner_dni`)
VALUES ('1', 'arrogante', '12345', 'calle 10 av 2', '+571234567890', 'unknow', '32482309'),
       ('2', 'el cielo', '67890', 'av 0 calle 12', '+578901237890', 'uknow', '32482309'),
       ('3', 'valencia', '34589', 'redoma 7', '+57890123456', 'unknow', '9899005'),
       ('4', 'el arrozal', '789234', 'calle 23 av 2', '+571238903450', 'unknow', '9899005');

INSERT INTO `dishes` (`id`, `name`, `description`, `price`, `url_image`, `active`, `id_category`, `id_restaurant`)
VALUES ('1', 'mariscos', 'delicious fish', '45000', 'uknow', 1, '1', '1'),
       ('2', 'russian salad', 'delicious salad', '38000', 'unknow', 1, '2', '1'),
       ('3', 'green salas', 'delicious green salad', '26000', 'unknow', 1, '2', '2'),
       ('4', 'spinach soup', 'delicious soup', '29000', 'unknow', 1, '3', '2'),
       ('5', 'leek soup', 'delicious leek soup', '32000', 'unknow', 1, '3', '2'),
       ('6', 'carbonara spaguetti', 'delicious carbonara', '49000', 'unknow', 1, '4', '3'),
       ('7', 'spaguetti con salsa de carne', 'delicious salsa de carne', '54000', 'unknow', 1, '4', '3');