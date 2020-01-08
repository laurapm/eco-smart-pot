SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE HUM_INT;
TRUNCATE TABLE TEMP_INT;
TRUNCATE TABLE LUM_EXT;
TRUNCATE TABLE POT_EXT;
TRUNCATE TABLE ALEXA_ORDER;
TRUNCATE TABLE TREATMENT_HISTORY;
TRUNCATE TABLE TREATMENTS;
TRUNCATE TABLE FERTILIZER;
TRUNCATE TABLE IRRIGATION_FACTORS;
TRUNCATE TABLE PLANT;
TRUNCATE TABLE FAMILY;
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

INSERT INTO ECO_DEVICE (ID, OWNERS_ID, FIRMWARE_UPDATE) VALUES (1, 6, '1.0');

INSERT INTO FAMILY (ID, NAME, DESCRIPTION) VALUES (1, 'Asteraceae', 'La familia se caracteriza por presentar las flores dispuestas en una inflorescencia compuesta denominada capítulo la cual se halla rodeada de una o más filas de brácteas (involucro).');
INSERT INTO FAMILY (ID, NAME, DESCRIPTION) VALUES (2, 'Crassulaceae', 'Son plantas herbáceas, algunas subarbustivas y relativamente pocas arbóreas o acuáticas.');
INSERT INTO FAMILY (ID, NAME, DESCRIPTION) VALUES (3, 'Paeoniaceae', 'Plantas herbáceas perennes o arbustivas. Hojas pinnadas alternas, de color verde claro u oscuro, a veces plateadas, más o menos divididas, sin estípulas. Flores solitarias y terminales, algunas olorosas, hermafroditas, 3-6 sépalos libres.');
INSERT INTO FAMILY (ID, NAME, DESCRIPTION) VALUES (4, 'Rosaceae', 'Incluye la mayor parte de las especies de frutas de consumo masivo: manzana, pera, cereza, fresa, almendra, frambuesa, etc. También incluye especies ornamentales como las rosas.');
INSERT INTO FAMILY (ID, NAME, DESCRIPTION) VALUES (5, 'Rubiaceae', 'Son árboles, arbustos, sufrútices, hierbas, enredaderas o lianas, de hábitos terrestres o raras veces epífitas, a veces con rafidios.');
INSERT INTO FAMILY (ID, NAME, DESCRIPTION) VALUES (6, 'Rutacaceae', 'Plantas leñosas o raramente herbáceas, provistas de glándulas secretoras oleíferas. Hojas alternas u opuestas, simples o compuestas, sin estípulas, a veces con espinas axilares. Flores generalmente hernafroditas.');
INSERT INTO FAMILY (ID, NAME, DESCRIPTION) VALUES (7, 'Scrophulariaceae', 'Principalmente son plantas herbáceas, a veces leñosas y sólo algunas trepadoras. De hojas simples, enteras o dentadas y sin estípulas. Las flores suelen ser de disposición variada, hermafroditas e irregulares. Fruto en cápsula.');

INSERT INTO PLANT (ID, COMMON_NAME, SCIENTIFIC_NAME, FAMILY_ID, MAX_TEMP, MIN_TEMP, MAX_HUM, MIN_HUM) VALUES (1, 'Monica Belluci', '', 3, 0, 0, 0, 0);
INSERT INTO PLANT (ID, COMMON_NAME, SCIENTIFIC_NAME, FAMILY_ID, MAX_TEMP, MIN_TEMP, MAX_HUM, MIN_HUM) VALUES (2, 'Peonia', 'Paeoniaceae', 4, 0, 0, 0, 0);
INSERT INTO PLANT (ID, COMMON_NAME, SCIENTIFIC_NAME, FAMILY_ID, MAX_TEMP, MIN_TEMP, MAX_HUM, MIN_HUM) VALUES (3, 'Limonero de Luna', '', 5, 0, 0, 0, 0);
INSERT INTO PLANT (ID, COMMON_NAME, SCIENTIFIC_NAME, FAMILY_ID, MAX_TEMP, MIN_TEMP, MAX_HUM, MIN_HUM) VALUES (4, 'Margarita Africana', 'Gerbera Jamesonii', 2, 0, 0, 0, 0);
INSERT INTO PLANT (ID, COMMON_NAME, SCIENTIFIC_NAME, FAMILY_ID, MAX_TEMP, MIN_TEMP, MAX_HUM, MIN_HUM) VALUES (5, 'Jazmín de la India', 'Gardenia Jasminoide', 7, 0, 0, 0, 0);
INSERT INTO PLANT (ID, COMMON_NAME, SCIENTIFIC_NAME, FAMILY_ID, MAX_TEMP, MIN_TEMP, MAX_HUM, MIN_HUM) VALUES (6, 'Kalanchoe', 'Kalanchoe Blossfeldiana', 1, 28, 10, 40, 80);
INSERT INTO PLANT (ID, COMMON_NAME, SCIENTIFIC_NAME, FAMILY_ID, MAX_TEMP, MIN_TEMP, MAX_HUM, MIN_HUM) VALUES (7, 'Dragonaria', 'Antirrhinum Majus', 6, 0, 0, 0, 0);

INSERT INTO IRRIGATION_FACTORS (ID, FAMILY_ID, TEMP_EXT_HIGH, TEMP_EXT_MOD, TEMP_EXT_LOW, HUM_EXT_HIGH, HUM_EXT_MOD, HUM_EXT_LOW, DAY, NIGHT) VALUES (1, 1, 0.8, 1, 1.1, 0.95, 1, 1.05, 1, 1.1);

INSERT INTO FERTILIZER (ID, PLANT_ID, FERTILIZER_PERIOD_DAYS, FERTILIZER_TYPE) VALUES (1, 7, 30, '20-20-20');

SET FOREIGN_KEY_CHECKS = 1;
