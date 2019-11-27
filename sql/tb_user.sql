
-- Drop table

-- DROP TABLE tb_user;

CREATE TABLE tb_user (
	uid serial NOT NULL,
	account varchar(60) NOT NULL,
	pwd varchar(100) NOT NULL,
	phone varchar(50) NULL,
	age int4 NULL,
	gender int4 NULL,
	status int4 NULL,
	remark varchar(100) NULL,
	create_id int4 NULL,
	create_time timestamp NULL,
	update_id int4 NULL,
	update_time timestamp NULL,
	CONSTRAINT tb_user_pk PRIMARY KEY (uid),
	CONSTRAINT tb_user_un UNIQUE (uid)
);

INSERT INTO tb_user (uid, account, pwd, phone, age, gender, status, remark, create_id, create_time, update_id, update_time) VALUES(9, 'jack', '4QrcOUm6Wau+VuBX8g+IPg==', '15630036006', 20, 2, 1, NULL, NULL, '2019-11-07 20:35:30.781', NULL, '2019-11-07 20:51:59.740');
INSERT INTO tb_user (uid, account, pwd, phone, age, gender, status, remark, create_id, create_time, update_id, update_time) VALUES(13, 'jerry', '4QrcOUm6Wau+VuBX8g+IPg==', '13150056006', NULL, 1, 1, NULL, NULL, '2019-11-07 21:30:32.288', NULL, '2019-11-07 21:30:32.288');
INSERT INTO tb_user (uid, account, pwd, phone, age, gender, status, remark, create_id, create_time, update_id, update_time) VALUES(11, 'tom', '4QrcOUm6Wau+VuBX8g+IPg==', '18390229022', 18, 1, 2, NULL, NULL, '2019-11-07 20:38:04.069', NULL, '2019-11-07 20:38:04.069');
