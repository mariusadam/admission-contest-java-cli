CREATE TABLE `users`
  ( 
     `id`         INTEGER auto_increment,
     `last_name`  VARCHAR(30),
     `first_name` VARCHAR(30),
     `is_active`  TINYINT(1) DEFAULT 1,
     `logged_in`  TINYINT(1) DEFAULT 0,
     `last_login` DATETIME NULL DEFAULT NULL,
     `email`      VARCHAR(255) UNIQUE,
     `password`   VARCHAR(128),
     `salt`       VARCHAR(32),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     PRIMARY KEY (`id`)
  );

CREATE TABLE `role`
  (
     `id`                INTEGER auto_increment,
     `name`              VARCHAR(255),
     `importance`        INTEGER,
     `resources`         BLOB,
     `allowedoperations` BLOB,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     PRIMARY KEY (`id`)
  );

CREATE TABLE `user_role`
  (
     `id`         INTEGER auto_increment,
     `user_id`    INTEGER,
     `role_id`    INTEGER,
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
     PRIMARY KEY (`id`) 
  );

INSERT INTO `users`
           (`id`,
            `last_name`,
            `first_name`,
            `is_active`,
            `logged_in`,
            `last_login`,
            `email`,
            `password`,
            `salt`,
            `created_at`,
            `updated_at`)
VALUES      (1,
            'Last Name 1',
            'First name 1',
            1,
            NULL,
            NULL,
            'a@b.com',
            '1456965399174b8e744879654679a01c4079529e35f748668e8f310bf6bff4e0de001c9078b056e94b070f79705de9fdd8ddd6822b8f72d9d188f9450585eb41',
            '!2?6m????>~?^?\"???n?vp????\rX',
            '2017-01-07 19:35:04',
            '2017-01-07 19:35:04');

INSERT INTO `role`
            (`id`,
             `name`,
             `importance`,
             `resources`,
             `allowedoperations`,
             `created_at`,
             `updated_at`)
VALUES      (1,
            'Admin',
            100,
            0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000057704000000057e72001b636f6d2e7562622e6d61702e646f6d61696e2e5265736f7572636500000000000000001200007872000e6a6176612e6c616e672e456e756d0000000000000000120000787074000943414e4449444154457e71007e000274000a4445504152544d454e547e71007e00027400064f5054494f4e7e71007e0002740004555345527e71007e0002740004524f4c4578,
            0xaced0005737200136a6176612e7574696c2e41727261794c6973747881d21d99c7619d03000149000473697a657870000000047704000000047e72001c636f6d2e7562622e6d61702e646f6d61696e2e4f7065726174696f6e00000000000000001200007872000e6a6176612e6c616e672e456e756d000000000000000012000078707400064352454154457e71007e0002740004524541447e71007e00027400065550444154457e71007e000274000644454c45544578,
            '2017-01-07 19:36:25',
            '2017-01-07 19:36:25');

INSERT INTO `user_role`
            (`id`,
             `user_id`,
             `role_id`,
             `created_at`,
             `updated_at`)
VALUES      (1,
             1,
             1,
             '2017-01-07 19:39:49',
             '2017-01-07 19:39:49');