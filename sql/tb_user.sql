
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
	CONSTRAINT tb_user_account_key UNIQUE (account),
	CONSTRAINT tb_user_phone_key UNIQUE (phone),
	CONSTRAINT tb_user_pk PRIMARY KEY (uid),
	CONSTRAINT tb_user_un UNIQUE (uid)
);
