SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE HUM_INT;
TRUNCATE TABLE TEMP_INT;
TRUNCATE TABLE LUM_EXT;
TRUNCATE TABLE POT_EXT;
TRUNCATE TABLE ALEXA_ORDER;
TRUNCATE TABLE TREATMENT_HISTORY;
TRUNCATE TABLE TREATMENTS;
TRUNCATE TABLE IRRIGATION;
TRUNCATE TABLE FERTILIZATION;
TRUNCATE TABLE PLANT;
TRUNCATE TABLE OWNERS;
TRUNCATE TABLE ECO_DEVICE;

INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (1, "irrigation", '[]', "Pumps water to the plant to increase its humidity.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (2, "drainage", '[]', "Prolongued humidily alongside excess of temperature produces decomposition of the plant.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (3, "fertilization", '[]', "Plants need nutrients to be healthy.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (4, "starvation", '[]', "Using too much fertilizer might poison the plant and pollute water/turf.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (5, "sunbathe", '[]', "Less light than neccessary might weaken the plan. Light should reflect on all over the plant.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (6, "isolation", '[]', "Too much light can make the plant sick. The plant should be oriented in the opposite side of where the light comes from.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (7, "warmpup", '[]', "Temperatures being too low might affect the biochemistry of the plant. Covering the plant or placing it in a warmer area solves the problem.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (8, "cooldown", '[]', "Temperatures being too high might accelerate plant's metabolism: needed more water, nutrients, etc. It could also lead to withering. Covering the plant or a cooler enviroment is advisable.");

INSERT INTO OWNERS (ID, NAME, SURNAME, COURTESY_TITLE, USERNAME, PASSWORD, EMAIL) VALUES (1, 'Pablo', 'Acereda', 'Mr.', 'pablo.acereda', SHA1('acereda'), 'pablo.acereda@edu.uah.es');
INSERT INTO OWNERS (ID, NAME, SURNAME, COURTESY_TITLE, USERNAME, PASSWORD, EMAIL) VALUES (2, 'Javier', 'Albert', 'Mr.', 'javier.albert', SHA1('albert'), 'javier.albert@uah.es');
INSERT INTO OWNERS (ID, NAME, SURNAME, COURTESY_TITLE, USERNAME, PASSWORD, EMAIL) VALUES (3, 'Ana', 'Castillo', 'Dr.', 'ana.castillo', SHA1('castillo'), 'ana.castillo@uah.es');
INSERT INTO OWNERS (ID, NAME, SURNAME, COURTESY_TITLE, USERNAME, PASSWORD, EMAIL) VALUES (4, 'Dave', 'Craciunescu', 'Mr.', 'dave.craciunescu', SHA1('craciunescu'), 'david.craciunescu@edu.uah.es');
INSERT INTO OWNERS (ID, NAME, SURNAME, COURTESY_TITLE, USERNAME, PASSWORD, EMAIL) VALUES (5, 'Pablo', 'Martinez', 'Mr.', 'pablo.martinez', SHA1('martinez'), 'pablo.marting@edu.uah.es');
INSERT INTO OWNERS (ID, NAME, SURNAME, COURTESY_TITLE, USERNAME, PASSWORD, EMAIL) VALUES (6, 'Laura', 'Perez', 'Ms.', 'laura.perez', SHA1('perez'), 'l.perezm@edu.uah.es');


SET FOREIGN_KEY_CHECKS = 1;
