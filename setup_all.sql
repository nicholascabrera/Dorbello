DROP DATABASE times_db;
CREATE DATABASE IF NOT EXISTS times_db;
USE times_db;
CREATE TABLE IF NOT EXISTS t_master (OUID VARCHAR(128), GRUID VARCHAR(128), CUID VARCHAR(128), PUID VARCHAR(128));
CREATE TABLE IF NOT EXISTS t_org (OUID VARCHAR(128), ORG_NAME VARCHAR(128));
CREATE TABLE IF NOT EXISTS t_group (GRUID VARCHAR(128), GROUP_NAME VARCHAR(128));
CREATE TABLE IF NOT EXISTS t_child (CUID VARCHAR(128), CHILD_NAME VARCHAR(128));
CREATE TABLE IF NOT EXISTS t_child_dynamic (CUID VARCHAR(128), LOCAT VARCHAR(128), APT VARCHAR(5));
CREATE TABLE IF NOT EXISTS t_parent (PUID VARCHAR(128), PARENT_NAME VARCHAR(128));
CREATE TABLE IF NOT EXISTS t_eta (CUID VARCHAR(128), PUID VARCHAR(128), ETA INT);

INSERT INTO t_master VALUES ('0000', '0000', '0000', '0000');     -- creating a suite of test values at some organization
INSERT INTO t_org VALUES ('0000', 'Example Organization');
INSERT INTO t_group VALUES ('0000', 'Example Group');
INSERT INTO t_child VALUES ('0000', 'Example Child Name');
INSERT INTO t_child_dynamic VALUES ('0000', 'Example Location', '00:00');
INSERT INTO t_parent VALUES ('0000', 'Example Parent Name');
INSERT INTO t_eta VALUES ('0000', '0000', '10');

CREATE USER IF NOT EXISTS 'userv'@'localhost' IDENTIFIED BY 'dfCa#uFcF8W*o&jG';

GRANT SELECT ON times_db.t_master TO 'userv'@'localhost';
GRANT INSERT ON times_db.t_master TO 'userv'@'localhost';
GRANT UPDATE ON times_db.t_master TO 'userv'@'localhost';

GRANT SELECT ON times_db.t_org TO 'userv'@'localhost';
GRANT INSERT ON times_db.t_org TO 'userv'@'localhost';
GRANT UPDATE ON times_db.t_org TO 'userv'@'localhost';

GRANT SELECT ON times_db.t_group TO 'userv'@'localhost';
GRANT INSERT ON times_db.t_group TO 'userv'@'localhost';
GRANT UPDATE ON times_db.t_group TO 'userv'@'localhost';

GRANT SELECT ON times_db.t_child TO 'userv'@'localhost';
GRANT INSERT ON times_db.t_child TO 'userv'@'localhost';
GRANT UPDATE ON times_db.t_child TO 'userv'@'localhost';

GRANT SELECT ON times_db.t_child_dynamic TO 'userv'@'localhost';
GRANT INSERT ON times_db.t_child_dynamic TO 'userv'@'localhost';
GRANT UPDATE ON times_db.t_child_dynamic TO 'userv'@'localhost';

GRANT SELECT ON times_db.t_parent TO 'userv'@'localhost';
GRANT INSERT ON times_db.t_parent TO 'userv'@'localhost';
GRANT UPDATE ON times_db.t_parent TO 'userv'@'localhost';

GRANT SELECT ON times_db.t_eta TO 'userv'@'localhost';
GRANT INSERT ON times_db.t_eta TO 'userv'@'localhost';
GRANT UPDATE ON times_db.t_eta TO 'userv'@'localhost';