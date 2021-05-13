INSERT IGNORE INTO role (id, role_name)
VALUES (1, 'ROLE_USER');
INSERT IGNORE INTO role (id, role_name)
VALUES (2, 'ROLE_ADMIN');

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (1, 'Emre', 'Calik', 'emreboun', 'emre.calik@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (2, 'Kaan', 'Can', 'kaancan', 'kaan.can@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (3, 'Sercan', 'Gök', 'sercangok', 'sercan.gok@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (4, 'Yusuf', 'Gökyer', 'yusufgokyer', 'yusuf.gokyer@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (5, 'Birkan', 'Yalug', 'birkanyalug', 'birkan.yalug@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (6, 'İdil', 'İsçi', 'idilisci', 'idil.isci@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (7, 'Beyza', 'Çalık', 'beyzacalik', 'beyza.calik@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id)
VALUES (8, 'Erdem', 'Döş', 'erdomdos', 'erdem.dos@boun.edu.tr',
        '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);


INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (2, 1);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (3, 1);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (4, 1);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (5, 1);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (6, 1);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (7, 1);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (8, 1);

INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (1, 2);
INSERT IGNORE INTO user_followee (user_id, followee_id)
VALUES (1, 5);
