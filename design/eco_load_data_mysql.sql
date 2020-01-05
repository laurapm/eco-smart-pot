SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE ALEXA_ORDER;
TRUNCATE TABLE TREATMENT_HISTORY;
TRUNCATE TABLE TREATMENTS;

INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (1, "irrigation", '[]', "Pumps water to the plant to increase its humidity.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (2, "drainage", '[]', "Prolongued humidily alongside excess of temperature produces decomposition of the plant.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (3, "fertilization", '[]', "Plants need nutrients to be healthy.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (4, "starvation", '[]', "Using too much fertilizer might poison the plant and pollute water/turf.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (5, "sunbathe", '[]', "Less light than neccessary might weaken the plan. Light should reflect on all over the plant.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (6, "isolation", '[]', "Too much light can make the plant sick. The plant should be oriented in the opposite side of where the light comes from.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (7, "warmpup", '[]', "Temperatures being too low might affect the biochemistry of the plant. Covering the plant or placing it in a warmer area solves the problem.");
INSERT INTO TREATMENTS (ID, TITLE, TREATMENT, DESCRIPTION) VALUES (8, "cooldown", '[]', "Temperatures being too high might accelerate plant's metabolism: needed more water, nutrients, etc. It could also lead to withering. Covering the plant or a cooler enviroment is advisable.");

SET FOREIGN_KEY_CHECKS = 1;
