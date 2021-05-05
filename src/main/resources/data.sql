INSERT IGNORE INTO role (id, role_name) VALUES (1, 'ROLE_USER');
INSERT IGNORE INTO role (id, role_name) VALUES (2, 'ROLE_ADMIN');

INSERT IGNORE INTO user (id, first_name, last_name, user_name, email, password, role_id) VALUES (1, 'Emre', 'Calik', 'emreboun', 'emre.calik@boun.edu.tr', '$2a$10$m23LwS3k720285GrinmHK.HLdiqrgs3ttrSAgPu1hyj.tOOQmEY6O', 1);