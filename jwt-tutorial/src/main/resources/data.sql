INSERT INTO user (user_id, username, password, nickname, activated)
VALUES (1, 'admin', '$2a$08$lDnHPz7eUkSi6ao14Twuau08mzhWrL4kyZGGU5xfiGALO/Vxd5DOi', 'admin', 1);

INSERT INTO authority (authority_name)
VALUES ('ROLE_USER');
INSERT INTO authority (authority_name)
VALUES ('ROLE_ADMIN');

INSERT INTO user_authority (user_id, authority_name)
VALUES (1, 'ROLE_USER');
INSERT INTO user_authority (user_id, authority_name)
VALUES (1, 'ROLE_ADMIN');