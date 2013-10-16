SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

CREATE SCHEMA IF NOT EXISTS `acs` DEFAULT CHARACTER SET utf8 ;
USE `acs` ;

-- -----------------------------------------------------
-- Table `acs`.`t_acs_user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_user` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_user` (
  `user_name` VARCHAR(50) NOT NULL COMMENT '用户名' ,
  `password` VARCHAR(100) NOT NULL COMMENT '用户密码' ,
  `dt_create_time` DATETIME NOT NULL COMMENT '用户创建时间' ,
  `dt_last_login_time` DATETIME NULL COMMENT '最后登录时间' ,
  `email` VARCHAR(255) NULL COMMENT '用户邮箱' ,
  `phone` VARCHAR(255) NULL COMMENT '用户手机号码' ,
  `user_type` INT(1) NOT NULL DEFAULT 0 COMMENT '用户类别：\n0、普通用户\n1、专家\n2、高手' ,
  `user_status` INT(1) NOT NULL DEFAULT 0 COMMENT '用户状态：\n0：正常使用\n1：屏蔽\n2：删除' ,
  `register_ip` VARCHAR(50) NULL COMMENT '注册时的IP' ,
  PRIMARY KEY (`user_name`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '访问控制用户信息';


-- -----------------------------------------------------
-- Table `acs`.`t_acs_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_role` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_role` (
  `role_id` INT NOT NULL AUTO_INCREMENT COMMENT '角色编号' ,
  `name` VARCHAR(50) NOT NULL COMMENT '角色名称' ,
  `desc` VARCHAR(100) NULL COMMENT '角色描述' ,
  PRIMARY KEY (`role_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '角色';

CREATE UNIQUE INDEX `ix_acs_role_01` ON `acs`.`t_acs_role` (`name` ASC) ;


-- -----------------------------------------------------
-- Table `acs`.`t_acs_resource`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_resource` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_resource` (
  `resource_id` INT NOT NULL AUTO_INCREMENT COMMENT 'URL资源编号' ,
  `url` VARCHAR(500) NOT NULL COMMENT 'URL资源，以ant目录方式保存' ,
  `desc` VARCHAR(100) NULL COMMENT 'URL资源描述' ,
  PRIMARY KEY (`resource_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '系统需要受保护的URL资源';


-- -----------------------------------------------------
-- Table `acs`.`t_acs_user_role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_user_role` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_user_role` (
  `user_role_id` INT NOT NULL AUTO_INCREMENT ,
  `role_id` INT NOT NULL ,
  `user_name` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`user_role_id`) ,
  CONSTRAINT `fk_acs_user_role_01`
    FOREIGN KEY (`role_id` )
    REFERENCES `acs`.`t_acs_role` (`role_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_acs_user_role_02`
    FOREIGN KEY (`user_name` )
    REFERENCES `acs`.`t_acs_user` (`user_name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户角色关联表';

CREATE INDEX `ix_acs_user_role_01` ON `acs`.`t_acs_user_role` (`role_id` ASC) ;

CREATE INDEX `ix_acs_user_role_02` ON `acs`.`t_acs_user_role` (`user_name` ASC) ;

CREATE UNIQUE INDEX `ix_acs_user_role_03` ON `acs`.`t_acs_user_role` (`role_id` ASC, `user_name` ASC) ;


-- -----------------------------------------------------
-- Table `acs`.`t_acs_role_resource`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_role_resource` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_role_resource` (
  `role_resource_id` INT NOT NULL AUTO_INCREMENT ,
  `role_id` INT NOT NULL ,
  `resource_id` INT NOT NULL ,
  PRIMARY KEY (`role_resource_id`) ,
  CONSTRAINT `fk_acs_role_resource_01`
    FOREIGN KEY (`role_id` )
    REFERENCES `acs`.`t_acs_role` (`role_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_acs_role_resource_02`
    FOREIGN KEY (`resource_id` )
    REFERENCES `acs`.`t_acs_resource` (`resource_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '角色URL资源关系表';

CREATE INDEX `ix_acs_role_resource_01` ON `acs`.`t_acs_role_resource` (`role_id` ASC) ;

CREATE INDEX `ix_acs_role_resource_02` ON `acs`.`t_acs_role_resource` (`resource_id` ASC) ;

CREATE UNIQUE INDEX `ix_acs_role_resource_03` ON `acs`.`t_acs_role_resource` (`role_id` ASC, `resource_id` ASC) ;


-- -----------------------------------------------------
-- Table `acs`.`t_acs_permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_permission` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_permission` (
  `permission_id` INT NOT NULL AUTO_INCREMENT COMMENT '授权编号' ,
  `name` VARCHAR(100) NOT NULL COMMENT '授权名称，为系统定义的常量' ,
  PRIMARY KEY (`permission_id`) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '系统需授权执行的功能';

CREATE UNIQUE INDEX `ix_acs_permission` ON `acs`.`t_acs_permission` (`name` ASC) ;


-- -----------------------------------------------------
-- Table `acs`.`t_acs_role_permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_role_permission` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_role_permission` (
  `role_permission_id` INT NOT NULL AUTO_INCREMENT ,
  `role_id` INT NOT NULL ,
  `permission_id` INT NOT NULL ,
  PRIMARY KEY (`role_permission_id`) ,
  CONSTRAINT `fk_acs_role_permission_01`
    FOREIGN KEY (`role_id` )
    REFERENCES `acs`.`t_acs_role` (`role_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_acs_role_permission_02`
    FOREIGN KEY (`permission_id` )
    REFERENCES `acs`.`t_acs_permission` (`permission_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '角色权限关系表';

CREATE INDEX `ix_acs_role_permission_01` ON `acs`.`t_acs_role_permission` (`role_id` ASC) ;

CREATE INDEX `ix_acs_role_permission_02` ON `acs`.`t_acs_role_permission` (`permission_id` ASC) ;

CREATE UNIQUE INDEX `ix_acs_role_permission_03` ON `acs`.`t_acs_role_permission` (`role_id` ASC, `permission_id` ASC) ;


-- -----------------------------------------------------
-- Table `acs`.`t_acs_user_block_permission`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_user_block_permission` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_user_block_permission` (
  `user_block_permission_id` INT NOT NULL AUTO_INCREMENT ,
  `permission_id` INT NOT NULL ,
  `user_name` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`user_block_permission_id`) ,
  CONSTRAINT `fk_acs_user_block_permission_02`
    FOREIGN KEY (`permission_id` )
    REFERENCES `acs`.`t_acs_permission` (`permission_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_acs_user_block_permission_01`
    FOREIGN KEY (`user_name` )
    REFERENCES `acs`.`t_acs_user` (`user_name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户被阻止权限列表';

CREATE INDEX `ix_acs_user_block_permission_02` ON `acs`.`t_acs_user_block_permission` (`permission_id` ASC) ;

CREATE UNIQUE INDEX `ix_acs_user_block_permission_03` ON `acs`.`t_acs_user_block_permission` (`permission_id` ASC, `user_name` ASC) ;

CREATE INDEX `ix_acs_user_block_permission_01` ON `acs`.`t_acs_user_block_permission` (`user_name` ASC) ;


-- -----------------------------------------------------
-- Table `acs`.`t_acs_user_block_resource`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_user_block_resource` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_user_block_resource` (
  `user_block_resource_id` INT NOT NULL AUTO_INCREMENT ,
  `resource_id` INT NOT NULL ,
  `user_name` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`user_block_resource_id`) ,
  CONSTRAINT `fk_acs_user_block_resource_01`
    FOREIGN KEY (`resource_id` )
    REFERENCES `acs`.`t_acs_resource` (`resource_id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_acs_user_block_resource_02`
    FOREIGN KEY (`user_name` )
    REFERENCES `acs`.`t_acs_user` (`user_name` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COMMENT = '用户被阻止的URL权限';

CREATE INDEX `ix_acs_user_block_resource_01` ON `acs`.`t_acs_user_block_resource` (`resource_id` ASC) ;

CREATE INDEX `ix_acs_user_block_resource_02` ON `acs`.`t_acs_user_block_resource` (`user_name` ASC) ;

CREATE UNIQUE INDEX `ix_acs_user_block_resource_03` ON `acs`.`t_acs_user_block_resource` (`resource_id` ASC, `user_name` ASC) ;


-- -----------------------------------------------------
-- Table `acs`.`t_acs_user_login_log`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `acs`.`t_acs_user_login_log` ;

CREATE  TABLE IF NOT EXISTS `acs`.`t_acs_user_login_log` (
  `log_id` INT NOT NULL AUTO_INCREMENT ,
  `user_name` VARCHAR(50) NULL ,
  `login_ip` VARCHAR(50) NULL ,
  `login_cookie` VARCHAR(100) NULL ,
  `login_date` DATETIME NULL ,
  PRIMARY KEY (`log_id`) )
ENGINE = InnoDB;

CREATE INDEX `IX_ACS_LOGIN_LOG_USERNAME` ON `acs`.`t_acs_user_login_log` (`user_name` ASC) ;

CREATE INDEX `IX_ACS_LOGIN_LOG_IP` ON `acs`.`t_acs_user_login_log` (`login_ip` ASC) ;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
