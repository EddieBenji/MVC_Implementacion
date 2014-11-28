-- MySQL Script generated by MySQL Workbench
-- 28/11/2014 11:31:08
-- Model: New Model    Version: 1.0
SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mvcdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mvcdb` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `mvcdb` ;

-- -----------------------------------------------------
-- Table `mvcdb`.`usuario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mvcdb`.`usuario` (
  `usuario_id` INT NOT NULL,
  `nombre` VARCHAR(80) NOT NULL,
  `password` VARCHAR(20) NOT NULL,
  `rol` VARCHAR(20) NOT NULL,
  PRIMARY KEY (`usuario_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mvcdb`.`candidato`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mvcdb`.`candidato` (
  `candidato_id` INT NOT NULL,
  `nombre` VARCHAR(80) NOT NULL,
  `num_votos` INT NOT NULL,
  PRIMARY KEY (`candidato_id`))
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
