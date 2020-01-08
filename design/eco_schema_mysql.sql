-- MySQL Script generated by MySQL Workbench
-- Wed Jan  8 04:17:55 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema eco
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `eco` ;

-- -----------------------------------------------------
-- Schema eco
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `eco` DEFAULT CHARACTER SET utf8 ;
USE `eco` ;

-- -----------------------------------------------------
-- Table `eco`.`TREATMENTS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`TREATMENTS` ;

CREATE TABLE IF NOT EXISTS `eco`.`TREATMENTS` (
  `ID` INT NOT NULL,
  `TITLE` VARCHAR(20) NULL,
  `TREATMENT` JSON NULL,
  `DESCRIPTION` VARCHAR(200) NULL,
  PRIMARY KEY (`ID`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`ALEXA_ORDER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`ALEXA_ORDER` ;

CREATE TABLE IF NOT EXISTS `eco`.`ALEXA_ORDER` (
  `ID` INT NOT NULL,
  `TREATMENTS_ID` INT NOT NULL,
  `TOKEN` VARCHAR(100) NULL,
  `DATE` TIMESTAMP NULL,
  PRIMARY KEY (`ID`, `TREATMENTS_ID`),
  INDEX `fk_ALEXA_ORDER_TREATMENTS_idx` (`TREATMENTS_ID` ASC) VISIBLE,
  CONSTRAINT `fk_ALEXA_ORDER_TREATMENTS`
    FOREIGN KEY (`TREATMENTS_ID`)
    REFERENCES `eco`.`TREATMENTS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`OWNERS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`OWNERS` ;

CREATE TABLE IF NOT EXISTS `eco`.`OWNERS` (
  `ID` INT NOT NULL,
  `NAME` VARCHAR(30) NULL,
  `SURNAME` VARCHAR(30) NULL,
  `COURTESY_TITLE` VARCHAR(5) NULL,
  `USERNAME` VARCHAR(30) NULL,
  `PASSWORD` VARCHAR(255) NULL,
  `EMAIL` VARCHAR(255) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `USERNAME_UNIQUE` (`USERNAME` ASC) VISIBLE,
  UNIQUE INDEX `EMAIL_UNIQUE` (`EMAIL` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`ECO_DEVICE`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`ECO_DEVICE` ;

CREATE TABLE IF NOT EXISTS `eco`.`ECO_DEVICE` (
  `ID` INT NOT NULL,
  `OWNERS_ID` INT NOT NULL,
  `FIRMWARE_UPDATE` VARCHAR(12) NULL,
  PRIMARY KEY (`ID`),
  INDEX `fk_ECO_DEVICE_OWNERS1_idx` (`OWNERS_ID` ASC) VISIBLE,
  CONSTRAINT `fk_ECO_DEVICE_OWNERS1`
    FOREIGN KEY (`OWNERS_ID`)
    REFERENCES `eco`.`OWNERS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`FAMILY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`FAMILY` ;

CREATE TABLE IF NOT EXISTS `eco`.`FAMILY` (
  `ID` INT NOT NULL,
  `NAME` VARCHAR(45) NULL,
  `DESCRIPTION` VARCHAR(255) NULL,
  PRIMARY KEY (`ID`),
  UNIQUE INDEX `NAME_UNIQUE` (`NAME` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`PLANT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`PLANT` ;

CREATE TABLE IF NOT EXISTS `eco`.`PLANT` (
  `ID` INT NOT NULL,
  `COMMON_NAME` VARCHAR(45) NULL,
  `SCIENTIFIC_NAME` VARCHAR(45) NULL,
  `FAMILY_ID` INT NOT NULL,
  `MAX_TEMP` FLOAT NULL,
  `MIN_TEMP` FLOAT NULL,
  `MAX_HUM` FLOAT NULL,
  `MIN_HUM` FLOAT NULL,
  PRIMARY KEY (`ID`, `FAMILY_ID`),
  INDEX `fk_PLANT_FAMILY1_idx` (`FAMILY_ID` ASC) VISIBLE,
  CONSTRAINT `fk_PLANT_FAMILY1`
    FOREIGN KEY (`FAMILY_ID`)
    REFERENCES `eco`.`FAMILY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`TREATMENT_HISTORY`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`TREATMENT_HISTORY` ;

CREATE TABLE IF NOT EXISTS `eco`.`TREATMENT_HISTORY` (
  `ID` INT NOT NULL,
  `ECO_DEVICE_ID` INT NOT NULL,
  `PLANT_ID` INT NOT NULL,
  `TREATMENTS_ID` INT NOT NULL,
  `DATE` TIMESTAMP NULL,
  PRIMARY KEY (`ID`, `ECO_DEVICE_ID`, `PLANT_ID`, `TREATMENTS_ID`),
  INDEX `fk_TREATMENT_HISTORY_TREATMENTS1_idx` (`TREATMENTS_ID` ASC) VISIBLE,
  INDEX `fk_TREATMENT_HISTORY_ECO_DEVICE1_idx` (`ECO_DEVICE_ID` ASC) VISIBLE,
  INDEX `fk_TREATMENT_HISTORY_PLANT1_idx` (`PLANT_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TREATMENT_HISTORY_TREATMENTS1`
    FOREIGN KEY (`TREATMENTS_ID`)
    REFERENCES `eco`.`TREATMENTS` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TREATMENT_HISTORY_ECO_DEVICE1`
    FOREIGN KEY (`ECO_DEVICE_ID`)
    REFERENCES `eco`.`ECO_DEVICE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TREATMENT_HISTORY_PLANT1`
    FOREIGN KEY (`PLANT_ID`)
    REFERENCES `eco`.`PLANT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`TEMP_INT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`TEMP_INT` ;

CREATE TABLE IF NOT EXISTS `eco`.`TEMP_INT` (
  `ID` INT NOT NULL,
  `ECO_DEVICE_ID` INT NOT NULL,
  `PLANT_ID` INT NOT NULL,
  `TEMPERATURE` FLOAT NULL,
  `MEASURE_TIME` TIMESTAMP NULL,
  PRIMARY KEY (`ID`, `ECO_DEVICE_ID`, `PLANT_ID`),
  INDEX `fk_TEMP_EXT_PLANT1_idx` (`PLANT_ID` ASC) VISIBLE,
  INDEX `fk_TEMP_EXT_ECO_DEVICE1_idx` (`ECO_DEVICE_ID` ASC) VISIBLE,
  CONSTRAINT `fk_TEMP_EXT_PLANT1`
    FOREIGN KEY (`PLANT_ID`)
    REFERENCES `eco`.`PLANT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_TEMP_EXT_ECO_DEVICE1`
    FOREIGN KEY (`ECO_DEVICE_ID`)
    REFERENCES `eco`.`ECO_DEVICE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`LUM_EXT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`LUM_EXT` ;

CREATE TABLE IF NOT EXISTS `eco`.`LUM_EXT` (
  `ID` INT NOT NULL,
  `ECO_DEVICE_ID` INT NOT NULL,
  `PLANT_ID` INT NOT NULL,
  `LUMINOSITY` FLOAT NULL,
  `MEASURE_TIME` TIMESTAMP NULL,
  PRIMARY KEY (`ID`, `ECO_DEVICE_ID`, `PLANT_ID`),
  INDEX `fk_LUM_EXT_PLANT1_idx` (`PLANT_ID` ASC) VISIBLE,
  INDEX `fk_LUM_EXT_ECO_DEVICE1_idx` (`ECO_DEVICE_ID` ASC) VISIBLE,
  CONSTRAINT `fk_LUM_EXT_PLANT1`
    FOREIGN KEY (`PLANT_ID`)
    REFERENCES `eco`.`PLANT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_LUM_EXT_ECO_DEVICE1`
    FOREIGN KEY (`ECO_DEVICE_ID`)
    REFERENCES `eco`.`ECO_DEVICE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`HUM_INT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`HUM_INT` ;

CREATE TABLE IF NOT EXISTS `eco`.`HUM_INT` (
  `ID` INT NOT NULL,
  `ECO_DEVICE_ID` INT NOT NULL,
  `PLANT_ID` INT NOT NULL,
  `HUMIDITY` FLOAT NULL,
  `MEASURE_TIME` TIMESTAMP NULL,
  PRIMARY KEY (`ID`, `ECO_DEVICE_ID`, `PLANT_ID`),
  INDEX `fk_HUM_EXT_PLANT1_idx` (`PLANT_ID` ASC) VISIBLE,
  INDEX `fk_HUM_EXT_ECO_DEVICE1_idx` (`ECO_DEVICE_ID` ASC) VISIBLE,
  CONSTRAINT `fk_HUM_EXT_PLANT1`
    FOREIGN KEY (`PLANT_ID`)
    REFERENCES `eco`.`PLANT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_HUM_EXT_ECO_DEVICE1`
    FOREIGN KEY (`ECO_DEVICE_ID`)
    REFERENCES `eco`.`ECO_DEVICE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`POT_EXT`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`POT_EXT` ;

CREATE TABLE IF NOT EXISTS `eco`.`POT_EXT` (
  `ID` INT NOT NULL,
  `ECO_DEVICE_ID` INT NOT NULL,
  `PLANT_ID` INT NOT NULL,
  `TEMPERATURE` FLOAT NULL,
  `HUMIDITY` FLOAT NULL,
  `MEASURE_TIME` TIMESTAMP NULL,
  PRIMARY KEY (`ID`, `ECO_DEVICE_ID`, `PLANT_ID`),
  INDEX `fk_POT_INT_PLANT1_idx` (`PLANT_ID` ASC) VISIBLE,
  INDEX `fk_POT_INT_ECO_DEVICE1_idx` (`ECO_DEVICE_ID` ASC) VISIBLE,
  CONSTRAINT `fk_POT_INT_PLANT1`
    FOREIGN KEY (`PLANT_ID`)
    REFERENCES `eco`.`PLANT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_POT_INT_ECO_DEVICE1`
    FOREIGN KEY (`ECO_DEVICE_ID`)
    REFERENCES `eco`.`ECO_DEVICE` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`IRRIGATION_FACTORS`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`IRRIGATION_FACTORS` ;

CREATE TABLE IF NOT EXISTS `eco`.`IRRIGATION_FACTORS` (
  `ID` INT NOT NULL,
  `FAMILY_ID` INT NOT NULL,
  `TEMP_EXT_HIGH` FLOAT NULL,
  `TEMP_EXT_MOD` FLOAT NULL,
  `TEMP_EXT_LOW` FLOAT NULL,
  `HUM_EXT_HIGH` FLOAT NULL,
  `HUM_EXT_MOD` FLOAT NULL,
  `HUM_EXT_LOW` FLOAT NULL,
  `DAY` FLOAT NULL,
  `NIGHT` FLOAT NULL,
  PRIMARY KEY (`ID`, `FAMILY_ID`),
  INDEX `fk_IRRIGATION_PARAMETERS_FAMILY1_idx` (`FAMILY_ID` ASC) VISIBLE,
  CONSTRAINT `fk_IRRIGATION_PARAMETERS_FAMILY1`
    FOREIGN KEY (`FAMILY_ID`)
    REFERENCES `eco`.`FAMILY` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `eco`.`FERTILIZER`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `eco`.`FERTILIZER` ;

CREATE TABLE IF NOT EXISTS `eco`.`FERTILIZER` (
  `ID` INT NOT NULL,
  `PLANT_ID` INT NOT NULL,
  `FERTILIZER_PERIOD_DAYS` FLOAT NULL,
  `FERTILIZER_TYPE` VARCHAR(45) NULL,
  PRIMARY KEY (`ID`, `PLANT_ID`),
  INDEX `fk_FERTILIZER_PLANT1_idx` (`PLANT_ID` ASC) VISIBLE,
  CONSTRAINT `fk_FERTILIZER_PLANT1`
    FOREIGN KEY (`PLANT_ID`)
    REFERENCES `eco`.`PLANT` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
