Для запуска приложения необходимо:
1) Установить Java 21
https://www.oracle.com/java/technologies/downloads/#java21

2) Установить MySQL
https://dev.mysql.com/downloads/mysql/

3) Создать базу данных и таблицу coefficients, используя следующий скрипт:
CREATE DATABASE session_nov;
USE DATABASE session_nov;

CREATE TABLE IF NOT EXISTS `session_nov`.`coefficients` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `matherial` VARCHAR(150) NOT NULL,
  `b0` DOUBLE NOT NULL,
  `b1` DOUBLE NOT NULL,
  `b2` DOUBLE NOT NULL,
  `b3` DOUBLE NOT NULL,
  `b4` DOUBLE NOT NULL,
  `b5` DOUBLE NOT NULL,
  `b6` DOUBLE NOT NULL,
  `b7` DOUBLE NOT NULL,
  `b8` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)
ENGINE = InnoDB;

4) Добавить в базу данных запись, используя следующий скрипт:
insert into coefficients(matherial, b0, b1, b2, b3, b4, b5, b6, b7, b8) 
values('Порошковая прессовка, состоящая из карбида вольфрама и никеля', 
14.64, 0.01683, 0.1033, 0.00012, -0.00014, -0.000034, -0.00000097, -0.00000004, 0.0000000003);

5) Запустить командную строку 
6) С помощью команды cd перейти в корень проекта
7) Выполнить команду 
java --module-path "javafx/lib" --add-modules javafx.controls,javafx.fxml,javafx.base -jar App.jar