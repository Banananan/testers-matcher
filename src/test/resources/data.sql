DROP TABLE bug_device;
DROP TABLE bug;
DROP TABLE tester_device;
DROP TABLE device;
DROP TABLE tester;

CREATE TABLE bug (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  tester_id BIGINT
);

CREATE TABLE device (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  description VARCHAR(255)
);

CREATE TABLE bug_device (
  bug_id BIGINT,
  device_id BIGINT
);

CREATE TABLE tester (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  first_name VARCHAR(255),
  last_name VARCHAR(255),
  country CHAR(2),
  last_login TIMESTAMP
);

CREATE TABLE tester_device (
  tester_id BIGINT,
  device_id BIGINT
);
