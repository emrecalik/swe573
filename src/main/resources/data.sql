INSERT IGNORE INTO role (id, role_name)
VALUES (1, 'ROLE_USER');
INSERT IGNORE INTO role (id, role_name)
VALUES (2, 'ROLE_ADMIN');

INSERT IGNORE INTO user(id, user_name, password, role_id)
VALUES (1, 'admin', '$2a$10$uzasPtTwxZvYToQxkwx58e1nHghtrRlSz8fGdFFDbJqDyCwssWOUa', 2);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (2, 'Emre', 'Calik', 'emreboun', 'emre.calik@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (3, 'Kaan', 'Can', 'kaancan', 'kaan.can@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (4, 'Sercan', 'Gök', 'sercangok', 'sercan.gok@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (5, 'Yusuf', 'Gökyer', 'yusufgokyer', 'yusuf.gokyer@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (6, 'Birkan', 'Yalug', 'birkanyalug', 'birkan.yalug@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (7, 'İdil', 'İsçi', 'idilisci', 'idil.isci@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (8, 'Beyza', 'Çalık', 'beyzacalik', 'beyza.calik@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (9, 'Erdem', 'Döş', 'erdomdos', 'erdem.dos@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);


INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (3, 2);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (4, 2);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (5, 2);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (6, 2);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (7, 2);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (8, 2);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (9, 2);

INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (2, 3);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (2, 6);
